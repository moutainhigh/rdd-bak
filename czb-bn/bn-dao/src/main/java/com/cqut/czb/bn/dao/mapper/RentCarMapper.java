package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.rentCar.personIncome;
import org.apache.ibatis.annotations.Param;

public interface RentCarMapper {
    personIncome getPersonIncome(@Param("userId") String userId);
}
