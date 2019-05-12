package com.cqut.czb.auth.serviceImpl;

import com.cqut.czb.auth.config.AuthConfig;
import com.cqut.czb.auth.service.UserDetailService;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.appCaptchaConfig.PhoneCode;
import com.cqut.czb.bn.entity.dto.appCaptchaConfig.VerificationCodeDTO;
import com.cqut.czb.bn.entity.dto.appRentCarContract.EnterpriseRegisterDTO;
import com.cqut.czb.bn.entity.dto.user.EnterpriseUserDTO;
import com.cqut.czb.bn.entity.dto.user.PersonalUserDTO;
import com.cqut.czb.bn.entity.entity.EnterpriseInfo;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import com.cqut.czb.bn.util.constants.SystemConstants;
import com.cqut.czb.bn.util.mapper.BeanMapper;
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
    public String registerPersonalUser(PersonalUserDTO personalUserDTO) {
        if(userMapperExtra.checkAccount(personalUserDTO.getUserAccount())) return "该用户已存在";

        VerificationCodeDTO verificationCodeDTO = BeanMapper.map(personalUserDTO, VerificationCodeDTO.class);
        if(verificationCodeMapperExtra.selectVerificationCode(verificationCodeDTO)==0) return "验证码校验失败";

        User user = BeanMapper.map(personalUserDTO, User.class);
        user.setUserId(StringUtil.createId());
        user.setUserType(0);
        user.setUserPsw(bCryptPasswordEncoder.encode(user.getUserPsw()));
        user.setCreateAt(new Date());
        user.setIsDeleted(0);
        user.setIsIdentified(0);
        user.setIsLoginPc(0);
        user.setUserRank(0);
        return (userMapper.insertSelective(user) > 0) + "";
    }

    @Override
    public String registerEnterpriseUser(EnterpriseUserDTO enterpriseUserDTO) {
        if(userMapperExtra.checkAccount(enterpriseUserDTO.getUserAccount())) return "该用户已存在";

        VerificationCodeDTO verificationCodeDTO = BeanMapper.map(enterpriseUserDTO, VerificationCodeDTO.class);
        if(verificationCodeMapperExtra.selectVerificationCode(verificationCodeDTO)==0) return "验证码校验失败";

        if(!this.enterpriseCertification(enterpriseUserDTO)) { return "企业信息校验失败"; }

        User user = BeanMapper.map(enterpriseUserDTO, User.class);
        user.setUserId(StringUtil.createId());
        user.setUserType(1);
        user.setUserPsw(bCryptPasswordEncoder.encode(user.getUserPsw()));
        user.setCreateAt(new Date());
        user.setIsDeleted(0);
        user.setIsIdentified(1);
        user.setIsLoginPc(0);
        user.setUserRank(0);
        boolean isInsertUser = userMapper.insertSelective(user) > 0;

        EnterpriseInfo enterpriseInfo = BeanMapper.map(enterpriseUserDTO, EnterpriseInfo.class);
        enterpriseInfo.setEnterpriseInfoId(StringUtil.createId());
        enterpriseInfo.setContactInfo(enterpriseUserDTO.getUserAccount());
        enterpriseInfo.setEnterpriseName(enterpriseUserDTO.getUserName());
        enterpriseInfo.setIsDeleted(0);
        enterpriseInfo.setCreateAt(new Date());
        enterpriseInfo.setUserId(user.getUserId());

        boolean isInsertEnterprise = enterpriseInfoMapper.insertSelective(enterpriseInfo) > 0;

        return (isInsertUser && isInsertEnterprise) + "";
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
    public boolean enterpriseCertification(EnterpriseUserDTO enterpriseUserDTO) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("appId", "2019042516271800110");
        paramMap.put("appKey", "uDCFes85C3OwDQ");
        paramMap.put("companyName", enterpriseUserDTO.getUserName()); // 企业名称
        paramMap.put("creditCode", enterpriseUserDTO.getOrgCode()); // 企业统一社会信用代码（组织机构代码）
        paramMap.put("legalPersonName", enterpriseUserDTO.getLegalPerson()); // 企业法人
        String code;
        try{
            String response = HttpClient4.doPost("https://authentic.yunhetong.com/authentic/company/authentic", paramMap);
            JSONObject json = new JSONObject();
            json.put("res", response);
            code = json.getJSONObject("res").get("code").toString();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        if(code != null && !code.equals("")) {
            return code.equals("200");
        } else {
            return false;
        }
    }

    @Override
    public String personalCertification(PersonalUserDTO personalUserDTO, User user) {
        VerificationCodeDTO verificationCodeDTO = BeanMapper.map(personalUserDTO, VerificationCodeDTO.class);
        if(verificationCodeMapperExtra.selectVerificationCode(verificationCodeDTO)==0) return "手机验证码校验失败";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("appId", "2019042516271800110");
        paramMap.put("appKey", "uDCFes85C3OwDQ");
        paramMap.put("idName", personalUserDTO.getUserName()); // 身份证名字
        paramMap.put("idNo", personalUserDTO.getUserIdCard()); // 身份证号
        String code;
        String message;
        try{
            String response = HttpClient4.doPost("https://authentic.yunhetong.com/authentic/personal/simple", paramMap);
            JSONObject json = new JSONObject();
            json.put("res", response);
            code = json.getJSONObject("res").get("code").toString();
            message = json.getJSONObject("res").get("msg").toString();
        }catch (Exception e){
            e.printStackTrace();
            return "参数异常，请重试";
        }
        // 临时处理
        if(true) {
            boolean isUpdate;
            user.setUpdateAt(new Date());
            user.setIsIdentified(1);
            user.setUserIdCard(personalUserDTO.getUserIdCard());
            isUpdate = userMapper.updateByPrimaryKeySelective(user) > 0;

            if(isUpdate) {
                redisUtils.remove(user.getUserAccount());
                redisUtils.put(user.getUserAccount(), user);
            }
            return isUpdate + "";
        } else {
            return message;
        }
    }

    @Override
    public boolean isCertification(User user) {

        return "1".equals(user.getIsIdentified());
    }
}
