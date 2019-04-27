package com.cqut.czb.auth.util;

import com.cqut.czb.auth.service.UserDetailService;
import com.cqut.czb.bn.entity.entity.VerificationCode;


import java.util.TimerTask;

/**
 * 创建人：陈德强
 * 创建时间：2019/4/26
 * 作用：计时器
 */
public class timerTask extends TimerTask {

    private VerificationCode verificationCode;

    private UserDetailService userDetailService;

    public timerTask(VerificationCode verificationCode) {
        this.verificationCode = verificationCode;
    }

    @Override
    public void run() {
         if(verificationCode!=null){
            boolean isChange=userDetailService.checkVerificationCode(verificationCode);
             this.cancel();
         }
        this.cancel();
    }
}
