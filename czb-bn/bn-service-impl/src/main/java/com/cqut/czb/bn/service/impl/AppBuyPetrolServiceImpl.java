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

import java.util.TimerTask;
import java.util.UUID;

@Service
public class AppBuyPetrolServiceImpl extends TimerTask implements AppBuyPetrolService {
    @Autowired
    PetrolMapper petrolMapper;

    @Autowired
    PetrolSalesRecordsMapper petrolSalesRecordsMapper;

    @Autowired
    DepositRecordsMapper depositRecordsMapper;

    @Autowired
    UserIncomeInfoMapper userIncomeInfoMapper;

    @Override
    public String BuyPetrol(Petrol petrol,PetrolInputDTO petrolInputDTO) {
        //随机获取一张卡
        if(petrol==null&&petrolInputDTO==null)
            return null;
        /**
         * 生成起调参数串
         */
         String rs=null;
         GetAlipayClient getAlipayClient=GetAlipayClient.getInstance();
         AlipayClient alipayClient=getAlipayClient.getAlipayClient();
         AlipayTradeAppPayRequest request=new AlipayTradeAppPayRequest();

         String orgId = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15);

        //要对其进行数据的改变——死数据
        PetrolSalesRecordsDTO petrolSalesRecordsDTO=new PetrolSalesRecordsDTO();
//        petrolSalesRecordsDTO.getPaymentMethod()-----支付类型
        request.setBizModel(petrolSalesRecordsDTO.toAlipayTradeAppPayModel(orgId, "3",
                petrolSalesRecordsDTO.getTurnoverAmount(),1));
        request.setNotifyUrl(getAlipayClient.getCallBackUrl());

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
        if (AllPetrolDTO.getCurrentPetrol()!=null){
            AllPetrolDTO.getPetrolMap().put(AllPetrolDTO.getCurrentPetrol().getPetrolId(),AllPetrolDTO.getCurrentPetrol());
            AllPetrolDTO.setCurrentPetrol(null);
            this.cancel();
        }
        AllPetrolDTO.setCurrentPetrol(null);
        this.cancel();
    }
}
