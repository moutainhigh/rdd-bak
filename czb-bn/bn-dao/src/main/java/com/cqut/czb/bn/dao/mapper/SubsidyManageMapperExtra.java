package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.subsidyManage.SubsidySearchDTO;
import com.cqut.czb.bn.entity.entity.subsidyManage.Subsidy;
import com.cqut.czb.bn.entity.entity.subsidyManage.SubsidyMission;
import com.github.pagehelper.Page;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

/**
 * auth: 谭深化
 * module：后台补贴模块
 * time：2019-07-11
 */

@CrossOrigin
public interface SubsidyManageMapperExtra {
    // 获取表格数据
    Page<Subsidy> getSubsidyData(SubsidySearchDTO searchDTO);

    // 补贴记录管理获取表格数据
    Page<SubsidyMission> getSubsidyMissionData(SubsidySearchDTO searchDTO);

}
