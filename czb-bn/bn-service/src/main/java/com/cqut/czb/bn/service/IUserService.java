package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.mallPartnerManage.MallPartnerDTO;
import com.cqut.czb.bn.entity.dto.myTeam.RecommenderDTO;
import com.cqut.czb.bn.entity.dto.myTeam.TeamDTO;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.dto.user.UserIdDTO;
import com.cqut.czb.bn.entity.dto.user.UserInputDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 创建人：曹渝
 * 创建时间：2019/4/24
 * 作用：用户管理
 */
public interface IUserService {

    boolean deleteUser(UserIdDTO userIdDTO);

    boolean updateUser(UserInputDTO userInputDTO);

    PageInfo<UserDTO> selectUser(UserInputDTO userInputDTO, PageDTO pageDTO);

    boolean assignRole(UserInputDTO userInputDTO);

    UserDTO selectUserInfo(User user);

    List<TeamDTO> selectTeam(String userId);

    RecommenderDTO selectRecommender(String userId);

    boolean changePartner(UserInputDTO userInputDTO);

    boolean updateTest();


    String bindingUser(UserInputDTO userInputDTO,String userId);

    JSONResult getMallPartner(User user);

    JSONResult getMyFriends(User user);
}
