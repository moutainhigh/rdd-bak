package com.cqut.czb.bn.service.rentCarService;

import com.cqut.czb.bn.entity.dto.appRentCarContract.EnterpriseRegisterDTO;
import com.cqut.czb.bn.entity.dto.appRentCarContract.PersonalRegisterDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

public interface ContractService {
    String getContractToken();

    String registerPersonalContractAccount(String userId, String token);

    String registerEnterpriseContractAccount(String userId, String token);

    String createContract(String token);

    String addContractOwner(String token);

    String signerContract(String token);

    String czContract(String token );

    String getContractId();

}
