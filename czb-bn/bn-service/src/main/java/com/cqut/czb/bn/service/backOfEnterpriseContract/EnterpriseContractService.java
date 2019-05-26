package com.cqut.czb.bn.service.backOfEnterpriseContract;

import com.cqut.czb.bn.entity.dto.backOfEnterpriseContract.*;
import com.cqut.czb.bn.entity.dto.platformIncomeRecords.PlatformIncomeRecordsDTO;
import com.cqut.czb.bn.entity.dto.rentCar.companyContractSigned.ContractIdInfo;
import com.cqut.czb.bn.entity.entity.PlatformIncomeRecords;
import com.cqut.czb.bn.entity.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EnterpriseContractService {
    BasisContractInfo getBasisInfo(String contractId);

    List<PayInfoDTO> getPayInfoList(String contractId);

    List<PetrolInfoDTO> getPetrolInfoList(String contractId);

    List<PetrolPayInfo> getPetrolPayInfoList(String petrolId);

    List<EnterprisePayDTO> getIncomeList(User user);

    Double getTotalIncome(User user);
}
