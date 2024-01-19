package com.example.thegrimpeurscyclingclub.data.users;

public class User {
    private String userId;
    private String password;
    private String role;

    public User(String userId,String password,String role){
        this.userId=userId;
        this.password=password;
        this.role=role;
    }
    public String getRole(){
        return this.role;
    }
    public String getPassword(){
        return this.password;
    }
    public String getUserId(){
        return this.userId;
    }
}
