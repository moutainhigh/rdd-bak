package com.cqut.czb.bn.api.controller.WeChatSmallProgram;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatTOWithdrawDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatWithdrawDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.weChatSmallProgram.WCPWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 作者：  WangYa + XiaoYang
 * 时间：  2019/12/16 19:13
 * 描述：
 */
@RestController
@RequestMapping("/api/WCPWithdraw")
public class WCPWithdrawController {

//    @Autowired
//    RedisUtils redisUtils;

    @Autowired
    WCPWithdrawService withdrawService;

    /**
     * 微信小程序提现数据获取
     */
    @GetMapping(value ="/selectWithdrawInfo")
    public JSONResult selectWithdrawInfo(WeChatWithdrawDTO pageDTO){
        return withdrawService.selectWithdrawInfo(pageDTO);
    }

    /**
     * 微信小程序提现
     */
    @GetMapping(value ="/toWithdraw")
    public JSONResult toWithdraw(WeChatTOWithdrawDTO weChatTOWithdrawDTO){
        return withdrawService.toWithdraw(weChatTOWithdrawDTO);
    }
}
