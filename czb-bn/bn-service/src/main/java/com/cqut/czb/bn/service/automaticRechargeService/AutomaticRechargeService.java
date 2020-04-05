package com.cqut.czb.bn.service.automaticRechargeService;

import com.cqut.czb.bn.entity.dto.AutoRechargeLoginResult.AutoRechargeRecordDTO;
import com.cqut.czb.bn.entity.dto.automaticRecharge.AutomaticRechargeDTO;
import com.cqut.czb.bn.entity.entity.autoRecharge.AutoRechargeRecord;
import com.cqut.czb.bn.entity.global.JSONResult;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public interface AutomaticRechargeService {
    JSONResult getAutoList(AutomaticRechargeDTO automaticRecharge);

    JSONResult deleteRecorder(String id);

    JSONResult editRecorder(AutomaticRechargeDTO automaticRechargeDTO);

    Boolean insertRecord(AutoRechargeRecordDTO autoRechargeRecord);

    JSONResult showRecorder(String id);

    Workbook exportOrderRecords(AutomaticRechargeDTO pageDTO) throws Exception;
}
