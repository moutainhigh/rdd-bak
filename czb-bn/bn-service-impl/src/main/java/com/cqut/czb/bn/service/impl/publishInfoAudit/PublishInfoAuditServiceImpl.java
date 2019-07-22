package com.cqut.czb.bn.service.impl.publishInfoAudit;

import com.cqut.czb.bn.dao.mapper.PublishInfoAuditMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.publishInfoAudit.PublishInfo;
import com.cqut.czb.bn.entity.dto.publishInfoAudit.PublishInfoId;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.publishInfoAudit.PublishInfoAuditService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublishInfoAuditServiceImpl implements PublishInfoAuditService{
    @Autowired
    private PublishInfoAuditMapperExtra publishInfoAuditMapperExtra;

    public JSONResult getPublishTableData(PublishInfoId publishInfoId, PageDTO pageDTO){
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        Page<PublishInfo> publishInfoList = publishInfoAuditMapperExtra.getPublishTableData(publishInfoId);

        return new JSONResult(new PageInfo(publishInfoList));
    }

    @Override
    public JSONResult changeIsExamine(String infoId) {
        int success = publishInfoAuditMapperExtra.changeIsExamine(infoId);
        if(success > 0) {
            return new JSONResult("审核成功", 200);
        } else {
            return new JSONResult("审核失败", 200);
        }
    }
}
