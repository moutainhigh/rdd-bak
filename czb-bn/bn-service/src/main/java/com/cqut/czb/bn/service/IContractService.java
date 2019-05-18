package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.contractManagement.ContractDTO;
import com.cqut.czb.bn.entity.dto.contractManagement.ContractInputDTO;
import com.cqut.czb.bn.entity.dto.contractManagement.PersonalContractDetailDTO;
import com.cqut.czb.bn.entity.entity.ContractModel;
import com.cqut.czb.bn.entity.entity.User;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IContractService {

    boolean uploadContractTemplate(String templateName, MultipartFile multipartFile, User user) throws IOException;

    PageInfo<ContractModel> selectContractModelList(PageDTO pageDTO);

    PageInfo<ContractDTO> selectEnterpriseContractList(ContractInputDTO contractInputDTO, PageDTO pageDTO);

    PageInfo<ContractDTO> selectPersonalContractList(ContractInputDTO contractInputDTO, PageDTO pageDTO);

    PageInfo<ContractDTO> selectEnterpriseContract(String contractId, PageDTO pageDTO);

    PageInfo<ContractDTO> selectPersonalContract(String contractId, PageDTO pageDTO);

    PersonalContractDetailDTO selectPersonalContractDetail(String contractId);

    boolean changeContractState(ContractInputDTO contractInputDTO);
}
