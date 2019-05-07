package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.appRentCarContract.EnterpriseRegisterDTO;
import com.cqut.czb.bn.entity.dto.appRentCarContract.PersonalRegisterDTO;
import com.cqut.czb.bn.entity.entity.EnterpriseInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

public interface ContractMapperExtra {
    int insertUserContractYunId(@Param("userId") String userId, @Param("userYunId") String userYunId);

    // 根据userId获得个人信息
    PersonalRegisterDTO getPersonInfo(@Param("userId") String userId);

    EnterpriseRegisterDTO getEnterpriseInfo(@Param("userId") String userId);

    int insertEnterpriseContractYunId(@Param("enterpriseId") String enterpriseId, @Param("enterpriseYunId") String enterpriseYunId);

    int insertContractId(@Param("userId") String userId, @Param("contractId") String contractId);

    String getEnterpriseId(@Param("userId")String userId);

}
