package com.cqut.czb.bn.dao.mapper.integral;

import com.cqut.czb.bn.entity.dto.integral.IntegralManageDTO;

import java.util.List;

public interface IntegralManageMapper {
    int deleteByPrimaryKey(String id);

    int insert(IntegralManageDTO integralManageDTO);

    int insertSelective(IntegralManageDTO integralManageDTO);

    IntegralManageDTO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(IntegralManageDTO integralManageDTO);

    int updateByPrimaryKey(IntegralManageDTO integralManageDTO);

    List<IntegralManageDTO> getIntegralValueList();
}
