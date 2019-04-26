package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.DepositRecordsMapper;
import com.cqut.czb.bn.dao.mapper.PetrolMapper;
import com.cqut.czb.bn.dao.mapper.PetrolSalesRecordsMapper;
import com.cqut.czb.bn.dao.mapper.UserIncomeInfoMapper;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.service.AppBuyPetrolService;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppBuyPetrolServiceImpl implements AppBuyPetrolService {
    @Autowired
    PetrolMapper petrolMapper;

    @Autowired
    PetrolSalesRecordsMapper petrolSalesRecordsMapper;

    @Autowired
    DepositRecordsMapper depositRecordsMapper;

    @Autowired
    UserIncomeInfoMapper userIncomeInfoMapper;

    @Override
    public boolean updatePetrol(Petrol petrol) {

        return petrolMapper.updateByPrimaryKeySelective(petrol)>0;
    }

    @Override
    public boolean insertPetrolSalesRecords(PetrolSalesRecords petrolSalesRecords) {
        return petrolSalesRecordsMapper.insert(petrolSalesRecords)>0;
    }

    @Override
    public boolean insertDepositRecords(DepositRecords depositRecords) {
        depositRecords.setRecordId(StringUtil.createId());
        return depositRecordsMapper.insert(depositRecords)>0;
    }

    @Override
    public boolean insertUserIncomeInfo(UserIncomeInfo userIncomeInfo) {
        return userIncomeInfoMapper.insert(userIncomeInfo)>0;
    }


}
