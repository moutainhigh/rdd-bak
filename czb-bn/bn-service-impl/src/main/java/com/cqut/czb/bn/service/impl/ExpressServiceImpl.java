package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.ExpressMapperExtra;
import com.cqut.czb.bn.entity.dto.ExpressDTO;
import com.cqut.czb.bn.service.ExpressService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpressServiceImpl implements ExpressService{
    @Autowired
    private ExpressMapperExtra expressMapperExtra;
    @Override
    public PageInfo<ExpressDTO> getExpress(ExpressDTO expressDTO) {
        PageHelper.startPage(expressDTO.getPageNum(),expressDTO.getPageSize());
        return new PageInfo<>( expressMapperExtra.selectExpress(expressDTO.getExpressNumber(),expressDTO.getExpressCompany(),expressDTO.getConsignee()));
    }

    @Override
    public Boolean addExpress(ExpressDTO expressDTO) {
        expressDTO.setExpressId(StringUtil.createId());
        return expressMapperExtra.addExpress(expressDTO);
    }
}
