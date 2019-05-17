package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.contractManagement.ContractDTO;
import com.cqut.czb.bn.entity.dto.contractManagement.ContractInputDTO;
import com.cqut.czb.bn.entity.dto.contractManagement.PersonalContractDetailDTO;

import java.util.List;

public interface ContractRecordsMapperExtra {

    List<ContractDTO> selectContractList(ContractInputDTO contractInputDTO);

    List<ContractDTO> selectEnterpriseContract(String contractId);

    List<ContractDTO> selectPersonalContract(String contractId);

    PersonalContractDetailDTO selectPersonalContractDetail(String contractId);

    int changeContractState(ContractInputDTO contractInputDTO);
}
