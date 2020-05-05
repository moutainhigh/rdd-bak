package com.cqut.czb.bn.service.impl.LuPay;

import com.cqut.czb.bn.service.LuPay.QueryBillService;
import com.cqut.czb.bn.service.impl.payBack.petrolCoupons.luPay.util.HttpRequest;
import com.cqut.czb.bn.service.impl.payBack.petrolCoupons.luPay.util.LuPayApiConfig;
import com.cqut.czb.bn.util.md5.MD5Util;
import net.sf.json.JSONObject;

/**
 * @ClassName QueryBillServiceImpl
 * @Description 账单查询
 * @Author chendeqiang
 * @Date 2020/5/5 13:20
 * @Version 1.0
 */
public class QueryBillServiceImpl implements QueryBillService {
    @Override
    public String queryBill(String OrderID) {
        String URL="http://jiayou.10010.wiki/Api/GetOrderInfo.aspx";

        String signStr="APIID=" +
                LuPayApiConfig.APIID +
                "&OrderID=" +
                OrderID +
                "&APIKEY=" +
                LuPayApiConfig.APIKEY;

        String Sign= MD5Util.MD5Encode(signStr,"UTF-8").toUpperCase();

        String params="APIID=" +
                LuPayApiConfig.APIID +
                "&OrderID=" +
                OrderID +
                "&Sign=" +
                Sign;

        String sr= HttpRequest.httpRequestGet(URL, params);
        return sr;
    }
}
