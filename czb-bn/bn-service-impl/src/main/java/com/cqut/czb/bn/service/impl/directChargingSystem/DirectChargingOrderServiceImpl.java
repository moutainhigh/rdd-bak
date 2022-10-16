package com.cqut.czb.bn.service.impl.directChargingSystem;

import com.cqut.czb.bn.dao.mapper.PetrolSalesRecordsMapperExtra;
import com.cqut.czb.bn.dao.mapper.directChargingSystem.DirectChargingOrderMapper;
import com.cqut.czb.bn.dao.mapper.directChargingSystem.DirectChargingOrderMapperExtra;
import com.cqut.czb.bn.dao.mapper.directChargingSystem.OilCardRechargeMapperExtra;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.PaymentProcess.FanYongService;
import com.cqut.czb.bn.service.autoRecharge.UserRechargeService;
import com.cqut.czb.bn.service.directChargingSystem.DirectChargingOrderService;
import com.cqut.czb.bn.service.fanyong.FanyongLogService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class DirectChargingOrderServiceImpl implements DirectChargingOrderService {

    @Autowired
    DirectChargingOrderMapperExtra directChargingOrderMapperExtra;

    @Autowired
    FanyongLogService fanyongLogService;

    @Autowired
    FanYongService fanYongService;

    @Autowired
    OilCardRechargeMapperExtra oilCardRechargeMapperExtra;

    @Autowired
    PetrolSalesRecordsMapperExtra petrolSalesRecordsMapperExtra;

    @Autowired
    UserRechargeService userRechargeService;

    @Override
    public JSONResult updateRecord(DirectChargingOrderDto directChargingOrderDto) {
        try {
            int num = directChargingOrderMapperExtra.updateRecordByOrderId(directChargingOrderDto);
            if (num == 1 && directChargingOrderDto.getState()==2){
                System.out.println("直充返佣進入方法");
                try {
                    directChargingOrderDto = oilCardRechargeMapperExtra.getOrder(directChargingOrderDto.getOrderId());
                    if (!fanyongLogService.isContainFanyongLog(directChargingOrderDto.getOrderId())){
                        String userId = directChargingOrderDto.getUserId();
                        Double actualPayment = directChargingOrderDto.getRechargeAmount();
                        Double money = directChargingOrderDto.getRealPrice();
                        if (actualPayment == null && money!=null){
                            actualPayment = money;
                        }
                        String orgId = directChargingOrderDto.getOrderId();
                        System.out.println(userId+" "+actualPayment+" "+money+" "+orgId);
                        boolean isSucceed = fanYongService.beginFanYong(7, "", userId, money, actualPayment, orgId);
                        System.out.println("返佣"+isSucceed + " " + directChargingOrderDto.getOrderId());
                    } else {
                        System.out.println("已存在返佣记录  " + directChargingOrderDto.getOrderId());
                    }
                } catch (Exception e){
                    System.out.println("返佣失败");
                    e.printStackTrace();
                }

            }
            if (num == 1 && directChargingOrderDto.getState()==4){
                // 充值失败
                try {
                    if (null != petrolSalesRecordsMapperExtra.selectInfoByOrgId(directChargingOrderDto.getOrderId())){
                        petrolSalesRecordsMapperExtra.updateMatterCard(directChargingOrderDto.getOrderId());
                        // 退款
                        userRechargeService.drawback(directChargingOrderDto.getOrderId(), false);
                        System.out.println("更变线下大客户充值订单成功");
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (num == 1) {
                return new JSONResult("更新成功");
            }
            return new JSONResult("更新失败");
        } catch (DuplicateKeyException e) {
            return new JSONResult("订单号已存在");
        }
    }

    @Override
    public JSONResult getRecordByOrderId(String orderId) {
        return new JSONResult(directChargingOrderMapperExtra.getRecordByOrderId(orderId));
    }
}
