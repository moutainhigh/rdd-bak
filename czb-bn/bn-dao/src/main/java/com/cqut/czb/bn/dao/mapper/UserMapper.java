package com.cqut.czb.bn.dao.mapper;


import com.cqut.czb.bn.entity.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User findUserByAccount(String account);
    Boolean insertUser(User user);
    Boolean checkAccount(@Param("account") String account);
}