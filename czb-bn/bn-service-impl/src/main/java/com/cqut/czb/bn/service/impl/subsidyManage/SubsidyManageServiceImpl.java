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

import java.text.DecimalFormat;
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
    public JSONResult createSubsidyMission(CreateSubsidiesQueryDTO createDto, UserIds input) {

        // 生成补贴任务
        String missionId = createId();
        SubsidyMission subsidyMission = new SubsidyMission(missionId ,input.getSubsidyRent(), input.getSubsidyMonth());
        subsidyMission.setSubsidyType(createDto.getPartner());
        // 设置是否指定金额
        if(null == input.getSubsidyMoney()){
            subsidyMission.setMoneyType(0); // 比例金额
        } else {
            subsidyMission.setMoneyType(1); // 指定金额
        }
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
                        // 如果前端传来的指定金额不为空，则type为1
                        if(null != input.getSubsidyMoney()) {
                            missions.add(getOneMission(dto, missionId, 1,input));
                        } else {
                            missions.add(getOneMission(dto, missionId, 0,input));
                        }
                    }
                } else {
                    CreateSubsidiesQueryDTO dto = createSubsidiesQueryDTOs.get(i);
                    // 如果前端传来的指定金额不为空，则type为1
                    if(null != input.getSubsidyMoney()) {
                        missions.add(getOneMission(dto, missionId, 1,input));
                    } else {
                        missions.add(getOneMission(dto, missionId, 0,input));
                    }
                }
            }
            System.out.println("删后长度" + createSubsidiesQueryDTOs.size());
        } else {
            for (CreateSubsidiesQueryDTO data:createSubsidiesQueryDTOs) {
                // 如果前端传来的指定金额不为空，则type为1
                if(null != input.getSubsidyMoney()) {
                    missions.add(getOneMission(data, missionId, 1,input));
                } else {
                    missions.add(getOneMission(data, missionId, 0,input));
                }
            }
        }
        System.out.println("任务长度"+missions.size());

        boolean insertSuccess = (missions.size() == subsidyManageMapper.insertMissionUserRelation(missions)) ;

        if (insertSubsidyMission == 1 && insertSuccess) {
            return new JSONResult("生成补贴任务成功", 200, missionId);
        } else {
            return new JSONResult("生成补贴任务失败", 500, insertSubsidyMission + " " + insertSuccess);
        }
    }

    public JSONResult seeTableData(String missionId, PageDTO pageDTO) {
        Page<SeeSubsidy> subsidyListFirst = subsidyManageMapper.seeTableData(missionId);
        Double allAmount = Double.valueOf("0");
        for(SeeSubsidy data:subsidyListFirst){
            allAmount = allAmount + data.getAmount();
        }
        DecimalFormat df = new DecimalFormat("#.00");
        SeeSubsidyListDTO result = new SeeSubsidyListDTO();
        result.setAmountAll(Double.valueOf(df.format(allAmount)));
        result.setPeopleAmount(subsidyListFirst.size());
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        Page<SeeSubsidy> subsidyList = subsidyManageMapper.seeTableData(missionId);
        result.setSeeSubsidyPageInfo(new PageInfo(subsidyList));


        return new JSONResult(result);
    }

    /**
     * 构造并返回补贴任务类
     * @param oneDto
     * @param missionId
     * @param type
     * @param input
     * @return
     */
    public SubsidyMissionUser getOneMission(CreateSubsidiesQueryDTO oneDto, String missionId, Integer type, UserIds input) {
        SubsidyMissionUser mission = new SubsidyMissionUser();
        mission.setRelationId(createId());
        mission.setMissionId(missionId);
        mission.setUserId(oneDto.getUserId());
        if(type == 0) {
            mission.setAmount(oneDto.getSubsidies());
        } else if(type == 1){
            mission.setAmount(input.getSubsidyMoney());
        }

        return mission;
    }

    public static String createMillisTimestamp() {
        return String.valueOf(System.nanoTime());
    }

    public static String createId() {
        return createMillisTimestamp() + "" + random.nextInt(10) + "" + random.nextInt(10);
    }
}
