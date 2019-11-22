package com.cqut.czb.bn.api.controller.vipApplication;

import com.cqut.czb.bn.entity.entity.VIPApply;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.vipApplicationService.VipApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 作者： 马杰,陈爽
 * 模块： 申请会员，读取申请人信息
 * 创建时间： 2019/11/21
 */
@CrossOrigin
@RestController
@RequestMapping("/api/service/VipApply")
public class VipApplyController {

    @Autowired
    VipApplyService vipApplyService;

    /**
     * 获取申请人信息
     * @param
     * @return
     */
    @GetMapping("/getvip")
    public JSONResult getVip(VIPApply vipApplication) {
        return new JSONResult(vipApplyService.getvip(vipApplication));
    }

    /**
     * 申请会员审核
     * @param vipApplication
     * @return
     */
    @PostMapping("/updatevip")
    public JSONResult updateVip(VIPApply vipApplication) {
        return new JSONResult(vipApplyService.updateVip(vipApplication));
    }
}
