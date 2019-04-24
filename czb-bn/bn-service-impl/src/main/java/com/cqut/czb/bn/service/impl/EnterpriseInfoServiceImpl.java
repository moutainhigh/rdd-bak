package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.EnterpriseInfoMapper;
import com.cqut.czb.bn.entity.dto.ExpressDTO;
import com.cqut.czb.bn.entity.entity.EnterpriseInfo;
import com.cqut.czb.bn.service.EnterpriseInfoService;
import com.cqut.czb.bn.service.ExpressService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseInfoServiceImpl  implements EnterpriseInfoService {

    @Autowired
    EnterpriseInfoMapper enterpriseInfoMapper;

    @Override
    public PageInfo<EnterpriseInfo> getEnterpriseInfo(EnterpriseInfo enterpriseInfo) {
        return new PageInfo<>( enterpriseInfoMapper.selectByPrimaryKey(enterpriseInfo));
    }

    @Override
    public Boolean deleteEnterpriseInfo(String id) {
        return enterpriseInfoMapper.deleteByPrimaryKey(id);
    }
}
