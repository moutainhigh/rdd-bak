package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.subsidyManage.SubsidySearchDTO;
import com.cqut.czb.bn.entity.entity.subsidyManage.Subsidy;
import com.cqut.czb.bn.entity.entity.subsidyManage.SubsidyIncomeLog;
import com.cqut.czb.bn.entity.entity.subsidyManage.SubsidyMission;
import com.cqut.czb.bn.entity.entity.subsidyManage.SubsidyMissionUser;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
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

    // 找出某个补贴任务的所有用户
    List<SubsidyIncomeLog> getSubsidyMissionSomeInfo(@Param("missionId") String missionId);

    // 循环插入变更记录
    int inserLog(@Param("list") List<SubsidyIncomeLog> list);

    // 循环修改收益记录
    int changeIncome(@Param("list") List<SubsidyIncomeLog> list);

    // 更新补贴任务状态
    int updateState(@Param("missionId") String missionId);

    // 插入补贴任务
    int insertSubsidyMission(SubsidyMission mission);

    // 插入补贴任务，用户id关系数据
    int insertMissionUserRelation(@Param("list") List<SubsidyMissionUser> list);

    // 删除补贴任务前删除关系
    int deleteRelation(@Param("missionId") String missionId);
}
