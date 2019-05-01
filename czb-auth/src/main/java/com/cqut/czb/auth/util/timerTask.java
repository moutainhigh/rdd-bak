package com.cqut.czb.auth.util;

import com.cqut.czb.auth.service.UserDetailService;
import com.cqut.czb.bn.entity.dto.appCaptchaConfig.VerificationCodeDTO;
import com.cqut.czb.bn.entity.entity.VerificationCode;


import java.util.TimerTask;

/**
 * 创建人：陈德强
 * 创建时间：2019/4/26
 * 作用：计时器
 */
public class timerTask extends TimerTask {

    private VerificationCodeDTO verificationCodeDTO;

    private UserDetailService userDetailService;

    public timerTask(VerificationCodeDTO verificationCodeDTO) {
        this.verificationCodeDTO = verificationCodeDTO;
    }

    @Override
    public void run() {
         if(verificationCodeDTO!=null){
            boolean isChange=userDetailService.checkVerificationCode(verificationCodeDTO);
             this.cancel();
         }
        this.cancel();
    }
}
