package com.cqut.czb.bn.service.petrolManagement;

import com.cqut.czb.bn.entity.dto.petrolManagement.GetPetrolListInputDTO;
import com.cqut.czb.bn.entity.dto.petrolManagement.ModifyPetrolInputDTO;
import com.cqut.czb.bn.entity.dto.petrolManagement.PetrolManagementInputDTO;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeInputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.GetPetrolSaleInfoInputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.SaleInfoOutputDTO;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.github.pagehelper.PageInfo;

import java.io.InputStream;

public interface IPetrolManagementService {
    PageInfo<Petrol> getPetrolList(GetPetrolListInputDTO inputDTO);
    int uploadPetrolExcel(InputStream inputStream,String originalFileName);
    PageInfo<SaleInfoOutputDTO> getPetrolSaleInfoList(GetPetrolSaleInfoInputDTO infoInputDTO);
    int salePetrol(String petrolIds);

    int saleSomePetrol(PetrolManagementInputDTO inputDTO);

    int notSalePetrol(String petrolIds);

    int notSaleSomePetrol(PetrolManagementInputDTO inputDTO);

    int BanPetrol(String petrolIds);

    boolean modifyPetrol(ModifyPetrolInputDTO inputDTO);

    String getPetrolMoneyCount(GetPetrolListInputDTO inputDTO);

    String getPetrolSaleMoneyCount(GetPetrolSaleInfoInputDTO infoInputDTO);

    JSONResult changePetrolNum(PetrolRechargeInputDTO inputDTO);

}
