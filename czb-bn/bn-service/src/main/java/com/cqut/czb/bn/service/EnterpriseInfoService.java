package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.enterpriseInfo.EnterpriseInfoDTO;
import com.cqut.czb.bn.entity.dto.enterpriseInfo.IdentifyCodeDTO;
import com.cqut.czb.bn.entity.entity.ContractRecords;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

@Service
public interface EnterpriseInfoService {
    PageInfo<EnterpriseInfoDTO> getEnterpriseInfo(EnterpriseInfoDTO enterpriseInfoDTO,PageDTO pageDTO);

    Boolean deleteEnterpriseInfo(String id);

    PageInfo<ContractRecords> getEnterpriseContract(EnterpriseInfoDTO enterpriseInfoDTO, PageDTO pageDTO);

    PageInfo<IdentifyCodeDTO> getIdentifyCode(EnterpriseInfoDTO enterpriseInfoDTO,PageDTO pageDTO);
}
