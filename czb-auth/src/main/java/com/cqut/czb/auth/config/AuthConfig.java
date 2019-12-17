package com.cqut.czb.auth.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String SECRET = "cqut.czb";
    public static final String ISS = "echisan";

    // 过期时间是3600秒，既是15个天
    public static final long EXPIRATION = 3600*24*15L;

    // 选择了记住我之后的过期时间为15天
    public static final long EXPIRATION_REMEMBER = 3600*24*15L;

    public static final String STATUS = "status";
    public static final String TOKEN = "token";
    public static final String SESSIONKEY = "sessionKey";
    public static final String FAILED_REASON = "failedReason";
}
