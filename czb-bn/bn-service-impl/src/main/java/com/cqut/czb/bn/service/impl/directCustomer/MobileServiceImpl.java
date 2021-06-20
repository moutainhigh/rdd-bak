package com.cqut.czb.bn.service.impl.directCustomer;

import com.cqut.czb.bn.dao.mapper.directCustomer.MobileMapperExtra;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directCustomers.CustomerManageDto;
import com.cqut.czb.bn.entity.dto.directCustomers.DirectCustomersDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directCustomer.MobileService;
import com.cqut.czb.bn.service.impl.directChargingSystem.OilCardRechargeServiceImpl;
import com.cqut.czb.bn.util.md5.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MobileServiceImpl implements MobileService {
    @Autowired
    MobileMapperExtra mobileMapperExtra;

    @Autowired
    OilCardRechargeServiceImpl oilCardRechargeService;

    public synchronized JSONResult telorder(DirectCustomersDto directCustomersDto){
        if (directCustomersDto != null) {

            if (directCustomersDto.getCardnum().intValue() != 50 ||
                    directCustomersDto.getCardnum().intValue() != 100 ||
                    directCustomersDto.getCardnum().intValue() != 200) {
                return new JSONResult("价格有误", 500);
            }

            CustomerManageDto customerManageDto = mobileMapperExtra.getCustomer(directCustomersDto);
            String appSecret = customerManageDto.getAppSecret();

            String string = directCustomersDto.getAppId() + appSecret + directCustomersDto.getPhoneno() +
                    String.valueOf(directCustomersDto.getCardnum().intValue()) + directCustomersDto.getOrdersn();
            // MD5加密
            String sign = MD5Util.MD5Encode(string,"UTF-8").toLowerCase();

            if (customerManageDto.getBalance() >= directCustomersDto.getCardnum() &&
                    directCustomersDto.getSign().equals(sign) &&
                    customerManageDto.getBalance() > 0){
                // 插入记录
                DirectChargingOrderDto directChargingOrderDto = new DirectChargingOrderDto();
                directChargingOrderDto.setUserId(System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15).replace("-", ""));
                directChargingOrderDto.setRealPrice(directCustomersDto.getCardnum());
                directChargingOrderDto.setOrderId(directCustomersDto.getOrdersn());
                directChargingOrderDto.setState(1);
                directChargingOrderDto.setRecordType(1);
                directChargingOrderDto.setPaymentMethod(3);
                directChargingOrderDto.setRechargeAccount(directCustomersDto.getPhoneno());
                directChargingOrderDto.setCustomerNumber(customerManageDto.getCustomerNumber());
                int insertPhoneOrder = mobileMapperExtra.insertPhoneOrder(directChargingOrderDto);

                // 更新数据
                CustomerManageDto customerManageDto1 = new CustomerManageDto();
                customerManageDto1.setCustomerId(customerManageDto.getCustomerId());
                customerManageDto1.setBalance(customerManageDto.getBalance() - directCustomersDto.getCardnum());
                customerManageDto1.setConsumptionAmount(customerManageDto.getConsumptionAmount() + directCustomersDto.getCardnum());
                mobileMapperExtra.updateConsumption(customerManageDto1);

                // 连接第三方
                directChargingOrderDto.setUserAccount(directCustomersDto.getPhoneno());
                directChargingOrderDto.setRechargeAmount(directCustomersDto.getCardnum());
                oilCardRechargeService.phoneRechargeSubmission(directChargingOrderDto);

                System.out.println("提交成功");
                return new JSONResult("提交成功", 200);
            }
        }
        return new JSONResult("提交失败", 500);
    }


    public synchronized JSONResult ordersta(DirectCustomersDto directCustomersDto){
        if (directCustomersDto != null) {
            CustomerManageDto customerManageDto = mobileMapperExtra.getCustomer(directCustomersDto);
            String appSecret = customerManageDto.getAppSecret();

            String string = directCustomersDto.getAppId() + appSecret +
                    directCustomersDto.getOrdersn();
            // MD5加密
            String sign = MD5Util.MD5Encode(string,"UTF-8").toLowerCase();
            if (directCustomersDto.getSign().equals(sign)){
                DirectChargingOrderDto directChargingOrderDto = new DirectChargingOrderDto();
                directChargingOrderDto.setOrderId(directCustomersDto.getOrdersn());
                oilCardRechargeService.getPhoneOrderState(directChargingOrderDto);

                // 返回状态
                mobileMapperExtra.getOrderState(directChargingOrderDto);
                return new JSONResult("查询成功", 200, mobileMapperExtra.getOrderState(directChargingOrderDto));
            }

        }
        return new JSONResult("查询失败", 500);
    }

}
