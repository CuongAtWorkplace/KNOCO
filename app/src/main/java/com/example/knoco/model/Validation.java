package com.example.knoco.model;

import java.util.regex.Pattern;

public class Validation {

    public static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    public static final Pattern PASS_PATTERN =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$");

    public int CheckEmail(String email) {
        if (email.isEmpty()) {
            return -1;
        } else if (!EMAIL_PATTERN.matcher(email).matches()) {
            return 0;
        } else {
            return 1;
        }
    }
    public int CheckPassword(String pass){
            if(pass.isEmpty()){
                return -1;
            }
            else if(!PASS_PATTERN.matcher(pass).matches()){
                return 0 ;
            }else {
                return 1 ;
            }
        }
}
