package com.cqut.czb.bn.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolSalesRecordsDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.entity.global.PetrolCache;
import com.cqut.czb.bn.service.AppBuyPetrolService;
import com.cqut.czb.bn.entity.dto.paymentRecord.GetAlipayClient;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

@Service
public class AppBuyPetrolServiceImpl implements AppBuyPetrolService {

    @Autowired
    private UserIncomeInfoMapper userIncomeInfoMapper;

    @Autowired
    private PetrolSalesRecordsMapperExtra petrolSalesRecordsMapperExtra;

    @Autowired
    private PetrolMapperExtra petrolMapperExtra;

    @Override
    public String BuyPetrol(Petrol petrol, PetrolInputDTO petrolInputDTO) {

        //检验是否都为空
        if (petrol == null && petrolInputDTO == null)
            return "没有油卡或传入数据有误（为空）";

        /**
         * 生成起调参数串——返回给app（支付订单）
         */
        String rs = null;//用于保存起调参数,
        GetAlipayClient getAlipayClient = GetAlipayClient.getInstance(petrolInputDTO.getPayType());//"0"代表的是购油
        AlipayClient alipayClient = getAlipayClient.getAlipayClient();
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();

        //订单标识
        String orgId = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15);
        //支付类型
        String payType =petrolInputDTO.getPayType();
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
        request.setBizModel(petrolSalesRecordsDTO.toAlipayTradeAppPayModel(orgId, payType, money,
                                                                            count, petrolKind, ownerId,
                                                                            petrolNum,
                                                                            actualPayment.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(),
                                                                            petrolInputDTO.getAddressId()));//支付订单

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

    @Override
    public boolean isTodayHadBuy(User user) {
        PetrolSalesRecords petrolSalesRecords=petrolSalesRecordsMapperExtra.selectPetrolSalesRecords(user.getUserId());
        if(petrolSalesRecords==null)
            return false;
        else
        {
            Date date = new Date();
            DateFormat df1 = DateFormat.getDateInstance();//日期格式，精确到日
            System.out.println(df1.format(date));
            if(df1.format(petrolSalesRecords.getTransactionTime())==df1.format(date)){
                System.out.println("今日已经购买了油卡");
                return true;
            }
            else {
                System.out.println("今日没有购买了油卡");
                return true;
            }
        }
    }

    @Override
    public boolean isBuyRepeat(PetrolInputDTO petrolInputDTO) {
        if(petrolMapperExtra.selectDifferentPetrol(petrolInputDTO)==null)
            return false;
        else
            return true;
    }

    @Override
    public String PurchaseControl(PetrolInputDTO petrolInputDTO) {
        //判断是哪种油卡
        if(petrolInputDTO.getPetrolKind()==0){//0代表国通卡
            System.out.println("购买国通卡");
            Petrol petrol=PetrolCache.randomPetrol(petrolInputDTO); //随机获取一张卡
            if(petrol==null)
                return "油卡申请失败，无此类油卡";
            petrolInputDTO.setPayType("0");//0为购买油卡，2为充值
            String buyPetrol=BuyPetrol(petrol,petrolInputDTO);//返回生成的支付单串
            return buyPetrol;
        }else if(petrolInputDTO.getPetrolKind()==1||petrolInputDTO.getPetrolKind()==2){
            System.out.println("购买中石油1或中石化2"+petrolInputDTO.getPetrolKind());
            //判断是否已经买了相应的卡
            Petrol petrol1= petrolMapperExtra.selectDifferentPetrol(petrolInputDTO);
            if(petrol1==null){//为空则没有购买过相应的卡————执行购买油卡
                //随机产生相应的卡
                Petrol petrol2=PetrolCache.randomPetrol(petrolInputDTO); //随机获取一张卡
                if(petrol2==null)
                    return "油卡申请失败，无此类油卡";
                petrolInputDTO.setPayType("0");//0为购买油卡，2为充值
                String buyPetrol=BuyPetrol(petrol2,petrolInputDTO);//返回生成的支付单串
                return buyPetrol;
            }else{//不为空则————执行充值
                petrolInputDTO.setPayType("2");//0为购买油卡，2为充值
                String buyPetrol=BuyPetrol(petrol1,petrolInputDTO);//返回生成的支付单串
                return buyPetrol;
            }

        }




        return null;
    }

}
