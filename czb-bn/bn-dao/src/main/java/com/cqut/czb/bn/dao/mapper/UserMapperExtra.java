package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.appCaptchaConfig.VerificationCodeDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.PersonalCenterUserDTO;
import com.cqut.czb.bn.entity.dto.myTeam.RecommenderDTO;
import com.cqut.czb.bn.entity.dto.myTeam.TeamDTO;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.dto.user.UserIdDTO;
import com.cqut.czb.bn.entity.dto.user.UserInputDTO;
import com.cqut.czb.bn.entity.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapperExtra {
    User selectByPrimaryKey(String userId);

    User findUserByAccount(String account);

    UserDTO findUserDTOByAccount(String account);

    UserDTO findUserDTOById(String id);

    Boolean checkAccount(@Param("account") String account);

    int deleteUser(UserIdDTO userIdDTO);

    int updateUser(UserInputDTO userInputDTO);

    List<UserDTO> selectUser(UserInputDTO userInputDTO);

    List<User> selectByPartner(UserInputDTO userInputDTO);

    int updateUserPSW(VerificationCodeDTO verificationCodeDTO);

    List<TeamDTO> selectTeam(@Param("userId") String userId);

    RecommenderDTO selectRecommender(String userId);

    List<User> getAllSubUser();

    int insertAllSubUser(String userId);

    int updatePartnerState(@Param("userList")List<User> userList, @Param("first") String first,@Param("second") String second);

//    UserTreeDTO getAllUserByOneParent(@Param("userId") String userId);

    /**
     * 修改密码——个人中心
     * @param user
     * @return
     */
    int changePWD(User user);

    /**
     * 通过userId查出上级的用户
     */
    String selectUserId(@Param("userId") String userId);

    /**
     * 用户个人及企业信息
     * @param userId
     * @return
     */
    PersonalCenterUserDTO GetUserEnterpriseInfo(@Param("userId") String userId);

    /**
     * 个人中心修改联系人
     * @param personalCenterUserDTO
     * @return
     */
    int ModifyContactInfo(PersonalCenterUserDTO personalCenterUserDTO);

    /**
     * 将用户身份改为vip
     */
    int UpdateToVip(@Param("userId") String userId);

    /**
     * 递归查询最近的一个vip
     */
    String selectVipUser(@Param("userId") String userId);


    List<User> getOldSubUserPartner(String userId);

    List<User> getOldSubUser(String userId);

    /**
     * 批量修改superiorUser
     */
    int updateSuperUser(List<User> userList);


    List<User> getTest();

    int updateTest(List<User> userList);

    User selectPassword(UserInputDTO userInputDTO);

    String selectBindingAccount(String bindingid);
}
