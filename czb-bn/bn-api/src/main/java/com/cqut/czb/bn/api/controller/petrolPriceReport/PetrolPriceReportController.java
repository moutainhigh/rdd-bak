package com.cqut.czb.bn.api.controller.petrolPriceReport;

import com.cqut.czb.auth.interceptor.PermissionCheck;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.PetrolPriceReportInputDTO;
import com.cqut.czb.bn.entity.dto.WeChatCommodity.PayInputDTO;
import com.cqut.czb.bn.entity.entity.PetrolPriceReport;
import com.cqut.czb.bn.entity.entity.User;
import org.apache.ibatis.annotations.Param;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.petrolPriceReport.PetrolPriceReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/PetrolPriceReport")
public class PetrolPriceReportController {

    @Autowired
    PetrolPriceReportService petrolPriceReportService;

    @RequestMapping(value = "/GetList", method = RequestMethod.GET)
    public JSONResult getList(PetrolPriceReportInputDTO petrolPriceReportInputDTO) {
        return new JSONResult(petrolPriceReportService.selectReportList(petrolPriceReportInputDTO));
    }

    @RequestMapping(value = "/DeleteById", method = RequestMethod.GET)
    public JSONResult deleteList(@Param("reportId") String reportId) {
        return new JSONResult(petrolPriceReportService.deletePetrolReport(reportId));
    }

    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/EditReport", method = RequestMethod.POST)
    public JSONResult editReport(@RequestBody PetrolPriceReport record) {
        return new JSONResult(petrolPriceReportService.editPetrolReport(record));
    }

    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/AddReport", method = RequestMethod.POST)
    public JSONResult addReport(@RequestBody PetrolPriceReport record) {
        return new JSONResult(petrolPriceReportService.addPetrolReport(record));
    }
}


