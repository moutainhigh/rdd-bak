package com.cqut.czb.bn.service.impl.serviceProviderManage;

import com.cqut.czb.bn.dao.mapper.ServiceProviderManageMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.serviceProviderManage.ServiceProvider;
import com.cqut.czb.bn.entity.dto.serviceProviderManage.ServiceProviderInputDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.serviceProviderManage.ServiceProviderManageService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceProviderManageServiceImpl implements ServiceProviderManageService{

    @Autowired
    private ServiceProviderManageMapperExtra serviceProviderManageMapperExtra;

    @Override
    public JSONResult getServiceInfoData(ServiceProviderInputDTO inputDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());

        Page<ServiceProvider> serviceProviders = serviceProviderManageMapperExtra.getServiceInfoData(inputDTO);
        
        return new JSONResult(new PageInfo(serviceProviders));
    }
}
