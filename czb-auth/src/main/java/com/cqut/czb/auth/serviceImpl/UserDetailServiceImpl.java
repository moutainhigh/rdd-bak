package com.cqut.czb.auth.serviceImpl;

import com.cqut.czb.auth.config.AuthConfig;
import com.cqut.czb.auth.service.UserDetailService;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.appCaptchaConfig.PhoneCode;
import com.cqut.czb.bn.entity.dto.appCaptchaConfig.VerificationCodeDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class UserDetailServiceImpl implements UserDetailService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserMapperExtra userMapperExtra;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private VerificationCodeMapperExtra verificationCodeMapperExtra;

    @Autowired
    PetrolMapperExtra petrolMapperExtra;

    @Autowired
    RedisUtils redisUtils;

    @Override
    public Boolean register(User user, VerificationCodeDTO verificationCodeDTO) {
        if(userMapperExtra.checkAccount(user.getUserAccount())) return new Boolean(false);
        if(verificationCodeMapperExtra.selectVerificationCode(verificationCodeDTO)==0) return new Boolean(false);

        user.setUserId(StringUtil.createId());
        user.setUserPsw(bCryptPasswordEncoder.encode(user.getUserPsw()));
        user.setCreateAt(new Date());
        user.setIsDeleted(0);
        return userMapper.insertSelective(user) > 0;
    }

    @Override
    public Boolean checkAccount(User user) {
        return userMapperExtra.checkAccount(user.getUserAccount());
    }

    @Override
    public Boolean insertVerificationCode(String phone) {
        //创建一个发送短信的对象（对象）
        PhoneCode phoneCode = new PhoneCode();
        String content = phoneCode.vcode();
        VerificationCodeDTO verificationCodeDTO = new VerificationCodeDTO(phone, content);
        //验证码保存数据库
        Boolean isSaveCode = false;
        //验证码发送
        String isSend = phoneCode.getPhonemsg(phone, content);
//        String isSend="true";
        if (isSend == "true") {
            isSaveCode = verificationCodeMapperExtra.insert(verificationCodeDTO) > 0;
        }
        if (isSaveCode != true) {
            return false;
        }
        //计时器——1分钟之后执行
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("计时器起吊");
                System.out.println(isSend);
                //将所有的验证码的都改状态为失效
                verificationCodeMapperExtra.updateVerificationCode(verificationCodeDTO);
            }
        }, 300000);
        return true;
    }

    @Override
    public boolean checkVerificationCode(VerificationCodeDTO verificationCodeDTO) {
        //判断信息是否为空 user.setUserPsw(bCryptPasswordEncoder.encode(user.getUserPsw()));
        System.out.println("inter service");
        if (verificationCodeDTO == null)
            return false;
        if (verificationCodeMapperExtra.selectVerificationCode(verificationCodeDTO) != 0) {//如果不为零则验证码未过期（返回的是验证码个数）
            //更改用户的密码
            System.out.println("inter service");
            verificationCodeDTO.setUserPsw(bCryptPasswordEncoder.encode(verificationCodeDTO.getUserPsw()));
            boolean updateUserPSW = userMapperExtra.updateUserPSW(verificationCodeDTO) > 0;
            //更改验证码的状态
            boolean updateVerificationCode = verificationCodeMapperExtra.updateVerificationCode(verificationCodeDTO) > 0;
            if (updateUserPSW == true && updateVerificationCode == true) {
                //清除缓存
                User user=userMapperExtra.findUserByAccount(verificationCodeDTO.getUserAccount());
                System.out.println("修改成功");
                redisUtils.remove(user.getUserAccount()+ AuthConfig.TOKEN);
                redisUtils.remove(user.getUserAccount());
                System.out.println("缓存以清除");
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean changePWD(User user, String oldPWD, String newPWD) {
        //检验密码是否一致。
        User checkUser = userMapperExtra.findUserByAccount(user.getUserAccount());//通过电话号码来查询
        System.out.println(checkUser.getUserPsw());
        boolean isLike=bCryptPasswordEncoder.matches(checkUser.getUserPsw(),oldPWD);
        if (isLike) {
            System.out.println("错误");
            return false;
        } else {
            //进行修改密码
            checkUser.setUserPsw(bCryptPasswordEncoder.encode(newPWD));
            boolean ischangePWD = userMapperExtra.changePWD(checkUser) > 0;
            return ischangePWD;
        }
    }
}
