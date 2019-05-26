package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.DepositRecords;
import com.cqut.czb.bn.entity.entity.User;
import com.github.pagehelper.PageInfo;

/**
 * 创建人：曹渝
 * 创建时间：2019/5/11
 * 作用：收款记录service
 */
public interface IDepositRecordsService {

    PageInfo<DepositRecords> selectDepositRecords(User user, PageDTO pageDTO);
}
