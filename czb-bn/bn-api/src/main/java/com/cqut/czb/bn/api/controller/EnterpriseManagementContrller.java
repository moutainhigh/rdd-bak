package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.dto.EnterpriseInfoDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.EnterpriseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**EnterpriseManagementContrller 企业管理
 *
 */
@RequestMapping("/enterprise")
@CrossOrigin
@RestController
public class EnterpriseManagementContrller {

    @Autowired
    EnterpriseInfoService enterpriseInfoService;

    @GetMapping("/getEnterprise")
    public JSONResult getEnterprise(EnterpriseInfoDTO enterpriseInfoDTO){
        return new JSONResult(enterpriseInfoService.getEnterpriseInfo(enterpriseInfoDTO));
    }
    @GetMapping("/deleteEnterprise")
    public Boolean deleteEnterprise(String id){
        return enterpriseInfoService.deleteEnterpriseInfo(id);
    }

}
