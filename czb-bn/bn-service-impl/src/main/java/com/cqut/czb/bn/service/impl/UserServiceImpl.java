package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.myTeam.RecommenderDTO;
import com.cqut.czb.bn.entity.dto.myTeam.TeamDTO;
import com.cqut.czb.bn.entity.dto.partnerVipIncome.PartnerVipIncomeDTO;
import com.cqut.czb.bn.entity.dto.role.RoleDTO;
import com.cqut.czb.bn.entity.dto.role.RoleInputDTO;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.dto.user.UserIdDTO;
import com.cqut.czb.bn.entity.dto.user.UserInputDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.service.IUserService;
import com.cqut.czb.bn.util.RedisUtil;
import com.cqut.czb.bn.util.date.DateUtil;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    private final UserMapper userMapper;

    private final UserMapperExtra userMapperExtra;

    private final UserRoleMapperExtra userRoleMapperExtra;

    private final RoleMapperExtra roleMapperExtra;

    private final DictMapperExtra dictMapperExtra;

    private final IndicatorRecordMapperExtra indicatorRecordMapperExtra;

    private final IndicatorRecordMapper indicatorRecordMapper;

    private final RedisUtil redisUtil;

    private final PartnerVipIncomeMapperExtra partnerVipIncomeMapperExtra;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserMapperExtra userMapperExtra, UserRoleMapperExtra userRoleMapperExtra, RoleMapperExtra roleMapperExtra, DictMapperExtra dictMapperExtra, IndicatorRecordMapperExtra indicatorRecordMapperExtra, IndicatorRecordMapper indicatorRecordMapper, RedisUtil redisUtil,PartnerVipIncomeMapperExtra partnerVipIncomeMapperExtra) {
        this.userMapper = userMapper;
        this.userMapperExtra = userMapperExtra;
        this.userRoleMapperExtra = userRoleMapperExtra;
        this.roleMapperExtra = roleMapperExtra;
        this.dictMapperExtra = dictMapperExtra;
        this.indicatorRecordMapperExtra = indicatorRecordMapperExtra;
        this.indicatorRecordMapper = indicatorRecordMapper;
        this.redisUtil = redisUtil;
        this.partnerVipIncomeMapperExtra = partnerVipIncomeMapperExtra;
    }

    @Override
    public boolean deleteUser(UserIdDTO userIdDTO) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userIdDTO.getUserId());
        List<UserRole> userRoleList = userRoleMapperExtra.slectUserRoleList(userRole);
        boolean isDelete = true;
        if(userRoleList.size() > 0) {

            isDelete = userRoleMapperExtra.deleteUserRoles(userRoleList) > 0;
        }
        if(isDelete) {
            return userMapperExtra.deleteUser(userIdDTO) > 0;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateUser(UserInputDTO userInputDTO) {
        if(null != userInputDTO.getSuperiorUser() && !"".equals(userInputDTO.getSuperiorUser())) {
            User superior = userMapperExtra.findUserByAccount(userInputDTO.getSuperiorUser());
            userInputDTO.setSuperiorUser(superior.getUserId());
            userInputDTO.setFirstLevelPartner(superior.getFirstLevelPartner());
            userInputDTO.setSecondLevelPartner(superior.getSecondLevelPartner());
            if(1 == superior.getPartner()) {
                userInputDTO.setSecondLevelPartner(superior.getUserId());
            }
            if(2 == superior.getPartner()) {
                userInputDTO.setFirstLevelPartner(superior.getUserId());
            }
            return userMapperExtra.updateUser(userInputDTO) > 0;
        }
        return false;
    }

    @Override
    public PageInfo<UserDTO> selectUser(UserInputDTO userInputDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(),true);
        List<UserDTO> userList = userMapperExtra.selectUser(userInputDTO);

        return new PageInfo<>(userList);
    }

    @Override
    public boolean assignRole(UserInputDTO userInputDTO) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userInputDTO.getUserId());
        List<UserRole> deleteList = userRoleMapperExtra.slectUserRoleList(userRole);
        boolean isInsert = true;
        if(userInputDTO.getRoleId() != null && !"".equals(userInputDTO)) {
            List<UserRole> tempList = new ArrayList<>();
            List<UserRole> insertList = initUserRoleList(userInputDTO);
            for(UserRole delete: deleteList) {
                for(UserRole insert: insertList) {
                    if(delete.getRoleId().equals(insert.getRoleId())) {
                        tempList.add(delete);
                        insertList.remove(insert);
                        break;
                    }
                }
            }
            for(UserRole temp: tempList) {
                deleteList.remove(temp);
            }
            if(insertList.size() > 0) {
                for (UserRole userRole1 : insertList){
                    if (userRole1.getRoleId() != null){
                        RoleInputDTO exp = new RoleInputDTO();
                        exp.setRoleId(userRole1.getRoleId());
                        List<RoleDTO> roleList = roleMapperExtra.selectRole(exp);
                        if (roleList!=null && roleList.size()!=0) {
                            if ("普通用户".equals(roleList.get(0).getRoleName())){
                                UserInputDTO user = new UserInputDTO();
                                user.setUserId(userInputDTO.getUserId());
                                user.setIsLoginPc(0);
                                userMapperExtra.updateUser(user);
                            }else {
                                UserInputDTO user = new UserInputDTO();
                                user.setUserId(userInputDTO.getUserId());
                                user.setIsLoginPc(1);
                                userMapperExtra.updateUser(user);
                            }
                            UserDTO user = userMapperExtra.findUserDTOById(userInputDTO.getUserId());

                            if(redisUtil.hasKey(user.getUserAccount())) {
                                redisUtil.remove(user.getUserAccount());
                                redisUtil.put(user.getUserAccount(), user);
                            }

                        }
                    }
                }
                isInsert = userRoleMapperExtra.insertUserRoles(insertList) > 0;
            }
        }
        return isInsert;
    }

    @Override
    public UserDTO selectUserInfo(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUserAccount(user.getUserAccount());
        userDTO.setUserName(user.getUserName());

        UserRole userRole = new UserRole();
        userRole.setUserId(userDTO.getUserId());
        List<UserRole> userRoleList = userRoleMapperExtra.slectUserRoleList(userRole);
        List<RoleDTO> roleDTOList = new ArrayList<>();
        for(UserRole userRole1: userRoleList) {
            RoleInputDTO roleInputDTO = new RoleInputDTO();
            roleInputDTO.setRoleId(userRole1.getRoleId());
            roleDTOList.add(roleMapperExtra.selectRole(roleInputDTO).get(0));
        }
        userDTO.setRoleList(roleDTOList);
        return userDTO;
    }

    @Override
    public List<TeamDTO> selectTeam(String userId) {
        //        for(int i = 0; i < teamDTOList.size(); i++) {
//            teamDTOList.get(i).setUserAccount(
////                    teamDTOList.get(i).getUserAccount().replaceAll(
////                            "(\\d{3})\\d{4}(\\d{4})","$1****$2"));
//                    teamDTOList.get(i).setUserAccount();
//        }
        return userMapperExtra.selectTeam(userId);
    }

    @Override
    public RecommenderDTO selectRecommender(String userId) {
        return userMapperExtra.selectRecommender(userId);
    }

    @Override
    public boolean changePartner(UserInputDTO userInputDTO) {
        IndicatorRecord indicatorRecord = indicatorRecordMapperExtra.selectIndicatorRecordInfo(userInputDTO.getUserId());
        PartnerVipIncomeDTO partnerVipIncomeDTO = partnerVipIncomeMapperExtra.selectVipIncomeByPartnerId(userInputDTO.getUserId());
        if (userInputDTO.getPartner()!=0){
            if (partnerVipIncomeDTO == null){
                PartnerVipIncome partnerVipIncome = new PartnerVipIncome();
                partnerVipIncome.setPartnerVipIncomeId(StringUtil.createId());
                partnerVipIncome.setPartnerId(userInputDTO.getUserId());
                partnerVipIncome.setIsSettle(0);
                partnerVipIncome.setCreateAt(new Date());
                partnerVipIncome.setStartTime(new Date());
                partnerVipIncome.setPartnerType(userInputDTO.getPartner());
                Boolean isInsert = partnerVipIncomeMapperExtra.insertIncome(partnerVipIncome)>0;
                if (!isInsert){
                    return false;
                }
            }
        }
        User superUser = userMapperExtra.findUserByAccount(userInputDTO.getSuperiorUser());
        if (superUser!=null && superUser.getUserId()!=null){   //前台传过来的为父级的userAccount,转换为id
            userInputDTO.setSuperiorUser(superUser.getUserId());
        }else {
            userInputDTO.setSuperiorUser("");
        }
        UserDTO userDTO = userMapperExtra.findUserDTOById(userInputDTO.getUserId());
        boolean isUpdateIndicatorRecord =true;
         if(0 == userInputDTO.getPartner()){
            if (superUser==null || superUser.getPartner()==null){   //如果没有父级可能为降级，则将oldSuperior填入super
                userInputDTO.setOldSuperior(userDTO.getOldSuperior());
            }
            userInputDTO.setIsLoginPc(0);
        }
        else if(null != indicatorRecord) {
            indicatorRecord.setUpdateAt(new Date());
            indicatorRecord.setIndicatorBeginTime(DateUtil.getDateStart(userInputDTO.getMissionStartTime()));
            Calendar cal = Calendar.getInstance();
            cal.setTime(userInputDTO.getMissionStartTime());
            cal.add(Calendar.MONTH, 1);
            cal.add(Calendar.DAY_OF_MONTH,-1);
            userInputDTO.setMissionEndTime(cal.getTime());
            indicatorRecord.setIndicatorEndTime(DateUtil.getDateEnd(cal.getTime()));
            if( 1 == userInputDTO.getPartner()) {
                indicatorRecord.setTargetPromotionNumber(Integer.parseInt(dictMapperExtra.selectDictByName("ordinaryNumIndicators").getContent()));
                indicatorRecord.setTargetNewConsumer(Integer.parseInt(dictMapperExtra.selectDictByName("ordinaryConNumIndicators").getContent()));
                indicatorRecord.setActualNewConsumer(0);
                indicatorRecord.setActualPromotionNumber(0);
                userInputDTO.setSecondLevelPartner("");
                if (superUser != null && superUser.getPartner()!=2){   //如果上级不是事业合伙人就要脱离
                    userInputDTO.setOldSuperior(userInputDTO.getSuperiorUser());
                    userInputDTO.setSuperiorUser("");
                }
                if (superUser == null || superUser.getPartner()==null){  //如果没有推荐人则看是否为事业合伙人降级,以前是否有事业合伙人上级
                    if (userDTO!=null && userDTO.getOldSuperior()!=null && !"".equals(userDTO.getOldSuperior())){
                        User oldSuperior = userMapper.selectByPrimaryKey(userDTO.getOldSuperior());
                        if (oldSuperior.getPartner()==2) {
                            userInputDTO.setSuperiorUser(userDTO.getOldSuperior());
                        }
                    }
                }
                //当升级为普通合伙人时，如果上级有事业合伙人，那么就暂时归到上级的事业合伙人的团队中
                if (superUser != null && superUser.getFirstLevelPartner()!=null && superUser.getFirstLevelPartner()!=""){
                    userInputDTO.setSuperiorUser(userInputDTO.getFirstLevelPartner());
                }
                isUpdateIndicatorRecord = indicatorRecordMapper.updateByPrimaryKey(indicatorRecord) > 0;
            }
            if( 2 == userInputDTO.getPartner()) {
                indicatorRecord.setTargetPromotionNumber(Integer.parseInt(dictMapperExtra.selectDictByName("businessNumIndicators").getContent()));
                indicatorRecord.setTargetNewConsumer(Integer.parseInt(dictMapperExtra.selectDictByName("businessConNumIndicators").getContent()));
                indicatorRecord.setActualNewConsumer(0);
                indicatorRecord.setActualPromotionNumber(0);
                isUpdateIndicatorRecord = indicatorRecordMapper.updateByPrimaryKey(indicatorRecord) > 0;
                userInputDTO.setFirstLevelPartner("");
                userInputDTO.setSecondLevelPartner("");
                userInputDTO.setOldSuperior(userInputDTO.getSuperiorUser());
                userInputDTO.setSuperiorUser("");
                //如果有暂归到其他事业合伙人名下的自身以前的普通合伙人下级
                List<User> userList = userMapperExtra.getOldSubUserPartner(userInputDTO.getUserId());
                if (userList!=null && userList.size()>0){
                    Boolean update = userMapperExtra.updateSuperUser(userList)>0; //当升级之后就归还
                }
            }
            userInputDTO.setIsLoginPc(1);
        } else if(0 != userInputDTO.getPartner()) {
            indicatorRecord = new IndicatorRecord();
            indicatorRecord.setRecordId(StringUtil.createId());
            indicatorRecord.setCreateAt(new Date());
            indicatorRecord.setState(0);
            indicatorRecord.setUserId(userInputDTO.getUserId());
            indicatorRecord.setIndicatorBeginTime(DateUtil.getDateStart(userInputDTO.getMissionStartTime()));
            Calendar cal = Calendar.getInstance();
            cal.setTime(userInputDTO.getMissionStartTime());
            cal.add(Calendar.MONTH, 1);
            cal.add(Calendar.DAY_OF_MONTH,-1);
            userInputDTO.setMissionEndTime(cal.getTime());
            indicatorRecord.setIndicatorEndTime(DateUtil.getDateEnd(cal.getTime()));
            if (1 == userInputDTO.getPartner()) {
                indicatorRecord.setTargetPromotionNumber(Integer.parseInt(dictMapperExtra.selectDictByName("ordinaryNumIndicators").getContent()));
                indicatorRecord.setTargetNewConsumer(Integer.parseInt(dictMapperExtra.selectDictByName("ordinaryConNumIndicators").getContent()));
                indicatorRecord.setActualNewConsumer(0);
                indicatorRecord.setActualPromotionNumber(0);
                isUpdateIndicatorRecord = indicatorRecordMapper.insertSelective(indicatorRecord) > 0;
                userInputDTO.setSecondLevelPartner("");
                if(superUser != null && 2 != superUser.getPartner()){
                    userInputDTO.setOldSuperior(userInputDTO.getSuperiorUser());
                    userInputDTO.setSuperiorUser("");
                }
                if (superUser == null || superUser.getPartner()==null){  //如果没有推荐人则看是否为事业合伙人降级,以前是否有事业合伙人上级
                    if (userDTO!=null && userDTO.getOldSuperior()!=null && !"".equals(userDTO.getOldSuperior())){
                        User oldSuperior = userMapper.selectByPrimaryKey(userDTO.getOldSuperior());
                        if (oldSuperior.getPartner()==2) {
                        userInputDTO.setSuperiorUser(userDTO.getOldSuperior());
                        }
                    }
                }
                //当升级为普通合伙人时，如果上级有事业合伙人，那么就暂时归到上级的事业合伙人的团队中
                if (superUser != null && superUser.getFirstLevelPartner()!=null && superUser.getFirstLevelPartner()!=""){
                    userInputDTO.setSuperiorUser(userInputDTO.getFirstLevelPartner());
                }
            }
            if (2 == userInputDTO.getPartner()) {
                indicatorRecord.setTargetPromotionNumber(Integer.parseInt(dictMapperExtra.selectDictByName("businessNumIndicators").getContent()));
                indicatorRecord.setTargetNewConsumer(Integer.parseInt(dictMapperExtra.selectDictByName("businessConNumIndicators").getContent()));
                indicatorRecord.setActualNewConsumer(0);
                indicatorRecord.setActualPromotionNumber(0);
                isUpdateIndicatorRecord = indicatorRecordMapper.insertSelective(indicatorRecord) > 0;
                userInputDTO.setFirstLevelPartner("");
                userInputDTO.setSecondLevelPartner("");
                userInputDTO.setOldSuperior(userInputDTO.getSuperiorUser());
                userInputDTO.setSuperiorUser("");
                //如果有暂归到其他事业合伙人名下的自身以前的普通合伙人下级
                List<User> userList = userMapperExtra.getOldSubUserPartner(userInputDTO.getUserId());
                if (userList!=null && userList.size()>0){
                    Boolean update = userMapperExtra.updateSuperUser(userList)>0; //当升级之后就归还
                }
            }
            userInputDTO.setIsLoginPc(1);
        }
        
        if(isUpdateIndicatorRecord) {
            User user = userMapper.selectByPrimaryKey(userInputDTO.getUserId());
            userMapperExtra.insertAllSubUser(userInputDTO.getUserId());
            List<User> userList = userMapperExtra.getAllSubUser();
            if(null != userList && 0 < userList.size()) {
                if (0 == userInputDTO.getPartner()) {
                    if (1 == user.getPartner()) {
                        // 一级变0级
                        userMapperExtra.updatePartnerState(userList, null, "");
                    }
                    if (2 == user.getPartner()) {
                        // 二级变0级
                        userMapperExtra.updatePartnerState(userList, "", null);
                    }
                }
                if (1 == userInputDTO.getPartner()) {
                    if (2 == user.getPartner()) {
                        // 2级变1级
                        userMapperExtra.updatePartnerState(userList, "", userInputDTO.getUserId());
                    }
                    if (0 == user.getPartner()) {
                        userMapperExtra.updatePartnerState(userList, null, userInputDTO.getUserId());
                    }
                }
                if (2 == userInputDTO.getPartner()) {
                    if (1 == user.getPartner()) {
                        // 1级变2级
                        userMapperExtra.updatePartnerState(userList, userInputDTO.getUserId(), "");
                    }
                    if (0 == user.getPartner()) {
                        userMapperExtra.updatePartnerState(userList, userInputDTO.getUserId(), null);
                    }
                }
            }
            if(userMapperExtra.updateUser(userInputDTO) > 0) {
                UserDTO userDTO1 = userMapperExtra.findUserDTOById(userInputDTO.getUserId());
                if(redisUtil.hasKey(user.getUserAccount())) {
                    redisUtil.remove(user.getUserAccount());
                    redisUtil.put(user.getUserAccount(), userDTO1);
                }
            }
            String roleId= roleMapperExtra.selectRoleIdByRoleName("合伙人");
            if(roleId != null){
                userInputDTO.setRoleId(roleId);
                return this.assignRole(userInputDTO);
            }
            return true;
        } else {
            return false;
        }
    }

    public List<UserRole> initUserRoleList(UserInputDTO userInputDTO) {
        List<UserRole> userRoleList = new ArrayList<>();
        for(String roleId : userInputDTO.getRoleId().split(",")) {
            UserRole userRole = new UserRole();
            userRole.setId(StringUtil.createId());
            userRole.setUserId(userInputDTO.getUserId());
            userRole.setRoleId(roleId);
            userRoleList.add(userRole);
        }

        return userRoleList;
    }
}
