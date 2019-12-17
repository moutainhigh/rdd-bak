package com.cqut.czb.bn.api.controller.partnerAndOperateCenter;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.AgentCenterBusinessPartnerDto;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.BusinessCommonUserOutputDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.partnerAndOperateCenter.AgentCenterBusinessPartnerService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Date;
import java.util.List;

/**
* @Description:    代理商中心事业合伙人管理
* @Author:         WenJunYuan
* @CreateDate:     2019/12/3 20:08
* @Version:        1.0
*/
@RestController
@RequestMapping("/api/agentCenterBusinessPartner")
public class AgentCenterBusinessPartnerController {

    @Autowired
    private RedisUtils redisUtils;


    @Autowired
    private AgentCenterBusinessPartnerService agentCenterBusinessPartnerService;

    /**
     * 代理商事业合伙人管理
     * @return
     */
    @RequestMapping("/list")
    public JSONResult list(Principal principal, String mobile, @DateTimeFormat(pattern = "yyyy-MM-dd") Date createAt, String area, PageDTO pageDTO){
        User user = (User) redisUtils.get(principal.getName());
        if(user.getUserId() == null){
            return new JSONResult("未登录",500);
       }
        List<AgentCenterBusinessPartnerDto> partnerList = agentCenterBusinessPartnerService.list(mobile,createAt,area,pageDTO);
        PageInfo<AgentCenterBusinessPartnerDto> pageInfo = new PageInfo<>(partnerList);
        return new JSONResult(pageInfo);
    }


}
