package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.entity.EnterpriseInfo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

@Service
public interface EnterpriseInfoService {
    PageInfo<EnterpriseInfo> getEnterpriseInfo(EnterpriseInfo enterpriseInfo);

    Boolean deleteEnterpriseInfo(String id);
}
