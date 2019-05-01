package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.ContractModel;
import com.cqut.czb.bn.entity.entity.User;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IContractService {

    boolean uploadContractTemplate(String templateName, MultipartFile multipartFile, User user) throws IOException;

    PageInfo<ContractModel> selectContractModelList(PageDTO pageDTO);
}
