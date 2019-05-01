package com.cqut.czb.bn.api.controller.rentCarServer;

import com.cqut.czb.bn.entity.dto.rentCar.OneContractInfoInputDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.rentCarService.RentCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

}
