package com.cqut.czb.bn.service.rentCarService;

import com.cqut.czb.bn.entity.dto.rentCar.*;
import net.sf.json.JSONObject;

import java.util.List;

public interface RentCarService {
    PersonIncome getPersonIncome(String userId);

    List<OneContractInfoDTO> getOneContractInfo(String userId, String contractId);

    List<PersonalContractDTO> getPersonalContractList(String userId);

    List<CompanyContractInfoDTO> getCompanyContractList(String userId);

    List<OneCompanyContractsPersonDTO> getOneCompanyContractInfo(String userId);

    int addCompanyContract(String userId, AddCompanyContractList addCompanyContractList);

    int addPersonContract(String userId, String personId, String identifyCode);

    JSONObject getPersonalIncome(String userId);
}
