package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.dto.expressManage.ExpressDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.ExpressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**ExpressManagementController 快递管理
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/api/express")
public class ExpressManagementController {

    @Autowired ExpressService expressService;
    /**
     * 查询快递
     */
    @GetMapping("/getExpress")
    public JSONResult getExpress(ExpressDTO expressDTO){
        return new JSONResult(expressService.getExpress(expressDTO));
    }
    /**
     * 增加快递
     */
    @GetMapping("/addExpress")
    public JSONResult addExpress(ExpressDTO expressDTO){
        return new JSONResult(expressService.addExpress(expressDTO));
    }
}
