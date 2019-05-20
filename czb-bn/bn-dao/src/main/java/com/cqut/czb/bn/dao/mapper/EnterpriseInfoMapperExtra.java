package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.enterpriseInfo.EnterpriseInfoDTO;
import com.cqut.czb.bn.entity.dto.enterpriseInfo.IdentifyCodeDTO;
import com.cqut.czb.bn.entity.entity.ContractRecords;
import com.cqut.czb.bn.entity.entity.EnterpriseInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface EnterpriseInfoMapperExtra {

    int deleteById(String enterpriseInfoId);

    int insert(EnterpriseInfo record);

    int insertSelective(EnterpriseInfo record);

    List<EnterpriseInfoDTO> selectByPrimaryKey(@Param("enterpriseInfo") EnterpriseInfoDTO enterpriseInfoDTO);

    List<ContractRecords> selectEnterpriseContract(EnterpriseInfoDTO enterpriseInfo);

    List<IdentifyCodeDTO> selectIdentifyCode(EnterpriseInfoDTO enterpriseInfoDTO);

    int updateByPrimaryKeySelective(EnterpriseInfo record);

    int updateByPrimaryKey(EnterpriseInfo record);
}
