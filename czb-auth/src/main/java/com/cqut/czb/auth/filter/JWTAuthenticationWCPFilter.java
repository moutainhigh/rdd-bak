package com.cqut.czb.auth.filter;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.auth.config.AuthConfig;
import com.cqut.czb.auth.config.Config;
import com.cqut.czb.auth.jwt.JwtTool;
import com.cqut.czb.auth.jwt.JwtUser;
import com.cqut.czb.auth.service.UserDetailService;
import com.cqut.czb.auth.serviceImpl.AuthUserServiceImpl;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.auth.util.SpringUtils;
import com.cqut.czb.bn.dao.mapper.UserMapperExtra;
import com.cqut.czb.bn.entity.dto.WCPLoginBack;
import com.cqut.czb.bn.entity.dto.WCProgramConfig;
import com.cqut.czb.bn.entity.dto.user.PersonalUserDTO;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @Description 该类用于微信小程序登录，登录成功后设置token信息
 * @auther nihao
 * @create 2019-11-22 14:11
 */
public class JWTAuthenticationWCPFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserMapperExtra userMapperExtra;

    public JWTAuthenticationWCPFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        //设置登录接口的api
        super.setFilterProcessesUrl("/auth/loginWCP");
    }

    //登录方法
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        this.initHttpServletRep(response);
        String code = request.getParameter("code");
        String superiorUser = request.getParameter("superiorUser");
        String tokenHeader = request.getHeader(AuthConfig.TOKEN_HEADER);
        //使用Token登录
        if ((code == null || "".equals(code)) && tokenHeader != null && tokenHeader.startsWith(AuthConfig.TOKEN_PREFIX)){
            if(redisUtils == null){
                redisUtils = SpringUtils.getBean(RedisUtils.class);
            }
            String token = tokenHeader.replace(AuthConfig.TOKEN_PREFIX, "");
            if(!tokenHeader.equals(redisUtils.get(JwtTool.getUsername(token) + AuthConfig.TOKEN))) {
                JSONObject result = new JSONObject();
                result.put(AuthConfig.FAILED_REASON, "身份验证已过期，请重新登录");
                result.put(AuthConfig.STATUS, false);
                this.setupResponseWrite(response, result);
            }
            if(userDetailsService == null){
                userDetailsService = SpringUtils.getBean(AuthUserServiceImpl.class);
            }
            String openId = JwtTool.getUsername(token);
            UserDetails userDetails =  userDetailsService.loadUserByUsername(openId);
            String psw = userDetails.getPassword();
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(openId, psw, new ArrayList<>())
            );
        }else if (code != null && !"".equals(code)){
            //置换SessionKey和openId;
            WCPLoginBack wcpLoginBack = this.getWcpLoginBack(code);
            JSONObject result = new JSONObject();
            if(wcpLoginBack == null){
                System.out.println("微信请求失败，请您稍后再试!");
                result.put(AuthConfig.FAILED_REASON, "微信请求失败，请您稍后再试!");
                result.put(AuthConfig.STATUS, false);
                this.setupResponseWrite(response, result);
                return null;
            }else if(wcpLoginBack.getErrmsg() != null && wcpLoginBack.getErrcode() != null){
                System.out.println("微信登录失败，错误码：" + code + "错误信息：" + wcpLoginBack.getErrcode());
                result.put(AuthConfig.FAILED_REASON, "身份验证已过期，请重新登录");
                result.put(AuthConfig.STATUS, false);
                this.setupResponseWrite(response, result);
                return null;
            }
            //判断用户是否存在过
            if(userMapperExtra == null){
                userMapperExtra = SpringUtils.getBean(UserMapperExtra.class);
            }
            User user = userMapperExtra.findUserByAccount(code);
            //首次登录用户，注册用户
            if (user == null){
                PersonalUserDTO personalUserDTO = new PersonalUserDTO();
                personalUserDTO.setUserAccount(wcpLoginBack.getOpenid());
                personalUserDTO.setUserPsw(Config.initPsw);
                personalUserDTO.setSuperiorUser(superiorUser);
                if(userDetailService == null){
                    userDetailService = SpringUtils.getBean(UserDetailService.class);
                }
                userDetailService.registerWCProgramUser(personalUserDTO);
            }
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(wcpLoginBack.getOpenid(), Config.initPsw, new ArrayList<>())
            );
        }
        return null;
    }

    //登录成功后设置token
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 调用getPrincipal()方法会返回一个实现了`UserDetails`接口的对象
        // 所以就是JwtUser啦
        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
        String token = JwtTool.createToken(jwtUser.getAccount(), false);
        if(redisUtils == null){
            redisUtils = SpringUtils.getBean(RedisUtils.class);
        }
        UserDTO user = jwtUser.getUser();
        redisUtils.put(jwtUser.getAccount(), user);
        if(redisUtils.hasKey(jwtUser.getAccount()+AuthConfig.TOKEN)) {
            redisUtils.remove(jwtUser.getAccount()+AuthConfig.TOKEN);
        }
        redisUtils.put(jwtUser.getAccount()+AuthConfig.TOKEN, AuthConfig.TOKEN_PREFIX + token);
        // 返回创建成功的token
        this.initHttpServletRep(response);
        JSONObject result = new JSONObject();
        result.put(AuthConfig.TOKEN, AuthConfig.TOKEN_PREFIX + token);
        result.put(AuthConfig.STATUS, true);
        response.getWriter().write(result.toJSONString());
    }

    //失败返回
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        this.initHttpServletRep(response);
        JSONObject result = new JSONObject();
        result.put(AuthConfig.FAILED_REASON, failed.getMessage());
        result.put(AuthConfig.STATUS, false);
        response.getWriter().write(result.toJSONString());
    }

    private void setupResponseWrite(HttpServletResponse httpServletResponse, JSONObject jsonObject){
        try {
            httpServletResponse.getWriter().write(jsonObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HttpServletResponse initHttpServletRep(HttpServletResponse httpServletResponse){
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setHeader("Content-Type", "application/json;charset=utf-8");
        return httpServletResponse;
    }

    private WCPLoginBack getWcpLoginBack(String code){
        //通过OkHttp访问微信后台获取sessionKey和openId
        WCPLoginBack wcpLoginBack;
        WCProgramConfig config = new WCProgramConfig();
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        Request req = new Request.Builder()
                .get()
                .url("https://api.weixin.qq.com/sns/jscode2session?appid=" + config.app_id + "&secret=" + config.app_secret + "&js_code=" + code + "&grant_type=" + config.grant_type )
                .build();
        Call call = client.newCall(req);
        try {
            Response res = call.execute();
            wcpLoginBack = gson.fromJson(res.body().charStream(), WCPLoginBack.class);
            return wcpLoginBack;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}