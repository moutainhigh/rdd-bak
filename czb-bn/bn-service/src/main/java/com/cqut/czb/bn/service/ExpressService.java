package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.expressManage.ExpressDTO;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

@Service
public interface ExpressService {

    PageInfo<ExpressDTO> getExpress(ExpressDTO expressDTO);

    Boolean addExpress(ExpressDTO expressDTO);
}
