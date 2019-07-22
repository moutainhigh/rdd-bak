package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.publishInfoAudit.PublishInfo;
import com.cqut.czb.bn.entity.dto.publishInfoAudit.PublishInfoId;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

public interface PublishInfoAuditMapperExtra {
    // 查询表格数据
    Page<PublishInfo> getPublishTableData(PublishInfoId publishInfoId);

    // 审核
    int changeIsExamine(@Param("infoId") String infoId);
}
