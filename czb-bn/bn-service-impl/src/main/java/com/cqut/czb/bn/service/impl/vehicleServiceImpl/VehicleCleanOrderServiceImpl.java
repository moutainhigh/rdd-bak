package com.cqut.czb.bn.service.impl.vehicleServiceImpl;

import com.cqut.czb.bn.dao.mapper.IncomeLogMapperExtra;
import com.cqut.czb.bn.dao.mapper.UserIncomeInfoMapperExtra;
import com.cqut.czb.bn.dao.mapper.UserMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.*;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.UserIncomeInfoDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.IssueServerCouponDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.VehicleCleanOrderDTO;
import com.cqut.czb.bn.entity.entity.IncomeLog;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.UserIncomeInfo;
import com.cqut.czb.bn.entity.entity.vehicleService.CouponStandard;
import com.cqut.czb.bn.entity.entity.vehicleService.RiderEvaluate;
import com.cqut.czb.bn.entity.entity.vehicleService.VehicleCleanOrder;
import com.cqut.czb.bn.service.vehicleService.VehicleCleanOrderService;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Transactional
@Service
public class VehicleCleanOrderServiceImpl implements VehicleCleanOrderService{

    @Autowired
    VehicleCleanOrderMapperExtra vehicleCleanOrderMapperExtra;
    @Autowired
    RiderEvaluateMapper riderEvaluateMapper;
    @Autowired
    ComparedPicMapperExtra comparedPicMapperExtra;
    @Autowired
    UserIncomeInfoMapperExtra userIncomeInfoMapperExtra;
    @Autowired
    IncomeLogMapperExtra incomeLogMapperExtra;
    @Autowired
    ServerCouponMapperExtra serverCouponMapperExtra;
    @Autowired
    UserMapperExtra userMapperExtra;
    @Autowired
    CouponManageServiceImpl couponManageServiceImpl;
    @Autowired
    CouponStandardMapperExtra couponStandardMapperExtra;

    @Override
    public List<VehicleCleanOrderDTO> getOrderList(VehicleCleanOrderDTO vehicleCleanOrderDTO,User user) {
        vehicleCleanOrderDTO.setUserId(user.getUserId());
         return vehicleCleanOrderMapperExtra.selectById(vehicleCleanOrderDTO);
    }

    @Override
    public VehicleCleanOrderDTO getOrderPic(VehicleCleanOrderDTO cleanOrderDTO) {
        VehicleCleanOrderDTO vehicleCleanOrderDTO = new VehicleCleanOrderDTO();
        vehicleCleanOrderDTO.setOrderPic(comparedPicMapperExtra.selectByOrder(cleanOrderDTO.getServerOrderId()));
        return vehicleCleanOrderDTO;
    }

    @Override
    public VehicleCleanOrderDTO getServicingOrder(User user) {
        VehicleCleanOrderDTO vehicleCleanOrderDTO = new VehicleCleanOrderDTO();
        vehicleCleanOrderDTO.setUserId(user.getUserId());
        return vehicleCleanOrderMapperExtra.selectByStatus(vehicleCleanOrderDTO);
    }

    @Override
    public Boolean cancelOrder(VehicleCleanOrderDTO vehicleCleanOrderDTO,User user) {
        if (vehicleCleanOrderDTO==null||vehicleCleanOrderDTO.getServerOrderId()==null||"".equals(vehicleCleanOrderDTO.getServerOrderId())){
            return null;
        }

        VehicleCleanOrderDTO vehicleCleanOrderDTO1 = vehicleCleanOrderMapperExtra.selectByServerOrderId(vehicleCleanOrderDTO.getServerOrderId());

        if (vehicleCleanOrderDTO1 == null || vehicleCleanOrderDTO1.getServerOrderId()==null){
            return false;
        }
        List<VehicleCleanOrderDTO> vehicleCleanOrderDTOList = vehicleCleanOrderMapperExtra.selectById(vehicleCleanOrderDTO);
        vehicleCleanOrderDTO.setCancelPersonId(user.getUserId());
        vehicleCleanOrderDTO.setUserId(user.getUserId());
        vehicleCleanOrderDTO.setPayStatus(2);
        vehicleCleanOrderDTO.setProcessStatus(4);
        vehicleCleanOrderDTO.setUpdateAt(new Date());
        boolean isCancel =  vehicleCleanOrderMapperExtra.updateOrderStateCancel(vehicleCleanOrderDTO)>0;
        if (isCancel) {
            if (vehicleCleanOrderDTOList!=null&&vehicleCleanOrderDTOList.size()!=0) {  //判断是否有此订单
                boolean updateCoupon =  serverCouponMapperExtra.updateCouponToNotUse(vehicleCleanOrderDTOList.get(0).getCouponId())>0;
                UserIncomeInfoDTO userIncomeInfo = userIncomeInfoMapperExtra.selectUserIncomeInfo(vehicleCleanOrderDTOList.get(0).getUserId());
                Boolean isRefund = false;
                String id = StringUtil.createId();
                if (userIncomeInfo!=null&&userIncomeInfo.getInfoId()!=null){  //判断用户收益表中是否有记录
                    userIncomeInfo.setUpdateAt(new Date());
                    userIncomeInfo.setRefundMoney(vehicleCleanOrderDTOList.get(0).getActualPrice());
                    isRefund = userIncomeInfoMapperExtra.updateOtherIncome(userIncomeInfo)>0;   //有记录则直接进行余额返还
                }else {
                    UserIncomeInfo userIncome = new UserIncomeInfo();
                    userIncome.setUserId(vehicleCleanOrderDTO.getUserId());
                    userIncome.setInfoId(id);
                    userIncome.setOtherIncome(0.00);
                    Boolean insert = userIncomeInfoMapperExtra.insert(userIncome)>0;  //无记录则插入
                    if (insert){   //插入成功开始进行余额返还
//                        userIncomeInfo.setInfoId(userIncome.getInfoId());
                        userIncomeInfo = userIncomeInfoMapperExtra.selectUserIncomeInfo(vehicleCleanOrderDTOList.get(0).getUserId());
                        userIncomeInfo.setUpdateAt(new Date());
                        userIncomeInfo.setRefundMoney(vehicleCleanOrderDTOList.get(0).getActualPrice());
                        isRefund = userIncomeInfoMapperExtra.updateOtherIncome(userIncomeInfo)>0;
                    }
                }
                if (isRefund){  //退款成功后加入收入记录
                    IncomeLog incomeLog = new IncomeLog();
                    incomeLog.setType(4); //其他
                    incomeLog.setRemark("洗车订单退款");
                    incomeLog.setRecordId(StringUtil.createId());
                    incomeLog.setSouseId(vehicleCleanOrderDTO.getServerOrderId());
                    incomeLog.setAmount(vehicleCleanOrderDTOList.get(0).getActualPrice());
                    if (userIncomeInfo!=null&&userIncomeInfo.getInfoId()!=null) {
                        incomeLog.setInfoId(userIncomeInfo.getInfoId());
                        incomeLog.setBeforeChangeIncome(userIncomeInfo.getOtherIncome());
                    }else {
                        incomeLog.setInfoId(id);
                        incomeLog.setBeforeChangeIncome(0.0);
                    }
                    Boolean isLog = incomeLogMapperExtra.insert(incomeLog)>0;  //插入收入记录
                    if (isLog){
                        return true;
                    } else {
                        return false;
                    }
                }
            }else {
                return null; //无此订单
            }
//            Boolean isRefund =
        }else {
            return false;
        }
        return isCancel;
    }

    @Override
    public Boolean completeOrder(VehicleCleanOrderDTO vehicleCleanOrderDTO,User user) {
        couponManageServiceImpl.issueCouponToPartner(user);
         vehicleCleanOrderDTO.setProcessStatus(3);
         vehicleCleanOrderDTO.setUpdateAt(new Date());
        return vehicleCleanOrderMapperExtra.updateOrderStateComplete(vehicleCleanOrderDTO)>0;
    }

    @Override
    public Boolean evaluateRider(RiderEvaluate riderEvaluate, User user) {
        if (riderEvaluate.getEvaluateRiderId()==null||"".equals(riderEvaluate.getEvaluateRiderId())){
            return null;
        }
        riderEvaluate.setEvaluateId(StringUtil.createId());
        riderEvaluate.setEvaluateUserId(user.getUserId());
        riderEvaluate.setCreateAt(new Date());
        return  riderEvaluateMapper.insert(riderEvaluate)>0;
    }
}
