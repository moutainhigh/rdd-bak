package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.EnterpriseInfoMapper;
import com.cqut.czb.bn.entity.dto.EnterpriseInfoDTO;
import com.cqut.czb.bn.service.EnterpriseInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseInfoServiceImpl  implements EnterpriseInfoService {

    @Autowired
    EnterpriseInfoMapper enterpriseInfoMapper;

    @Override
    public PageInfo<EnterpriseInfoDTO> getEnterpriseInfo(EnterpriseInfoDTO enterpriseInfoDTO) {
        return new PageInfo<>( enterpriseInfoMapper.selectByPrimaryKey(enterpriseInfoDTO));
    }

    @Override
    public Boolean deleteEnterpriseInfo(String id) {
        return enterpriseInfoMapper.deleteByPrimaryKey(id);
    }
}
