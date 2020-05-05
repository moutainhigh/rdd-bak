package com.cqut.czb.bn.service.impl.LuPay;

import com.cqut.czb.bn.service.LuPay.BalanceQueryService;
import com.cqut.czb.bn.service.impl.payBack.petrolCoupons.luPay.util.HttpRequest;
import com.cqut.czb.bn.service.impl.payBack.petrolCoupons.luPay.util.LuPayApiConfig;
import com.cqut.czb.bn.util.md5.MD5Util;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @ClassName BalanceQueryServiceImpl
 * @Description 余额查询
 * @Author chendeqiang
 * @Date 2020/5/5 8:19
 * @Version 1.0
 */
@Service
public class BalanceQueryServiceImpl implements BalanceQueryService {
    @Override
    public Double BalanceQuery() {

        String URL="http://jiayou.10010.wiki/Api/GetUserMoney.aspx";

        String signStr= "APIID="+LuPayApiConfig.APIID+"&APIKEY="+LuPayApiConfig.APIKEY;

        String Sign= MD5Util.MD5Encode(signStr,"UTF-8").toUpperCase();

        String params="APIID="+LuPayApiConfig.APIID+"&Sign="+Sign;

        String sr= HttpRequest.httpRequestGet(URL, params);

        System.out.println(sr);

        if(sr!=null) {
            JSONObject jsonObject = JSONObject.fromObject(sr);
            return Double.valueOf(jsonObject.get("Money").toString());
        }

        return 0.0;
    }

    public static void main(String[] args) {
        new BalanceQueryServiceImpl().BalanceQuery();
    }

}
