package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.service;

import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.controller.CredentialController;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.Credential;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.Note;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialService {
    private Logger logger = LoggerFactory.getLogger(CredentialService.class);

    private final UserMapper userMapper;
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(UserMapper userMapper, CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.userMapper = userMapper;
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public Credential getCredentialById(Integer credentialId) {
        return credentialMapper.getCredential(credentialId);
    }

    public List<Credential> getAllCredentials (Integer userId) {
        List<Credential> credentialList = credentialMapper.getCredentials(userId);
        /**
         * References:
         * 1. [(57条消息) Java 8 Streams map()_前往JAVA架构的路上-CSDN博客_java8 map()](https://blog.csdn.net/u014042066/article/details/76372116)
         * 2. [Stream In Java - GeeksforGeeks](https://www.geeksforgeeks.org/stream-in-java/?ref=lbp)
         */
        List<Credential> rtnCredentialList = credentialList.stream().map(
                // one object convert to another object, see reference 1.
                // get decryptedPassword and reply to user.
                credential-> {
                    String encryptedPassword = credential.getPassword();
                    String key = credential.getKey();
                    String decryptedPassword = encryptionService.decryptValue(encryptedPassword, key);

                    credential.setDecryptedPassword(decryptedPassword);
                    return credential;
                }
        ).collect(Collectors.toList());
        return rtnCredentialList;
    }

    public void deleteCredential(Integer credentialId) {
        credentialMapper.deleteCredentialById(credentialId);
    }

    public int addCredential(CredentialForm credentialForm, Integer userId) {
        String url = credentialForm.getUrl();
        String userName = credentialForm.getUserName();
        String key = getKey();
        String password = encryptionService.encryptValue(credentialForm.getPassword(), key);
        Credential credential = new Credential(null, url, userName, key, password, userId);
        this.logger.info(url + " " + userName + " " + password);
        return credentialMapper.addCredential(credential);
    }

    public void editCredential(Integer credentialId, CredentialForm credentialForm) {
        String key = getKey();
        String password = encryptionService.encryptValue(credentialForm.getPassword(), key);
        credentialMapper.editNote(credentialId, credentialForm.getUrl(), credentialForm.getUserName(), key, password);
    }

    public String decodePassword(String password, String key) {
        return encryptionService.decryptValue(password, key);
    }

    public boolean isCredBelongToUser(Integer credId, Integer userId) {
        Credential credential = credentialMapper.getCredential(credId);

        return credential.getUserid().equals(userId);
    }

    public String getKey() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String key = Base64.getEncoder().encodeToString(salt);
        return key;
    }
}
