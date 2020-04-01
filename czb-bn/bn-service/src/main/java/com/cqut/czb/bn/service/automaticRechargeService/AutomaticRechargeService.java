package com.cqut.czb.bn.service.automaticRechargeService;

import com.cqut.czb.bn.entity.dto.automaticRecharge.AutomaticRechargeDTO;
import com.cqut.czb.bn.entity.entity.autoRecharge.AutoRechargeRecord;
import com.cqut.czb.bn.entity.global.JSONResult;

import java.util.List;

public interface AutomaticRechargeService {
    public JSONResult getAutoList(AutomaticRechargeDTO automaticRecharge);

    public JSONResult deleteRecorder(String id);

    public JSONResult editRecorder(AutomaticRechargeDTO automaticRechargeDTO);

    public JSONResult showRecorder(String id);

    Boolean insertRecord(AutoRechargeRecord autoRechargeRecord);
}
