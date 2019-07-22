package com.cqut.czb.bn.service.serviceProviderManage;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.serviceProviderManage.ServiceProvider;
import com.cqut.czb.bn.entity.dto.serviceProviderManage.ServiceProviderInputDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.github.pagehelper.PageInfo;

public interface ServiceProviderManageService {
    // 服务商管理数据获取与查询
    JSONResult getServiceInfoData(ServiceProviderInputDTO inputDTO, PageDTO pageDTO);

}
