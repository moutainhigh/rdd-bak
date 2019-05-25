package com.cqut.czb.bn.service.impl.backOfEnterpriseContract;

import com.cqut.czb.bn.dao.mapper.EnterpriseContractMapperExtra;
import com.cqut.czb.bn.entity.dto.backOfEnterpriseContract.*;
import com.cqut.czb.bn.entity.dto.platformIncomeRecords.PlatformIncomeRecordsDTO;
import com.cqut.czb.bn.entity.dto.rentCar.companyContractSigned.ContractIdInfo;
import com.cqut.czb.bn.entity.entity.User;
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

    @Override
    public List<EnterprisePayDTO> getIncomeList(User user) {
        List<EnterprisePayDTO> enterprisePayDTOS = contractMapperExtra.getIncomeList(user.getUserId());
        if (enterprisePayDTOS!=null&&enterprisePayDTOS.size()!=0) {
            Double money = contractMapperExtra.getIncomeTotalMoney(user.getUserId());
            if (money!=null) {
                enterprisePayDTOS.get(0).setTotalMoney(money);
            }else {
                enterprisePayDTOS.get(0).setTotalMoney(0.0);
            }
        }
        return enterprisePayDTOS;
    }
}
