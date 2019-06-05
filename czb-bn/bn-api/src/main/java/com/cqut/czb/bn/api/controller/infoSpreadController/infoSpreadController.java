package com.cqut.czb.bn.api.controller.infoSpreadController;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.infoSpread.PartnerDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.InfoSpreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.json.Json;

import java.security.Principal;


/**
 * /infoSpread 推广信息
 */
@RestController
@RequestMapping("/infoSpread")
public class infoSpreadController {
    @Autowired
    InfoSpreadService infoSpreadService;
    @Autowired
    RedisUtils redisUtils;
    /**
     * 获取合伙人信息
     * @param partnerDTO
     * @return
     */
    @GetMapping("/getPartnerInfo")
    public JSONResult getPartnerInfo(PartnerDTO partnerDTO, Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(infoSpreadService.getPartnerInfo(partnerDTO,user));
    }

    /**
     * 获取子级的下一级
     * @param partnerDTO
     * @return
     */
    @GetMapping("/getNextChild")
    public JSONResult getNextChild(PartnerDTO partnerDTO){
        return new JSONResult(infoSpreadService.getNextChildInfo(partnerDTO));

    }

    /**
     * 获取过去7天的新增推广人数及消费人数
     * @param partnerDTO
     * @return
     */
    @GetMapping("/getChildByDay")
    public JSONResult getChildByDay(PartnerDTO partnerDTO,Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(infoSpreadService.getNewChildByDay(partnerDTO,user));
    }

    /**
     * 获取合伙人下的总子级用户数和总消费金额
     * @param partnerDTO
     * @return
     */
    @GetMapping("/getTotalInfo")
    public  JSONResult getTotalInfo(PartnerDTO partnerDTO,Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(infoSpreadService.getTotalInfo(partnerDTO,user));
    }
}
