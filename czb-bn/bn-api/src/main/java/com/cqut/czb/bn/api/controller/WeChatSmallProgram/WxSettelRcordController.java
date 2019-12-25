package com.cqut.czb.bn.api.controller.WeChatSmallProgram;

import com.cqut.czb.bn.service.weChatSmallProgram.WxSettelRcordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/WxSettelRcord")
public class WxSettelRcordController {
    WxSettelRcordService wxSettelRcordService;
}
