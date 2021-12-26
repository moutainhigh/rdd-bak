package com.cqut.czb.bn.service.impl.directCustomer;

import com.cqut.czb.bn.dao.mapper.DictMapperExtra;
import com.cqut.czb.bn.dao.mapper.directCustomer.MobileMapperExtra;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directCustomers.CustomerManageDto;
import com.cqut.czb.bn.entity.dto.directCustomers.DirectCustomersDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directCustomer.SinopecService;
import com.cqut.czb.bn.service.impl.directChargingSystem.OilCardRechargeServiceImpl;
import com.cqut.czb.bn.util.md5.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SinopecServiceImpl implements SinopecService {
    @Autowired
    MobileMapperExtra mobileMapperExtra;

    @Autowired
    OilCardRechargeServiceImpl oilCardRechargeService;

    @Autowired
    DictMapperExtra dictMapperExtra;

    public synchronized JSONResult onlineorder(DirectCustomersDto directCustomersDto){
        if (directCustomersDto != null) {

            // 价格验证
            if (directCustomersDto.getCardnum().intValue() != 500 &&
                    directCustomersDto.getCardnum().intValue() != 100 &&
                    directCustomersDto.getCardnum().intValue() != 200) {
                return new JSONResult("价格有误", 500);
            }

            System.out.println("打印进单信息：" + directCustomersDto);

            CustomerManageDto customerManageDto = mobileMapperExtra.getCustomer(directCustomersDto);

            System.out.println("获取AppId相关：" + customerManageDto);

            if (customerManageDto.getIsShutRecharge() == 1) {
                return new JSONResult("今日提交次数已达上限", 500);
            }
            String appSecret = customerManageDto.getAppSecret();

            String string = directCustomersDto.getAppId() + appSecret + directCustomersDto.getGasUserid() +
                     directCustomersDto.getOrdersn() + String.valueOf(directCustomersDto.getCardnum().intValue()) + directCustomersDto.getGasMobile();

            System.out.println("string:" + string);

            // MD5加密
            String sign = MD5Util.MD5Encode(string,"UTF-8").toLowerCase();

            System.out.println("sign："+sign);

            System.out.println("判断是否成立：" + (customerManageDto.getBalance() >= directCustomersDto.getCardnum() &&
                    directCustomersDto.getSign().equals(sign) &&
                    customerManageDto.getBalance() > 0));

            System.out.println("余额是否充足 : " + (customerManageDto.getBalance() >= directCustomersDto.getCardnum() ? "是" : "否"));

            System.out.println("密匙是否吻合：" + (directCustomersDto.getSign().equals(sign) ? "是" : "否"));

            System.out.println("余额是否大于零：" + (customerManageDto.getBalance() > 0 ? "是" : "否"));

            if (customerManageDto.getBalance() >= directCustomersDto.getCardnum() &&
                    directCustomersDto.getSign().equals(sign) &&
                    customerManageDto.getBalance() > 0){
                // 插入记录
                DirectChargingOrderDto directChargingOrderDto = new DirectChargingOrderDto();
                directChargingOrderDto.setUserId(System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15).replace("-", ""));
                directChargingOrderDto.setRealPrice(directCustomersDto.getCardnum());
                directChargingOrderDto.setOrderId(directCustomersDto.getOrdersn());
                directChargingOrderDto.setState(1);
                directChargingOrderDto.setRecordType(4);
                directChargingOrderDto.setPaymentMethod(3);
                directChargingOrderDto.setRechargeAccount(directCustomersDto.getGasUserid());
                directChargingOrderDto.setCardholder(directCustomersDto.getGasMobile());
                directChargingOrderDto.setCustomerNumber(customerManageDto.getCustomerNumber());
                int insertPhoneOrder = mobileMapperExtra.insertOilOrder(directChargingOrderDto);

                // 更新数据
                CustomerManageDto customerManageDto1 = new CustomerManageDto();
                customerManageDto1.setCustomerId(customerManageDto.getCustomerId());
                customerManageDto1.setBalance(customerManageDto.getBalance() - directCustomersDto.getCardnum());
                customerManageDto1.setConsumptionAmount(customerManageDto.getConsumptionAmount() + directCustomersDto.getCardnum());
                mobileMapperExtra.updateConsumption(customerManageDto1);

                //是否开启充值
                if (dictMapperExtra.selectDictByName("is_direct_recharge").getContent().equals("0")) {
                    return new JSONResult("提交成功，请耐心等待", 200);
                }
                // 连接第三方
                directChargingOrderDto.setUserAccount(directCustomersDto.getPhoneno());
                directChargingOrderDto.setRechargeAmount(directCustomersDto.getCardnum());
                oilCardRechargeService.onlineorderSubmission(directChargingOrderDto);
                System.out.println("提交成功");
                return new JSONResult("提交成功", 200);
            }
        }
        return new JSONResult("提交失败", 500);
    }

    public synchronized JSONResult ordersta(DirectCustomersDto directCustomersDto) {
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
                if (dictMapperExtra.selectDictByName("is_direct_recharge").getContent().equals("1")) {
                    oilCardRechargeService.getOilOrderState(directChargingOrderDto);
                }

                // 返回状态
                mobileMapperExtra.getState(directChargingOrderDto);
                return new JSONResult("查询成功", 200, mobileMapperExtra.getState(directChargingOrderDto));
            }
        }
        return new JSONResult("查询失败", 500);
    }
}
