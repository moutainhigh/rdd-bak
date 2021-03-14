package com.cqut.czb.bn.service.impl.directChargingSystem;

import com.cqut.czb.bn.dao.mapper.directChargingSystem.DirectChargingOrderMapper;
import com.cqut.czb.bn.dao.mapper.directChargingSystem.DirectChargingOrderMapperExtra;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directChargingSystem.DirectChargingOrderService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectChargingOrderServiceImpl implements DirectChargingOrderService {

    @Autowired
    DirectChargingOrderMapperExtra directChargingOrderMapperExtra;

    @Override
    public JSONResult updateRecord(DirectChargingOrderDto directChargingOrderDto) {
        try {
            int num = directChargingOrderMapperExtra.updateRecordByOrderId(directChargingOrderDto);
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
