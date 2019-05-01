package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.IContractService;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

/**
 * ContractManagementController 合同配置接口
 * 设计者:   曹渝
 * 更新日期: 2019/04/29
 */
@Controller
@RequestMapping("/api/ContractManagement")
public class ContractManagementController {

    @Autowired
    IContractService contractService;

    @Autowired
    RedisUtils redisUtils;

    @RequestMapping(value = "/uploadContractTemplate",method = RequestMethod.POST)
    public JSONResult uploadContractTemplate(String templateName, @RequestParam("file") MultipartFile file, Principal principal) throws IOException {
        User user = (User)redisUtils.get(principal.getName());
        boolean isUpload = contractService.uploadContractTemplate(templateName, file, user);
        if(isUpload) {
            return new JSONResult(ResponseCodeConstants.SUCCESS, "新增成功");
        } else {
            return new JSONResult(ResponseCodeConstants.FAILURE, "新增失败");
        }
    }

    @RequestMapping(value = "/selectContractModelList",method = RequestMethod.GET)
    public JSONResult selectContractModelList(PageDTO pageDTO){

        return new JSONResult(contractService.selectContractModelList(pageDTO));
    }
}
