package com.fitcom.fitcom_restapi.utils;

import java.util.regex.Pattern;

public class ApplicationConstants {

    // Controller endpoints
    public static final String API_V1_ENDPOINT = "/api/v1";
    public static final String USERCONTROLLER_ENDPOINT = "/api/v1/users";
    public static final String ROLECONTROLLER_ENDPOINT = "/api/v1/roles";
    public static final String GARMENTCONTROLLER_ENDPOINT = "/api/v1/garments";

    // Regex
    public static final String EMAIL_REGEX = "^(?!.*@.*@)(?!.*\\.\\.)(^[a-zA-Z0-9._%+-]{1,64}@)([a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(?:\\.[a-zA-Z]{2,256})?)$";
    public static final String USERNAME_REGEX = "^[A-Za-z]\\w{1,256}$";
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&*!])[A-Za-z\\d@#$%^&*!]{8,}$";
    public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    public static final Pattern USERNAME_PATTERN = Pattern.compile(USERNAME_REGEX);
    public static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

}
