package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.appCaptchaConfig.VerificationCodeDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.PersonalCenterUserDTO;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.dto.user.UserIdDTO;
import com.cqut.czb.bn.entity.dto.user.UserInputDTO;
import com.cqut.czb.bn.entity.entity.EnterpriseInfo;
import com.cqut.czb.bn.entity.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapperExtra {
    User findUserByAccount(String account);

    Boolean checkAccount(@Param("account") String account);

    int deleteUser(UserIdDTO userIdDTO);

    int updateUser(UserInputDTO userInputDTO);

    List<UserDTO> selectUser(UserInputDTO userInputDTO);

    int updateUserPSW(VerificationCodeDTO verificationCodeDTO);

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
}
