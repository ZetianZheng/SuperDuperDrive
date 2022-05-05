package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.constants;

public class Messages {
    private Messages(){ }
    public static final String RESULT = "result";
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    public static final String MESSAGE = "message";
    public static final String NOTES = "notes";


    public static final String SIGNUP_ERROR_USER_EXISTS = "Error: The username already exists!";
    public static final String SIGNUP_ERROR = "Error: There was an error signing you up. Please try again.";
    public static final String SIGNUP_SUCCESS = "You successfully signed up! welcome!";
    public static final String FILE_UPLOAD_SUCCESS = "File: %s upload successful!";
    public static final String FILE_DELETE_SUCCESS = "File: %s delete successful!";
    public static final String NOTE_UPLOAD_SUCCESS = "note: %s upload successful!";
    public static final String NOTE_UPLOAD_FAIL = "Error, note: %s upload failed!";
    public static final String FILE_OTHER_ERR = "Error, File: %s upload failed because other problem!";
    public static final String NOTE_OTHER_ERR = "Error, Note: %s upload failed because other problem!";
    public static final String FILE_DUPLICATE_ERROR = "File: %s exists, please select a new file!";
    public static final String FILE_DELETE_ERROR = "File: %s can not be deleted, please try again!";
    public static final String FILE_NOT_SELECTED_ERR = "File: %s is empty, not been selected!";
    public static final String NOTE_EXISTS_ERROR = "Error, Note: %s exists, please creat a new note!";
    public static final String NOTE_BELONGS_ERROR = "Error, Note: %s exists, but this note not belong to you!";
    public static final String FILE_SIZE_LIMIT_EXCEED = "Error, File: %s exceed the limit!";
}
