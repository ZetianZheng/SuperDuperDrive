package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.service;

import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

/** 4
 * &1 于user mapper的关系：
 * the user service layer surround the mapper layer
 *
 * createUser method:
 * 1. take an object user as an argument, 这个user是由form表单提交的
 * 2. 调用hashService 和 生成的salt来 生成hash之后的密码，最后再调用mapper将这个salt和密码写入数据库。insert the credential to the user database
 */
@Service
public class UserService {
    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    // return true if this user can be created(not exists), false if not.
    public boolean isUserNameAvailable(String username) {
        return userMapper.getUserByName(username) == null;
    }

    // create user through POJO form
    public int createUser(User user) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        return userMapper.insert(new User(null, user.getUsername(),encodedSalt,hashedPassword, user.getFirstName(), user.getLastName()));
    }

    // get user by user name
    public User getUser(String username) {
        return userMapper.getUserByName(username);
    }
}
