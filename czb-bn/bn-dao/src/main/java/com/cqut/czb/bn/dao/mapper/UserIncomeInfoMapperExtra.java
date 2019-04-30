package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.appPersonalCenter.UserIncomeInfoDTO;
import com.cqut.czb.bn.entity.entity.UserIncomeInfo;

import java.util.List;

public interface UserIncomeInfoMapperExtra {
    int deleteByPrimaryKey(String infoId);

    int insert(UserIncomeInfo record);

    int insertSelective(UserIncomeInfo record);

    UserIncomeInfo selectByPrimaryKey(String infoId);

    int updateByPrimaryKeySelective(UserIncomeInfo record);

    int updateByPrimaryKey(UserIncomeInfo record);

    List<UserIncomeInfoDTO> selectUserIncomeInfo(String userId);
}