package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.dto.NotifyInsertInputDTO;
import com.cqut.czb.bn.entity.dto.petrolManagement.GetPetrolListInputDTO;
import com.cqut.czb.bn.entity.dto.petrolManagement.PetrolManagementInputDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.petrolManagement.IPetrolManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@RestController
@RequestMapping("/api/petrolManagement")
public class PetrolManagementController {
    @Autowired
    IPetrolManagementService petrolManagementService;
    @RequestMapping(value = "/getPetrolList",method = RequestMethod.GET)
    public JSONResult getPetrolList(GetPetrolListInputDTO inputDTO){
//        return null;
        return new JSONResult(petrolManagementService.getPetrolList(inputDTO));
    }

    @RequestMapping(value="/uploadPetrol",method=RequestMethod.POST)
    public JSONResult uploadPetrol(MultipartFile file){
        System.out.println(file.getOriginalFilename());
        int successResult = 0;
        try {
            successResult = petrolManagementService.uploadPetrolExcel(file.getInputStream(),file.getOriginalFilename());
        }catch (Exception e){
            e.printStackTrace();
        }

        return new JSONResult(successResult);
    }

    @RequestMapping(value = "salePetrol",method = RequestMethod.POST)
    public JSONResult salePetrol(@RequestBody PetrolManagementInputDTO inputDTO){

        return new JSONResult(petrolManagementService.salePetrol(inputDTO.getPetrolIds()));
    }
    @RequestMapping(value = "notSalePetrol",method = RequestMethod.POST)
    public JSONResult notSalePetrol(@RequestBody PetrolManagementInputDTO inputDTO){

        return new JSONResult(petrolManagementService.notSalePetrol(inputDTO.getPetrolIds()));
    }
}
