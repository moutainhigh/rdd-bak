package com.cqut.czb.bn.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@CrossOrigin
public interface MyWalletMapperExtra {
    Double getUserAllIncome(@Param("userId") String userId);

    int increaseWithdrawed(@Param("paymentAmount") double paymentAmount);

    String getPsw(@Param("userId") String userId);

    BigDecimal getBalance(@Param("userId") String userId);
}
