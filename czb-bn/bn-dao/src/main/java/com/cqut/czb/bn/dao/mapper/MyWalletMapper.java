package com.cqut.czb.bn.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public interface MyWalletMapper {
    Double getUserAllIncome(@Param("userId") String userId);

}
