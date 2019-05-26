package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.backOfEnterpriseContract.*;
import com.cqut.czb.bn.entity.dto.platformIncomeRecords.PlatformIncomeRecordsDTO;
import com.cqut.czb.bn.entity.dto.rentCar.companyContractSigned.ContractIdInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EnterpriseContractMapperExtra {
    BasisContractInfo getBasisInfo(@Param("contractId")String contractId);

    List<PayInfoDTO> getPayInfoList(@Param("contractId") String contractId);

    List<PetrolInfoDTO> getPetrolInfoList(@Param("contractId")String contractId);

    List<PetrolPayInfo> getPetrolPayInfoList(@Param("petrolId")String petrolId);

    List<EnterprisePayDTO> getIncomeList(String id);

    TotalMoney getIncomeTotalMoney(String id);
}
