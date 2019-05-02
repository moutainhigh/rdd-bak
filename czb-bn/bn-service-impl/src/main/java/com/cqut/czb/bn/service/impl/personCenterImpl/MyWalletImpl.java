package com.cqut.czb.bn.service.impl.personCenterImpl;

import com.cqut.czb.bn.dao.mapper.MyWalletMapperExtra;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.AlipayRecordDTO;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.BalanceAndInfoIdDTO;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.IncomeLogDTO;
import com.cqut.czb.bn.service.personCenterService.MyWallet;
import com.cqut.czb.bn.util.string.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MyWalletImpl implements MyWallet {
    @Autowired
    private MyWalletMapperExtra myWalletMapper;

    // TODO 用户id需要修改，这里写死
    @Override
    public JSONObject getBalance(){
        JSONObject json = new JSONObject();
        json.put("balance",myWalletMapper.getUserAllIncome("1"));
        return json;
    }

    // TODO 用户id需要修改，这里写死
    @Override
    public synchronized String withDraw(AlipayRecordDTO alipayRecordDTO, String keyWord) {
        BalanceAndInfoIdDTO balanceAndInfoId = myWalletMapper.getUserAllIncome("1");

        if(!myWalletMapper.getPsw("1").equals(keyWord)){
            return new String("账户密码错误");
        }

        if(alipayRecordDTO.getPaymentAmount().compareTo(new BigDecimal(0)) < 0){
            return new String("提现金额不能是负数");
        }

        if(alipayRecordDTO.getPaymentAmount().compareTo(balanceAndInfoId.getBalance()) > 0){
            return new String("提现金额超出余额");
        }

        int compareValue = alipayRecordDTO.getPaymentAmount().compareTo(new BigDecimal("0.1"));
        if(!(compareValue == 0 || compareValue > 0)){
            return new String("提现金额不能低于0.1元");
        }

        myWalletMapper.increaseWithdrawed(alipayRecordDTO.getPaymentAmount().doubleValue());
        IncomeLogDTO incomeLog = new IncomeLogDTO();
        incomeLog.setInfoId(balanceAndInfoId.getInfoId());
        incomeLog.setAmount(alipayRecordDTO.getPaymentAmount().doubleValue());
        incomeLog.setBeforeChangeIncome(balanceAndInfoId.getBalance().doubleValue());
        incomeLog.setRecordId(StringUtil.createId());
        System.out.println(incomeLog.getRecordId());
        incomeLog.setRemark("支付宝提现");
        // TODO 支付宝提现订单号先写死
        incomeLog.setSourceId("111111");
        incomeLog.setType(1);
        incomeLog.setWithdrawAmount(alipayRecordDTO.getPaymentAmount().doubleValue());
        incomeLog.setWithdrawTo(alipayRecordDTO.getPaymentAccount());
        incomeLog.setWithdrawName(alipayRecordDTO.getPaymentName());
        myWalletMapper.insertIncomeLog(incomeLog);
//        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
//                AlipayConfig.merchant_private_key, "json", "utf-8", AlipayConfig.alipay_public_key, "RSA2");
//        AlipayFundTransToaccountTransferRequest request1 = new AlipayFundTransToaccountTransferRequest();
//
//        request1.setBizContent("{" + "\"out_biz_no\":\"" + System.currentTimeMillis() + "\","
//                + "\"payee_type\":\"ALIPAY_LOGONID\"," + "\"payee_account\":\"" + alipayRecordDTO.getPaymentAccount()
//                + "\"," + "\"amount\":\"" + alipayRecordDTO.getPaymentAmount() + "\","
//                + "\"payer_show_name\":\"重庆叮铛猫网络科技有限公司\"," + "\"payee_real_name\":\"" + alipayRecordDTO.getPaymentName()
//                + "\"," + "\"remark\":\"" + "提现收入" + "\"," + "}");
//
//        AlipayFundTransToaccountTransferResponse response;
//
//        try {
//            response = alipayClient.execute(request1);
//            String string = response.getBody().toString();
//            if (response.isSuccess()) {
//                JSONObject jso = JSONObject.parseObject(response.getBody());
//                String orderId = jso.getJSONObject("alipay_fund_trans_toaccount_transfer_response")
//                        .getString("order_id");
//                // 更新用户可提现金额并插入提现记录
//
//
//                return string;
//            } else {
//                return string;
//            }
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//            return "0";
//        }
        return new String("成功提现");
    }
}
