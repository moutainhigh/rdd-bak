package com.cqut.czb.auth.service;

import com.cqut.czb.bn.entity.dto.appCaptchaConfig.VerificationCodeDTO;
import com.cqut.czb.bn.entity.dto.user.EnterpriseUserDTO;
import com.cqut.czb.bn.entity.dto.user.PersonalUserDTO;
import com.cqut.czb.bn.entity.entity.User;

public interface UserDetailService {
    String registerPersonalUser(PersonalUserDTO personalUserDTO);
    String registerEnterpriseUser(EnterpriseUserDTO enterpriseUserDTO);
    Boolean registerWCProgramUser(PersonalUserDTO personalUserDTO);
    Boolean checkAccount(User user);
    User loadUserByUsername(String account);

    /**
     * 添加验证码并发送验证码
     * @param phone
     * @return
     */
    Boolean insertVerificationCode(String phone);


    /**
     * 插入游客登录验证码
     */
    Boolean insertVCode(VerificationCodeDTO verificationCodeDTO);

    /**
     * 查询出数据库中的验证码
     * @param verificationCodeDTO
     * @return
     */
    boolean checkVerificationCode(VerificationCodeDTO verificationCodeDTO);

    /**
     * 修改密码
     * @param user
     * @param oldPWD
     * @param newPWD
     * @return
     */

    boolean changePWD(User user,String oldPWD,String newPWD);

    /**
     * 企业认证
     * */
    boolean enterpriseCertification(EnterpriseUserDTO enterpriseUserDTO);

    String personalCertification(PersonalUserDTO personalUserDTO, User user);

    boolean isCertification(User user);

    /**
     * 合同验证码验证
     */
    VerificationCodeDTO checkContractVcode(VerificationCodeDTO verificationCodeDTO);
}
