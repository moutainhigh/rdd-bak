package com.cqut.czb.bn.service.rentCarService;

import com.cqut.czb.bn.entity.dto.rentCar.*;

import java.util.List;

public interface RentCarService {
    personIncome getPersonIncome();

    List<OneContractInfoDTO> getOneContractInfo(String contractId);

    List<PersonalContractDTO> getPersonalContractList();

    List<CompanyContractInfoDTO> getCompanyContractList();

    List<OneCompanyContractsPersonDTO> getOneCompanyContractInfo();

    int addCompanyContract(AddCompanyContractList addCompanyContractList);
}
