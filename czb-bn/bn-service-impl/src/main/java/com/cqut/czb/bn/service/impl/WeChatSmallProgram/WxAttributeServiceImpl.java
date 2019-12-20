package com.cqut.czb.bn.service.impl.WeChatSmallProgram;

import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.AttributeMapper;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.AttributeMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.AttributeDTO;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.Attribute;
import com.cqut.czb.bn.service.weChatSmallProgram.WxAttributeService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class WxAttributeServiceImpl implements WxAttributeService{
    @Autowired
    AttributeMapperExtra attributeMapperExtra;
    @Override
    public PageInfo<AttributeDTO> getAllAttribute(Attribute attribute, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        return new PageInfo<>(attributeMapperExtra.selectAttribute(attribute)) ;
    }

    @Override
    public Boolean editAttribute(Attribute attribute) {
        attribute.setUpdateAt(new Date());
        return attributeMapperExtra.updateByPrimaryKeySelective(attribute)>0;
    }

    @Override
    public Boolean addAttribute(Attribute attribute) {
        attribute.setAttributeId(StringUtil.createId());
        attribute.setCreateAt(new Date());
        return attributeMapperExtra.insertSelective(attribute)>0;
    }

    @Override
    public Boolean deleteAttribute(String attributeId) {
        return attributeMapperExtra.deleteById(attributeId)>0;
    }
}
