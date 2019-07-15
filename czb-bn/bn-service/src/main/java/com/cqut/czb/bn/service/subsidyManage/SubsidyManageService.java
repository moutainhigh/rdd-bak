package com.cqut.czb.bn.service.subsidyManage;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.subsidyManage.SubsidySearchDTO;
import com.cqut.czb.bn.entity.entity.subsidyManage.Subsidy;
import com.cqut.czb.bn.entity.entity.subsidyManage.SubsidyMission;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;


/**
 * auth: 谭深化
 * module：后台补贴模块
 * time：2019-07-11
 */

@CrossOrigin
public interface SubsidyManageService {
    // 补贴记录管理获取表格数据
    PageInfo<Subsidy> getSubsidyData(SubsidySearchDTO searchDTO, PageDTO pageDTO);

    // 补贴管理获取表格数据
    PageInfo<SubsidyMission> getSubsidyMissionData(SubsidySearchDTO searchDTO, PageDTO pageDTO);
}
