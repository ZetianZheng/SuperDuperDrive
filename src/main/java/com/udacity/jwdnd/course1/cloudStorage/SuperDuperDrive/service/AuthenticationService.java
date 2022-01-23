package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.service;

import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * 自己的authentication 策略，会被SecurityConfig使用来用作用户认证。
 * 要被 authenticationProvider 使用，就必须实现这个authenticationProvider接口：
 * 实现两个函数：authenticate 和 support
 */
@Service
public class AuthenticationService implements AuthenticationProvider {
    private UserMapper userMapper;
    private HashService hashService;

    /**
     * 使用spring bean 注入userMapper 和hashService
     * separate concerns: 分离HashService.
     * @param userMapper
     * @param hashService
     */
    public AuthenticationService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    /**
     * 实现这个authenticationProvider接口:
     * 覆写authenticate方法，自定义验证策略。
     * spring security 会提供authentication object包含需要验证的用户信息，
     * 并返回一个authentication token 用于判断是否验证成功。
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // 通过MyBaits获得数据库中的用户信息。
        User user = userMapper.getUser(username);
        if(user != null) {
            // 将用户输入的秘钥加密
            // TODO 修改为可以加密并解密的双向方法。
            String encodedSalt = user.getSalt();
            String hashedPassword = hashService.getHashedValue(password, encodedSalt);
            // 相等则返回一个token。
            if (user.getPassword().equals(hashedPassword)) {
                return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
            }
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
