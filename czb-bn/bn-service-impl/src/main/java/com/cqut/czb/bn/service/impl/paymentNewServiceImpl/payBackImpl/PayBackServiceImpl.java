package com.cqut.czb.bn.service.impl.paymentNewServiceImpl.payBackImpl;

import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.dao.mapper.food.DishOrderMapper;
import com.cqut.czb.bn.dao.mapper.integral.IntegralPurchaseMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.ServerCouponMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.VehicleCleanOrderMapperExtra;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatCommodityMapper;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatCommodityOrderMapper;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatGoodsDeliveryRecordsMapper;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatStockMapperExtra;
import com.cqut.czb.bn.entity.dto.H5StockDTO;
import com.cqut.czb.bn.entity.dto.appCaptchaConfig.PhoneCode;
import com.cqut.czb.bn.entity.dto.equityPayment.EquityPaymentDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralInfoDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralLogDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralRechargeDTO;
import com.cqut.czb.bn.entity.entity.Shop;
import com.cqut.czb.bn.entity.entity.integral.IntegralPurchaseRecord;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatCommodity;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatCommodityOrder;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatGoodsDeliveryRecords;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatStock;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.InfoSpreadService;
import com.cqut.czb.bn.service.PartnerVipIncomeService;
import com.cqut.czb.bn.service.PaymentProcess.DataProcessService;
import com.cqut.czb.bn.service.PaymentProcess.DealWithPetrolCouponsService;
import com.cqut.czb.bn.service.PaymentProcess.FanYongService;
import com.cqut.czb.bn.service.PaymentProcess.PetrolRecharge;
import com.cqut.czb.bn.service.impl.equityPaymentServiceImpl.EquityPaymentThirdImpl;
import com.cqut.czb.bn.service.impl.paymentNewServiceImpl.H5PaymentBuyCommodityServiceImpl;
import com.cqut.czb.bn.service.impl.personCenterImpl.AlipayConfig;
import com.cqut.czb.bn.service.impl.vehicleServiceImpl.ServerOrderServiceImpl;
import com.cqut.czb.bn.service.integral.IntegralService;
import com.cqut.czb.bn.service.paymentNew.paybackService.PayBackService;
import com.cqut.czb.bn.util.config.SendMesConfig.MesInfo;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service("PayBackService")
public class PayBackServiceImpl implements PayBackService {

    @Autowired
    IntegralPurchaseMapperExtra integralPurchaseMapperExtra;

    @Autowired
    public IntegralService integralService;

    @Autowired
    WeChatCommodityMapper weChatCommodityMapper;

    @Autowired
    DictMapperExtra dictMapperExtra;

    @Autowired
    private DataProcessService dataProcessService;

    @Autowired
    private PetrolMapperExtra petrolMapperExtra;

    @Autowired
    private PetrolSalesRecordsMapperExtra petrolSalesRecordsMapperExtra;

    @Autowired
    FanYongService fanYongService;

    @Autowired
    PetrolRecharge petrolRecharge;

    @Autowired
    PetrolDeliveryRecordsMapper petrolDeliveryRecordsMapper;

    @Autowired
    PetrolDeliveryRecordsMapperExtra petrolDeliveryRecordsMapperExtra;

    @Autowired
    ConsumptionRecordMapperExtra consumptionRecordMapperExtra;

    @Autowired
    InfoSpreadService infoSpreadService;

    @Autowired
    OrderMapperExtra orderMapperExtra;

    @Autowired
    UserMapperExtra userMapperExtra;

    @Autowired
    VipRechargeRecordsMapperExtra vipRechargeRecordsMapperExtra;

    @Autowired
    VehicleCleanOrderMapperExtra vehicleCleanOrderMapperExtra;

    @Autowired
    ServerCouponMapperExtra serverCouponMapperExtra;

    @Autowired
    DishOrderMapper dishOrderMapper;

    @Autowired
    ServerOrderServiceImpl serverOrderService;

    @Autowired
    PartnerVipIncomeService partnerVipIncomeService;

    @Autowired
    WeChatCommodityOrderMapper weChatCommodityOrderMapper;

    @Autowired
    WeChatGoodsDeliveryRecordsMapper weChatGoodsDeliveryRecordsMapper;

    @Autowired
    ShopMapper shopMapper;

    @Autowired
    DealWithPetrolCouponsService dealWithPetrolCouponsService;

    @Autowired
    WeChatStockMapperExtra weChatStockMapperExtra;

    @Autowired
    H5PaymentBuyCommodityServiceImpl h5PaymentBuyCommodityService;

    @Autowired
    H5PaymentBuyCommodityMapperExtra h5PaymentBuyCommodityMapperExtra;

    /**
     *
     * @Description： 支付宝回调
     * @author： WangYa
     * @Data： 2021/2/17 15:40
     */
    @Override
    public Map AliPayback(Object[] param, String consumptionType) {
        // 支付宝支付
        Map<String, String> result = new HashMap<>();
        Map<String, String> params = (HashMap<String, String>) param[0];
        if(consumptionType.equals("Integral")){//购买积分
            if (getIntegralOrderAli(params) == 1) {
                result.put("success", AlipayConfig.response_success);
                return result;
            }
        }else if (consumptionType.equals("H5Commodity")){
            if (getCommodityOrderAli(params) == 1) {
                result.put("success", AlipayConfig.response_success);
                return result;
            }
        }else if (consumptionType.equals("EquityGoods")){
            if (getEquityGoodsOrderAli(params) == 1) {
                result.put("success", AlipayConfig.response_success);
                return result;
            }
        } else {
            result.put("fail", AlipayConfig.response_fail);
            return result;
        }
        result.put("fail", AlipayConfig.response_fail);
        return result;
    }

    /**
     *
     * @Description： 微信回调
     * @author： WangYa
     * @Data： 2021/2/17 15:41
     */
    @Override
    public Map<String, Integer> WeChatPayBack(Object[] param, String consumptionType) {
        Map<String, Object> restmap = (HashMap<String, Object>) param[0];
        Map<String, Integer> result = new HashMap<>();
        if(consumptionType.equals("Integral")){
            result.put("success", getAddBuyIntegralOrderWechat(restmap));
        } else if(consumptionType.equals("AppletPayment")){
            result.put("success", addAppletPaymentOrderWeChat(restmap));
        } else if(consumptionType.equals("EquityGoods")){
            result.put("success", getAddBuyEquityGoodsOrderWechat(restmap));
        } else {
            result.put("fail",0);
        }
        return result;
    }

    /**
     * 小程序库存支付成功(微信)
     * @param restmap
     * @return
     */
    public Integer addAppletPaymentOrderWeChat(Map<String, Object> restmap) {
        String[] resDate = restmap.get("attach").toString().split("\\^");
        //商户订单号
        String out_trade_no = restmap.get("out_trade_no").toString();
        //微信交易订单号
        String thirdOrderId = restmap.get("transaction_id").toString();
        String[] temp;
        int flag = 0;
        String stockId = "";
        double money = Double.valueOf(restmap.get("total_fee").toString());
        money = (BigDecimal.valueOf(money).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)).doubleValue();
        System.out.println("微信小程序支付:"+money);
        String ownerId = "";
        String userId = "";
        int integralAmount = 0;
        for (String data : resDate) {
            temp = data.split("\'");
            if (temp.length < 2) {
                continue;
            }
            //商家订单
            if ("stockId".equals(temp[0])) {
                stockId = temp[1];
            }
            //用户id
            if ("userId".equals(temp[0])) {
                userId = temp[1];
            }

            if("integralAmount".equals(temp[0])){
                integralAmount = Integer.valueOf(temp[1]);
            }

        }

        // 更新
        H5StockDTO h5StockDTO = new H5StockDTO();
        h5StockDTO.setStockId(stockId);
        h5StockDTO.setThirdOrder(thirdOrderId);

        // 判断支付人和付款人是否是同一个

        if (h5PaymentBuyCommodityService.judgeChangeSte(0, stockId, userId)) {
            System.out.println("库存更新成功");
            boolean update = h5PaymentBuyCommodityMapperExtra.updateStockState(h5StockDTO) > 0;
            //插入log记录
            IntegralLogDTO integralLogDTO = integralService.getIntegralInfo(userId);
            integralLogDTO.setOrderId(stockId);
            integralLogDTO.setIntegralLogId(System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15).replace("-", ""));
            integralLogDTO.setUserId(userId);
            integralLogDTO.setIntegralLogType(5);
            integralLogDTO.setRemark("抵扣");
            integralLogDTO.setIntegralAmount(integralAmount);
            integralPurchaseMapperExtra.insertIntegralLog(integralLogDTO);

            IntegralInfoDTO integralInfoDTO = integralService.getGotTotal(userId);
            integralInfoDTO.setCurrentTotal(integralLogDTO.getBeforeIntegralAmount() - integralLogDTO.getIntegralAmount());
            integralInfoDTO.setUserId(userId);
            integralInfoDTO.setGotTotal(integralInfoDTO.getGotTotal());
            integralPurchaseMapperExtra.updateIntegralInfo(integralInfoDTO);
        }
        return 1;
    }

    /**
     * 小程序库存支付成功(支付宝)
     * @param params
     * @return
     */
    private int getCommodityOrderAli(Map<String, String> params) {
        String[] resDate = params.get("passback_params").split("\\^");
        String[] temp;
        String thirdOrderId = params.get("trade_no");
        String orgId = "";
        System.out.println("第三方订单" + params.get("trade_no"));
        String ownerId = "";
        String stockId = "";
        double money = 0.0 ;
        int integralAmount = 0;
        for (String data : resDate) {
            temp = data.split("\'");
            if (temp.length < 2) {
                continue;
            }
            //商家订单
            if ("orgId".equals(temp[0])) {
                orgId = temp[1];
            }
            if("money".equals(temp[0])){
                money = Double.valueOf(temp[1]);;
                money = (BigDecimal.valueOf(money).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)).doubleValue();

            }
            if ("stockId".equals(temp[0])) {
                stockId = temp[1];
            }
            //用户id
            if ("ownerId".equals(temp[0])) {
                ownerId = temp[1];
            }

            if("integralAmount".equals(temp[0])){
                integralAmount = Integer.valueOf(temp[1]);
            }
        }
        System.out.println(ownerId);
        // 更新
        H5StockDTO h5StockDTO = new H5StockDTO();
        h5StockDTO.setStockId(stockId);
        h5StockDTO.setThirdOrder(thirdOrderId);

        // 判断支付人和付款人是否是同一个

        System.out.println("库存更新成功");
        boolean update = h5PaymentBuyCommodityMapperExtra.updateStockState(h5StockDTO) > 0;


        //插入log记录
        IntegralLogDTO integralLogDTO = integralService.getIntegralInfo(ownerId);
        integralLogDTO.setOrderId(orgId);
        integralLogDTO.setIntegralLogId(System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15).replace("-", ""));
        integralLogDTO.setUserId(ownerId);
        integralLogDTO.setIntegralLogType(5);
        integralLogDTO.setRemark("抵扣");
        integralLogDTO.setIntegralAmount(integralAmount);
        integralPurchaseMapperExtra.insertIntegralLog(integralLogDTO);

        IntegralInfoDTO integralInfoDTO = integralService.getGotTotal(ownerId);
        integralInfoDTO.setCurrentTotal(integralLogDTO.getBeforeIntegralAmount() - integralLogDTO.getIntegralAmount());
        integralInfoDTO.setUserId(ownerId);
        integralInfoDTO.setGotTotal(integralInfoDTO.getGotTotal());
        integralPurchaseMapperExtra.updateIntegralInfo(integralInfoDTO);
        return 1;
    }
    /**
     * 积分购买-支付宝
     * @param params
     * @return
     */
    private int getIntegralOrderAli(Map<String, String> params) {
        String[] resDate = params.get("passback_params").split("\\^");
        String[] temp;
        String thirdOrderId = params.get("trade_no");
        System.out.println("第三方订单" + params.get("trade_no"));
        String orgId = "";
        String userId = "";
        double amount = 0;
        int integralAmount = 0;
        String ownerId = "";
        double actualPayment = 0;

        for (String data : resDate) {
            temp = data.split("\'");
            if (temp.length < 2) {//判空
                continue;
            }
            if ("trade_no".equals(temp[0])) {
                System.out.println("该交易在支付宝系统中的交易流水号:" + temp[1]+ "\n" + thirdOrderId);
            }
            if ("out_trade_no".equals(temp[0])) {
                System.out.println("商户网站唯一订单号:" + temp[1]);
            }
            if ("orgId".equals(temp[0])) {
                orgId = temp[1];
                System.out.println("商家订单orgId:" + orgId);
            }
            if ("userId".equals(temp[0])) {
                userId = temp[1];
            }
            if ("integralAmount".equals(temp[0])) {
                integralAmount = Integer.valueOf(temp[1]);
            }
            if ("amount".equals(temp[0])) {
                amount = Double.valueOf(temp[1]);
            }
            if ("actualPayment".equals(temp[0])) {
                actualPayment = Double.valueOf(temp[1]);
                System.out.println("实际支付actualPayment:" + actualPayment);
            }
        }

        // 更新
        IntegralPurchaseRecord integralPurchaseRecord = new IntegralPurchaseRecord();
        integralPurchaseRecord.setIntegralPurchaseRecordId(orgId);
        integralPurchaseRecord.setThirdTradeNum(thirdOrderId);
        integralPurchaseRecord.setUpdateAt(new Date());
        integralPurchaseRecord.setIsReceived(1);
        boolean update = integralPurchaseMapperExtra.updateIntegralPurchaseRecord(integralPurchaseRecord) > 0;
        System.out.println("更新成功"+ update);

        //插入log记录
        IntegralLogDTO integralLogDTO = integralService.getIntegralInfo(userId);
        integralLogDTO.setIntegralLogId(System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15).replace("-", ""));
        integralLogDTO.setUserId(userId);
        integralLogDTO.setOrderId(orgId);
        integralLogDTO.setIntegralLogType(4);
        integralLogDTO.setRemark("购买");
        integralLogDTO.setIntegralAmount(integralAmount);
        integralPurchaseMapperExtra.insertIntegralLog(integralLogDTO);

        IntegralInfoDTO integralInfoDTO = integralService.getGotTotal(userId);
        integralInfoDTO.setCurrentTotal(integralLogDTO.getIntegralAmount() + integralLogDTO.getBeforeIntegralAmount());
        integralInfoDTO.setUserId(userId);
        integralInfoDTO.setGotTotal(integralInfoDTO.getGotTotal() + integralLogDTO.getIntegralAmount());
        integralPurchaseMapperExtra.updateIntegralInfo(integralInfoDTO);
        return 1;

    }

    /**
     * 权益商品购买-支付宝
     * @param params
     * @return
     */
    private int getEquityGoodsOrderAli(Map<String, String> params) {
        String[] resDate = params.get("passback_params").split("\\^");
        String[] temp;
        String thirdOrderId = params.get("trade_no");
        System.out.println("第三方订单" + params.get("trade_no"));
        String orderId = "";
        String userId = "";
        double amount = 0.0;
        String account = "";
        String productCode = "";
        int buyNum = 0;
        int isCallBack = 0;
        int tradeType = 0;
        String clientIP = "";
        double unitPrice = 0;
        int totalPrice = 0;
        String goodsId = "";
        int rechargeType = 0;
        int integralAmount = 0;

        for (String data : resDate) {
            temp = data.split("\'");
            if (temp.length < 2) {//判空
                continue;
            }
            if ("trade_no".equals(temp[0])) {
                System.out.println("该交易在支付宝系统中的交易流水号:" + temp[1]+ "\n" + thirdOrderId);
            }
            if ("out_trade_no".equals(temp[0])) {
                System.out.println("商户网站唯一订单号:" + temp[1]);
            }
            if ("orderId".equals(temp[0])) {
                orderId = temp[1];
            }
            if ("userId".equals(temp[0])) {
                userId = temp[1];
            }
            if ("integralAmount".equals(temp[0])) {
                integralAmount = Integer.valueOf(temp[1]);
            }
            if ("amount".equals(temp[0])) {
                amount = Double.valueOf(temp[1]);
            }
            if ("account".equals(temp[0])) {
                account = temp[1];
            }
            if ("productCode".equals(temp[0])) {
                productCode = temp[1];
            }
            if ("buyNum".equals(temp[0])) {
                buyNum = Integer.valueOf(temp[1]);
            }
            if ("isCallBack".equals(temp[0])) {
                isCallBack = Integer.valueOf(temp[1]);
            }
            if ("tradeType".equals(temp[0])) {
                tradeType = Integer.valueOf(temp[1]);
            }
            if ("unitPrice".equals(temp[0])) {
                unitPrice = Double.valueOf(temp[1]);
            }
            if ("totalPrice".equals(temp[0])) {
                totalPrice = Integer.valueOf(temp[1]);
            }
            if ("goodsId".equals(temp[0])) {
                goodsId = temp[1];
            }
            if ("rechargeType".equals(temp[0])) {
                rechargeType = Integer.valueOf(temp[1]);
            }
        }

        // 获取订单信息
        EquityPaymentDTO equityPaymentDTO1  = new EquityPaymentDTO();
        equityPaymentDTO1.setOrderId(orderId);
        EquityPaymentDTO equityPaymentDTO2 = integralPurchaseMapperExtra.getEquityGoodsRecord(equityPaymentDTO1);
        equityPaymentDTO2.setRechargeType(rechargeType);
        System.out.println(equityPaymentDTO2.toString());
        JSONResult<String> stringJSONResult = null;
        if(equityPaymentDTO2.getRechargeType() == 1){
            stringJSONResult = EquityPaymentThirdImpl.videoCharge(equityPaymentDTO2);
        }else if (equityPaymentDTO2.getRechargeType() == 2){
            stringJSONResult = EquityPaymentThirdImpl.gameCharge(equityPaymentDTO2);
        }else {
            throw new RuntimeException("充值类型错误！");
        }
        System.out.println(stringJSONResult);

        // 更新
        EquityPaymentDTO equityPaymentDTO = new EquityPaymentDTO();
        equityPaymentDTO.setOrderId(orderId);
        equityPaymentDTO.setThirdOrder(thirdOrderId);
        equityPaymentDTO.setGoodsId(goodsId);
        equityPaymentDTO.setBuyNum(buyNum);
        System.out.println("更新成功");
        boolean update = integralPurchaseMapperExtra.updateEquityGoodsRecord(equityPaymentDTO) > 0;

        //更新销量
        boolean updateSoldNumber = integralPurchaseMapperExtra.updateSoldNumber(equityPaymentDTO) > 0;

        //插入log记录
        IntegralLogDTO integralLogDTO = integralService.getIntegralInfo(userId);
        integralLogDTO.setOrderId(orderId);
        integralLogDTO.setIntegralLogId(System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15).replace("-", ""));
        integralLogDTO.setUserId(userId);
        integralLogDTO.setIntegralLogType(5);
        integralLogDTO.setRemark("抵扣");
        integralLogDTO.setIntegralAmount(integralAmount);
        integralPurchaseMapperExtra.insertIntegralLog(integralLogDTO);

        IntegralInfoDTO integralInfoDTO = integralService.getGotTotal(userId);
        integralInfoDTO.setCurrentTotal(integralLogDTO.getBeforeIntegralAmount() - integralLogDTO.getIntegralAmount());
        integralInfoDTO.setUserId(userId);
        integralInfoDTO.setGotTotal(integralInfoDTO.getGotTotal());
        integralPurchaseMapperExtra.updateIntegralInfo(integralInfoDTO);
        return 1;
    }
    // 积分购买（微信）
    public int getAddBuyIntegralOrderWechat(Map<String, Object> restmap){
        String[] resDate = restmap.get("attach").toString().split("\\^");
        String[] temp;
        String thirdOrderId = restmap.get("transaction_id").toString();
        String orderId = "";
        String userId = "";
        double amount = 0;
        int integralAmount = 0;
        for (String data : resDate) {
            temp = data.split("\'");
            if (temp.length < 2) {//判空
                continue;
            }
            if ("orderId".equals(temp[0])) {
                orderId = temp[1];
            }
            if ("userId".equals(temp[0])) {
                userId = temp[1];
            }
            if ("integralAmount".equals(temp[0])) {
                integralAmount = Integer.valueOf(temp[1]);
            }
            if ("amount".equals(temp[0])) {
                amount = Double.valueOf(temp[1]);
            }
        }
        // 更新
        IntegralPurchaseRecord integralPurchaseRecord = new IntegralPurchaseRecord();
        integralPurchaseRecord.setIntegralPurchaseRecordId(orderId);
        integralPurchaseRecord.setThirdTradeNum(thirdOrderId);
        integralPurchaseRecord.setUpdateAt(new Date());
        integralPurchaseRecord.setIsReceived(1);
        System.out.println("更新成功");
        boolean update = integralPurchaseMapperExtra.updateIntegralPurchaseRecord(integralPurchaseRecord) > 0;

        //插入log记录
        IntegralLogDTO integralLogDTO = integralService.getIntegralInfo(userId);
        integralLogDTO.setOrderId(orderId);
        integralLogDTO.setIntegralLogId(System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15).replace("-", ""));
        integralLogDTO.setUserId(userId);
        integralLogDTO.setIntegralLogType(4);
        integralLogDTO.setIntegralAmount(integralAmount);
        integralPurchaseMapperExtra.insertIntegralLog(integralLogDTO);

        IntegralInfoDTO integralInfoDTO = integralService.getGotTotal(userId);
        integralInfoDTO.setCurrentTotal(integralLogDTO.getIntegralAmount() + integralLogDTO.getBeforeIntegralAmount());
        integralInfoDTO.setUserId(userId);
        integralInfoDTO.setGotTotal(integralInfoDTO.getGotTotal() + integralLogDTO.getIntegralAmount());
        integralPurchaseMapperExtra.updateIntegralInfo(integralInfoDTO);
        return 1;
    }

    // 权益商品购买（微信）
    public int getAddBuyEquityGoodsOrderWechat(Map<String, Object> restmap){
        String[] resDate = restmap.get("attach").toString().split("\\^");
        String[] temp;
        String thirdOrderId = restmap.get("transaction_id").toString();
        String orderId = "";
        String userId = "";
        double amount = 0;
        String account = "";
        String productCode = "";
        int buyNum = 0;
        int isCallBack = 0;
        int tradeType = 0;
        String clientIP = "";
        double unitPrice = 0;
        int totalPrice = 0;
        String goodsId = "";
        int rechargeType = 0;
        int integralAmount = 0;

        for (String data : resDate) {
            temp = data.split("\'");
            if (temp.length < 2) {//判空
                continue;
            }
            if ("orderId".equals(temp[0])) {
                orderId = temp[1];
            }
            if ("userId".equals(temp[0])) {
                userId = temp[1];
            }
            if ("integralAmount".equals(temp[0])) {
                integralAmount = Integer.valueOf(temp[1]);
            }
            if ("buyNum".equals(temp[0])) {
                buyNum = Integer.valueOf(temp[1]);
            }
            if ("rechargeType".equals(temp[0])) {
                rechargeType = Integer.valueOf(temp[1]);
            }
            if ("tradeType".equals(temp[0])) {
                tradeType = Integer.valueOf(temp[1]);
            }
            if ("goodsId".equals(temp[0])) {
                goodsId = temp[1];
            }
        }


        // 更新
        EquityPaymentDTO equityPaymentDTO = new EquityPaymentDTO();
        equityPaymentDTO.setOrderId(orderId);
        equityPaymentDTO.setThirdOrder(thirdOrderId);
        equityPaymentDTO.setGoodsId(goodsId);
        equityPaymentDTO.setBuyNum(buyNum);
        System.out.println("更新成功");
        boolean update = integralPurchaseMapperExtra.updateEquityGoodsRecord(equityPaymentDTO) > 0;

        // 获取订单信息
        System.out.println("获取订单信息");
        EquityPaymentDTO equityPaymentDTO1  = new EquityPaymentDTO();
        equityPaymentDTO1.setOrderId(orderId);
        EquityPaymentDTO equityPaymentDTO2 = integralPurchaseMapperExtra.getEquityGoodsRecord(equityPaymentDTO1);
        equityPaymentDTO2.setRechargeType(rechargeType);
        equityPaymentDTO2.setTradeType(tradeType);
        System.out.println(equityPaymentDTO2.toString());
        JSONResult<String> stringJSONResult = null;
        if(equityPaymentDTO2.getRechargeType() == 1){
            stringJSONResult = EquityPaymentThirdImpl.videoCharge(equityPaymentDTO2);
        }else if (equityPaymentDTO2.getRechargeType() == 2){
            stringJSONResult = EquityPaymentThirdImpl.gameCharge(equityPaymentDTO2);
        }else {
            throw new RuntimeException("充值类型错误！");
        }
        System.out.println(stringJSONResult);

        //更新销量
        boolean updateSoldNumber = integralPurchaseMapperExtra.updateSoldNumber(equityPaymentDTO) > 0;


        //插入log记录
        IntegralLogDTO integralLogDTO = integralService.getIntegralInfo(userId);
        integralLogDTO.setOrderId(orderId);
        integralLogDTO.setIntegralLogId(System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15).replace("-", ""));
        integralLogDTO.setUserId(userId);
        integralLogDTO.setIntegralLogType(5);
        integralLogDTO.setRemark("抵扣");
        integralLogDTO.setIntegralAmount(integralAmount);
        integralPurchaseMapperExtra.insertIntegralLog(integralLogDTO);

        IntegralInfoDTO integralInfoDTO = integralService.getGotTotal(userId);
        integralInfoDTO.setCurrentTotal(integralLogDTO.getBeforeIntegralAmount() - integralLogDTO.getIntegralAmount());
        integralInfoDTO.setUserId(userId);
        integralInfoDTO.setGotTotal(integralInfoDTO.getGotTotal());
        integralPurchaseMapperExtra.updateIntegralInfo(integralInfoDTO);
        return 1;

    }
}
