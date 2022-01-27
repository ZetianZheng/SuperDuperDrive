package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.mapper;

import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.File;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    File getFile(String filename);

    @Insert("INSERT INTO FILES (filename, contentType, filesize, userid, fileData) VALUES(#{filename}, #{contentType}, #{filesize}, #{userid}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int addNewFile(File file);

    @Delete("DELETE FROM FILES WHERE filename = #{filename} AND userid = #{userId}")
    int deleteFileByName(String filename, Integer userId);

    // get all filename list by userID
    @Select("SELECT filename FROM FILES WHERE userId=#{userId}")
    String[] getFileListByUserId(Integer userId);
}
