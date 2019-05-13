package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.DictMapperExtra;
import com.cqut.czb.bn.entity.entity.Dict;
import com.cqut.czb.bn.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictServiceImpl implements IDictService {

    @Autowired
    DictMapperExtra dictMapperExtra;

    @Override
    public List<Dict> selectCustomerServiceStaff() {
        String name = "kf";
        return dictMapperExtra.selectDict(name);
    }
}
