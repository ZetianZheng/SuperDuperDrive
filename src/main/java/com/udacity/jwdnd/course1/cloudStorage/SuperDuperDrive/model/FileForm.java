package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * form object which created by user
 */
public class FileForm {
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
