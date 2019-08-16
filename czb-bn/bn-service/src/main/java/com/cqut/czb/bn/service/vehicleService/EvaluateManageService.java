package com.cqut.czb.bn.service.vehicleService;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.RiderEvaluateDTO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EvaluateManageService {
    PageInfo<RiderEvaluateDTO> getEvaluateList(RiderEvaluateDTO riderEvaluateDTO, PageDTO pageDTO);
}
