package com.cqut.czb.auth.config;

import com.cqut.czb.auth.filter.JWTAuthenticationFilter;
import com.cqut.czb.auth.filter.JWTAuthorizationFilter;
import com.cqut.czb.auth.provider.DaoAuthenticationProvider;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
/**
 * 杨强
 * 2018/10/10
 * 该类是Security的总体配置，设置拦截器，和要拦截api接口
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    // 因为UserDetailsService的实现类实在太多啦，这里设置一下我们要注入的实现类
    @Qualifier("authUserServiceImpl")
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider bean = new DaoAuthenticationProvider();
        bean.setHideUserNotFoundExceptions(false);
        bean.setUserDetailsService(this.userDetailsService);
        bean.setPasswordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                try {
                    return bCryptPasswordEncoder().encode(rawPassword.toString());
                } catch (Exception e) {
                    throw new BadCredentialsException("调用加密算法对密码进行加密时异常", e);
                }
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                if (StringUtil.isBlank(rawPassword)) {
                    throw new BadCredentialsException("密码为空，请重新输入密码");
                } else if (StringUtil.isBlank(encodedPassword)) {
                    throw new BadCredentialsException("系统中密码密文是空白字符串，请联系客服");
                } else {
                    boolean isMatches;
                    if(encodedPassword.equals(rawPassword)) {
                        isMatches = true;
                    } else {
                        isMatches = bCryptPasswordEncoder().matches(rawPassword, encodedPassword);
                    }
                    if(isMatches) {
                        return isMatches;
                    } else {
                        throw new BadCredentialsException("密码不匹配，请重新输入密码");
                    }
                }
            }
        });

        return bean;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //设置使用获取用户实体信息的服务类，和使用的加密方式
        auth.authenticationProvider(daoAuthenticationProvider());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                // 设置需要身份认证的接口
//                .antMatchers("/api/**").authenticated()
                // 其余放行，可以不设置
                .anyRequest().permitAll()
                .and()
                //设置登录拦截器
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                //设置接口身份认证拦截器
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}



