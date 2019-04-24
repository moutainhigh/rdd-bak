package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.user.UserIdDTO;
import com.cqut.czb.bn.entity.dto.user.UserInputDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.service.IUserService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Override
    public boolean deleteUser(UserIdDTO userIdDTO) {
        return false;
    }

    @Override
    public boolean updateUser(UserInputDTO userInputDTO) {
        return false;
    }

    @Override
    public PageInfo<User> selectUser(UserInputDTO userInputDTO, PageDTO pageDTO) {
        return null;
    }
}
