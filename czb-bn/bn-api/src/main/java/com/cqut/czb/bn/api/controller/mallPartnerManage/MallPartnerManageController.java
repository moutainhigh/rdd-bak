package com.cqut.czb.bn.api.controller.mallPartnerManage;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.mallPartnerManage.MallPartnerDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.mallPartnerManageService.MallPartnerManageService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mallPartnerManage")
public class MallPartnerManageController {
    @Autowired
    MallPartnerManageService mallPartnerManageService;

    /**
     * 统计总金额
     * @return
     */
    @GetMapping(value = "/statisticsMoney")
    public JSONResult statisticsMoney() {
        return mallPartnerManageService.statisticsMoney();
    }

    @GetMapping(value = "/getMallPartnerList")
    public JSONResult getMallPartnerList(MallPartnerDTO mallPartnerDTO, PageDTO pageDTO) {
        return new JSONResult(mallPartnerManageService.getMallPartnerList(mallPartnerDTO, pageDTO));
    }

    @GetMapping(value = "/getMallPartnerConsumptionDetails")
    public JSONResult getMallPartnerConsumptionDetails(User user) {
        return mallPartnerManageService.getMallPartnerConsumptionDetails(user);
    }
}
