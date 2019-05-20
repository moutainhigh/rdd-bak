package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.PayToPersonMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.payToPerson.PayToPersonDTO;
import com.cqut.czb.bn.entity.entity.PayToPerson;
import com.cqut.czb.bn.service.PayToPersonService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

public class PayToPersonServiceImpl implements PayToPersonService{

    @Autowired
    PayToPersonMapperExtra payToPersonMapperExtra;
    @Override
    public PageInfo<PayToPerson> getPayList(PayToPersonDTO payToPersonDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        return new PageInfo<>(payToPersonMapperExtra.selectByPrimaryKey(payToPersonDTO));
    }
}
