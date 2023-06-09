package com.cqut.czb.bn.service.impl.vehicleServiceImpl;

import com.cqut.czb.bn.dao.mapper.AppRouterMapper;
import com.cqut.czb.bn.dao.mapper.FileMapperExtra;
import com.cqut.czb.bn.dao.mapper.MyWalletMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.*;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.BalanceAndInfoIdDTO;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.IncomeLogDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.ImageInfoDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.TuiKuanDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.VehicleCleanOrderDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.VehicleOrderManageDTO;
import com.cqut.czb.bn.entity.entity.File;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.vehicleService.CleanRider;
import com.cqut.czb.bn.entity.entity.vehicleService.RemotePush;
import com.cqut.czb.bn.entity.entity.vehicleService.VehicleCleanOrder;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.impl.AnnouncementServiceImpl;
import com.cqut.czb.bn.service.vehicleService.ServerOrderService;
import com.cqut.czb.bn.util.file.FileUploadUtil;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class ServerOrderServiceImpl implements ServerOrderService {
    @Autowired
    FileMapperExtra fileMapperExtra;

    @Autowired
    VehicleCleanOrderMapperExtra mapperExtra;

    @Autowired
    VehicleCleanOrderMapper mapper;

    @Autowired
    private MyWalletMapperExtra myWalletMapper;

    @Autowired
    AnnouncementServiceImpl announcementServiceImpl;

    @Autowired
    RiderServiceImpl riderServiceImpl;

    @Autowired
    CleanRiderMapperExtra cleanRiderMapper;

    @Autowired
    RemotePushMapperExtra remotePushMapperExtra;

    @Autowired
    RemotePushNoticeMapperExtra remotePushNoticeMapperExtra;

    @Autowired
    AppRouterMapper appRouterMapper;

    @Autowired
    VehicleCleanOrderMapperExtra vehicleCleanOrderMapperExtra;

    @Override
    public JSONResult distribute(VehicleOrderManageDTO manageDTO) {
        // 设置查询骑手的条件实体
        CleanRider rider = new CleanRider();
        rider.setRiderName(manageDTO.getRiderName());
        rider.setContactNumber(manageDTO.getRiderPhone());

        // 查询骑手
        CleanRider cleanRider =mapperExtra.getCleanRider(rider);
        // 判断骑手是否存在
        if (null != cleanRider) {
            // 判断骑手的状态
            if(cleanRider.getStatus() == 1) {
                return new JSONResult("骑手正在路上", 500);
            } else if (cleanRider.getStatus() == 2) {
                return new JSONResult("骑手正在服务", 500);
            } else {
                // 修改订单的实体
                VehicleCleanOrder cleanOrder = new VehicleCleanOrder();
                cleanOrder.setServerOrderId(manageDTO.getServerOrderId());
                cleanOrder.setRiderId(cleanRider.getRiderId());
                Byte byteStatus = 1;
                cleanOrder.setProcessStatus(byteStatus);
                // 分配骑手
                if (mapper.updateByPrimaryKeySelective(cleanOrder) > 0) {
                    // 分配骑手成功后，改变骑手状态
                    if (mapperExtra.updateRiderStatus(cleanRider.getRiderId(), "1") > 0) {
                        RemotePush remotePush = remotePushMapperExtra.selectByUser(manageDTO.getUserId());
                        if (remotePush!=null&&remotePush.getDeviceType()!=null){
                            if (remotePush.getDeviceType()==1){
                                User user = new User();
                                user.setUserId(manageDTO.getUserId());
                                JGPush jgPush = new JGPush();
                                jgPush.setNoticeId("8708831135559901");
                                jgPush.setUserId(user.getUserId());
                                jgPush.setCleanRiderMapper(cleanRiderMapper);
                                jgPush.setAppRouterMapper(appRouterMapper);
                                jgPush.setRemotePushMapperExtra(remotePushMapperExtra);
                                jgPush.setRemotePushNoticeMapperExtra(remotePushNoticeMapperExtra);
                                Thread thread = new Thread(jgPush);
                                thread.start();
                            }else {
                                User user = new User();
                                user.setUserId(manageDTO.getUserId());
                                MessageThread messageThread = new MessageThread();
                                messageThread.noticeId = "8708831135559901";
                                messageThread.userId = user.getUserId();
                                messageThread.setCleanRiderMapper(cleanRiderMapper);
                                messageThread.setAppRouterMapper(appRouterMapper);
                                messageThread.setRemotePushMapperExtra(remotePushMapperExtra);
                                messageThread.setRemotePushNoticeMapperExtra(remotePushNoticeMapperExtra);
                                Thread thread = new Thread(messageThread);
                                thread.start();
                            }
                        }
                        return new JSONResult("分配骑手成功", 200);
                    } else {
                        return new JSONResult("分配骑手成功,但改变骑手状态失败", 200);
                    }
                } else {
                    return new JSONResult("分配骑手失败", 200);
                }
            }
        } else {
            return new JSONResult("没有这个骑手", 500);
        }
    }

    @Override
    public JSONResult complete(VehicleCleanOrderDTO cleanOrderDTO) {
        VehicleCleanOrder cleanOrder = new VehicleCleanOrder();
        cleanOrder.setServerOrderId(cleanOrderDTO.getServerOrderId());
        Byte status = 2;
        cleanOrder.setProcessStatus(status);
        if (mapper.updateByPrimaryKeySelective(cleanOrder) > 0) {
            mapperExtra.updateRiderStatus(cleanOrderDTO.getRiderId(), "0");
            sendMessage(cleanOrderDTO.getUserId(),"688008757855812",null);
//            riderServiceImpl.sendMesToApp("688008757855812",user.getUserId());
            return new JSONResult("完成订单成功", 200);
        } else {
            return new JSONResult("完成订单失败", 200);
        }
    }

    public void sendMessage(String userId, String noticeId, Map<String,String> insertContent){
        RemotePush remotePush = remotePushMapperExtra.selectByUser(userId);
        if (remotePush!=null&&remotePush.getDeviceType()!=null){
            if (remotePush.getDeviceType()==1){
                User user = new User();
                user.setUserId(userId);
                JGPush jgPush = new JGPush();
                jgPush.setType(1);
                if (insertContent!=null) {
                    jgPush.setContent(insertContent);
                }
//                jgPush.setNoticeId("688008757855812");
                jgPush.setNoticeId(noticeId);
                jgPush.setUserId(user.getUserId());
                jgPush.setCleanRiderMapper(cleanRiderMapper);
                jgPush.setAppRouterMapper(appRouterMapper);
                jgPush.setRemotePushMapperExtra(remotePushMapperExtra);
                jgPush.setRemotePushNoticeMapperExtra(remotePushNoticeMapperExtra);
                Thread thread = new Thread(jgPush);
                thread.start();
            }else if (remotePush.getDeviceType()==2){
                User user = new User();
                user.setUserId(userId);
                MessageThread messageThread = new MessageThread();
                if (insertContent!=null) {
                    messageThread.content = insertContent;
                }
                messageThread.noticeId = noticeId;
                messageThread.userId = user.getUserId();
                messageThread.setCleanRiderMapper(cleanRiderMapper);
                messageThread.setAppRouterMapper(appRouterMapper);
                messageThread.setRemotePushMapperExtra(remotePushMapperExtra);
                messageThread.setRemotePushNoticeMapperExtra(remotePushNoticeMapperExtra);
                Thread thread = new Thread(messageThread);
                thread.start();
            }
        }
    }

    @Override
    public JSONResult change() {
        return null;
    }

    /**
     * 退款
     * @param tuiKuanDTO
     * @return
     */
    @Override
    public JSONResult tuiKuan(TuiKuanDTO tuiKuanDTO) {

        VehicleCleanOrderDTO vehicleCleanOrderDTO = vehicleCleanOrderMapperExtra.selectByServerOrderId(tuiKuanDTO.getServerOrderId());

        if (vehicleCleanOrderDTO == null || vehicleCleanOrderDTO.getServerOrderId()==null){
            return new JSONResult("退款失败",500);
        }

        // 完成修改订单状态和骑手状态
        VehicleCleanOrder cleanOrderDTO = new VehicleCleanOrder();
        cleanOrderDTO.setServerOrderId(tuiKuanDTO.getServerOrderId());
        Byte status = 4;
        cleanOrderDTO.setProcessStatus(status);
        mapper.updateByPrimaryKeySelective(cleanOrderDTO);
        if (tuiKuanDTO.getRiderId() != null && !tuiKuanDTO.getRiderId().equals("")) {
            mapperExtra.updateRiderStatus(tuiKuanDTO.getRiderId(), "0");
        }

        // 如果有优惠券，则改变优惠券状态
        if(tuiKuanDTO.getCouponId() != null) {
            mapperExtra.updateCouponStatus(tuiKuanDTO.getCouponId());
        }

        // 获取用户余额和收益信息id
        BalanceAndInfoIdDTO balanceAndInfoId = myWalletMapper.getUserAllIncome(tuiKuanDTO.getUserId());

        // 订单金额 - 扣款金额相减 = 实际退款金额
        BigDecimal bigDecimal = (new BigDecimal(tuiKuanDTO.getAmountMoney().toString())).subtract(new BigDecimal(tuiKuanDTO.getMoney().toString()));

        // 设置退款记录基本信息
        IncomeLogDTO incomeLog = new IncomeLogDTO();
        incomeLog.setInfoId(balanceAndInfoId.getInfoId());
        incomeLog.setAmount(bigDecimal.doubleValue());
        incomeLog.setBeforeChangeIncome(balanceAndInfoId.getBalance().doubleValue());
        incomeLog.setRecordId(StringUtil.createId());
        incomeLog.setRemark("洗车订单退款");
        incomeLog.setType(4);
        incomeLog.setSourceId(tuiKuanDTO.getServerOrderId());// 来源为平台本身洗车订单单号

        // 插入提现记录成功，则改变余额
        if(myWalletMapper.insertIncomeLog(incomeLog) > 0) {
            if (myWalletMapper.increaseOtherMoney(balanceAndInfoId.getInfoId(), bigDecimal.toString()) > 0) {
                return new JSONResult("退款成功", 200);
            } else {
                return new JSONResult("退款失败", 500);
            }
        } else {
            return new JSONResult("退款失败", 500);
        }
    }

    @Override
    public JSONResult search(VehicleOrderManageDTO orderManageDTO, PageDTO pageDTO) {

        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        Page<VehicleOrderManageDTO> orderList = mapperExtra.search(orderManageDTO);
        PageInfo<VehicleOrderManageDTO> orderInfoList = new PageInfo<>(orderList);

        //获取总的订单，然后将收益相加
        orderManageDTO.setProcessStatus(new Byte("3"));
        orderManageDTO.setServerOrderId(null);
        orderManageDTO.setThirdOrder(null);
        orderManageDTO.setPhone(null);
        orderManageDTO.setRiderPhone(null);
        orderManageDTO.setRiderName(null);
        orderManageDTO.setFirstName(null);
        List<VehicleOrderManageDTO> orderAllList = mapperExtra.search(orderManageDTO);
        BigDecimal allMoney = new BigDecimal("0"); // 总收益
        Integer orderAllAmount = 0; //总单数
        for (VehicleOrderManageDTO order: orderAllList) {
            // 满足if条件的为实收金额的订单，将钱加到总收益里
            allMoney = allMoney.add(new BigDecimal(order.getActualPrice().toString()));
            orderAllAmount++;
        }

        //获取今日的订单，然后将收益相加
        SimpleDateFormat day = new SimpleDateFormat("y-MM-dd");//设置日期格式为天,大写的H为24小时制，小写为12
        Date today = new Date();
        String todayStr = day.format(today);

        orderManageDTO.setStartTime(todayStr + " 00:00:00");
        orderManageDTO.setEndTime(todayStr + " 23:59:59");

        List<VehicleOrderManageDTO> orderTodayList = mapperExtra.search(orderManageDTO);
        // 今日总收益
        BigDecimal todayMoney = new BigDecimal("0"); // 总收益
        Integer orderTodayAmount = 0;
        for (VehicleOrderManageDTO order: orderTodayList) {
            // 满足if条件的为实收金额的订单，将钱加到总收益里
            orderTodayAmount++;
            todayMoney = todayMoney.add(new BigDecimal(order.getActualPrice().toString()));
        }

        // 设置总收益
        String pageInfo = "总销售额：" + allMoney.doubleValue() + "元——总订单数："  + orderAllAmount.toString() + "单";
        pageInfo = pageInfo  + ";";
        pageInfo = pageInfo +  "今销售额：" + todayMoney.doubleValue() + "元——今订单数："  +  orderTodayAmount.toString() + "单";

        return new JSONResult(pageInfo, 200, orderInfoList);
    }

    @Override
    public JSONResult getRiders() {
        List<CleanRider> cleanRiders = mapperExtra.getCleanRiders();
        return new JSONResult("获取空闲骑手成功", 200, cleanRiders);
    }

    @Override
    public JSONResult getUrls(String serverOrderId) {
        // 获得某个订单的洗车前后urls
        List<ImageInfoDTO> beforeUrls = mapperExtra.getUrls(serverOrderId, "0");
        List<ImageInfoDTO> afterUrls = mapperExtra.getUrls(serverOrderId, "1");
        JSONObject json = new JSONObject();
        json.put("beforeUrls", beforeUrls);
        json.put("afterUrls", afterUrls);

        return new JSONResult("查询url成功", 200, json);
    }

    @Override
    public JSONResult deleteImage(String fileId, String type) {
        fileMapperExtra.deleteByPrimaryKey(fileId);
        if (mapperExtra.deleteImageRelation(fileId) > 0) {
            return new JSONResult("删除成功", 200);
        } else {
            return new JSONResult("删除失败", 500);
        }
    }

    @Override
    public JSONResult uploadImage(String status, String serverOrderId, String userId, MultipartFile file) {
        String address = "";
        try{
            if (file!=null||!file.isEmpty()) {
                address = FileUploadUtil.putObject(file.getOriginalFilename(), file.getInputStream());//返回图片储存路径
            }
            File file1 = announcementServiceImpl.setFile(file.getOriginalFilename(), address, userId, new Date());
            fileMapperExtra.insertSelective(file1);
            if (mapperExtra.insertImageRelation(serverOrderId, file1.getFileId(), StringUtil.createId(), status) > 0) {
                return new JSONResult("上传图片成功", 200);
            } else {
                return new JSONResult("上传图片失败", 500);
            }
        } catch(Exception e) {
            return new JSONResult("图片上传失败",500);
        }
    }
}
