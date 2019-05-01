package com.cqut.czb.bn.service.rentCarService;

import com.cqut.czb.bn.entity.dto.rentCar.OneContractInfoDTO;
import com.cqut.czb.bn.entity.dto.rentCar.OneContractInfoInputDTO;
import com.cqut.czb.bn.entity.dto.rentCar.PersonalContractDTO;
import com.cqut.czb.bn.entity.dto.rentCar.personIncome;

import java.util.List;

public interface RentCarService {
    personIncome getPersonIncome();

    List<OneContractInfoDTO> getOneContractInfo(String contractId);

    List<PersonalContractDTO> getPersonalContractList();

}
