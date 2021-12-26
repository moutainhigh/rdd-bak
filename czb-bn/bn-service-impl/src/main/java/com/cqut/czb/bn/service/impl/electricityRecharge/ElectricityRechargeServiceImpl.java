package com.cqut.czb.bn.service.impl.electricityRecharge;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.cqut.czb.bn.dao.mapper.directCustomer.MobileMapperExtra;
import com.cqut.czb.bn.dao.mapper.electricityRecharge.ElectricityRechargeMapperExtra;
import com.cqut.czb.bn.entity.dto.PayConfig.*;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directCustomers.CustomerManageDto;
import com.cqut.czb.bn.entity.dto.directCustomers.ElectricityDto;
import com.cqut.czb.bn.entity.dto.directCustomers.ElectricityRechargeDto;
import com.cqut.czb.bn.entity.dto.directCustomers.ElectricityTotalDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.electricityRecharge.ElectricityRechargeService;
import com.cqut.czb.bn.service.impl.directChargingSystem.DirectChargingSystemDelivery;
import com.cqut.czb.bn.util.constants.SystemConstants;
import com.cqut.czb.bn.util.md5.MD5Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ElectricityRechargeServiceImpl implements ElectricityRechargeService {

    @Autowired
    ElectricityRechargeMapperExtra electricityRechargeMapperExtra;

    @Autowired
    MobileMapperExtra mobileMapperExtra;

    @Override
    public JSONResult getCustomers(ElectricityRechargeDto electricityRechargeDto) {
        System.out.println(electricityRechargeDto);
        PageHelper.startPage(electricityRechargeDto.getCurrentPage(), electricityRechargeDto.getPageSize(),true);
        List<ElectricityRechargeDto> withdrawList = electricityRechargeMapperExtra.getCustomers(electricityRechargeDto);
        PageInfo<ElectricityRechargeDto> pageInfo = new PageInfo<>(withdrawList);
        return new JSONResult("列表数据查询成功", 200, pageInfo);
    }

    @Override
    public JSONResult getTotal(ElectricityRechargeDto electricityRechargeDto) {
        ElectricityTotalDto electricityTotalDto = new ElectricityTotalDto();
        int tempAll = 0;
        int tempSuccessful = 0;
        int tempFail = 0;
        int tempWait = 0;
        double tempTotalMoney = 0;
        double tempSuccessfulMoney = 0;
        double tempFailMoney = 0;
        double tempWaitMoney = 0;
        List<ElectricityRechargeDto> withdrawList = electricityRechargeMapperExtra.getCustomers(electricityRechargeDto);
        for (ElectricityRechargeDto item : withdrawList) {
            tempAll++;
            tempTotalMoney = tempTotalMoney + item.getRechargeAmount();
            if (item.getState().equals("1")){
                tempWait++;
                tempWaitMoney = tempWaitMoney + item.getRechargeAmount();
            }else if (item.getState().equals("2")){
                tempSuccessful++;
                tempSuccessfulMoney = tempSuccessfulMoney + item.getRechargeAmount();
            }else if (item.getState().equals("4")){
                tempFail++;
                tempFailMoney = tempFailMoney + item.getRechargeAmount();
            }
        }
        electricityTotalDto.setWaiting(tempWait);
        electricityTotalDto.setSuccessful(tempSuccessful);
        electricityTotalDto.setFail(tempFail);
        electricityTotalDto.setAll(tempAll);
        electricityTotalDto.setFailMoney(tempFailMoney);
        electricityTotalDto.setTotalMoney(tempTotalMoney);
        electricityTotalDto.setWaitMoney(tempWaitMoney);
        electricityTotalDto.setSuccessfulMoney(tempSuccessfulMoney);
        return new JSONResult("查询成功",200,electricityTotalDto);
    }

    @Override
    public JSONResult editOrder(ElectricityRechargeDto electricityRechargeDto) {
        int result = electricityRechargeMapperExtra.editOrder(electricityRechargeDto);
        if (result > 0 ) {
            return new JSONResult("更新成功",200);
        }
        return new JSONResult("更新失败",500);
    }

    @Override
    public Workbook exportData(ElectricityRechargeDto electricityRechargeDto) throws Exception {
        System.out.println(electricityRechargeDto);
        List<ElectricityRechargeDto> list = electricityRechargeMapperExtra.getCustomers(electricityRechargeDto);
        if(list==null||list.size()==0){
            return getCustomerWorkBook(null,electricityRechargeDto);
        }
        return getCustomerWorkBook(list, electricityRechargeDto);
    }

    @Override
    public int importData(MultipartFile file, Integer recordType) throws Exception {
        int result = 0;
        InputStream inputStream = file.getInputStream();
        List<ElectricityRechargeDto> deliveryList = null;
        Map<String, DirectChargingOrderDto> deliveryMap = new HashMap<>();
        System.out.println("读取Excel");
        deliveryList = ElectricityRechargeDelivery.readExcel(file.getOriginalFilename(), inputStream);
        System.out.println(deliveryList);
//        if(deliveryList != null){
//            for (ElectricityRechargeDto electricityRechargeDto : deliveryList){
//                result = electricityRechargeMapperExtra.editOrder(electricityRechargeDto);
//            }
//        }
        return result;
    }

    @Override
    public com.alibaba.fastjson.JSONObject WeChatRechargeDirect(DirectChargingOrderDto directChargingOrderDto) {

        String orgId = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15).replace("-", "");
        /**
         * 生成起调参数串——返回给app（微信的支付订单）
         */
        //订单标识


        String nonceStrTemp = WeChatUtils.getRandomStr();

        //支付的金额
//        Double money=backMoney( petrol,petrolInputDTO);
//        Double rechargeAmount = directChargingOrderDto.getRealPrice();

        // 获取金额校验
        Double rechargeAmount = electricityRechargeMapperExtra.getRechargeAmount(directChargingOrderDto.getCommodityId());

        if (rechargeAmount == null) {
            System.out.println("直冲无此价格");
            return null;
        }
//        if ((oilCardRechargeMapperExtra.getDirectRechargeAmount(directChargingOrderDto.getRealPrice())==null)) {
//            System.out.println("直充无此价格");
//            return null;
//        }

        // 积分校验
        int integralAmount = 0;
        if (directChargingOrderDto.getIntegralAmount() <= electricityRechargeMapperExtra.getMaxIntegralAmount(directChargingOrderDto.getCommodityId())) {
            integralAmount = directChargingOrderDto.getIntegralAmount();
        } else {
            System.out.println("积分超过本商品最高抵扣额度");
            return null;
        }
        Double amount = rechargeAmount - integralAmount;

        directChargingOrderDto.setRechargeAmount(rechargeAmount);
        directChargingOrderDto.setRealPrice(amount);
        // userId
        String userId = directChargingOrderDto.getUserId();
        //直充类型
        Integer recordType = directChargingOrderDto.getRecordType();

        String userAccount = directChargingOrderDto.getUserAccount();

        Integer isBrowser = directChargingOrderDto.getIsBrowser();

//        Integer integralAmount = directChargingOrderDto.getIntegralAmount();

        String cardNum = "";

        String openId = directChargingOrderDto.getOpenId();
        // 设置参数
        SortedMap<String, Object> parameters = WeChatH5ParameterConfig.getParametersElectricityRecharge(nonceStrTemp,orgId, amount, rechargeAmount,userAccount, isBrowser, userId, integralAmount,openId,directChargingOrderDto.getCommodityId(),directChargingOrderDto.getRegional());
        boolean insertSalesRecords = false;
        if (recordType == 1){
            insertSalesRecords= insertPhonePillRecords(directChargingOrderDto,orgId);
            System.out.println("插入成功");
        }
        return WeChatH5ParameterConfig.getSign(parameters, nonceStrTemp);
    }

    @Override
    public String findRegional(String orgId) {
        String regional = electricityRechargeMapperExtra.getRegional(orgId);
        return regional;
    }

    @Override
    public int changeState(String orgId) {
        return electricityRechargeMapperExtra.changeState(orgId);
    }

    @Override
    public int changeFinishTime(ElectricityRechargeDto electricityRechargeDto) {
        return electricityRechargeMapperExtra.changeFinishTime(electricityRechargeDto);
    }

    @Override
    public String AlipayRechargeDirect(DirectChargingOrderDto directChargingOrderDto) {
        //检验是否都为空
        if (directChargingOrderDto == null)
            return "传入数据有误（为空）";
        /**
         * 生成起调参数串——返回给app（支付宝的支付订单）
         */
        String orderString = null;//用于保存起调参数,
        AlipayClientConfig alipayClientConfig = AlipayClientConfig.getInstance("8");
        AlipayClient alipayClient = alipayClientConfig.getAlipayClient();
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();

        String orderId = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15).replace("-", "");
        // 获取金额校验
        Double rechargeAmount = electricityRechargeMapperExtra.getRechargeAmount(directChargingOrderDto.getCommodityId());

        if (rechargeAmount == null) {
            System.out.println("无此金额");
            return null;
        }
        // 积分校验
        int integralAmount = 0;
        if (directChargingOrderDto.getIntegralAmount() <= electricityRechargeMapperExtra.getMaxIntegralAmount(directChargingOrderDto.getCommodityId())) {
            integralAmount = directChargingOrderDto.getIntegralAmount();
        } else {
            System.out.println("积分超过本商品最高抵扣额度");
            return null;
        }
        Double amount = rechargeAmount - integralAmount;
        amount = new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP).doubleValue();

        directChargingOrderDto.setRechargeAmount(rechargeAmount);
        directChargingOrderDto.setRealPrice(amount);

        // userId
        String userId = directChargingOrderDto.getUserId();
        //直充类型
        Integer recordType = directChargingOrderDto.getRecordType();
        String rechargeAccount = directChargingOrderDto.getRechargeAccount();
        System.out.println("起吊参数" + directChargingOrderDto.toString());

        request.setReturnUrl(AliPayConfig.Return_url);
        if (recordType == 1){
            request.setBizModel(AliParameterConfig.AlipayElectricityPayModel(orderId,amount, rechargeAmount, recordType,rechargeAccount,integralAmount,userId,directChargingOrderDto.getCommodityId(),directChargingOrderDto.getRegional()));
        }
        request.setNotifyUrl(AliPayConfig.Electricity_url);//支付回调接口

        try {
            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeWapPayResponse response = alipayClient.pageExecute(request);
            orderString = response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        boolean insertSalesRecords = false;
        if (recordType == 1){
            insertSalesRecords= insertPhonePillRecords(directChargingOrderDto,orderId);
            System.out.println("插入成功");
        }
        System.out.println("新增水电费充值记录完毕"+insertSalesRecords);
        return orderString;
    }

    @Override
    public JSONResult updateList(ElectricityRechargeDto electricityRechargeDto) {
        int result = 0;
        for (int i = 0; i < electricityRechargeDto.getOrders().length; i++) {
            ElectricityRechargeDto electricityRechargeDto1 = new ElectricityRechargeDto();
            if (!electricityRechargeDto.getAccount().equals("")){
                electricityRechargeDto1.setRechargeAccount(electricityRechargeDto.getAccount());
            }
            if (!electricityRechargeDto.getRegional().equals("")){
                electricityRechargeDto1.setRegional(electricityRechargeDto.getRegional());
            }
            electricityRechargeDto1.setOrderId(electricityRechargeDto.getOrders()[i]);
            electricityRechargeDto1.setState(electricityRechargeDto.getState());
            result = electricityRechargeMapperExtra.editOrder(electricityRechargeDto1);
        }
        if (result > 0) {
            return new JSONResult(200,"批量修改成功");
        }
        return new JSONResult(500,"批量修改失败");
    }

    @Override
    public JSONResult elcOrder(ElectricityDto electricityDto) {
        if (electricityDto != null) {
            //金额判断
            System.out.println(electricityDto);
            if (electricityDto.getAmount() != 200 && electricityDto.getAmount() != 300 &&
                    electricityDto.getAmount() != 500){
                return new JSONResult("充值金额不正确",500);
            }
            CustomerManageDto customerManageDto = electricityRechargeMapperExtra.getCustomer(electricityDto);
            if (customerManageDto.getIsShutRecharge() == 1) {
                return new JSONResult("今日提交次数已达上限", 500);
            }
            String appSecret = customerManageDto.getAppSecret();

            String string = electricityDto.getAppId() + appSecret + electricityDto.getRechargeAccount() +
                    String.valueOf(electricityDto.getAmount().intValue()) + electricityDto.getOrderId();

            String sign = MD5Util.MD5Encode(string,"UTF-8").toLowerCase();

            System.out.println(sign);

            if (customerManageDto.getBalance() >= electricityDto.getAmount() &&
                    electricityDto.getSign().equals(sign) &&
                    customerManageDto.getBalance() > 0){
                //插入记录
                boolean insertRecords=electricityRechargeMapperExtra.insertThirdOrder(electricityDto)>0;

                // 更新数据
                CustomerManageDto customerManageDto1 = new CustomerManageDto();
                customerManageDto1.setCustomerId(customerManageDto.getCustomerId());
                customerManageDto1.setBalance(customerManageDto.getBalance() - electricityDto.getAmount());
                customerManageDto1.setConsumptionAmount(customerManageDto.getConsumptionAmount() + electricityDto.getAmount());
                mobileMapperExtra.updateConsumption(customerManageDto1);

                if (insertRecords){
                    System.out.println("提交成功");
                    return new JSONResult("提交成功",200);
                }
            }
        }
        return new JSONResult("提交失败", 500);
    }

    private Workbook getCustomerWorkBook(List<ElectricityRechargeDto> list,ElectricityRechargeDto electricityRechargeDto) throws Exception {
        String[] CustomerHead = SystemConstants.ELECTRICITY_EXCEL_HEAD;
        Workbook workbook = null;
        if(list == null) {
            workbook = new SXSSFWorkbook(1);
            Sheet sheet = workbook.createSheet("导出订单记录");//创建工作表
            Row row =sheet.createRow(0);//创建行从第0行开始
            Cell cell = row.createCell(0);
            cell.setCellValue("该时间段无订单记录");
            return workbook;
        }
        try{
            workbook = new SXSSFWorkbook(list.size());
        } catch (Exception e) {
            throw new Exception("Excel数据量过大，请缩短时间间隔");
        }
        Sheet sheet = workbook.createSheet("导出订单记录");//创建工作表
        Row row =sheet.createRow(0);//创建行从第0行开始
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); //对齐方式
        for (int i = 0; i < CustomerHead.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(CustomerHead[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, (short) 7500); // 设置列宽
        }
        for (int i = 0; i < list.size(); i++){
            int count = 0;
            row = sheet.createRow(i+1);
            row.createCell(count++).setCellValue(list.get(i).getRechargeAccount());
            row.createCell(count++).setCellValue(list.get(i).getRegional());
            row.createCell(count++).setCellValue(list.get(i).getRechargeAmount());
            row.createCell(count++).setCellValue(list.get(i).getRealAmount());
            row.createCell(count++).setCellValue(list.get(i).getAccount());
            if (list.get(i).getStartTime() != null)
            row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getStartTime()));
            else {
                row.createCell(count++).setCellValue("");
            }
            if (list.get(i).getEndTime() != null)
            row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getEndTime()));
            else {
                row.createCell(count++).setCellValue("");
            }
            if ("1".equals(list.get(i).getState())){
                row.createCell(count++).setCellValue("待充值");
            }else if ("0".equals(list.get(i).getState())){
                row.createCell(count++).setCellValue("未支付");
            }else if ("2".equals(list.get(i).getState())){
                row.createCell(count++).setCellValue("已充值");
            }
            else if ("3".equals(list.get(i).getState())){
                row.createCell(count++).setCellValue("支付失败");
            }
            else if ("4".equals(list.get(i).getState())){
                row.createCell(count++).setCellValue("充值失败");
            }
            else if ("5".equals(list.get(i).getState())){
                row.createCell(count++).setCellValue("充值中");
            }
            else if ("6".equals(list.get(i).getState())){
                row.createCell(count++).setCellValue("已退款");
            }
            else if ("7".equals(list.get(i).getState())){
                row.createCell(count++).setCellValue("当日超次数");
            }else if ("8".equals(list.get(i).getState())){
                row.createCell(count++).setCellValue("携号转网");
            }else {
                row.createCell(count++).setCellValue("");
            }
            if (list.get(i).getThirdAccount() != null)
            row.createCell(count++).setCellValue(list.get(i).getThirdAccount());
            else {
                row.createCell(count++).setCellValue("");
            }
        }
        return workbook;
    }

    //    插入订单
    public boolean insertPhonePillRecords(DirectChargingOrderDto directChargingOrderDto, String orderId) {
        boolean insertRecords = false;
        double account = electricityRechargeMapperExtra.getRechargeAmount(directChargingOrderDto.getCommodityId());

        ElectricityRechargeDto electricityRechargeDto = new ElectricityRechargeDto();
        electricityRechargeDto.setOrderId(orderId);
        electricityRechargeDto.setUserId(directChargingOrderDto.getUserId());
        electricityRechargeDto.setRegional(directChargingOrderDto.getRegional());
        electricityRechargeDto.setRechargeAmount(account);
        electricityRechargeDto.setRealAmount(account - directChargingOrderDto.getIntegralAmount());
        electricityRechargeDto.setRechargeAccount(directChargingOrderDto.getRechargeAccount());

        if (directChargingOrderDto.getRecordType() == 1) {
            insertRecords=electricityRechargeMapperExtra.insertOrder(electricityRechargeDto)>0;
        }
        return insertRecords;
    }
}
