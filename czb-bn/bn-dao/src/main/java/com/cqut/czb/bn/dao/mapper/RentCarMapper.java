package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.rentCar.OneContractInfoDTO;
import com.cqut.czb.bn.entity.dto.rentCar.PersonalContractDTO;
import com.cqut.czb.bn.entity.dto.rentCar.personIncome;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public interface RentCarMapper {
    personIncome getPersonIncome(@Param("userId") String userId);

    List<OneContractInfoDTO> getOneContractInfo(@Param("userId")String userId, @Param("contractId") String contractId);

    List<PersonalContractDTO> getPersonalContractList(@Param("userId") String userId);
}
