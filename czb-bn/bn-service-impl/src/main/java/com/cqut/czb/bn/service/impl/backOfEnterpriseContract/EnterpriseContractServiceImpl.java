package com.cqut.czb.bn.service.impl.backOfEnterpriseContract;

import com.cqut.czb.bn.dao.mapper.EnterpriseContractMapperExtra;
import com.cqut.czb.bn.entity.dto.backOfEnterpriseContract.BasisContractInfo;
import com.cqut.czb.bn.entity.dto.backOfEnterpriseContract.PayInfoDTO;
import com.cqut.czb.bn.entity.dto.backOfEnterpriseContract.PetrolInfoDTO;
import com.cqut.czb.bn.entity.dto.backOfEnterpriseContract.PetrolPayInfo;
import com.cqut.czb.bn.service.backOfEnterpriseContract.EnterpriseContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnterpriseContractServiceImpl implements EnterpriseContractService{
    @Autowired
    EnterpriseContractMapperExtra contractMapperExtra;


    @Override
    public BasisContractInfo getBasisInfo(String contractId) {
        return contractMapperExtra.getBasisInfo(contractId);
    }

    @Override
    public List<PayInfoDTO> getPayInfoList(String contractId) {
        return contractMapperExtra.getPayInfoList(contractId);
    }

    @Override
    public List<PetrolInfoDTO> getPetrolInfoList(String contractId) {
        return contractMapperExtra.getPetrolInfoList(contractId);
    }

    @Override
    public List<PetrolPayInfo> getPetrolPayInfoList(String petrolId) {
        return contractMapperExtra.getPetrolPayInfoList(petrolId);
    }
}
