package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.ExpressDTO;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExpressService {

    PageInfo<ExpressDTO> getExpress(ExpressDTO expressDTO);

    Boolean addExpress(ExpressDTO expressDTO);
}
