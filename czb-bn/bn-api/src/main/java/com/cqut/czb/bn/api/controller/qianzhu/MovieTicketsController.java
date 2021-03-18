package com.cqut.czb.bn.api.controller.qianzhu;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.impl.payBack.petrolCoupons.luPay.util.HttpRequest;
import com.cqut.czb.bn.service.movieTickets.MovieTicketsService;
import com.cqut.czb.bn.util.md5.MD5Util;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.Date;

/**
 * @author Liyan
 */
@RestController
@RequestMapping("/api/film")
public class MovieTicketsController {

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    MovieTicketsService service;

    @GetMapping("/qz")
    public JSONResult qz(Principal principal) {
        User user = (User)redisUtils.get(principal.getName());
        return service.qianzhu(principal, user);
    }
}
