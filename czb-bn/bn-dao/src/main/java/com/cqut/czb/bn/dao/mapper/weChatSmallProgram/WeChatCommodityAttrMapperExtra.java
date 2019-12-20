package com.cqut.czb.bn.dao.mapper.weChatSmallProgram;

import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatCommodityAttr;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WeChatCommodityAttrMapperExtra {

    List<WeChatCommodityAttr> selectByPrimaryKeys(@Param("list") List<String> list);

}