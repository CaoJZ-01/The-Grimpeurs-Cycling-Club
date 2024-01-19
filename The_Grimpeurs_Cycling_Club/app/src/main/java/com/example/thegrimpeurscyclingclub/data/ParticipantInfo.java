package com.example.thegrimpeurscyclingclub.data;

public class ParticipantInfo {
    int age;
    int level;
    String name;
    double pace;
    String userId;
    public ParticipantInfo(String name,int age,int level,double pace,String userId){
        this.name=name;
        this.age= age;
        this.level=level;
        this.pace=pace;
        this.userId=userId;
    }

    public double getPace() {
        return pace;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPace(double pace) {
        this.pace = pace;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
