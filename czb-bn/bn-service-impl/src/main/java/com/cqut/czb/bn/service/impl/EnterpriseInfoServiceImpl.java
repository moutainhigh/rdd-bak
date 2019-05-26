package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.EnterpriseInfoMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.enterpriseInfo.EnterpriseInfoDTO;
import com.cqut.czb.bn.entity.dto.enterpriseInfo.IdentifyCodeDTO;
import com.cqut.czb.bn.entity.entity.ContractRecords;
import com.cqut.czb.bn.service.EnterpriseInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class EnterpriseInfoServiceImpl  implements EnterpriseInfoService {

    @Autowired
    EnterpriseInfoMapperExtra enterpriseInfoMapperExtra;

    @Override
    public PageInfo<EnterpriseInfoDTO> getEnterpriseInfo(EnterpriseInfoDTO enterpriseInfoDTO,PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        return new PageInfo<>( enterpriseInfoMapperExtra.selectByPrimaryKey(enterpriseInfoDTO));
    }

    @Override
    public Boolean deleteEnterpriseInfo(String id) {
        return (enterpriseInfoMapperExtra.deleteById(id)>0);
    }

    @Override
    public PageInfo<ContractRecords> getEnterpriseContract(EnterpriseInfoDTO enterpriseInfoDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        List<ContractRecords> contractRecords = enterpriseInfoMapperExtra.selectEnterpriseContract(enterpriseInfoDTO);
            return new PageInfo<>(contractRecords);
    }

    @Override
    public PageInfo<IdentifyCodeDTO> getIdentifyCode(EnterpriseInfoDTO enterpriseInfoDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        List<IdentifyCodeDTO> identifyCodeDTOS = enterpriseInfoMapperExtra.selectIdentifyCode(enterpriseInfoDTO);
            return new PageInfo<>(identifyCodeDTOS);
    }
}
