package com.cqut.czb.bn.service.impl.movieTickets;

import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.impl.payBack.petrolCoupons.luPay.util.HttpRequest;
import com.cqut.czb.bn.service.movieTickets.MovieTicketsService;
import com.cqut.czb.bn.util.md5.MD5Util;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;

@Service
public class MovieTicketsServiceImpl implements MovieTicketsService {
    @Override
    public JSONResult qianzhu(Principal principal, User user) {
        // 测试
//        String URL="https://live-test.qianzhu8.com/api/v1/platform/getToken";
//
//        String platformId = "10340";
//        String nickname = "cs";
//        String platformUniqueId = "123";
//        String secret = "8x6f3ud5m38qz2ad";
//        long timestamp = new Date().getTime();

        // 正式
        String URL="https://live.qianzhu8.com/api/v1/platform/getToken";
        String platformId = "10387";
        String nickname = user.getUserAccount();
        String platformUniqueId = user.getUserAccount();
        String secret = "lpw6chcgdrt18q0x";
        long timestamp = new Date().getTime();

        String params = "nickname=" + nickname + "&platformId=" + platformId +
                "&platformUniqueId=" + platformUniqueId +
                "&timestamp=" + timestamp;

        String sign = MD5Util.MD5Encode(params + "" + secret,"UTF-8").toLowerCase();

        params = params + "&sign=" + sign;

        System.out.println(URL+"?"+params);

        String sr= HttpRequest.httpRequestGet(URL, params);

        System.out.println(sr);
        sr = sr.replaceAll("null", "\"\"");
        JSONObject jsonObject= JSONObject.fromObject(sr);

        System.out.println(jsonObject);

        return new JSONResult(jsonObject);
    }
}
