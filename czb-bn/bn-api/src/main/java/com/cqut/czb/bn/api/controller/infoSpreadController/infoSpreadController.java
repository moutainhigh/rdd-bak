package com.cqut.czb.bn.api.controller.infoSpreadController;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.infoSpread.PartnerDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.InfoSpreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.json.Json;

import java.security.Principal;


/**
 * /infoSpread 推广信息
 */
@RestController
@RequestMapping("/api/infoSpread")
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
    public JSONResult getNextChild(PartnerDTO partnerDTO, PageDTO pageDTO){
        return new JSONResult(infoSpreadService.getNextChildInfo(partnerDTO,pageDTO));

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

    @GetMapping("/getAllPartner")
    public JSONResult getAllPartner(PartnerDTO partnerDTO){
        return new JSONResult(infoSpreadService.selectAllPartnerInfo(partnerDTO));
    }

    /**
     * 对新用户的父级合伙人的每月新增人数指标加一
     * @param partnerDTO
     * @return
     */
    @PostMapping("/addChildPromotion")
    public JSONResult addChildPromotion(PartnerDTO partnerDTO){
        return new JSONResult(infoSpreadService.addChildPromotion(partnerDTO));
    }

    /**
     * 对新用户的父级合伙人的每月新增消费人数指标加一
     * @param partnerDTO
     * @return
     */
    @PostMapping("/addChildConsumer")
    public JSONResult addChildConsumer(PartnerDTO partnerDTO){
        return new JSONResult(infoSpreadService.addChildConsumer(partnerDTO));
    }

    /**
     * 通过用户名查询
      * @param partnerDTO
     * @param pageDTO
     * @return
     */
    @PostMapping("/getChildByName")
    public JSONResult getChildByName(PartnerDTO partnerDTO,PageDTO pageDTO){
        return new JSONResult(infoSpreadService.getChildByName(partnerDTO,pageDTO));
    }

    /**
     * 我的总下级消费
     * @param partnerDTO
     * @param pageDTO
     * @return
     */
    @GetMapping("/totalChildMoney")
    public JSONResult myTotalChildMoney(PartnerDTO partnerDTO,PageDTO pageDTO){
        return new JSONResult(infoSpreadService.myTotalChildMoney(partnerDTO,pageDTO));
    }

    /**
     * 合伙人信息管理
     * @param partnerDTO
     * @param pageDTO
     * @return
     */
    @GetMapping("/allPartnerManage")
    public JSONResult allPartnerManage(PartnerDTO partnerDTO,PageDTO pageDTO) {
        return new JSONResult(infoSpreadService.allPartnerManage(partnerDTO,pageDTO));
    }
}
