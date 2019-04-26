package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.EnterpriseInfoDTO;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

@Service
public interface EnterpriseInfoService {
    PageInfo<EnterpriseInfoDTO> getEnterpriseInfo(EnterpriseInfoDTO enterpriseInfoDTO);

    Boolean deleteEnterpriseInfo(String id);
}
