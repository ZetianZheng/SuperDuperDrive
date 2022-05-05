package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.constants;

public class Messages {
    private Messages(){ }

    public static final String SIGNUP_ERROR_USER_EXISTS = "Error: The username already exists!";
    public static final String SIGNUP_ERROR = "Error: There was an error signing you up. Please try again.";
    public static final String SIGNUP_SUCCESS = "You successfully signed up! welcome!";
    public static final String FILE_UPLOAD_SUCCESS = "File: %s upload successful!";
    public static final String FILE_OTHER_ERR = "File: %s upload failed because other problem!";
    public static final String FILE_DUPLICATE_ERROR = "File: %s exists, please select a new file!";
    public static final String FILE_SIZE_LIMIT_EXCEED = "File: %s exceed the limit!";
}
