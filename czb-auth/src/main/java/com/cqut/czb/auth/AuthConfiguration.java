package com.cqut.czb.auth;

import com.cqut.czb.bn.dao.DaoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:com/cqut/czb/bn/dao/mybatis-config.properties")
@Import(DaoConfiguration.class)
public class AuthConfiguration {
}
