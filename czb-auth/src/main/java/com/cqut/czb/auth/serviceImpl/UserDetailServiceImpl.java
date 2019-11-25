package com.cqut.czb.auth.serviceImpl;

import com.cqut.czb.auth.config.AuthConfig;
import com.cqut.czb.auth.service.UserDetailService;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.appCaptchaConfig.PhoneCode;
import com.cqut.czb.bn.entity.dto.appCaptchaConfig.VerificationCodeDTO;
import com.cqut.czb.bn.entity.dto.infoSpread.PartnerDTO;
import com.cqut.czb.bn.entity.dto.user.EnterpriseUserDTO;
import com.cqut.czb.bn.entity.dto.user.PersonalUserDTO;
import com.cqut.czb.bn.entity.entity.EnterpriseInfo;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.UserIncomeInfo;
import com.cqut.czb.bn.service.InfoSpreadService;
import com.cqut.czb.bn.util.date.DateUtil;
import com.cqut.czb.bn.util.mapper.BeanMapper;
import com.cqut.czb.bn.util.method.HttpClient4;
import com.cqut.czb.bn.util.string.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserDetailServiceImpl implements UserDetailService {

    private final UserMapper userMapper;

    private final UserMapperExtra userMapperExtra;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final VerificationCodeMapperExtra verificationCodeMapperExtra;

    private final EnterpriseInfoMapper enterpriseInfoMapper;

    private final UserIncomeInfoMapper userIncomeInfoMapper;

    private final RedisUtils redisUtils;

    private final InfoSpreadService infoSpreadService;

    @Autowired
    public UserDetailServiceImpl(UserMapper userMapper, UserMapperExtra userMapperExtra, BCryptPasswordEncoder bCryptPasswordEncoder, VerificationCodeMapperExtra verificationCodeMapperExtra, EnterpriseInfoMapper enterpriseInfoMapper, UserIncomeInfoMapper userIncomeInfoMapper, RedisUtils redisUtils, InfoSpreadService infoSpreadService) {
        this.userMapper = userMapper;
        this.userMapperExtra = userMapperExtra;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.verificationCodeMapperExtra = verificationCodeMapperExtra;
        this.enterpriseInfoMapper = enterpriseInfoMapper;
        this.userIncomeInfoMapper = userIncomeInfoMapper;
        this.redisUtils = redisUtils;
        this.infoSpreadService = infoSpreadService;
    }


    @Override
    synchronized public String registerPersonalUser(PersonalUserDTO personalUserDTO) {
        if(userMapperExtra.checkAccount(personalUserDTO.getUserAccount())) return "亲，您的手机号已注册，换一个吧";

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
        if(null == personalUserDTO.getSuperiorUser() && "".equals(personalUserDTO.getSuperiorUser())) {
//            user.setSuperiorUser(userMapperExtra.findUserByAccount("18008354161").getUserId());
        } else {
            if(userMapperExtra.checkAccount(personalUserDTO.getSuperiorUser())) {
                User superior = userMapperExtra.findUserByAccount(personalUserDTO.getSuperiorUser());
                user.setSuperiorUser(superior.getUserId());
                user.setFirstLevelPartner(superior.getFirstLevelPartner());
                user.setSecondLevelPartner(superior.getSecondLevelPartner());
                if(1 == superior.getPartner()) {
                    user.setSecondLevelPartner(superior.getUserId());
                }
                if(2 == superior.getPartner()) {
                    user.setFirstLevelPartner(superior.getUserId());
                }
            } else {
                user.setSuperiorUser(null);
            }
        }
        UserIncomeInfo userIncomeInfo = new UserIncomeInfo();
        userIncomeInfo.setUserId(user.getUserId());
        userIncomeInfo.setInfoId(StringUtil.createId());
        userIncomeInfo.setFanyongIncome(0.00);
        userIncomeInfo.setShareIncome(0.00);
        userIncomeInfo.setGotTotalRent(0.00);
        userIncomeInfo.setOtherIncome(0.00);
        userIncomeInfo.setPayTotalRent(0.00);
        userIncomeInfo.setWithdrawed(0.00);
        userIncomeInfo.setCreateAt(new Date());
        boolean isInsert = userIncomeInfoMapper.insertSelective(userIncomeInfo) > 0;
        if(isInsert) {
            if(userMapper.insertSelective(user) > 0) {
                PartnerDTO partnerDTO = new PartnerDTO();
                partnerDTO.setUserId(user.getUserId());
                partnerDTO.setMonthTime(DateUtil.dateToStr(user.getCreateAt()));
                infoSpreadService.addChildPromotion(partnerDTO);
            } else {
                return String.valueOf("用户注册失败");
            }
        } else {
            return String.valueOf("用户收益信息添加失败");
        }

        return String.valueOf(true);
    }

    @Override
    synchronized public String registerEnterpriseUser(EnterpriseUserDTO enterpriseUserDTO) {
        if(userMapperExtra.checkAccount(enterpriseUserDTO.getUserAccount())) return "该用户已存在";

        VerificationCodeDTO verificationCodeDTO = BeanMapper.map(enterpriseUserDTO, VerificationCodeDTO.class);
        if(verificationCodeMapperExtra.selectVerificationCode(verificationCodeDTO)==0) return "验证码校验失败";

//        if(!this.enterpriseCertification(enterpriseUserDTO)) { return "企业信息校验失败"; }

        User user = BeanMapper.map(enterpriseUserDTO, User.class);
        user.setUserId(StringUtil.createId());
        user.setUserType(1);
        user.setUserPsw(bCryptPasswordEncoder.encode(user.getUserPsw()));
        user.setCreateAt(new Date());
        user.setIsDeleted(0);
        user.setIsIdentified(1);
        user.setIsLoginPc(0);
        user.setUserRank(0);

        UserIncomeInfo userIncomeInfo = new UserIncomeInfo();
        userIncomeInfo.setUserId(user.getUserId());
        userIncomeInfo.setInfoId(StringUtil.createId());
        userIncomeInfo.setFanyongIncome(0.00);
        userIncomeInfo.setShareIncome(0.00);
        userIncomeInfo.setGotTotalRent(0.00);
        userIncomeInfo.setOtherIncome(0.00);
        userIncomeInfo.setPayTotalRent(0.00);
        userIncomeInfo.setWithdrawed(0.00);
        userIncomeInfo.setCreateAt(new Date());
        boolean isInsert = userIncomeInfoMapper.insertSelective(userIncomeInfo) > 0;
        if(isInsert) {
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
        } else {
            return false + "";
        }
    }

    @Override
    public Boolean registerWCProgramUser(PersonalUserDTO personalUserDTO) {
        if(userMapperExtra.checkAccount(personalUserDTO.getUserAccount())) return false;
        User user = BeanMapper.map(personalUserDTO, User.class);
        user.setUserId(StringUtil.createId());
        user.setUserType(2);
        user.setUserPsw(bCryptPasswordEncoder.encode("000000"));
        user.setCreateAt(new Date());
        user.setIsDeleted(0);
        user.setIsIdentified(0);
        user.setIsLoginPc(0);
        user.setUserRank(0);
        if(null != personalUserDTO.getSuperiorUser() && !"".equals(personalUserDTO.getSuperiorUser())) {
            user.setSuperiorUser(personalUserDTO.getSuperiorUser());
        }
        UserIncomeInfo userIncomeInfo = new UserIncomeInfo();
        userIncomeInfo.setUserId(user.getUserId());
        userIncomeInfo.setInfoId(StringUtil.createId());
        userIncomeInfo.setFanyongIncome(0.00);
        userIncomeInfo.setShareIncome(0.00);
        userIncomeInfo.setGotTotalRent(0.00);
        userIncomeInfo.setOtherIncome(0.00);
        userIncomeInfo.setPayTotalRent(0.00);
        userIncomeInfo.setWithdrawed(0.00);
        userIncomeInfo.setCreateAt(new Date());
        if(userIncomeInfoMapper.insertSelective(userIncomeInfo) > 0 && userMapper.insertSelective(user) > 0) {
            return true;
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
                System.out.println("改变验证码状态计时器");
                System.out.println(isSend);
                //将所有的验证码的都改状态为失效
                verificationCodeMapperExtra.updateVerificationCode(verificationCodeDTO);
            }
        }, 120000);
        return true;
    }

    @Override
    public Boolean insertVCode(VerificationCodeDTO verificationCodeDTO) {
        verificationCodeDTO.setVerificationCodeId(StringUtil.createId());
        return verificationCodeMapperExtra.insert(verificationCodeDTO) > 0;
    }

    @Override
    public boolean checkVerificationCode(VerificationCodeDTO verificationCodeDTO) {
        //判断信息是否为空
        if (verificationCodeDTO == null)
            return false;
        if (verificationCodeMapperExtra.selectVerificationCode(verificationCodeDTO) != 0) {//如果不为零则验证码未过期（返回的是验证码个数）
            //更改用户的密码
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
        boolean isLike=bCryptPasswordEncoder.matches(oldPWD, checkUser.getUserPsw());
        if (!isLike) {
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
    public User loadUserByUsername(String account) throws UsernameNotFoundException {
        if(!redisUtils.hasKey(account)) {
            User user = userMapperExtra.findUserByAccount(account);
            if(user != null) {
                return user;
            } else {
                throw new UsernameNotFoundException("该用户名不存在");
            }
        } else {
            return (User)redisUtils.get(account);
        }
    }

    @Override
    public String personalCertification(PersonalUserDTO personalUserDTO, User user) {
        VerificationCodeDTO verificationCodeDTO = BeanMapper.map(personalUserDTO, VerificationCodeDTO.class);
        if(verificationCodeMapperExtra.selectVerificationCode(verificationCodeDTO)==0) return "手机验证码校验失败";

        if(0 != user.getUserType()) {
            return "非法操作，禁止使用企业账号进行个人认证";
        }

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

        if("200".equals(code)) {
            boolean isUpdate;
            user.setUpdateAt(new Date());
            user.setIsIdentified(1);
            user.setUserName(personalUserDTO.getUserName());
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

        return 1 == user.getIsIdentified();
    }

    @Override
    public VerificationCodeDTO checkContractVcode(VerificationCodeDTO verificationCodeDTO) {
        return verificationCodeMapperExtra.selectContractVcode(verificationCodeDTO);
    }

}
