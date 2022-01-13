package com.cqut.czb.bn.service.impl.directCustomer;

import com.cqut.czb.bn.dao.mapper.DictMapperExtra;
import com.cqut.czb.bn.dao.mapper.directChargingSystem.OilCardRechargeMapperExtra;
import com.cqut.czb.bn.dao.mapper.directCustomer.MobileMapperExtra;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directCustomers.CustomerManageDto;
import com.cqut.czb.bn.entity.dto.directCustomers.DirectCustomersDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directChargingSystem.OilCardRechargeService;
import com.cqut.czb.bn.service.directCustomer.MobileService;
import com.cqut.czb.bn.service.impl.directChargingSystem.OilCardRechargeServiceImpl;
import com.cqut.czb.bn.util.md5.MD5Util;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

@Service
public class MobileServiceImpl implements MobileService {
    @Autowired
    MobileMapperExtra mobileMapperExtra;

    @Autowired
    OilCardRechargeServiceImpl oilCardRechargeService;

    @Autowired
    OilCardRechargeMapperExtra oilCardRechargeMapperExtra;

    @Autowired
    DictMapperExtra dictMapperExtra;

//    private BufferQueueThread bufferQueueThread;

    @Override
    public synchronized JSONResult telorder(DirectCustomersDto directCustomersDto){
        if (directCustomersDto != null) {

            // 价格验证
            if (directCustomersDto.getCardnum().intValue() != 50 &&
                    directCustomersDto.getCardnum().intValue() != 100 &&
                    directCustomersDto.getCardnum().intValue() != 200) {
                return new JSONResult("价格有误", 500);
            }

            CustomerManageDto customerManageDto = mobileMapperExtra.getCustomer(directCustomersDto);
            if (customerManageDto.getIsShutRecharge() == 1) {
                return new JSONResult("今日提交次数已达上限", 500);
            }
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

                //是否开启充值
                if (dictMapperExtra.selectDictByName("is_direct_recharge").getContent().equals("0")) {
                    return new JSONResult("提交成功，请耐心等待", 200);
                }
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


    @Override
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
                if (dictMapperExtra.selectDictByName("is_direct_recharge").getContent().equals("1")) {
                    oilCardRechargeService.getPhoneOrderState(directChargingOrderDto);
                }

                // 返回状态
                mobileMapperExtra.getState(directChargingOrderDto);
                return new JSONResult("查询成功", 200, mobileMapperExtra.getState(directChargingOrderDto));
            }

        }
        return new JSONResult("查询失败", 500);
    }

    @Override
    public synchronized JSONResult getState(DirectCustomersDto directCustomersDto){
        if (directCustomersDto != null) {
                // 返回状态
            DirectChargingOrderDto directChargingOrderDto = new DirectChargingOrderDto();
            directChargingOrderDto.setCustomerOrderId(directCustomersDto.getOrdersn());
            return new JSONResult("查询成功", 200, mobileMapperExtra.getOrderState(directChargingOrderDto));
        }
        return new JSONResult("查询失败", 500);
    }

    @Override
    public synchronized JSONResult getTheBalance(DirectCustomersDto directCustomersDto){
        if (directCustomersDto != null) {
            CustomerManageDto customerManageDto = mobileMapperExtra.getCustomer(directCustomersDto);
            String appSecret = customerManageDto.getAppSecret();

            String string = directCustomersDto.getAppId() + appSecret;
            // MD5加密
            String sign = MD5Util.MD5Encode(string,"UTF-8").toLowerCase();

            if (directCustomersDto.getSign().equals(sign)){
                return new JSONResult("查询成功", 200, mobileMapperExtra.getTheBalance(directCustomersDto));
            }
        }
        return new JSONResult("查询失败", 500);
    }

    @Override
    public JSONResult DirectPhone(HttpServletRequest request) {
        System.out.println("进入36第三方接口回调");
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        String orderId = "";
        String status = "";
        Double amount = 0.00;
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            System.out.println(name + " : " + valueStr);
            if (name.equals("orderId")) {
                orderId = valueStr;
            }
            if (name.equals("status")) {
                status = valueStr;
            }
            if (name.equals("amount")) {
                amount = Double.parseDouble(valueStr);
            }
        }
        DirectChargingOrderDto directChargingOrderDto1 = new DirectChargingOrderDto();
        directChargingOrderDto1.setOrderId(orderId);
        if (status.equals("SUCCESS")) {
            directChargingOrderDto1.setState(2);
        }else {
            directChargingOrderDto1.setState(4);
        }
        //验证信息是否一致
        DirectChargingOrderDto directChargingOrderDto = oilCardRechargeMapperExtra.getOrder(orderId);
        System.out.println(directChargingOrderDto);
        if (directChargingOrderDto == null) {
            return new JSONResult(300,"未找到对应账单");
        }
        if (Double.doubleToLongBits(directChargingOrderDto.getRechargeAmount()) != Double.doubleToLongBits(amount)) {
            return new JSONResult(300,"金额与账单金额不一致");
        }
        int result = oilCardRechargeMapperExtra.updateOrderState(directChargingOrderDto1);
        if (result == 1) {
            return new JSONResult(200,"success");
        }
        return new JSONResult(400,"状态修改失败");
    }

    public Map<String, String>  parseOrder(Map<String, String> params, Map requestParams){
        //解读相应的信息
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        return params;
    }

}

//class BufferQueueThread extends Thread{
//    private Queue queue = new ConcurrentLinkedDeque<DirectChargingOrderDto>();
//    private int bufferMillis;
//    private int pollCount;
//    private long lastCallBackMilliSecond = System.currentTimeMillis();
//
//    @Autowired
//    OilCardRechargeServiceImpl oilCardRechargeService = new OilCardRechargeServiceImpl();
//
//    public BufferQueueThread(int bufferMillis){
//        this.bufferMillis = bufferMillis * 1000;
//    }
//    public void add(DirectChargingOrderDto directChargingOrderDto){
//        queue.add(directChargingOrderDto);
//    }
//
//    @Override
//    public void run() {
//        while (true){
//            pollCount = 0;
//            long currentTimeMillis = System.currentTimeMillis();
//            long diffMillis = currentTimeMillis - lastCallBackMilliSecond;
//            if (diffMillis < 0 || diffMillis > bufferMillis) {
//                pollCount = queue.size();
//            }
//            if (pollCount > 0){
//                oilCardRechargeService.phoneRechargeSubmission((DirectChargingOrderDto)queue.poll());
//            }else {
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//}
