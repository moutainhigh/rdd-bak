package com.cqut.czb.bn.service.petrolPriceReport;

import com.cqut.czb.bn.entity.dto.PetrolPriceReportInputDTO;
import com.cqut.czb.bn.entity.dto.appHomePage.petrolPriceReportDTO;
import com.cqut.czb.bn.entity.entity.PetrolPriceReport;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface PetrolPriceReportService {

    PageInfo<PetrolPriceReport> selectReportList(PetrolPriceReportInputDTO petrolPriceReportInputDTO);

    boolean deletePetrolReport(String reportId);

    boolean editPetrolReport(PetrolPriceReport record);

    boolean addPetrolReport(PetrolPriceReport record);

}
