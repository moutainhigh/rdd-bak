package com.cqut.czb.bn.api.controller.withDrawLog;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.IncomeLog;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.WithDrawLog.WithDrawLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/withDrawLog")
public class WithDrawLogController {

    @Autowired
    WithDrawLogService service;

    @GetMapping("/getLogData")
    public JSONResult getLogData(IncomeLog incomeLog, PageDTO pageDTO) {
        return service.getLogData(incomeLog, pageDTO);
    }
}
