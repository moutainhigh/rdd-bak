package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.DepositRecordsMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.DepositRecords;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.service.IDepositRecordsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepositRecordsServiceImpl implements IDepositRecordsService {

    @Autowired
    DepositRecordsMapperExtra depositRecordsMapperExtra;

    @Override
    public PageInfo<DepositRecords> selectDepositRecords(User user, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        System.out.println("123");
        List<DepositRecords> depositRecordsList = depositRecordsMapperExtra.selectDepositRecords(user);
        return new PageInfo<>(depositRecordsList);
    }
}
