package com.cqut.czb.bn.service.rentCarService;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.appRentCarContract.EnterpriseRegisterDTO;
import com.cqut.czb.bn.entity.dto.appRentCarContract.PersonalRegisterDTO;
import com.cqut.czb.bn.entity.entity.ContractModel;
import com.cqut.czb.bn.entity.entity.User;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin
public interface ContractService {
    String getContractToken();

    String registerPersonalContractAccount(PersonalRegisterDTO personalRegisterDTO, String token);

    String registerEnterpriseContractAccount(EnterpriseRegisterDTO enterpriseRegisterDTO, String token);

    boolean uploadContractTemplate(String templateName, MultipartFile multipartFile, User user) throws IOException;

    PageInfo<ContractModel> selectContractModelList(PageDTO pageDTO);
}
