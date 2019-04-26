package com.cqut.czb.bn.service.rentCarService;

import com.cqut.czb.bn.entity.dto.appRentCarContract.PersonalRegisterDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public interface ContractService {
    String getContractToken();

    String registerPersonalContractAccount(PersonalRegisterDTO personalRegisterDTO, String token);
}
