package com.cqut.czb.bn.service.vipApplicationService;


import com.cqut.czb.bn.entity.entity.VIPApply;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

/**
 * 会员申请
 */
@Component
public interface VipApplyService {

   PageInfo<VIPApply> getvip(VIPApply record);
}
