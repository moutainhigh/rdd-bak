package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.payToPerson.PayToPersonDTO;
import com.cqut.czb.bn.entity.entity.PayToPerson;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface PayToPersonService {

    PageInfo<PayToPersonDTO> getPayList(PayToPersonDTO payToPersonDTO , PageDTO pageDTO);

    Workbook exportPayList(PayToPersonDTO payToPersonDTO) throws Exception;

    int importPayList(MultipartFile file) throws Exception;

}
