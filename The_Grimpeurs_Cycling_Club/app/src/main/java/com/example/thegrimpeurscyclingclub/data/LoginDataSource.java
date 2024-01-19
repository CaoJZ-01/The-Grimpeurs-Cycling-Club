package com.example.thegrimpeurscyclingclub.data;

public class LoginDataSource {

    private String userId;
    private String password;
    private String role;
    private String email;

    public LoginDataSource(String userId,String password, String role,String email){
        this.userId=userId;
        this.password=password;
        this.role=role;
        this.email=email;
    }
    public String getUserId(){
        return userId;
    }
    public String getPassword(){
        return password;
    }
    public String getRole(){
        return role;
    }
    public String getEmail(){
        return email;
    }
}
