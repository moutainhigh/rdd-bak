package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.user.UserInputDTO;
import com.cqut.czb.bn.entity.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapperExtra {

    User findUserByAccount(String account);

    Boolean checkAccount(@Param("account") String account);

    int deleteUser(UserInputDTO userInputDTO);

    int updateUser(UserInputDTO userInputDTO);

    List<User> selectUser(UserInputDTO userInputDTO);
}
