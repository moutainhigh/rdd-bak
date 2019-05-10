package com.cqut.czb.bn.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolSalesRecordsDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.entity.global.PetrolCache;
import com.cqut.czb.bn.service.AppBuyPetrolService;
import com.cqut.czb.bn.entity.dto.paymentRecord.GetAlipayClient;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

@Service
public class AppBuyPetrolServiceImpl implements AppBuyPetrolService {

    @Autowired
    private UserIncomeInfoMapper userIncomeInfoMapper;

    @Override
    public String BuyPetrol(Petrol petrol, PetrolInputDTO petrolInputDTO) {

        //检验是否都为空
        if (petrol == null && petrolInputDTO == null)
            return "没有油卡或传入数据有误（为空）";

        /**
         * 生成起调参数串——返回给app（支付订单）
         */
        String rs = null;//用于保存起调参数
        GetAlipayClient getAlipayClient = GetAlipayClient.getInstance("0");//"0"代表的是购油
        AlipayClient alipayClient = getAlipayClient.getAlipayClient();
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();

        //订单标识
        String orgId = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15);
        //支付类型(没有对应的类型值，默认回调后执行3类型，暂时只是支持支付宝支付)/死数据****************/
        String payType = "1";//1代表爱虎支付宝(后面可能涉及到多种支付方式)
        //支付的金额
        Double money = petrol.getPetrolPrice();
        System.out.println("money" + money);
        //购买的数量/******************/
        Integer count = 1;
        //购买的油卡类型
        Integer petrolKind = petrol.getPetrolKind();
        System.out.println("petrolKind" + petrolKind);
        //购买的油卡号
        String petrolNum = petrol.getPetrolNum();
        System.out.println("petrolNum" + petrolNum);

        //购买人的id
        String ownerId = petrol.getOwnerId();
        System.out.println("ownerId" + ownerId);

        //获取用户的余额(balance)
        UserIncomeInfo balance = userIncomeInfoMapper.selectByPrimaryKey(petrolInputDTO.getOwnerId());
        BigDecimal actualPayment;
        if (balance != null) {
            //返佣收益
            double fanyongIncome = balance.getFanyongIncome();
            System.out.println("返佣" + balance.getFanyongIncome());
            //推荐收益
            double shareIncome = balance.getShareIncome();
            System.out.println("推荐" + balance.getShareIncome());
            actualPayment = BigDecimal.valueOf(money).subtract(BigDecimal.valueOf(fanyongIncome)).subtract(BigDecimal.valueOf(shareIncome));
        } else {
            actualPayment = BigDecimal.valueOf(money);
        }

        if (actualPayment.compareTo(new BigDecimal(0.00)) == -1) {
            actualPayment = new BigDecimal(0.00);
        }

        System.out.println("actualPayment" + actualPayment);

        PetrolSalesRecordsDTO petrolSalesRecordsDTO = new PetrolSalesRecordsDTO();

        //对判断是否能生成订单
        if (orgId == null || payType == null || money == null || count == null || petrolKind == null || ownerId == null || petrolNum == null) {
            return "无法生成支付订单";
        }
        request.setBizModel(petrolSalesRecordsDTO.toAlipayTradeAppPayModel(orgId, payType, money, count, petrolKind, ownerId, petrolNum, actualPayment.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));//支付订单

        request.setNotifyUrl(getAlipayClient.getCallBackUrl());//支付回调接口
        try {
            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            rs = response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return rs;
    }

}
