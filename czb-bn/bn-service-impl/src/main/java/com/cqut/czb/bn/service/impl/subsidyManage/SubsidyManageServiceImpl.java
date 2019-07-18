package com.cqut.czb.bn.service.impl.subsidyManage;

import com.cqut.czb.bn.dao.mapper.SubsidyManageMapperExtra;
import com.cqut.czb.bn.dao.mapper.SubsidyMissionMapper;
import com.cqut.czb.bn.dao.mapper.SubsidyMissionMapperExtra;
import com.cqut.czb.bn.entity.dto.CreateSubsidies.CreateSubsidiesOutputDTO;
import com.cqut.czb.bn.entity.dto.CreateSubsidies.CreateSubsidiesQueryDTO;
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
import java.util.Random;

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

    @Autowired
    private SubsidyMissionMapperExtra subsidyMissionMapperExtra;

    private static Random random = new Random();


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
        int deleteRelation = subsidyManageMapper.deleteRelation(missionId);
        return deleteRelation> 0 && subsidyMissionMapper.deleteByPrimaryKey(missionId) > 0;
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
    public JSONResult createSubsidyMission(CreateSubsidiesQueryDTO createDto, UserIds input) {

        // 生成补贴任务
        String missionId = createId();
        SubsidyMission subsidyMission = new SubsidyMission(missionId ,input.getSubsidyRent(), input.getSubsidyMonth());
        subsidyMission.setSubsidyType(createDto.getPartner());
        int insertSubsidyMission = subsidyManageMapper.insertSubsidyMission(subsidyMission);
        System.out.println("1234123412341234123412341234123" +missionId);

        // 删除前端页面勾选出的用户(若有) 并生成补贴、用户关系数据
        // createSubsidiesQueryDTOs为根据前端查询的条件，进行所有用户的查询，然后删除前端取消的userId后进行插入补贴关系数据list
        List<CreateSubsidiesQueryDTO> createSubsidiesQueryDTOs = subsidyMissionMapperExtra.getPartnerSubordinates(createDto);
        List<SubsidyMissionUser> missions = new ArrayList<>(); // 补贴关系数据list
        if(input.getUserIdS() != null){
            System.out.println("初始长度" + createSubsidiesQueryDTOs.size());
            String[] ids= input.getUserIdS().split(",");
            System.out.println("要删长度" + ids.length);
            int times = 0; // 若ids删完了，则不进行删除id的for循环，提高性能
            for (int i = 0; i<createSubsidiesQueryDTOs.size(); i++) {
                if(times != ids.length) {
                    boolean isDelete = false; // 是否进行了删除
                    for (String id:ids) {
                        if(createSubsidiesQueryDTOs.get(i).getUserId().equals(id)){
                            createSubsidiesQueryDTOs.remove(i);
                            i--;
                            times++;
                            isDelete = true;
                            break;
                        }
                    }
                    if(!isDelete){
                        CreateSubsidiesQueryDTO dto = createSubsidiesQueryDTOs.get(i);
                        SubsidyMissionUser mission = new SubsidyMissionUser();
                        mission.setRelationId(createId());
                        mission.setMissionId(missionId);
                        mission.setUserId(dto.getUserId());
                        mission.setAmount(dto.getSubsidies());
                        missions.add(mission);
                    }
                } else {
                    CreateSubsidiesQueryDTO dto = createSubsidiesQueryDTOs.get(i);
                    SubsidyMissionUser mission = new SubsidyMissionUser();
                    mission.setRelationId(createId());
                    mission.setMissionId(missionId);
                    mission.setUserId(dto.getUserId());
                    mission.setAmount(dto.getSubsidies());
                    missions.add(mission);
                }
            }
            System.out.println("删后长度" + createSubsidiesQueryDTOs.size());
        } else {
            for (CreateSubsidiesQueryDTO data:createSubsidiesQueryDTOs) {
                SubsidyMissionUser mission = new SubsidyMissionUser();
                mission.setRelationId(createId());
                mission.setMissionId(missionId);
                mission.setUserId(data.getUserId());
                mission.setAmount(data.getSubsidies());
                missions.add(mission);
            }
        }
        System.out.println("任务长度"+missions.size());

        boolean insertSuccess = (missions.size() == subsidyManageMapper.insertMissionUserRelation(missions)) ;

        if (insertSubsidyMission == 1 && insertSuccess) {
            return new JSONResult("生成补贴任务成功", 200, insertSubsidyMission + " " + insertSuccess);
        } else {
            return new JSONResult("生成补贴任务失败", 500, insertSubsidyMission + " " + insertSuccess);
        }
    }


    public static String createMillisTimestamp() {
        return String.valueOf(System.nanoTime());
    }

    public static String createId() {
        return createMillisTimestamp() + "" + random.nextInt(10) + "" + random.nextInt(10);
    }
}
