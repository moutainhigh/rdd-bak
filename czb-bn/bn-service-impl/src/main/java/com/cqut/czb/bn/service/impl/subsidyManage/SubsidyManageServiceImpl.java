package com.cqut.czb.bn.service.impl.subsidyManage;

import com.cqut.czb.bn.dao.mapper.SubsidyManageMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.subsidyManage.SubsidySearchDTO;
import com.cqut.czb.bn.entity.entity.subsidyManage.Subsidy;
import com.cqut.czb.bn.entity.entity.subsidyManage.SubsidyMission;
import com.cqut.czb.bn.service.subsidyManage.SubsidyManageService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * auth: 谭深化
 * module：后台补贴模块
 * time：2019-07-11
 */

@Service
public class SubsidyManageServiceImpl implements SubsidyManageService {

    @Autowired
    private SubsidyManageMapperExtra subsidyManageMapper;

    @Override
    public PageInfo<Subsidy> getSubsidyData(SubsidySearchDTO searchDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        Page<Subsidy> subsidies = subsidyManageMapper.getSubsidyData(searchDTO);
        return new PageInfo(subsidies);
    }

    @Override
    public PageInfo<SubsidyMission> getSubsidyMissionData(SubsidySearchDTO searchDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());

        Page<SubsidyMission> subsidyMissions = subsidyManageMapper.getSubsidyMissionData(searchDTO);

        return new PageInfo(subsidyMissions);
    }
}
