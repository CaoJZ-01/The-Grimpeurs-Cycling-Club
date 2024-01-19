package com.example.thegrimpeurscyclingclub.data.event_relative;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class EventType implements Parcelable {
    private String type;
    private String detail;
    private int age;
    private double pace;
    private int level;
    private String additionRequirement;

    public EventType(String type, String detail, int age, double pace, int level, String additionRequirement){
        this.detail=detail;
        this.age=age;
        this.pace=pace;
        this.level=level;
        this.type=type;
        this.additionRequirement=additionRequirement;
    }

    protected EventType(Parcel in) {
        type = in.readString();
        detail = in.readString();
        age = in.readInt();
        pace = in.readDouble();
        level = in.readInt();
        additionRequirement = in.readString();
    }

    public static final Creator<EventType> CREATOR = new Creator<EventType>() {
        @Override
        public EventType createFromParcel(Parcel in) {
            return new EventType(in);
        }

        @Override
        public EventType[] newArray(int size) {
            return new EventType[size];
        }
    };

    public double getPace() {
        return pace;
    }

    public int getAge() {
        return age;
    }

    public int getLevel() {
        return level;
    }

    public String getDetail() {
        return detail;
    }

    public String getType() {
        return type;
    }

    public String getAdditionRequirement() {
        return additionRequirement;
    }

    public void setAdditionRequirement(String additionRequirement) {
        this.additionRequirement = additionRequirement;
    }

    public void setPace(double pace) {
        this.pace = pace;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(type);
        parcel.writeString(detail);
        parcel.writeInt(age);
        parcel.writeDouble(pace);
        parcel.writeInt(level);
        parcel.writeString(additionRequirement);
    }
}
