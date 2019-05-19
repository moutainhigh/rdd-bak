package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.backOfEnterpriseContract.BasisContractInfo;
import com.cqut.czb.bn.entity.dto.backOfEnterpriseContract.PayInfoDTO;
import com.cqut.czb.bn.entity.dto.backOfEnterpriseContract.PetrolInfoDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EnterpriseContractMapperExtra {
    BasisContractInfo getBasisInfo(@Param("contractId")String contractId);

    List<PayInfoDTO> getPayInfoList(@Param("contractId") String contractId);

    List<PetrolInfoDTO> getPetrolInfoList(@Param("contractId")String contractId);
}
