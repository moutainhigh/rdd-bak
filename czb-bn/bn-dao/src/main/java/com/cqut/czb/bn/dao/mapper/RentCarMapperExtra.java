package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.rentCar.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public interface RentCarMapperExtra {
    personIncome getPersonIncome(@Param("userId") String userId);

    List<OneContractInfoDTO> getOneContractInfo(@Param("userId")String userId, @Param("contractId") String contractId);

    List<PersonalContractDTO> getPersonalContractList(@Param("userId") String userId);

    List<CompanyContractInfoDTO> getCompanyContractList(@Param("userId") String userId);

    List<ContractAndSignedNumDTO> getSignedNumList(@Param("userId") String userId);

    List<OneCompanyContractsPersonDTO> getOneCompanyContractInfo(@Param("userId") String userId);

    // 插入一条合同记录
    int insertContractLog(ContractLog contractLog);

    // 插入合同记录后，插入相应的所有租车者到车辆人员服务表中
    int insertCompanyPerson(PersonCar personCar);
}
