package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.appPersonalCenter.UserIncomeInfoDTO;
import com.cqut.czb.bn.entity.entity.UserIncomeInfo;

public interface UserIncomeInfoMapperExtra {
    int deleteByPrimaryKey(String infoId);

    int insert(UserIncomeInfo record);

    int insertSelective(UserIncomeInfo record);

    UserIncomeInfo selectByPrimaryKey(String infoId);

    int updateByPrimaryKeySelective(UserIncomeInfo record);

    int updateByPrimaryKey(UserIncomeInfo record);

    UserIncomeInfoDTO selectUserIncomeInfo(String userId);

    UserIncomeInfo selectOneUserIncomeInfo(String userId);

    int updateOtherIncome(UserIncomeInfoDTO userIncomeInfoDTO);

    int updateFanYongIncome(UserIncomeInfoDTO userIncomeInfoDTO);


}