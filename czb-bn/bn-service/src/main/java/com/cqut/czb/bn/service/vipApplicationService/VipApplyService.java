package com.cqut.czb.bn.service.vipApplicationService;


import com.cqut.czb.bn.entity.entity.VIPApply;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatVipApply;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.security.Principal;

/**
 * 会员申请
 */
@Component
public interface VipApplyService {

    PageInfo<VIPApply> selectVipApply(VIPApply vipApply);

    boolean updateVipApply(VIPApply vipApply);

    Boolean applyWCPVip(WeChatVipApply weChatVipApply, Principal principal);
}
