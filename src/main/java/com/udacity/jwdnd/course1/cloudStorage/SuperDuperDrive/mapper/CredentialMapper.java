package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.mapper;

import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.Credential;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credential getCredential(Integer credentialId);

    // TODO : keyProperty 是 以POJO为主还是以mysql中的index为主？
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) " +
            "VALUES(#{url}, #{userName}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int addCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    int deleteCredentialById(Integer credentialId);

    // Mybaits must return array?
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credential> getCredentials(Integer userId);

    // change the content
    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key=#{key}, password=#{password} WHERE credentialid = #{credentialId}")
    void editNote(Integer credentialId, String url, String username, String key, String password);
}
