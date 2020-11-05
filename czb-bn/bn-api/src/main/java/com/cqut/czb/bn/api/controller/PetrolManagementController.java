package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.interceptor.PermissionCheck;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.DataWithCountOutputDTO;
import com.cqut.czb.bn.entity.dto.petrolManagement.GetPetrolListInputDTO;
import com.cqut.czb.bn.entity.dto.petrolManagement.ModifyPetrolInputDTO;
import com.cqut.czb.bn.entity.dto.petrolManagement.PetrolInputDTO;
import com.cqut.czb.bn.entity.dto.petrolManagement.PetrolManagementInputDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.petrolManagement.IPetrolManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/api/petrolManagement")
public class PetrolManagementController {
    @Autowired
    IPetrolManagementService petrolManagementService;

    @Autowired
    private RedisUtils redisUtils;

    @Value("${register.common.petrolType}")
    private Integer commonPetrolType;

    @Value("${register.special.petrolType}")
    private Integer specialPetrolType;

    @RequestMapping(value = "/getPetrolList",method = RequestMethod.GET)
    public JSONResult getPetrolList(GetPetrolListInputDTO inputDTO, Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        if (user.getIsSpecial() == 0){
            inputDTO.setIsSpecialPetrol(commonPetrolType);
        }
        else if (user.getIsSpecial() == 1){
            inputDTO.setIsSpecialPetrol(specialPetrolType);
        }
        DataWithCountOutputDTO dataWithCountOutputDTO = new DataWithCountOutputDTO();
        dataWithCountOutputDTO.setData(petrolManagementService.getPetrolList(inputDTO));
        dataWithCountOutputDTO.setCount(petrolManagementService.getPetrolMoneyCount(inputDTO));
        return new JSONResult(dataWithCountOutputDTO);
    }

    @PermissionCheck(role = "管理员")
    @RequestMapping(value="/uploadPetrol",method=RequestMethod.POST)
    public JSONResult uploadPetrol(MultipartFile file, Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        System.out.println(file.getOriginalFilename());
        int successResult = 0;
        try {
            if (user.getIsSpecial() == 0){
                successResult = petrolManagementService.uploadPetrolExcel(file.getInputStream(),file.getOriginalFilename(),commonPetrolType);
            }
            else if (user.getIsSpecial() == 1){
                successResult = petrolManagementService.uploadPetrolExcel(file.getInputStream(),file.getOriginalFilename(),specialPetrolType);
            }
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
    @RequestMapping(value="/editPetrol",method=RequestMethod.POST)
    public JSONResult editPetrol(@RequestBody GetPetrolListInputDTO inputDTO){
        return new JSONResult(petrolManagementService.editPetrol(inputDTO));
    }

    @PermissionCheck(role = "管理员")
    @RequestMapping(value="/insertPetrol",method=RequestMethod.POST)
    public JSONResult insertPetrol(@RequestBody PetrolInputDTO inputDTO){
        return new JSONResult(petrolManagementService.addPetrol(inputDTO));
    }
    @PermissionCheck(role = "管理员")
    @RequestMapping(value="/deletePetrol",method=RequestMethod.POST)
    public JSONResult deletePetrol(@RequestBody PetrolInputDTO inputDTO, Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        if (user.getIsSpecial() == 0){
            inputDTO.setIsSpecialPetrol(commonPetrolType);
        }
        else if (user.getIsSpecial() == 1){
            inputDTO.setIsSpecialPetrol(specialPetrolType);
        }
        return new JSONResult(petrolManagementService.deletePetrol(inputDTO));
    }
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "salePetrol",method = RequestMethod.POST)
    public JSONResult salePetrol(@RequestBody PetrolManagementInputDTO inputDTO, Principal principal){

        User user = (User)redisUtils.get(principal.getName());
        if (user.getIsSpecial() == 0){
            inputDTO.setIsSpecialPetrol(commonPetrolType);
        }
        else if (user.getIsSpecial() == 1){
            inputDTO.setIsSpecialPetrol(specialPetrolType);
        }

        return new JSONResult(petrolManagementService.saleSomePetrol(inputDTO));
    }
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "notSalePetrol",method = RequestMethod.POST)
    public JSONResult notSalePetrol(@RequestBody PetrolManagementInputDTO inputDTO, Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        if (user.getIsSpecial() == 0){
            inputDTO.setIsSpecialPetrol(commonPetrolType);
        }
        else if (user.getIsSpecial() == 1){
            inputDTO.setIsSpecialPetrol(specialPetrolType);
        }

        return new JSONResult(petrolManagementService.notSaleSomePetrol(inputDTO));
    }

    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "BanPetrol",method = RequestMethod.POST)
    public JSONResult BanPetrol(@RequestBody PetrolManagementInputDTO inputDTO, Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        if (user.getIsSpecial() == 0){
            return new JSONResult(petrolManagementService.BanPetrol(inputDTO.getPetrolIds(),commonPetrolType));
        }
        else if (user.getIsSpecial() == 1){
            return new JSONResult(petrolManagementService.BanPetrol(inputDTO.getPetrolIds(),specialPetrolType));
        }
        return new JSONResult("删除失败");
    }

    @RequestMapping(value = "/modifyPetrol",method = RequestMethod.POST)
    public JSONResult modifyPetrol(@RequestBody ModifyPetrolInputDTO inputDTO){
        return new JSONResult(petrolManagementService.modifyPetrol(inputDTO));
    }

//    @RequestMapping(value = "/payManage",method = RequestMethod.GET)
//    public JSONResult payManage(){
//        return new JSONResult(petrolManagementService.getPayInstruction());
//    }
    @RequestMapping(value = "/getWarning",method = RequestMethod.GET)
    public JSONResult getWarning(String name){
        return new JSONResult(petrolManagementService.getWarning(name));
    }


}
