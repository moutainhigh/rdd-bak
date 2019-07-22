package com.cqut.czb.bn.service.publishInfoAudit;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.publishInfoAudit.PublishInfoId;
import com.cqut.czb.bn.entity.global.JSONResult;

public interface PublishInfoAuditService {
    // 查询数据
    JSONResult getPublishTableData(PublishInfoId publishInfoId, PageDTO pageDTO);

    // 审核
    JSONResult changeIsExamine(String infoId);
}
