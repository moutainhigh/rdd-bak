package com.cqut.czb.bn.api.controller.test;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.api.controller.test.model.GameChargeDTO;
import com.cqut.czb.bn.api.controller.test.model.VideoChargeDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.impl.payBack.petrolCoupons.luPay.util.HttpRequest;
import com.cqut.czb.bn.util.md5.MD5Util;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.DatatypeConverter;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.Date;

import static com.cqut.czb.bn.service.impl.payBack.petrolCoupons.luPay.RequestLuPayServiceImpl.getNowDate;

/**
 * @author Liyan
 */
@RestController
@RequestMapping("/film")
public class PurchaseFilmController {

    @Autowired
    RedisUtils redisUtils;

    @GetMapping("/qz")
    public JSONResult qz(Principal principal) throws NoSuchAlgorithmException {

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

//        User user = (User)redisUtils.get(principal.getName());
        String platformId = "10387";
//        String nickname = user.getUserAccount();
//        String platformUniqueId = user.getUserAccount();
        String nickname = "123456";
        String platformUniqueId = "123456";
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
