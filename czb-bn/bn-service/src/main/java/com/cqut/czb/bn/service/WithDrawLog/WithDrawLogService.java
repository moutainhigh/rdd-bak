package com.cqut.czb.bn.service.WithDrawLog;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.IncomeLog;
import com.cqut.czb.bn.entity.global.JSONResult;

public interface WithDrawLogService {
    JSONResult getLogData(IncomeLog incomeLog, PageDTO pageDTO);
}
