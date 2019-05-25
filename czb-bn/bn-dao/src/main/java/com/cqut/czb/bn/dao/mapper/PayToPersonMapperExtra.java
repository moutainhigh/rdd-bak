package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.payToPerson.PayToPersonDTO;
import com.cqut.czb.bn.entity.entity.PayToPerson;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PayToPersonMapperExtra {
    int deleteByPrimaryKey(String recordId);

    int insert(List<PayToPersonDTO> list);

    int insertSelective(PayToPerson record);

    List<PayToPersonDTO> selectPayInfo(PayToPersonDTO payToPersonDTO);

    List<PayToPersonDTO> selectByPrimaryKey(PayToPersonDTO payToPersonDTO);

    int updateByPrimaryKeySelective(PayToPerson record);

    int updateByPrimaryKey(PayToPerson record);

    int updateImportData (List<PayToPersonDTO> list);

    List<PayToPersonDTO> selectNewContract(@Param("list")List<PayToPersonDTO> list,@Param("pay")PayToPersonDTO payToPersonDTO );
}
