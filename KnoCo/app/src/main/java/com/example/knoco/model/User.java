package com.example.knoco.model;

public class User {

     String Uid ;
     String UserName ;

    public User(String uid, String userName) {
        Uid = uid;
        UserName = userName;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
