package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.VerificationCode;

public interface VerificationCodeMapperExtra {
    /**
     * 添加验证码内容
     * @param verificationCode
     * @return
     */
    int insert(VerificationCode verificationCode);

    /**
     * 找出对应的验证码
     * @param verificationCode
     * @return
     */
    int selectVerificationCode(VerificationCode verificationCode);

    /**
     * 更改验证码的状态
     * @param verificationCode
     * @return
     */
    int updateVerificationCode(VerificationCode verificationCode);

    /**
     *
     */
}
