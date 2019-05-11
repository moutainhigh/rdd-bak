package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.DepositRecords;
import com.cqut.czb.bn.entity.entity.User;

import java.util.List;

public interface DepositRecordsMapperExtra {

    List<DepositRecords> selectDepositRecords(User user);
}