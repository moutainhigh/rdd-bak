package com.cqut.czb.bn.service.rentCarService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public interface ContractService {
    String getContractToken();

    String registerPersonalContractAccount(String token);
}
