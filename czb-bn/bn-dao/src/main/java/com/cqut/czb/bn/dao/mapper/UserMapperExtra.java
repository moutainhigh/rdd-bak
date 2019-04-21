package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapperExtra {
    User findUserByAccount(String account);

    Boolean checkAccount(@Param("account") String account);
}
