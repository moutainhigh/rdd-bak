package com.cqut.czb.bn.api.controller.vipApplication;

import com.cqut.czb.bn.entity.entity.VIPApply;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatVipApply;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.vipApplicationService.VipApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * 作者： 马杰,陈爽
 * 模块： 申请会员，读取申请人信息
 * 创建时间： 2019/11/21
 */
@CrossOrigin
@RestController
@RequestMapping("/api/VipApply")
public class VipApplyController {

    @Autowired
    VipApplyService vipApplyService;

    /**
     * 获取申请人信息
     * @param
     * @return
     */
    @GetMapping("/selectVipApply")
    public JSONResult selectVipApply(VIPApply vipApply) {
        return new JSONResult(vipApplyService.selectVipApply(vipApply));
    }

    /**
     * 申请会员审核
     * @param vipApply
     * @return
     */
    @PostMapping("/updateVipApply")
    public JSONResult updateVipApply(VIPApply vipApply) {
        return new JSONResult(vipApplyService.updateVipApply(vipApply));
    }

    /**
     * 申请会员
     * @return
     */
    @PostMapping("/applyWCPVip")
    public JSONResult applyWCPVip(Principal principal,@RequestBody @Valid WeChatVipApply weChatVipApply){
        return new JSONResult(vipApplyService.applyWCPVip(weChatVipApply, principal));
    }
}
