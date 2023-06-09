package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.AutoRechargeLoginResult.AutoRechargeRecordDTO;
import com.cqut.czb.bn.entity.dto.automaticRecharge.AutomaticRechargeDTO;
import com.cqut.czb.bn.entity.dto.automaticRecharge.SumAutoRecharge;
import com.cqut.czb.bn.entity.entity.autoRecharge.AutoRechargeRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AutomaticRechargeMapperExtra {
    List<AutomaticRechargeDTO> getAutoList(AutomaticRechargeDTO automaticRecharge);

    int deleteRecorder(@Param("id")String id);

    int editRecorder(AutomaticRechargeDTO automaticRechargeDTO);

    AutomaticRechargeDTO showRecorder(@Param("id")String id);

    SumAutoRecharge getSumData(AutomaticRechargeDTO pageDTO);

    SumAutoRecharge getSuccessPeople(AutomaticRechargeDTO pageDTO);

    int insertSelective(AutoRechargeRecordDTO record);
}
