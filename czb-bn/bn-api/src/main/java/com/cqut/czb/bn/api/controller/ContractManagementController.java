package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.interceptor.PermissionCheck;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.contractManagement.ContractInputDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.IContractService;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * ContractManagementController 合同管理接口
 * 设计者:   曹渝
 * 更新日期: 2019/04/29
 */
@RestController
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

    /**
     *  查看合同模板
     * */
    @RequestMapping(value = "/viewContractTemplate",method = RequestMethod.GET)
    public JSONResult viewContractTemplate(String templateId, HttpServletResponse response) throws IOException {
        boolean isSuccess = contractService.viewContractTemplate(templateId, response);
        if(isSuccess) {
            return new JSONResult(ResponseCodeConstants.SUCCESS, "文件流创建成功");
        } else {
            return new JSONResult(ResponseCodeConstants.FAILURE, "文件流创建失败");
        }
    }

    @RequestMapping(value = "/selectContractModelList",method = RequestMethod.GET)
    public JSONResult selectContractModelList(PageDTO pageDTO){

        return new JSONResult(contractService.selectContractModelList(pageDTO));
    }

    /**
     * 获取企业合同列表
     * */
    @RequestMapping(value = "/selectEnterpriseContractList",method = RequestMethod.GET)
    public JSONResult selectEnterpriseContractList(ContractInputDTO contractInputDTO, PageDTO pageDTO){

        return new JSONResult(contractService.selectEnterpriseContractList(contractInputDTO, pageDTO));
    }

    /**
     * 获取个人合同列表
     * */
    @RequestMapping(value = "/selectPersonalContractList",method = RequestMethod.GET)
    public JSONResult selectPersonalContractList(ContractInputDTO contractInputDTO, PageDTO pageDTO){

        return new JSONResult(contractService.selectPersonalContractList(contractInputDTO, pageDTO));
    }

    /**
     * 根据个人合同查看企业合同
     * */
    @RequestMapping(value = "/selectEnterpriseContract",method = RequestMethod.GET)
    public JSONResult selectEnterpriseContract(String contractId, PageDTO pageDTO){

        return new JSONResult(contractService.selectEnterpriseContract(contractId, pageDTO));
    }

    /**
     * 根据企业合同查看个人合同
     * */
    @RequestMapping(value = "/selectPersonalContract",method = RequestMethod.GET)
    public JSONResult selectPersonalContract(String contractId, PageDTO pageDTO){

        return new JSONResult(contractService.selectPersonalContract(contractId, pageDTO));
    }

    /**
     * 查看个人合同详情
     * */
    @RequestMapping(value = "/selectPersonalContractDetail",method = RequestMethod.GET)
    public JSONResult selectPersonalContractDetail(String contractId){

        return new JSONResult(contractService.selectPersonalContractDetail(contractId));
    }

    /**
     *  修改合同状态
     * */
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/changeContractState",method = RequestMethod.POST)
    public JSONResult changeContractState(ContractInputDTO contractInputDTO){

        return new JSONResult(contractService.changeContractState(contractInputDTO));
    }

    /**
     *  下载合同
     * */
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/downloadContract",method = RequestMethod.POST)
    public JSONResult downloadContract(String contractId, HttpServletResponse response){

        boolean isSuccess = contractService.downloadContract(contractId, response);
        if(isSuccess) {
            return new JSONResult(ResponseCodeConstants.SUCCESS, "创建文件流成功");
        } else {
            return new JSONResult(ResponseCodeConstants.FAILURE, "创建文件流失败");
        }
    }
}
