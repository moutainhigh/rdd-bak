package com.cqut.czb.bn.api.controller.vipApplication;

import com.cqut.czb.auth.interceptor.PermissionCheck;
import com.cqut.czb.bn.entity.dto.WxVipMoenyDTO;
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

    @PostMapping("/getVipMoney")
    public JSONResult getVipMoney(){
        return new JSONResult(vipApplyService.getVipMoney());
    }

    /**
     * 获取会员价格
     */
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/getWxVIP",method = RequestMethod.POST)
    public JSONResult getWxVIP(){
        return new JSONResult(vipApplyService.getWxVIP());
    }

    /**
     * 更改会员价格
     */
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/changeWxVIP",method = RequestMethod.POST)
    public JSONResult changeWxVIP(WxVipMoenyDTO wxVipMoenyDTO){
        return vipApplyService.changeWxVIP(wxVipMoenyDTO);
    }
}
