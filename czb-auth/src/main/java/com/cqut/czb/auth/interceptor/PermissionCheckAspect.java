package com.cqut.czb.auth.interceptor;

import com.cqut.czb.auth.config.AuthConfig;
import com.cqut.czb.auth.jwt.JwtTool;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 角色权限校验-AOP
 * @author  xu
 * @date 2019-04-29
 */
@Aspect
@Component
public class PermissionCheckAspect {

    @Autowired
    RedisUtils redisUtils;

    //切入点表达式决定了用注解方式的方法切还是针对某个路径下的所有类和方法进行切，方法必须是返回void类型
    @Pointcut("@annotation(com.cqut.czb.auth.interceptor.PermissionCheck)")
    private void permissionCheckCut() {
    };

    //定义了切面的处理逻辑。即方法上加了@PermissionCheck
    @Around("permissionCheckCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        //1.记录日志信息
        Signature signature = pjp.getSignature();
//        String className = pjp.getTarget().getClass().getSimpleName();
//        String methodName = signature.getName();

        //2.角色权限校验
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        if (targetMethod.isAnnotationPresent(PermissionCheck.class)) {
            //获取方法上注解中表明的权限
            PermissionCheck permission = (PermissionCheck) targetMethod.getAnnotation(PermissionCheck.class);
            String role = permission.role();
            if (role != null && role != "") {
                try{
                    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                    String tokenHeader = request.getHeader(AuthConfig.TOKEN_HEADER);
                    String token = tokenHeader.replace(AuthConfig.TOKEN_PREFIX, "");
                    String username = JwtTool.getUsername(token);
                    UserDTO userDTO = (UserDTO) redisUtils.get(username);
                    for(String roleName : userDTO.getRoles()){
                        if(role.contains(roleName)){
                            return pjp.proceed();
                        }
                    }
                    return new JSONResult("身份验证失败");
                }catch (Exception e){
                    e.printStackTrace();
                    return new JSONResult("身份验证失败");
                }
            }
        }
        return null;
    }

}

