package com.cqut.czb.bn.service.impl.subsidyManage;

import com.cqut.czb.bn.dao.mapper.SubsidyManageMapperExtra;
import com.cqut.czb.bn.dao.mapper.SubsidyMissionMapper;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.subsidyManage.SubsidySearchDTO;
import com.cqut.czb.bn.entity.entity.subsidyManage.*;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.subsidyManage.SubsidyManageService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Autowired
    private SubsidyMissionMapper subsidyMissionMapper;

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

    @Override
    public boolean deleteSubsidyMission(String missionId) {

        return subsidyMissionMapper.deleteByPrimaryKey(missionId) > 0;
    }

    @Override
    public JSONResult sendSubsidy(String missionId) {
        List<SubsidyIncomeLog> missionUsers = subsidyManageMapper.getSubsidyMissionSomeInfo(missionId);

        boolean success = (missionUsers.size() == subsidyManageMapper.inserLog(missionUsers));

        int successChangeIncome = subsidyManageMapper.changeIncome(missionUsers);

        int successChangeState = subsidyManageMapper.updateState(missionId);

        if(success && (successChangeState > 0)) {
            return new JSONResult("发放补贴成功", 200, success + " " + successChangeIncome + " " + successChangeState);
        } else {
            return new JSONResult("发放补贴失败", 500, success + " " + successChangeIncome + " " + successChangeState);
        }
    }

    @Override
    public JSONResult createSubsidyMission(UserIds input) {

        // 生成补贴任务
        String missionId = StringUtil.createId();
        SubsidyMission subsidyMission = new SubsidyMission(missionId ,input.getSubsidyRent(), input.getSubsidyMonth());
        int insertSubsidyMission = subsidyManageMapper.insertSubsidyMission(subsidyMission);
        System.out.println("1234123412341234123412341234123" +missionId);

        // 删除前端页面勾选出的用户

        // 生成补贴、用户关系数据
        // TODO 测试数据
        List<SubsidyMissionUser> missions = new ArrayList<>();
        SubsidyMissionUser a = new SubsidyMissionUser();
        a.setRelationId(StringUtil.createId());
        a.setMissionId(missionId);
        a.setUserId("155896401758586");
        a.setAmount((double) 50);
        SubsidyMissionUser b = new SubsidyMissionUser();
        b.setRelationId(StringUtil.createId());
        b.setMissionId(missionId);
        b.setUserId("155894225604277");
        b.setAmount((double) 50);

        missions.add(a);
        missions.add(b);

        boolean insertSuccess = (missions.size() == subsidyManageMapper.insertMissionUserRelation(missions)) ;

        if (insertSubsidyMission == 1 && insertSuccess) {
            return new JSONResult("生成补贴任务成功", 200, insertSubsidyMission + " " + insertSuccess);
        } else {
            return new JSONResult("生成补贴任务失败", 500, insertSubsidyMission + " " + insertSuccess);
        }
    }
}
