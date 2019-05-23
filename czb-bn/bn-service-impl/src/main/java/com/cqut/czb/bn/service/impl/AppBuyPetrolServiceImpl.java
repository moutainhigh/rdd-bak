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
import java.util.*;

@Service
public class AppBuyPetrolServiceImpl implements AppBuyPetrolService {

    @Autowired
    private UserIncomeInfoMapperExtra userIncomeInfoMapperExtra;

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

        //来源合同——合同id
        String contractId=petrolInputDTO.getContractId();
        System.out.println("contractId" + contractId);
        //订单标识
        String orgId = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15);
        System.out.println("orgId" + orgId);
        //支付类型
        String payType =petrolInputDTO.getPayType();
        //支付的金额
        Double money = petrol.getPetrolPrice();
        System.out.println("money" + money);
        //购买的油卡类型
        Integer petrolKind = petrol.getPetrolKind();
        System.out.println("petrolKind" + petrolKind);
        //购买的油卡号
        String petrolNum = petrol.getPetrolNum();
        System.out.println("petrolNum" + petrolNum);
        //购买人的id
        String ownerId = petrol.getOwnerId();
        System.out.println("ownerId" + ownerId);
        //获取用户收益信息
        UserIncomeInfo userIncomeInfo = userIncomeInfoMapperExtra.selectOneUserIncomeInfo(petrolInputDTO.getOwnerId());
        //用户实际支付的钱
        BigDecimal actualPayment = null;
        if (userIncomeInfo != null) {
            System.out.println("收益记录不为空");
            //返佣收益
            double fanyongIncome=0.00;
            if(userIncomeInfo.getFanyongIncome()!=null)
            {
                fanyongIncome = userIncomeInfo.getFanyongIncome();
                System.out.println("返佣" + userIncomeInfo.getFanyongIncome());
            }
            //推荐收益
            double shareIncome =0.00;
            if(userIncomeInfo.getShareIncome()!=null)
            {
                shareIncome = userIncomeInfo.getShareIncome();
                System.out.println("推荐" + userIncomeInfo.getShareIncome());
            }
            //其他收益
            double otherIncome=0.00;
            if(userIncomeInfo.getOtherIncome()!=null)
            {
                otherIncome=userIncomeInfo.getOtherIncome();
                System.out.println("其他收益" + otherIncome);
            }
            //提现
            double withdrawed=0.00;
            if(userIncomeInfo.getWithdrawed()!=null)
            {
                withdrawed=userIncomeInfo.getWithdrawed();
                System.out.println("提现" + withdrawed);
            }
            //余额
            BigDecimal balance=BigDecimal.valueOf(fanyongIncome).add(BigDecimal.valueOf(otherIncome)).add(BigDecimal.valueOf(shareIncome)).subtract(BigDecimal.valueOf(withdrawed));
                if(balance.compareTo(new BigDecimal(0.00))==-1||balance.compareTo(new BigDecimal(0.00))==0)
                {//没有余额
                    actualPayment= BigDecimal.valueOf(money);
                    System.out.println("余额为0，实际应支付:"+actualPayment);
                }
                else {//有余额
                    double payMoney=( BigDecimal.valueOf(money).subtract(balance)).doubleValue();
                    if(payMoney==0||payMoney<0){
                        actualPayment = BigDecimal.valueOf(0.00);
                    }
                    else {
                        actualPayment=BigDecimal.valueOf(payMoney);
                    }
                    System.out.println("实际应支付:"+actualPayment);
                }
        } else {
            System.out.println("收益记录为空");
            actualPayment = BigDecimal.valueOf(money);
            System.out.println("余额为0，实际应支付:"+actualPayment);
        }

        //对判断是否能生成订单
        if (orgId == null || payType == null || money == null || petrolKind == null || ownerId == null || petrolNum == null) {
            return "无法生成支付订单";
        }
        PetrolSalesRecordsDTO petrolSalesRecordsDTO = new PetrolSalesRecordsDTO();
        request.setBizModel(petrolSalesRecordsDTO.toAlipayTradeAppPayModel(orgId, payType,contractId ,money,
                                                                             petrolKind, ownerId, petrolNum,
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
    public boolean isTodayHadBuy(PetrolInputDTO petrolInputDTO) {
        PetrolSalesRecords petrolSalesRecords=petrolSalesRecordsMapperExtra.selectPetrolSalesRecords(petrolInputDTO);
        if(petrolSalesRecords==null||petrolSalesRecords.getTransactionTime()==null)
        {
            System.out.println("今日是否买过油卡"+petrolSalesRecords==null);
            return false;
        }
        else
        {
            Date date = new Date();
            DateFormat df1 = DateFormat.getDateInstance();//日期格式，精确到日
            System.out.println(df1.format(date));
            System.out.println(df1.format(petrolSalesRecords.getTransactionTime()).equals(df1.format(date)));
            if(df1.format(petrolSalesRecords.getTransactionTime()).equals(df1.format(date))){
                System.out.println("今日已经购买了油卡,或充值了一次，请明天再来");
                return true;
            }
            else {
                System.out.println("今日没有购买油卡");
                return false;
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
    public Map<String,String> PurchaseControl(PetrolInputDTO petrolInputDTO) {
        //判断是哪种油卡
        if(petrolInputDTO.getPetrolKind()==0){//0代表国通卡
            System.out.println("购买国通卡：0");
            Petrol petrol=PetrolCache.randomPetrol(petrolInputDTO); //随机获取一张卡
            if(petrol==null)
            {
                Map<String,String> info=new HashMap<>();
                info.put("-1","油卡申请失败，无此类油卡");
                return info;
            }
            petrolInputDTO.setPayType("0");//0为购买油卡，2为充值
            String buyPetrol=BuyPetrol(petrol,petrolInputDTO);//返回生成的支付单串
            if(buyPetrol!=null){
                Map<String,String> info=new HashMap<>();
                info.put("0",buyPetrol);
                return info;
            }else {
                Map<String,String> info=new HashMap<>();
                info.put("-1","油卡申请失败，信息有误，无此法生成订单");
                return info;
            }
        }else if(petrolInputDTO.getPetrolKind()==1||petrolInputDTO.getPetrolKind()==2){
            System.out.println("购买中石油1或中石化2："+petrolInputDTO.getPetrolKind());
            //判断是否已经买了相应的卡
            Petrol petrol1= petrolMapperExtra.selectDifferentPetrol(petrolInputDTO);
            if(petrol1==null){//为空则没有购买过相应的卡————执行购买油卡
                //随机产生相应的卡
                Petrol petrol2=PetrolCache.randomPetrol(petrolInputDTO); //随机获取一张卡
                if(petrol2==null)
                {
                    Map<String,String> info=new HashMap<>();
                    info.put("-1","油卡申请失败，无此类油卡");
                    return info;
                }
                petrolInputDTO.setPayType("0");//0为购买油卡，2为充值
                String buyPetrol=BuyPetrol(petrol2,petrolInputDTO);//返回生成的支付单串
                if(buyPetrol!=null){
                    Map<String,String> info=new HashMap<>();
                    info.put("0",buyPetrol);
                    return info;
                }else {
                    Map<String,String> info=new HashMap<>();
                    info.put("-1","油卡申请失败，信息有误，无此法生成订单");
                    return info;
                }
            }else{//不为空则————执行充值
                petrolInputDTO.setPayType("2");//0为购买油卡，2为充值
                String buyPetrol=BuyPetrol(petrol1,petrolInputDTO);//返回生成的支付单串
                if(buyPetrol!=null){
                    Map<String,String> info=new HashMap<>();
                    info.put("2",buyPetrol);
                    return info;
                }else {
                    Map<String,String> info=new HashMap<>();
                    info.put("-1","油卡申请失败，信息有误，无此法生成订单");
                    return info;
                }
            }

        }

        return null;
    }

    @Override
    public boolean insertPetrolSalesRecords(PetrolSalesRecords petrolSalesRecords) {
        return false;
    }

}
