package com.cqut.czb.auth.service;

import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.VerificationCode;

public interface UserDetailService {
    Boolean register(User user);
    Boolean checkAccount(User user);

    /**
     * 添加验证码
     * @param phone
     * @return
     */
    Boolean insertVerificationCode(String phone);

    /**
     * 查询出数据库中的验证码
     * @param verificationCode
     * @return
     */
    boolean checkVerificationCode(VerificationCode verificationCode);

}
