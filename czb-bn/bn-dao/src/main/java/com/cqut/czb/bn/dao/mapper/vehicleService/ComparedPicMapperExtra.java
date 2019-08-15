package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.dto.vehicleService.ComparedPicDTO;
import com.cqut.czb.bn.entity.entity.vehicleService.ComparedPic;

import java.util.List;

public interface ComparedPicMapperExtra {
    List<ComparedPicDTO> selectByOrder(String serverOrderId);
}
