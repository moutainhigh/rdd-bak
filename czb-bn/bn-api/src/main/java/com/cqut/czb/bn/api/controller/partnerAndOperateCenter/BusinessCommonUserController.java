package com.cqut.czb.bn.api.controller.partnerAndOperateCenter;


import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.partnerAndOperateCenter.BusinessCommonUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Date;

/**
* @Description:    事业合伙人中心普通用户管理
* @Author:  WenJunYuan
* @CreateDate:     2019/11/16 16:04
* @Version:        1.0
*/
@RestController
@RequestMapping("/businessPartner/commonUser")
public class BusinessCommonUserController {

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    BusinessCommonUserService businessCommonUserService;


    /**
     * 查询事业合伙人下普通用户列表
     * @param principal principal
     * @param mobile 普通合伙人手机号码
     * @param createAt 创建时间
     * @param areaId 区域id
     * @param pageDTO 分页dto
     * @return
     */
    @GetMapping("/list")
    public JSONResult list(Principal principal,String mobile, Date createAt, Integer areaId, PageDTO pageDTO){
       /* if(principal == null || principal.getName() == null){
            return new JSONResult("没有权限",500);
        }
        User user = (User) redisUtils.get(principal.getName());
        if(user == null || user.getUserId() == null){
            return new JSONResult("没有权限",500);
        }*/
        return new JSONResult(businessCommonUserService.list("156342470371369",mobile,createAt,areaId,pageDTO));
    }
}
