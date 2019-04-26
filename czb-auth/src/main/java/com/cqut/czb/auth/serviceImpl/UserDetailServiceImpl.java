package com.cqut.czb.auth.serviceImpl;

import com.cqut.czb.auth.service.UserDetailService;
import com.cqut.czb.bn.dao.mapper.UserMapper;
import com.cqut.czb.bn.dao.mapper.UserMapperExtra;
import com.cqut.czb.bn.dao.mapper.VerificationCodeMapper;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.VerificationCode;
import com.cqut.czb.auth.util.timerTask;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Timer;

@Service
public class UserDetailServiceImpl implements UserDetailService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserMapperExtra userMapperExtra;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private VerificationCodeMapper verificationCodeMapper;

    @Override
    public Boolean register(User user) {
        if(userMapperExtra.checkAccount(user.getUserAccount())) return new Boolean(false);
        user.setUserId(StringUtil.createId());
        user.setUserPsw(bCryptPasswordEncoder.encode(user.getUserPsw()));
        return userMapper.insertSelective(user) > 0;
    }

    @Override
    public Boolean checkAccount(User user) {
        return userMapperExtra.checkAccount(user.getUserAccount());
    }

    @Override
    public Boolean insertVerificationCode(String phone) {
        VerificationCode verificationCode=new VerificationCode(phone);
        //计时器——5000分钟之后执行
        timerTask task=new timerTask(verificationCode);
        Timer timer=new Timer();
        timer.schedule(task,300000);
        return verificationCodeMapper.insert(verificationCode)>0;
    }

    @Override
    public boolean checkVerificationCode(VerificationCode verificationCode) {
        if(verificationCodeMapper.selectVerificationCode(verificationCode)!=0){
            boolean updateUserPSW= userMapper.updateUserPSW(verificationCode.getUserPsw())>0;
            boolean updateVerificationCode=verificationCodeMapper.updateVerificationCode(verificationCode)>0;
            if(updateUserPSW==updateVerificationCode==true){
                return true;
            }
            return false;
        }
        return false;
    }


}
