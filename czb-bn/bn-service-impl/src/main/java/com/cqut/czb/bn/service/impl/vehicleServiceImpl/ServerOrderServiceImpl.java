package com.cqut.czb.bn.service.impl.vehicleServiceImpl;

import com.cqut.czb.bn.dao.mapper.FileMapperExtra;
import com.cqut.czb.bn.dao.mapper.MyWalletMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.VehicleCleanOrderMapper;
import com.cqut.czb.bn.dao.mapper.vehicleService.VehicleCleanOrderMapperExtra;
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
import java.util.Date;
import java.util.List;

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
                        User user = new User();
                        user.setUserId(manageDTO.getUserId());
                        MessageThread messageThread = new MessageThread("8708831135559901",user.getUserId());
                        Thread thread = new Thread(messageThread);
                        thread.start();
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
            User user = new User();
            user.setUserId(cleanOrderDTO.getUserId());
            MessageThread messageThread = new MessageThread("688008757855812",user.getUserId());
            Thread thread = new Thread(messageThread);
            thread.start();
            return new JSONResult("完成订单成功", 200);
        } else {
            return new JSONResult("完成订单失败", 200);
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

        // 完成修改订单状态和骑手状态
        VehicleCleanOrder cleanOrderDTO = new VehicleCleanOrder();
        cleanOrderDTO.setServerOrderId(tuiKuanDTO.getServerOrderId());
        Byte status = 4;
        cleanOrderDTO.setProcessStatus(status);
        mapper.updateByPrimaryKeySelective(cleanOrderDTO);
        if (tuiKuanDTO.getRiderId() != null && !tuiKuanDTO.getRiderId().equals("")) {
            mapperExtra.updateRiderStatus(tuiKuanDTO.getRiderId(), "0");
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

        return new JSONResult("查询成功", 200, orderInfoList);
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
