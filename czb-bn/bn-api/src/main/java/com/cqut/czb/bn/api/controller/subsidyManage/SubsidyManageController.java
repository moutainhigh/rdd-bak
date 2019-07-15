package com.cqut.czb.bn.api.controller.subsidyManage;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.subsidyManage.SubsidySearchDTO;
import com.cqut.czb.bn.entity.entity.subsidyManage.Subsidy;
import com.cqut.czb.bn.entity.entity.subsidyManage.SubsidyMission;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.subsidyManage.SubsidyManageService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * auth: 谭深化
 * module：后台补贴模块
 * time：2019-07-11
 */

@CrossOrigin
@RestController
@RequestMapping("/api/subsidyManage")
public class SubsidyManageController {
    @Autowired
    private SubsidyManageService subsidyManageService;

    /**
     * 获取表格数据
     * @param searchDTO
     * @return
     */
    @GetMapping("/getSubsidyManageData")
    public JSONResult getTableData(SubsidySearchDTO searchDTO, PageDTO pageDTO) {
        PageInfo<Subsidy> subsidyList = subsidyManageService.getSubsidyData(searchDTO, pageDTO);

        return new JSONResult(subsidyList);
    }


    @GetMapping("/getSubsidyMissionData")
    public JSONResult getSubsidyMissionData(SubsidySearchDTO searchDTO, PageDTO pageDTO){
        PageInfo<SubsidyMission> subsidyMissionList = subsidyManageService.getSubsidyMissionData(searchDTO, pageDTO);

        return new JSONResult(subsidyMissionList);
    }
}
