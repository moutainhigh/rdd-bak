package com.cqut.czb.bn.api.controller.partnerAndOperateCenter;

import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.GeneralPartnerUserPageDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.partnerAndOperateCenter.OrdinaryManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @ClassName: OrdinaryManagementController
 * @Author: Iriya720
 * @Date: 2019/12/2
 * @Description: 普通用户管理接口
 * @version: v1.0
 */

@RestController
@RequestMapping("/api/ordinary")
public class OrdinaryManagementController {

    @Autowired
    private OrdinaryManagement ordinaryManagement;

    @GetMapping("/getTableData")
    public JSONResult getTableData(GeneralPartnerUserPageDTO pageDTO){
        return ordinaryManagement.getTableData(pageDTO);
    }
}
