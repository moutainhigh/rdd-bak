package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.LoginInfo;
import org.apache.ibatis.annotations.Param;

public interface LoginInfoMapperExtra {
    int deleteByPrimaryKey(String recordId);

    int insertSelective(LoginInfo record);

    int updateByUserIdSelective(LoginInfo recordId);

    //查找是否已经存在数据
    int selectIsExist(@Param("userId") String userId,@Param("deviceType") String deviceType);
}