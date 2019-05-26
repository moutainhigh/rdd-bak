package com.cqut.czb.auth.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String SECRET = "cqut.czb";
    public static final String ISS = "echisan";

    // 过期时间是3600秒，既是1个小时
    public static final long EXPIRATION = 604800L;

    // 选择了记住我之后的过期时间为7天
    public static final long EXPIRATION_REMEMBER = 604800L;

    public static final String STATUS = "status";
    public static final String TOKEN = "token";
    public static final String FAILED_REASON = "failedReason";
}
