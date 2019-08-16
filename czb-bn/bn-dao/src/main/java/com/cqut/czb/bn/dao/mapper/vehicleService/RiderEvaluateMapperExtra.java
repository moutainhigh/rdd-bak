package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.dto.vehicleService.RiderEvaluateDTO;

import java.util.List;

public interface RiderEvaluateMapperExtra {
    List<RiderEvaluateDTO> selectRiderEvaluateList(RiderEvaluateDTO riderEvaluateDTO);
}
