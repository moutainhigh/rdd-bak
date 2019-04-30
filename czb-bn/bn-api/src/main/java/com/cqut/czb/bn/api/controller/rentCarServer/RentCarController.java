package com.cqut.czb.bn.api.controller.rentCarServer;

import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.rentCarService.RentCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/renCar")
@CrossOrigin
public class RentCarController {
    @Autowired
    private RentCarService rentCarService;

    @RequestMapping(value = "/getPersonIncome", method = RequestMethod.GET)
    public JSONResult getPersonIncome(){
        return new JSONResult(rentCarService.getPersonIncome());
    }

}
