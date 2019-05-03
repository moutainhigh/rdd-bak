package com.cqut.czb.bn.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.cqut.czb.bn.dao.mapper.DepositRecordsMapper;
import com.cqut.czb.bn.dao.mapper.PetrolMapper;
import com.cqut.czb.bn.dao.mapper.PetrolSalesRecordsMapper;
import com.cqut.czb.bn.dao.mapper.UserIncomeInfoMapper;
import com.cqut.czb.bn.entity.dto.AllPetrolDTO;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolSalesRecordsDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.service.AppBuyPetrolService;
import com.cqut.czb.bn.entity.dto.paymentRecord.GetAlipayClient;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

@Service
public class AppBuyPetrolServiceImpl extends TimerTask implements AppBuyPetrolService {
    @Autowired
    private PetrolMapper petrolMapper;

    @Autowired
    private PetrolSalesRecordsMapper petrolSalesRecordsMapper;

    @Autowired
    private DepositRecordsMapper depositRecordsMapper;

    @Autowired
    private UserIncomeInfoMapper userIncomeInfoMapper;

    @Override
    public String BuyPetrol(Petrol petrol,PetrolInputDTO petrolInputDTO) {

        //检验是否都为空
        if(petrol==null&&petrolInputDTO==null)
            return "没有油卡或传入数据有误（为空）";
        /**
         * 生成起调参数串——返回给app（支付订单）
         */
         String rs=null;//用于保存起调参数
         GetAlipayClient getAlipayClient=GetAlipayClient.getInstance();
         AlipayClient alipayClient=getAlipayClient.getAlipayClient();
         AlipayTradeAppPayRequest request=new AlipayTradeAppPayRequest();

         //订单标识
         String orgId = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15);
         //支付类型(没有对应的类型值，默认回调后执行3类型，暂时只是支持支付宝支付)/死数据****************/
         String payType="2";//3代表爱虎支付宝(后面可能涉及到多种支付方式)
         //支付的金额
         Double money=petrol.getPetrolPrice();
        System.out.println("money"+money);
         //购买的数量/******************/
         Integer count=1;
         //购买的油卡类型
         Integer petrolKind=petrol.getPetrolKind();
        System.out.println("petrolKind"+petrolKind);
        //购买的油卡号
        String petrolNum=petrol.getPetrolNum();
        System.out.println("petrolNum"+petrolNum);

         //购买人的id
         String ownerId=petrol.getOwnerId();
        System.out.println("ownerId"+ownerId);

        PetrolSalesRecordsDTO petrolSalesRecordsDTO=new PetrolSalesRecordsDTO();

        //对判断是否能生成订单
        if(orgId==null&&payType==null&&money==null&&count==null&&petrolKind==null&&ownerId==null&&petrolNum==null)
            return "无法生成支付订单";

        request.setBizModel(petrolSalesRecordsDTO.toAlipayTradeAppPayModel(orgId,payType,money,count,petrolKind,ownerId,petrolNum));//支付订单

        request.setNotifyUrl(getAlipayClient.getCallBackUrl());//支付回调接口

        try {
            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            rs = response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
//        LogUtil.sysPrint(loginInfoDTO.getCurUserid(), "DD猫支付宝订单创建2__" + paymentRecord.getPayType());

        // 构建油卡购买记录表对象
//        PetrolSalesRecords PetrolSalesRecordsDTO=new PetrolSalesRecords();
//        PetrolSalesRecordsDTO.setBuyerId(petrolInputDTO.getOwnerId());
//        PetrolSalesRecordsDTO.setPetrolId(petrol.getPetrolId());

        /**
         * 插入计时器进行检测5分钟之内是否支付——暂时关闭便于测试
         */
//        Timer timer=new Timer();
//        timer.schedule(this,300000);
        //计时器——2分钟之后执行
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("已经进入了油卡倒计时");
                boolean isHave=AllPetrolDTO.isContainPetorlMap(AllPetrolDTO.getCurrentPetrolMap(),petrol.getPetrolNum());
                if(isHave==false){
                    AllPetrolDTO.putBackPetrol(AllPetrolDTO.getAllpetrolMap(),petrol);
                    System.out.println("已经放回");
                }
            }
        }, 10000);
        return rs;
    }

    @Override
    public boolean ConfirmBuyPetrol(Petrol petrol, PetrolSalesRecords petrolSalesRecords) {
        //更改油卡状态
        boolean updatePetrol=this.updatePetrol(petrol);
        //插入油卡购买记录表
        boolean insertPetrolSalesRecords=this.insertPetrolSalesRecords(petrolSalesRecords);
        //新增用户收益信息表——返佣
        boolean insertUserIncomeInfo=this.insertUserIncomeInfo(petrol);

        this.cancel();//关闭计时器线程

        if(updatePetrol==true&&insertPetrolSalesRecords==true&&insertUserIncomeInfo==true)
            return true;
        return false;
    }

    @Override
    public boolean updatePetrol(Petrol petrol) {

        return petrolMapper.updateByPrimaryKeySelective(petrol)>0;
    }

    @Override
    public boolean insertPetrolSalesRecords(PetrolSalesRecords petrolSalesRecords) {
        return petrolSalesRecordsMapper.insert(petrolSalesRecords)>0;
    }

    @Override
    public boolean insertDepositRecords(DepositRecords depositRecords) {
        depositRecords.setRecordId(StringUtil.createId());
        return depositRecordsMapper.insert(depositRecords)>0;
    }

    @Override
    public boolean insertUserIncomeInfo(Petrol petrol) {
        //用户收益信息
        UserIncomeInfo userIncomeInfo=new UserIncomeInfo();

        userIncomeInfo.setUserId(petrol.getOwnerId());

        userIncomeInfo.setFanyongIncome(petrol.getPetrolPrice()*0.01);//返佣金额比例为0.01

        userIncomeInfo.setUserId(StringUtil.createId());

        return userIncomeInfoMapper.insert(userIncomeInfo)>0;
    }

    /**
     * 计时器
     */
    @Override
    public void run() {
        AllPetrolDTO allPetrolDTO=new AllPetrolDTO();
//        if (AllPetrolDTO.getCurrentPetrol()!=null){
//            /***************************************************************/
//            AllPetrolDTO.getPetrolMap().put(AllPetrolDTO.getCurrentPetrol().get(0).getPetrolId(),AllPetrolDTO.getCurrentPetrol().get(0));
//            AllPetrolDTO.setCurrentPetrol(null);
//            this.cancel();
//        }
//        AllPetrolDTO.setCurrentPetrol(null);
        this.cancel();
    }
}
