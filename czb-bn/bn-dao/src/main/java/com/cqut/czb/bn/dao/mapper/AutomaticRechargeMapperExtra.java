package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.automaticRecharge.AutomaticRechargeDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AutomaticRechargeMapperExtra {
    List<AutomaticRechargeDTO> getAutoList(AutomaticRechargeDTO automaticRecharge);

    int deleteRecorder(@Param("id")String id);

    int editRecorder(AutomaticRechargeDTO automaticRechargeDTO);

    AutomaticRechargeDTO showRecorder(@Param("id")String id);
}
