package com.cqut.czb.bn.service.impl.personCenterImpl;

import com.cqut.czb.bn.dao.mapper.MyWalletMapperExtra;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.AlipayRecordDTO;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.BalanceAndInfoIdDTO;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.IncomeLogDTO;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.WithDrawLogDTO;
import com.cqut.czb.bn.service.personCenterService.MyWallet;
import com.cqut.czb.bn.util.string.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class MyWalletImpl implements MyWallet {
    @Autowired
    private MyWalletMapperExtra myWalletMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public BalanceAndInfoIdDTO getBalance(String userId){
        BalanceAndInfoIdDTO balance = myWalletMapper.getUserAllIncome(userId);

        // 如果取出的余额小于0，则把余额设置为0
        if(balance.getBalance().compareTo(new BigDecimal(0)) < 0){
            balance.setBalance(new BigDecimal(0));
        }

        return balance;
    }

    @Override
    public JSONObject getWithdrawLog(String userId) {
        List<WithDrawLogDTO> withDrawLogs = myWalletMapper.getWithdrawLog(userId);
        JSONObject jsonAll = new JSONObject();
        JSONArray jsonAllArray = new JSONArray();

        List<String> yearMonths = new ArrayList<>();

        for(WithDrawLogDTO data:withDrawLogs){
            data.setCreateTime(data.getCreateTime().replace(".0",""));
            if(yearMonths.size() ==  0){
                yearMonths.add(data.getYearMonth());
                continue;
            }
            boolean ifExsits = false;
            for(int i = 0; i<yearMonths.size(); i++){
                if(yearMonths.get(i).equals(data.getYearMonth())){
                    ifExsits = true;
                    continue;
                }
            }
            if(!ifExsits)
                yearMonths.add(data.getYearMonth());
        }

        yearMonths.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                o1 = o1.replace("-","");
                o2 = o2.replace("-","");

                return o2.compareTo(o1);
            }
        });

        for(int i = 0; i<yearMonths.size(); i++){
            JSONObject json = new JSONObject();
            json.put("yearMonth", yearMonths.get(i));
            JSONArray jsonArray = new JSONArray();

            for(WithDrawLogDTO data:withDrawLogs){
                if(data.getYearMonth().equals(yearMonths.get(i))){
                    JSONObject jsonOneLog = new JSONObject();
                    jsonOneLog.put("money", "-" + data.getMoney() );
                    System.out.println(data.getCreateTime());
                    jsonOneLog.put("createTime", data.getCreateTime());
                    System.out.println(data.getCreateTime());
                    jsonArray.add(jsonOneLog);
                }
            }
            json.put("info", jsonArray);
            jsonAllArray.add(json);
        }

        jsonAll.put("allInfo", jsonAllArray);

        return jsonAll;
    }

    @Override
    public synchronized int withDraw(AlipayRecordDTO alipayRecordDTO, String userId) {
        BalanceAndInfoIdDTO balanceAndInfoId = myWalletMapper.getUserAllIncome(userId);

        if(!bCryptPasswordEncoder.matches(alipayRecordDTO.getKeyWord(), myWalletMapper.getPsw(userId))){
            return 100;
        }

        if(alipayRecordDTO.getPaymentAmount().compareTo(new BigDecimal(0)) < 0){
            return 101;
        }

        if(alipayRecordDTO.getPaymentAmount().compareTo(balanceAndInfoId.getBalance()) > 0){
            return 102;
        }

        int compareValue = alipayRecordDTO.getPaymentAmount().compareTo(new BigDecimal("0.1"));
        if(!(compareValue == 0 || compareValue > 0)){
            return 103;
        }

        myWalletMapper.increaseWithdrawed(balanceAndInfoId.getInfoId(), alipayRecordDTO.getPaymentAmount().toString());
        IncomeLogDTO incomeLog = new IncomeLogDTO();
        incomeLog.setInfoId(balanceAndInfoId.getInfoId());
        incomeLog.setAmount(alipayRecordDTO.getPaymentAmount().doubleValue());
        incomeLog.setBeforeChangeIncome(balanceAndInfoId.getBalance().doubleValue());
        incomeLog.setRecordId(StringUtil.createId());
        System.out.println(incomeLog.getRecordId());
        incomeLog.setRemark("支付宝提现");
        // TODO 谭深化——支付宝提现订单号先写死
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

        return 1;
    }
}
