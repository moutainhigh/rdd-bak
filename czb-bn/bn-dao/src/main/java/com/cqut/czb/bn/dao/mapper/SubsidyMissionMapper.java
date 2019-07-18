package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.SubsidyMission;
import org.apache.ibatis.annotations.Param;

public interface SubsidyMissionMapper {
    int deleteByPrimaryKey(String missionId);

    int insert(SubsidyMission record);

    int insertSelective(SubsidyMission record);

    SubsidyMission selectByPrimaryKey(String missionId);

    int updateByPrimaryKeySelective(SubsidyMission record);

    int updateByPrimaryKey(SubsidyMission record);
}