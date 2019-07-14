package com.cqut.czb.auth.serviceImpl;

import com.cqut.czb.auth.jwt.JwtUser;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.dao.mapper.UserMapperExtra;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 该类用于Security调用，获取用户信息
 */
@Service
public class AuthUserServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapperExtra userMapperExtra;
    @Autowired
    private RedisUtils redisUtils;
    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        if(!redisUtils.hasKey(account)) {
            UserDTO user = userMapperExtra.findUserDTOByAccount(account);
            if(user != null) {
                return new JwtUser(user);
            } else {
                throw new UsernameNotFoundException("该用户名不存在");
            }
        } else {
            return new JwtUser((UserDTO)redisUtils.get(account));
        }
    }
}
