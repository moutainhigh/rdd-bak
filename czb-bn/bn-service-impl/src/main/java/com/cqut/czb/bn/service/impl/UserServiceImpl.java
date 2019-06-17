package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.myTeam.RecommenderDTO;
import com.cqut.czb.bn.entity.dto.myTeam.TeamDTO;
import com.cqut.czb.bn.entity.dto.role.RoleDTO;
import com.cqut.czb.bn.entity.dto.role.RoleInputDTO;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.dto.user.UserIdDTO;
import com.cqut.czb.bn.entity.dto.user.UserInputDTO;
import com.cqut.czb.bn.entity.entity.IndicatorRecord;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.UserRole;
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

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserMapperExtra userMapperExtra, UserRoleMapperExtra userRoleMapperExtra, RoleMapperExtra roleMapperExtra, DictMapperExtra dictMapperExtra, IndicatorRecordMapperExtra indicatorRecordMapperExtra, IndicatorRecordMapper indicatorRecordMapper, RedisUtil redisUtil) {
        this.userMapper = userMapper;
        this.userMapperExtra = userMapperExtra;
        this.userRoleMapperExtra = userRoleMapperExtra;
        this.roleMapperExtra = roleMapperExtra;
        this.dictMapperExtra = dictMapperExtra;
        this.indicatorRecordMapperExtra = indicatorRecordMapperExtra;
        this.indicatorRecordMapper = indicatorRecordMapper;
        this.redisUtil = redisUtil;
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
            User user = userMapperExtra.findUserByAccount(userInputDTO.getSuperiorUser());
            userInputDTO.setSuperiorUser(user.getUserId());
        }
            return userMapperExtra.updateUser(userInputDTO) > 0;
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
                isInsert = userRoleMapperExtra.insertUserRoles(insertList) > 0;
            }
        }
        boolean isDelete = true;
        if(deleteList.size() > 0) {
            isDelete = userRoleMapperExtra.deleteUserRoles(deleteList) > 0;
        }
        return isInsert && isDelete;
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
        for(UserRole userRole1: userRoleList) {
            RoleInputDTO roleInputDTO = new RoleInputDTO();
            roleInputDTO.setRoleId(userRole1.getRoleId());
            List<RoleDTO> roleDTOList = roleMapperExtra.selectRole(roleInputDTO);
            userDTO.setRoleList(roleDTOList);
        }
        return userDTO;
    }

    @Override
    public List<TeamDTO> selectTeam(String userId) {
        List<TeamDTO> teamDTOList = userMapperExtra.selectTeam(userId);
        for(int i = 0; i < teamDTOList.size(); i++) {
            teamDTOList.get(i).setUserAccount(
                    teamDTOList.get(i).getUserAccount().replaceAll(
                            "(\\d{3})\\d{4}(\\d{4})","$1****$2"));
        }
        return teamDTOList;
    }

    @Override
    public RecommenderDTO selectRecommender(String userId) {
        return userMapperExtra.selectRecommender(userId);
    }

    @Override
    public boolean changePartner(UserInputDTO userInputDTO) {
        IndicatorRecord indicatorRecord = indicatorRecordMapperExtra.selectIndicatorRecordInfo(userInputDTO.getUserId());
        boolean isUpdateIndicatorRecord =true;
        if(null != indicatorRecord) {
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
                isUpdateIndicatorRecord = indicatorRecordMapper.updateByPrimaryKey(indicatorRecord) > 0;
            }
            if( 2 == userInputDTO.getPartner()) {
                indicatorRecord.setTargetPromotionNumber(Integer.parseInt(dictMapperExtra.selectDictByName("businessNumIndicators").getContent()));
                indicatorRecord.setTargetNewConsumer(Integer.parseInt(dictMapperExtra.selectDictByName("businessConNumIndicators").getContent()));
                indicatorRecord.setActualNewConsumer(0);
                indicatorRecord.setActualPromotionNumber(0);
                isUpdateIndicatorRecord = indicatorRecordMapper.updateByPrimaryKey(indicatorRecord) > 0;
                userInputDTO.setSuperiorUser("");
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
            }
            if (2 == userInputDTO.getPartner()) {
                indicatorRecord.setTargetPromotionNumber(Integer.parseInt(dictMapperExtra.selectDictByName("businessNumIndicators").getContent()));
                indicatorRecord.setTargetNewConsumer(Integer.parseInt(dictMapperExtra.selectDictByName("businessConNumIndicators").getContent()));
                indicatorRecord.setActualNewConsumer(0);
                indicatorRecord.setActualPromotionNumber(0);
                isUpdateIndicatorRecord = indicatorRecordMapper.insertSelective(indicatorRecord) > 0;
                userInputDTO.setSuperiorUser("");
            }
            userInputDTO.setIsLoginPc(1);
        } else if(0 == userInputDTO.getPartner()){
            userInputDTO.setIsLoginPc(0);
        }
        
        if(isUpdateIndicatorRecord) {
            if(userMapperExtra.updateUser(userInputDTO) > 0) {
                User user = userMapper.selectByPrimaryKey(userInputDTO.getUserId());
                redisUtil.put(user.getUserAccount(), user);
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
