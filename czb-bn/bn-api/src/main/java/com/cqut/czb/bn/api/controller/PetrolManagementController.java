package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.interceptor.PermissionCheck;
import com.cqut.czb.bn.entity.dto.DataWithCountOutputDTO;
import com.cqut.czb.bn.entity.dto.petrolManagement.GetPetrolListInputDTO;
import com.cqut.czb.bn.entity.dto.petrolManagement.ModifyPetrolInputDTO;
import com.cqut.czb.bn.entity.dto.petrolManagement.PetrolManagementInputDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.petrolManagement.IPetrolManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/petrolManagement")
public class PetrolManagementController {
    @Autowired
    IPetrolManagementService petrolManagementService;
    @RequestMapping(value = "/getPetrolList",method = RequestMethod.GET)
    public JSONResult getPetrolList(GetPetrolListInputDTO inputDTO){
//        return null;
        DataWithCountOutputDTO dataWithCountOutputDTO = new DataWithCountOutputDTO();
        dataWithCountOutputDTO.setData(petrolManagementService.getPetrolList(inputDTO));
        dataWithCountOutputDTO.setCount(petrolManagementService.getPetrolMoneyCount(inputDTO));
        return new JSONResult(dataWithCountOutputDTO);
    }

    @PermissionCheck(role = "管理员")
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

    @PermissionCheck(role = "管理员")
    @RequestMapping(value="/getPetrolPrices",method=RequestMethod.GET)
    public JSONResult getPetrolPrices(){
        return new JSONResult(petrolManagementService.getPetrolPrice());
    }

    @PermissionCheck(role = "管理员")
    @RequestMapping(value="/updatePetrolPrices",method=RequestMethod.POST)
    public JSONResult updatePetrolPrices(String petrolPrices){
        return new JSONResult(petrolManagementService.updatePetrolPrices(petrolPrices));
    }

    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "salePetrol",method = RequestMethod.POST)
    public JSONResult salePetrol(@RequestBody PetrolManagementInputDTO inputDTO){

        return new JSONResult(petrolManagementService.saleSomePetrol(inputDTO));
    }
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "notSalePetrol",method = RequestMethod.POST)
    public JSONResult notSalePetrol(@RequestBody PetrolManagementInputDTO inputDTO){

        return new JSONResult(petrolManagementService.notSaleSomePetrol(inputDTO));
    }

    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "BanPetrol",method = RequestMethod.POST)
    public JSONResult BanPetrol(@RequestBody PetrolManagementInputDTO inputDTO){

        return new JSONResult(petrolManagementService.BanPetrol(inputDTO.getPetrolIds()));
    }

    @RequestMapping(value = "/modifyPetrol",method = RequestMethod.POST)
    public JSONResult modifyPetrol(@RequestBody ModifyPetrolInputDTO inputDTO){
        return new JSONResult(petrolManagementService.modifyPetrol(inputDTO));
    }

//    @RequestMapping(value = "/payManage",method = RequestMethod.GET)
//    public JSONResult payManage(){
//        return new JSONResult(petrolManagementService.getPayInstruction());
//    }



}
