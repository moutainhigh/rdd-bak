package com.cqut.czb.bn.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public interface ContractMapper {
    int insertUserContractYunId(@Param("userId") String userId, @Param("userYunId") String userYunId);

    int insertEnterpriseContractYunId(@Param("enterpriseId") String enterpriseId, @Param("enterpriseYunId") String enterpriseYunId);

    int insertContractId(@Param("userId") String userId, @Param("contractId") String contractId);

    String getEnterpriseId(@Param("userId")String userId);
}
