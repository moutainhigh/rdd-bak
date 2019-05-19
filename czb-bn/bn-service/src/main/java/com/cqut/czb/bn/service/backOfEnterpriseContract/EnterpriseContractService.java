package com.cqut.czb.bn.service.backOfEnterpriseContract;

import com.cqut.czb.bn.entity.dto.backOfEnterpriseContract.BasisContractInfo;
import com.cqut.czb.bn.entity.dto.backOfEnterpriseContract.PayInfoDTO;
import com.cqut.czb.bn.entity.dto.backOfEnterpriseContract.PetrolInfoDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EnterpriseContractService {
    BasisContractInfo getBasisInfo(String contractId);

    List<PayInfoDTO> getPayInfoList(String contractId);

    List<PetrolInfoDTO> getPetrolInfoList(String contractId);
}
