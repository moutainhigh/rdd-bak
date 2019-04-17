package com.cqut.czb.bn.dao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:com/cqut/czb/bn/dao/mybatis-config.properties")
@MapperScan("com.cqut.czb.bn.dao.mapper")
public class DaoConfiguration {

}
