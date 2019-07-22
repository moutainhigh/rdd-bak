package com.cqut.czb.bn.api.controller.serviceProviderManage;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.serviceProviderManage.ServiceProviderInputDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.serviceProviderManage.ServiceProviderManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ServiceProviderManage")
public class ServiceProviderManageController {
    @Autowired
    private ServiceProviderManageService serviceProviderManageService;

    /**
     * 服务商管理获取数据与查询
     * @param inputDTO
     * @param pageDTO
     * @return
     */
    @GetMapping("/getServiceInfoData")
    public JSONResult getServiceInfoData(ServiceProviderInputDTO inputDTO, PageDTO pageDTO) {
        return serviceProviderManageService.getServiceInfoData(inputDTO, pageDTO);
    }
}
