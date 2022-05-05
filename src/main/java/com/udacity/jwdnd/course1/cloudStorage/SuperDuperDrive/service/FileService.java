package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.service;

import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.File;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class FileService {
     // include user mapper and file mapper
    private final FileMapper fileMapper;
    private final UserMapper userMapper;

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    public int addFile(MultipartFile newFile, Integer userId) throws IOException {
//        User user =  userMapper.getUserByName(username);
//        Integer userId = user.getUserId();

        byte[] fileData = newFile.getBytes();
        String contentType = newFile.getContentType();
        String fileSize = String.valueOf(newFile.getSize());
        String fileName = newFile.getOriginalFilename();

        return this.fileMapper.addNewFile(new File(null, fileName, contentType, fileSize, userId, fileData));
    }

    public boolean noDuplicateFileName(String filename, Integer userId) {
        String[] allFiles = getALlFiles(userId);

        for (String file:allFiles) {
            if (file.equals(filename)) {
                return false;
            }
        }

        return true;
    }

    public int deleteFile(String filename, Integer userId) {
        return fileMapper.deleteFileByName(filename, userId);
    }
    public File getFile(String filename) {
        return fileMapper.getFile(filename);
    }

    public String[] getALlFiles(Integer userId) {
        return fileMapper.getFileListByUserId(userId);
    }
}
