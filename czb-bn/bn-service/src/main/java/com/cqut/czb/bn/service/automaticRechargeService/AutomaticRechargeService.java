package com.cqut.czb.bn.service.automaticRechargeService;

import com.cqut.czb.bn.entity.dto.automaticRecharge.AutomaticRechargeDTO;
import com.cqut.czb.bn.entity.global.JSONResult;

import java.util.List;

public interface AutomaticRechargeService {
    public JSONResult getAutoList(AutomaticRechargeDTO automaticRecharge);

    public JSONResult deleteRecorder(String id);
}
