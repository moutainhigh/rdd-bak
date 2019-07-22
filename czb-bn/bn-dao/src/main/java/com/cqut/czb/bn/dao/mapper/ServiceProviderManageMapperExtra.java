package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.serviceProviderManage.ServiceProvider;
import com.cqut.czb.bn.entity.dto.serviceProviderManage.ServiceProviderInputDTO;
import com.github.pagehelper.Page;

public interface ServiceProviderManageMapperExtra {
    Page<ServiceProvider> getServiceInfoData(ServiceProviderInputDTO inputDTO);
}
