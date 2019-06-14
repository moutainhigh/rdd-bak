package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.platformIncomeRecords.PlatformIncomeRecordsDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface PlatformIncomeRecordsService {
    PageInfo<PlatformIncomeRecordsDTO> getReceiveRecords(PlatformIncomeRecordsDTO platformIncomeRecords, PageDTO pageDTO);

    Workbook exportRecords(PlatformIncomeRecordsDTO platformIncomeRecordsDTO) throws Exception;

    int importRecords(MultipartFile file) throws Exception;

    PageInfo<Petrol> selectPetrol(PlatformIncomeRecordsDTO platformIncomeRecordsDTO,PageDTO pageDTO);

    /**
     * 对多条记录进行操作
     */
    boolean handleManyPlatFormIncomeRecords(String contractRecordIds,String recordId);

    /**
     * 处理单条记录油卡或充值
     * @return
     */
    boolean handleOnePlatFormIncomeRecord(String contractRecordId);

    /**
     * 查出所有的子id
     */
    boolean selectSonContractId(PlatformIncomeRecordsDTO platformIncomeRecordsDTO);

    /**
     * 检测是否分配了油卡
     */
    PetrolSalesRecords isHaveDistributionPetrol(String contractRecordId);

    Boolean selectPayState(User user);


}
