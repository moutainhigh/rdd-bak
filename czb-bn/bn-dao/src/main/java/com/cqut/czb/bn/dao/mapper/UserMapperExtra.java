package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.dto.user.UserIdDTO;
import com.cqut.czb.bn.entity.dto.user.UserInputDTO;
import com.cqut.czb.bn.entity.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapperExtra {
    User findUserByAccount(String account);

    Boolean checkAccount(@Param("account") String account);

    int deleteUser(UserIdDTO userIdDTO);

    int updateUser(UserInputDTO userInputDTO);

    List<UserDTO> selectUser(UserInputDTO userInputDTO);

    int updateUserPSW(String userPsw);
}
