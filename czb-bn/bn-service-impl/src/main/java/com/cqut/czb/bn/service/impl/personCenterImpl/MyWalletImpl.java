package com.cqut.czb.bn.service.impl.personCenterImpl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.cqut.czb.bn.dao.mapper.MyWalletMapperExtra;
import com.cqut.czb.bn.entity.dto.PayConfig.AliPayConfig;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.*;
import com.cqut.czb.bn.entity.global.JSONResult;
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

        if(balance == null){
            InsertIncomeInfo info = new InsertIncomeInfo();
            info.setInfoId(StringUtil.createId());
            info.setUserId(userId);
            int success = myWalletMapper.insertIncomeInfo(info);
            if (success > 0){
                balance = new BalanceAndInfoIdDTO();
                balance.setBalance(new BigDecimal(0));
                return balance;
            }
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
    public synchronized JSONResult withDraw(AlipayRecordDTO alipayRecordDTO, String userId) {

        if(!bCryptPasswordEncoder.matches(alipayRecordDTO.getKeyWord(), myWalletMapper.getPsw(userId))){
            return new JSONResult("账户密码错误", 200, "账户密码错误");
        }

        if(alipayRecordDTO.getPaymentAmount().compareTo(new BigDecimal(0)) < 0){
            return new JSONResult("提现金额不能是负数", 200, "提现金额不能是负数");
        }

        if(alipayRecordDTO.getPaymentAmount().compareTo(new BigDecimal("0.1")) < 0){
            return new JSONResult("提现金额不能低于0.1元", 200, "提现金额不能低于0.1元");
        }

        // 取出余额，进行对比
        BalanceAndInfoIdDTO balanceAndInfoId = myWalletMapper.getUserAllIncome(userId);

        if(alipayRecordDTO.getPaymentAmount().compareTo(balanceAndInfoId.getBalance()) > 0){
            return new JSONResult("提现金额超出余额", 200, "提现金额超出余额");
        }

        // 设置提现记录基本信息
        IncomeLogDTO incomeLog = new IncomeLogDTO();
        incomeLog.setInfoId(balanceAndInfoId.getInfoId());
        incomeLog.setAmount((alipayRecordDTO.getPaymentAmount().doubleValue()));
        incomeLog.setBeforeChangeIncome(balanceAndInfoId.getBalance().doubleValue());
        incomeLog.setRecordId(StringUtil.createId());
        incomeLog.setRemark("支付宝提现");
        incomeLog.setType(1);
        incomeLog.setWithdrawAmount(alipayRecordDTO.getPaymentAmount().doubleValue());
        incomeLog.setWithdrawTo(alipayRecordDTO.getPaymentAccount());
        incomeLog.setWithdrawName(alipayRecordDTO.getPaymentName());

        // 支付宝参数设置
        AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id,
                AliPayConfig.merchant_private_key, "json", "utf-8", AliPayConfig.alipay_public_key, "RSA2");
        AlipayFundTransToaccountTransferRequest request1 = new AlipayFundTransToaccountTransferRequest();

        request1.setBizContent("{" + "\"out_biz_no\":\"" + System.currentTimeMillis() + "\","
                + "\"payee_type\":\"ALIPAY_LOGONID\"," + "\"payee_account\":\"" + alipayRecordDTO.getPaymentAccount()
                + "\"," + "\"amount\":\"" + alipayRecordDTO.getPaymentAmount() + "\","
                + "\"payer_show_name\":\"重庆爱动体育文化传播有限公司\"," + "\"payee_real_name\":\"" + alipayRecordDTO.getPaymentName()
                + "\"," + "\"remark\":\"" + "提现收入" + "\"," + "}");

        AlipayFundTransToaccountTransferResponse response;
        String string = null;

        try {
            // 进行请求
            response = alipayClient.execute(request1);
            string = response.getBody().toString();

            // 是否成功的后续操作
            if (response.isSuccess()) {
                com.alibaba.fastjson.JSONObject jso = com.alibaba.fastjson.JSONObject.parseObject(response.getBody());
                String orderId = jso.getJSONObject("alipay_fund_trans_toaccount_transfer_response")
                        .getString("order_id");
                // 更新用户已提现金额
                int updateBalance = myWalletMapper.increaseWithdrawed(balanceAndInfoId.getInfoId(), alipayRecordDTO.getPaymentAmount().toString());

                if (updateBalance != 1)
                    return new JSONResult("提现成功，但更新用户余额失败", 500, "提现成功");
                System.out.println(updateBalance);

                // 设置提现记录的sourceId为支付宝返回的单号
                incomeLog.setSourceId(orderId);
                // 插入提现记录
                int insertSuccess = myWalletMapper.insertIncomeLog(incomeLog);
                System.out.println(insertSuccess);
                if (insertSuccess != 1)
                    return new JSONResult("提现成功，但插入提现记录出错", 500, "提现成功");
                return new JSONResult("提现成功", 200, "提现成功");
            } else {
                return new JSONResult("支付宝提现请求失败", 200, "支付宝提现请求失败");
            }

        } catch (AlipayApiException e) {
            e.printStackTrace();
            return new JSONResult("提现过程中出先未知错误", 200, string);
        }

    }
}
