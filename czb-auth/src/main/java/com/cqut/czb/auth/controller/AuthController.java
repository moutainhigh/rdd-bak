package com.cqut.czb.auth.controller;

import com.cqut.czb.auth.config.AuthConfig;
import com.cqut.czb.auth.service.UserDetailService;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.appCaptchaConfig.VerificationCodeDTO;
import com.cqut.czb.bn.entity.dto.user.EnterpriseUserDTO;
import com.cqut.czb.bn.entity.dto.user.PersonalUserDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * 该类是注册的api接口，密码使用BCryptPasswordEncoder加密
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    UserDetailService userDetailService;

    @Autowired
    RedisUtils redisUtils;

    /**
     *  个人用户注册
     * */
    @PostMapping("/register")
    public JSONResult registerPersonalUser(@Validated @RequestBody PersonalUserDTO personalUserDTO){
        String result = userDetailService.registerPersonalUser(personalUserDTO);
        JSONResult jsonResult = new JSONResult();
        if(!result.equals("true")) {
            jsonResult.setCode(ResponseCodeConstants.FAILURE);
            jsonResult.setMessage(result);
            jsonResult.setData(false);
            return jsonResult;
        } else {
            jsonResult.setCode(ResponseCodeConstants.SUCCESS);
            jsonResult.setMessage(result);
            jsonResult.setData(true);
            return jsonResult;
        }
    }

    /**
     *  企业用户注册
     * */
    @PostMapping("/enterpriseRegister")
    public JSONResult registerEnterpriseUser(@Validated @RequestBody EnterpriseUserDTO enterpriseUserDTO) {
        String result = userDetailService.registerEnterpriseUser(enterpriseUserDTO);
        JSONResult jsonResult = new JSONResult();
        if(!result.equals("true")) {
            jsonResult.setCode(ResponseCodeConstants.FAILURE);
            jsonResult.setMessage(result);
            jsonResult.setData(false);
            return jsonResult;
        } else {
            jsonResult.setCode(ResponseCodeConstants.SUCCESS);
            jsonResult.setMessage(result);
            jsonResult.setData(true);
            return jsonResult;
        }
    }

    @PostMapping("/checkAccount")
    public JSONResult checkAccount(@Validated @RequestBody User user){
        return  new JSONResult(userDetailService.checkAccount(user));
    }

    /**
     * 检测是否已经注册
     * author：陈德强
     * @param user
     * @return
     */
    @PostMapping("/checkIsRegistered")
    public JSONResult checkIsRegistered(@Validated @RequestBody User user){
        if(user==null||user.getUserAccount()==null)
            return new JSONResult(false);
        //判断此电话号码是否注册
        boolean isRegistered=userDetailService.checkAccount(user);
        if (isRegistered==false){
            return new JSONResult(false);
        }else {
            return new JSONResult(true);
        }
    }



    /**
     * 忘记密码第一个接口：发送验证码并存入验证码
     * author：陈德强
     * @param verificationCodeDTO
     * @return
     */
    @PostMapping("/sendVerificationCode")
    public  JSONResult sendtVerificationCode(@Validated  VerificationCodeDTO verificationCodeDTO){
        //判断电话号码是否为空
        if(verificationCodeDTO==null||verificationCodeDTO.getUserAccount()==null){
            return new JSONResult(false);
        }
        boolean sendVerificationCode=userDetailService.insertVerificationCode(verificationCodeDTO.getUserAccount());
        if(sendVerificationCode) {
            return new JSONResult(true);
        } else {
            return new JSONResult(false);
        }
    }

    /**
     *忘记密码第二个接口：检查验证码
     * author：陈德强
     * @param input
     * @return
     */
    @RequestMapping(value = "/checkVerificationCode",method = RequestMethod.POST)
    public  JSONResult checkVerificationCode(@RequestBody VerificationCodeDTO input){
        //判断验证码是否为空
        if(input==null){
            System.out.println("为空");
            return new JSONResult(false);
        }
        System.out.println(input.getContent());
        System.out.println(input.getUserAccount());
        System.out.println(input.getUserPsw());
        VerificationCodeDTO verificationCodeDTO=new VerificationCodeDTO(input.getUserAccount(),input.getContent());
        verificationCodeDTO.setUserPsw(input.getUserPsw());
        boolean checkVerificationCode=userDetailService.checkVerificationCode(verificationCodeDTO);
        if(checkVerificationCode) {
            return new JSONResult(true);
        } else {
            return new JSONResult(false);
        }
    }

    /**
     * * 修改密码——个人中心
     *author:陈德强
     * @param principal
     * @param verificationCodeDTO
     * @return
     */
    @RequestMapping(value = "/changePWD",method = RequestMethod.POST)
    public  JSONResult changePWD(Principal principal,@RequestBody VerificationCodeDTO verificationCodeDTO) {
        if(principal==null||verificationCodeDTO==null){
            return new JSONResult(false);
        }
        User user = (User)redisUtils.get(principal.getName());
        String oldPWD=verificationCodeDTO.getOldPsw();
        System.out.println(oldPWD);
        String newPWD=verificationCodeDTO.getNewPsw();
        System.out.println(newPWD);
        boolean ischange=userDetailService.changePWD(user,oldPWD,newPWD);
        if(ischange) {
//            return new JSONResult(ResponseCodeConstants.SUCCESS, "修改成功");
            System.out.println("修改成功");
            redisUtils.remove(user.getUserAccount()+ AuthConfig.TOKEN);
            redisUtils.remove(user.getUserAccount());
            System.out.println("缓存以清除");
            return new JSONResult(true);
        } else {
//            return new JSONResult(ResponseCodeConstants.FAILURE, "修改失败");
            return new JSONResult(false);
        }
    }

    @RequestMapping(value = "/personalCertification",method = RequestMethod.POST)
    public  JSONResult personalCertification(@Validated @RequestBody PersonalUserDTO personalUserDTO, Principal principal) {
        User user = (User)redisUtils.get(principal.getName());
        String result = userDetailService.personalCertification(personalUserDTO, user);
        JSONResult jsonResult = new JSONResult();
        if(!result.equals("true")) {
            jsonResult.setCode(ResponseCodeConstants.FAILURE);
            jsonResult.setData(false);
            jsonResult.setMessage(result);
            return jsonResult;
        } else {
            jsonResult.setCode(ResponseCodeConstants.SUCCESS);
            jsonResult.setMessage(result);
            jsonResult.setData(true);
            return jsonResult;
        }
    }

    @RequestMapping(value = "/isCertification",method = RequestMethod.POST)
    public  JSONResult isCertification(Principal principal) {
        User user = (User)redisUtils.get(principal.getName());
        boolean result = userDetailService.isCertification(user);
        JSONResult jsonResult = new JSONResult();
        if(!result) {
            jsonResult.setCode(ResponseCodeConstants.FAILURE);
            jsonResult.setMessage(result +"");
            jsonResult.setData(false);
            return jsonResult;
        } else {
            jsonResult.setCode(ResponseCodeConstants.SUCCESS);
            jsonResult.setMessage(result + "");
            jsonResult.setData(true);
            return jsonResult;
        }
    }


}

