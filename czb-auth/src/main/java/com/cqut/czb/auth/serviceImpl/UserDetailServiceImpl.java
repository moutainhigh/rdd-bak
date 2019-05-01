package com.cqut.czb.auth.serviceImpl;

import com.cqut.czb.auth.service.UserDetailService;
import com.cqut.czb.bn.dao.mapper.UserMapper;
import com.cqut.czb.bn.dao.mapper.UserMapperExtra;
import com.cqut.czb.bn.dao.mapper.VerificationCodeMapper;
import com.cqut.czb.bn.dao.mapper.VerificationCodeMapperExtra;
import com.cqut.czb.bn.entity.dto.appCaptchaConfig.VerificationCodeDTO;
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

    @Autowired
    private VerificationCodeMapperExtra verificationCodeMapperExtra;

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
        //创建一个发送短信的对象（对象）
        VerificationCodeDTO verificationCodeDTO=new VerificationCodeDTO(phone,"122");
        //计时器——5分钟之后执行
        timerTask task=new timerTask(verificationCodeDTO);
        Timer timer=new Timer();
        timer.schedule(task,300000);
        return verificationCodeMapperExtra.insert(verificationCodeDTO)>0;
    }

    @Override
    public boolean checkVerificationCode(VerificationCodeDTO verificationCodeDTO) {
        if(verificationCodeMapperExtra.selectVerificationCode(verificationCodeDTO)!=0){
            boolean updateUserPSW= userMapperExtra.updateUserPSW(verificationCodeDTO.getUserPsw())>0;
            boolean updateVerificationCode=verificationCodeMapperExtra.updateVerificationCode(verificationCodeDTO)>0;
            if(updateUserPSW==updateVerificationCode==true){
                return true;
            }
            return false;
        }
        return false;
    }


}
