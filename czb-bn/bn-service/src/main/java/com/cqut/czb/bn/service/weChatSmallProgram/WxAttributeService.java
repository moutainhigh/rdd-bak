package com.cqut.czb.bn.service.weChatSmallProgram;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.AttributeDTO;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.Attribute;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface WxAttributeService {
    PageInfo<AttributeDTO> getAllAttribute(Attribute attribute, PageDTO pageDTO);

    Boolean editAttribute(Attribute attribute);

    Boolean addAttribute(Attribute attribute);

    Boolean deleteAttribute(String attributeId);
}
