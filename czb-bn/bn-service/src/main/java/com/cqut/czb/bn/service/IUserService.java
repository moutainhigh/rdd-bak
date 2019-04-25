package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.dto.user.UserIdDTO;
import com.cqut.czb.bn.entity.dto.user.UserInputDTO;
import com.github.pagehelper.PageInfo;

/**
 * 创建人：曹渝
 * 创建时间：2019/4/24
 * 作用：用户管理
 */
public interface IUserService {

    boolean deleteUser(UserIdDTO userIdDTO);

    boolean updateUser(UserInputDTO userInputDTO);

    PageInfo<UserDTO> selectUser(UserInputDTO userInputDTO, PageDTO pageDTO);
}
