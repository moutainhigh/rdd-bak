package com.cqut.czb.bn.service.impl.WithDrawLogServiceImpl;

import com.cqut.czb.bn.dao.mapper.IncomeLogMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.IncomeLog;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.WithDrawLog.WithDrawLogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WithDrawLogServiceImpl implements WithDrawLogService {
    @Autowired
    IncomeLogMapperExtra mapperExtra;

    @Override
    public JSONResult getLogData(IncomeLog incomeLog, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        Page<IncomeLog> incomeLogs =  mapperExtra.getLogData(incomeLog);

        return new JSONResult("数据获取成功", 200, new PageInfo(incomeLogs));
    }
}
