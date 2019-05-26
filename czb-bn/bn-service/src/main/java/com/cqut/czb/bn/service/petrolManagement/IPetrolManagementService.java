package com.cqut.czb.bn.service.petrolManagement;

import com.cqut.czb.bn.entity.dto.petrolManagement.GetPetrolListInputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.GetPetrolSaleInfoInputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.SaleInfoOutputDTO;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.github.pagehelper.PageInfo;

import java.io.InputStream;
import java.util.List;

public interface IPetrolManagementService {
    PageInfo<Petrol> getPetrolList(GetPetrolListInputDTO inputDTO);
    int uploadPetrolExcel(InputStream inputStream,String originalFileName);
    PageInfo<SaleInfoOutputDTO> getPetrolSaleInfoList(GetPetrolSaleInfoInputDTO infoInputDTO);

    int salePetrol(String petrolIds);

    int notSalePetrol(String petrolIds);
}
