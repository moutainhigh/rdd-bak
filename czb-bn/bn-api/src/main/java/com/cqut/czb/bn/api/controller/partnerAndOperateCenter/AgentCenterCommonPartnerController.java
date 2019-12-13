package com.cqut.czb.bn.api.controller.partnerAndOperateCenter;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.AgentCenterCommonPartnerDto;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.partnerAndOperateCenter.AgentCenterCommonPartnerService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Date;
import java.util.List;

/**
* @Description:    代理商中心普通合伙人管理
* @Author:         WenJunYuan
* @CreateDate:     2019/12/4 15:45
* @Version:        1.0
*/
@RestController
@RequestMapping("/api/agentCenterCommonPartner")
public class AgentCenterCommonPartnerController {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private AgentCenterCommonPartnerService agentCenterCommonPartnerService;
    @RequestMapping("/list")
    public JSONResult list(Principal principal, String mobile, @DateTimeFormat(pattern = "yyyy-MM-dd") Date createAt, String area, PageDTO pageDTO){
        User user = (User) redisUtils.get(principal.getName());
        if(user.getUserId() == null){
            return new JSONResult("未登录",500);
        }
        List<AgentCenterCommonPartnerDto> commonList = agentCenterCommonPartnerService.list(mobile,createAt,area,pageDTO);
        PageInfo<AgentCenterCommonPartnerDto> pageInfo = new PageInfo<>(commonList);
        return new JSONResult(pageInfo);
    }
}
