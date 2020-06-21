package com.cqut.czb.bn.dao.mapper.weChatSmallProgram;


import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatVipApply;

public interface WeChatVipApplyMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(WeChatVipApply record);

    int insertSelective(WeChatVipApply record);

    WeChatVipApply selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(WeChatVipApply record);

    int updateByPrimaryKey(WeChatVipApply record);

    double getWxVIPMoeny();

    double getWxVIPFY1();

    double getWxVIPFY2();

    boolean changeWxVIPMoeny(double WxVipMoeny);

    boolean changeWxVIPFY1(double WxVipFY1);

    boolean changeWxVIPFY2(double WxVipFY2);

}