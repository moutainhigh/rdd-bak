package com.cqut.czb.auth.interceptor;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionCheck {
    //自定义角色值，如果是多个角色，用逗号分割。
    public String role() default "";
}