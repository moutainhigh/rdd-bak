package com.cqut.czb.bn.api.controller.rentCarServer;

import com.cqut.czb.bn.entity.dto.rentCar.AddCompanyContractList;
import com.cqut.czb.bn.entity.dto.rentCar.OneContractInfoInputDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.rentCarService.RentCarService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/renCar")
@RestController
public class RentCarController {
    @Autowired
    private RentCarService rentCarService;

    /**
     * 获取个人收益记录
     * @return JSONResult
     */
    @RequestMapping(value = "/getPersonIncome", method = RequestMethod.GET)
    public JSONResult getPersonIncome(){
        return new JSONResult(rentCarService.getPersonIncome());
    }

    /**
     * 获取合同概要信息
     * @param oneContractInfoInputDTO
     * @return JSONResult
     */
    @RequestMapping(value = "/getOneContractInfo", method = RequestMethod.POST)
    public JSONResult getOneContractInfo(@Validated OneContractInfoInputDTO oneContractInfoInputDTO){
        return new JSONResult(rentCarService.getOneContractInfo(oneContractInfoInputDTO.getContractId()));
    }

    /**
     * 个人租车服务，合同列表获取
     */
    @RequestMapping(value = "/getPersonalContractList", method = RequestMethod.GET)
    public JSONResult getPersonalContractList(){
        return new JSONResult(rentCarService.getPersonalContractList());
    }

    /**
     * 企业合同服务，合同列表获取
     */
    @RequestMapping(value = "/getCompanyContractList", method = RequestMethod.GET)
    public JSONResult getCompanyContractList(){
        return new JSONResult(rentCarService.getCompanyContractList());
    }

    /**
     * 企业合同概要信息列表获取
     */
    @RequestMapping(value = "/getCompanyPersonList", method = RequestMethod.GET)
    public JSONResult getCompanyPersonList(){
        return new JSONResult(rentCarService.getOneCompanyContractInfo());
    }

    /**
     * 企业合同新增
     */
    @RequestMapping(value = "/addCompanyContract", method = RequestMethod.POST)
    public JSONResult addCompanyContract(@RequestBody AddCompanyContractList addCompanyContractList){
        int success = rentCarService.addCompanyContract(addCompanyContractList);

        return new JSONResult(success);
    }
}
