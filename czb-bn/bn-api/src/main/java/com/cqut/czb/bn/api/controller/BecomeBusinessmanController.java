package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.shop.ShopDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.BecomeBusinessmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @ClassName: BecomeBusinessmanController
 * @Author: Iriya720
 * @Date: 2019/12/12
 * @Description: 添加成为商家
 * @version: v1.0
 */
@RestController
@RequestMapping("/api/businessman")
public class BecomeBusinessmanController {

    @Autowired
    private BecomeBusinessmanService becomeBusinessman;

    @Autowired
    private RedisUtils redisUtils;

    @PostMapping("/addBusinessman")
    public JSONResult addBusinessman(ShopDTO shopDTO, Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(becomeBusinessman.addBusinessman(shopDTO,user));
    }
}
