package com.cqut.czb.bn.service.impl.directChargingSystem;

import com.cqut.czb.bn.dao.mapper.directChargingSystem.NoLoginDirectSystemMapperExtra;
import com.cqut.czb.bn.entity.dto.directChargingSystem.CustomerPhoneOrderDto;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directChargingSystem.NoLoginDirectSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NoLoginDirectSystemServiceImpl implements NoLoginDirectSystemService {

    @Autowired
    NoLoginDirectSystemMapperExtra noLoginDirectSystemMapperExtra;

    public String getCommodityId(DirectChargingOrderDto directChargingOrderDto){
        return noLoginDirectSystemMapperExtra.getCommodityId(directChargingOrderDto);
    }

    public String insertPhoneOrder(DirectChargingOrderDto directChargingOrderDto){
        CustomerPhoneOrderDto customerPhoneOrderDto = new CustomerPhoneOrderDto();
        String id = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15).replace("-", "");
        customerPhoneOrderDto.setId(id);
        customerPhoneOrderDto.setOrderId(directChargingOrderDto.getOrdersn());
        customerPhoneOrderDto.setCommodityId(noLoginDirectSystemMapperExtra.getCommodityId(directChargingOrderDto));
        customerPhoneOrderDto.setRechargeAccount(directChargingOrderDto.getRechargeAccount());
        customerPhoneOrderDto.setRechargeAmount(directChargingOrderDto.getRechargeAmount());
        customerPhoneOrderDto.setOurOrderId(directChargingOrderDto.getOurOrderId());
        if (noLoginDirectSystemMapperExtra.insertPhoneOrder(customerPhoneOrderDto) > 0) {
            return id;
        }
        return null;
    }

    public JSONResult getOrderDetails(CustomerPhoneOrderDto customerPhoneOrderDto){
        CustomerPhoneOrderDto customerPhoneOrderDto1 = noLoginDirectSystemMapperExtra.getOrderDetails(customerPhoneOrderDto);
        if (customerPhoneOrderDto1 != null) {
            return new JSONResult("查询成功", 200, customerPhoneOrderDto1);
        }
        return new JSONResult("无此订单", 500);
    }

    public boolean updateState(String orderId){
        DirectChargingOrderDto directChargingOrderDto = new DirectChargingOrderDto();
        directChargingOrderDto.setOrderId(orderId);
        String customerOrderId = noLoginDirectSystemMapperExtra.findCustomerId(directChargingOrderDto);

        System.out.println("customerOrderId"+customerOrderId);
        //更新链接
        CustomerPhoneOrderDto customerPhoneOrderDto = new CustomerPhoneOrderDto();
        customerPhoneOrderDto.setOurOrderId(customerOrderId);
        return noLoginDirectSystemMapperExtra.updateCustomerState(customerPhoneOrderDto) > 0;

    }
}
