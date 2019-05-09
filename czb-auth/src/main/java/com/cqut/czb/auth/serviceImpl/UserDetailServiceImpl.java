package com.cqut.czb.auth.serviceImpl;

import com.cqut.czb.auth.config.AuthConfig;
import com.cqut.czb.auth.service.UserDetailService;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.appCaptchaConfig.PhoneCode;
import com.cqut.czb.bn.entity.dto.appCaptchaConfig.VerificationCodeDTO;
import com.cqut.czb.bn.entity.dto.appRentCarContract.EnterpriseRegisterDTO;
import com.cqut.czb.bn.entity.entity.EnterpriseInfo;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.util.constants.SystemConstants;
import com.cqut.czb.bn.util.method.HttpClient4;
import com.cqut.czb.bn.util.string.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
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
    private PetrolMapperExtra petrolMapperExtra;

    @Autowired
    private EnterpriseInfoMapper enterpriseInfoMapper;

    @Autowired
    RedisUtils redisUtils;

    @Override
    public Boolean register(User user, VerificationCodeDTO verificationCodeDTO, EnterpriseInfo enterpriseInfo) {
        if(userMapperExtra.checkAccount(user.getUserAccount())) return new Boolean(false);
        if(verificationCodeMapperExtra.selectVerificationCode(verificationCodeDTO)==0) return new Boolean(false);

        user.setUserId(StringUtil.createId());
        user.setUserPsw(bCryptPasswordEncoder.encode(user.getUserPsw()));
        user.setCreateAt(new Date());
        user.setIsDeleted(0);
        boolean isInsertUser = userMapper.insertSelective(user) > 0;

        if(user.getUserType() == SystemConstants.PERSONAL_USER) {
            // 个人用户处理
            user.setIsIdentified(0);
            return  isInsertUser;

        } else if(user.getUserType() == SystemConstants.ENTERPRISE_USER) {
            // 企业用户处理
            user.setIsIdentified(1);

            enterpriseInfo.setEnterpriseInfoId(StringUtil.createId());
            enterpriseInfo.setIsDeleted(0);
            enterpriseInfo.setCreateAt(new Date());
            enterpriseInfo.setUserId(user.getUserId());

            boolean isInsertEnterprise = enterpriseInfoMapper.insertSelective(enterpriseInfo) > 0;
            return isInsertUser && isInsertEnterprise;
        }

        return false;
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

    @Override
    public boolean enterpriseCertification(EnterpriseInfo enterpriseInfo) {
        JSONObject requestJson = new JSONObject();
        requestJson.put("appId", "2019042516271800110");
        requestJson.put("appKey", "uDCFes85C3OwDQ");
        requestJson.put("companyName", enterpriseInfo.getEnterpriseName()); // 企业名称
        requestJson.put("creditCode", enterpriseInfo.getOrgCode()); // 企业证件号
        requestJson.put("legalPersonName", enterpriseInfo.getLegalPerson()); // 企业法人
        String id;
        try{
            String response = HttpClient4.doPost("https://authentic.yunhetong.com/authentic/company/authentic", requestJson, 1);
            Map map = new HashMap();
            map.put("a", response);
            JSONObject json = new JSONObject();
            json.putAll(map);
            id = json.getJSONObject("a").getJSONObject("data").getString("id");
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        if(id != null) {
            return id.length() > 0;
        } else {
            return false;
        }
    }
}
