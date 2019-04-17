package com.cqut.czb.global;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore({FlywayAutoConfiguration.class})
@ImportAutoConfiguration({FlywayAutoConfiguration.class, DataSourceAutoConfiguration.class})
public class GlobalConfig {
}
