package com.cqut.czb.bn.api.controller.publishInfoAudit;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.publishInfoAudit.PublishInfoId;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.publishInfoAudit.PublishInfoAuditService;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/publishInfoAudit")
public class PublishInfoAuditController {

    @Autowired
    private PublishInfoAuditService publishInfoAuditService;

    /**
     * 获取表格数据与查询
     * @param publishInfoId
     * @param pageDTO
     * @return
     */
    @GetMapping("/getPublishTableData")
    public JSONResult getPublishTableData(PublishInfoId publishInfoId, PageDTO pageDTO){
        return publishInfoAuditService.getPublishTableData(publishInfoId, pageDTO);
    }

    /**
     * 审核
     * @param publishInfoId
     * @return
     */
    @PostMapping("/changeIsExamine")
    public JSONResult changeIsExamine(@RequestBody  PublishInfoId publishInfoId){
        return publishInfoAuditService.changeIsExamine(publishInfoId.getInfoId());
    }
}
