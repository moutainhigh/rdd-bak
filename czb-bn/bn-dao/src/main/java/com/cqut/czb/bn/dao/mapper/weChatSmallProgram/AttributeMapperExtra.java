package com.cqut.czb.bn.dao.mapper.weChatSmallProgram;

import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.AttributeDTO;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.Attribute;

import java.util.List;

public interface AttributeMapperExtra {

    int insertSelective(Attribute record);

    List<AttributeDTO> selectAttribute(Attribute attribute);

    int updateByPrimaryKeySelective(Attribute record);

    int deleteById(String attributeId);

}
