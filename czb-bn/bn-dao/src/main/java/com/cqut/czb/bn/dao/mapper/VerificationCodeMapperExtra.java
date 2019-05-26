package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.appCaptchaConfig.VerificationCodeDTO;

public interface VerificationCodeMapperExtra {
    /**
     * 添加验证码内容
     * @param verificationCodeDTO
     * @return
     */
    int insert(VerificationCodeDTO verificationCodeDTO);

    /**
     * 找出对应的验证码数量
     * @param verificationCodeDTO
     * @return
     */
    int selectVerificationCode(VerificationCodeDTO verificationCodeDTO);

    /**
     * 查出相应的验证码
     */
    VerificationCodeDTO selectContractVcode(VerificationCodeDTO verificationCodeDTO);

    /**
     * 更改验证码的状态
     * @param verificationCodeDTO
     * @return
     */
    int updateVerificationCode(VerificationCodeDTO verificationCodeDTO);

}
