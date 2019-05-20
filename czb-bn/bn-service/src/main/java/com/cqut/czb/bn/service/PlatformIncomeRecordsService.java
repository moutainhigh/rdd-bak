package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.platformIncomeRecords.PlatformIncomeRecordsDTO;
import com.cqut.czb.bn.entity.entity.PlatformIncomeRecords;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

@Service
public interface PlatformIncomeRecordsService {
    PageInfo<PlatformIncomeRecordsDTO> getReceiveRecords(PlatformIncomeRecordsDTO platformIncomeRecords, PageDTO pageDTO);

    Workbook exportRecords(PlatformIncomeRecordsDTO platformIncomeRecordsDTO) throws Exception;
}
