package com.example.thegrimpeurscyclingclub.data.event_relative;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.thegrimpeurscyclingclub.data.users.Participant;

import java.io.Serializable;
import java.util.ArrayList;

public class Event  implements Parcelable {
    private String name;
    private String eventType;
    private String detail;
    private int age;
    private double pace;
    private int level;
    private String additionRequirement;
    private String cyclingClub;
    private ArrayList<Participant> participants;

    private int volume;
    public Event(EventType eventType){
        this.name=null;
        this.eventType=eventType.getType();
        this.detail=null;
        this.pace=0;
        this.age=0;
        this.additionRequirement=null;
        this.volume=0;
        this.cyclingClub=null;
        this.level=0;
        this.participants=new ArrayList<>();


    }

    public Event(String type,String detail,int age,double pace,int level,String additionRequirement,String name, int volume,String cyclingClub){
        this.name=name;
        this.eventType=type;
        this.detail=detail;
        this.pace=pace;
        this.age=age;
        this.additionRequirement=additionRequirement;
        this.volume=volume;
        this.cyclingClub=cyclingClub;
        this.level=level;
        this.participants=new ArrayList<>();

    }

    protected Event(Parcel in) {
        name = in.readString();
        eventType = in.readString();
        detail = in.readString();
        age = in.readInt();
        pace = in.readDouble();
        level = in.readInt();
        additionRequirement = in.readString();
        volume = in.readInt();
        cyclingClub=in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public String getAdditionRequirement() {
        return additionRequirement;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public int getLevel() {
        return level;
    }

    public int getAge() {
        return age;
    }

    public double getPace() {
        return pace;
    }

    public String getEventType() {
        return eventType;
    }

    public void setAdditionRequirement(String additionRequirement) {
        this.additionRequirement = additionRequirement;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(eventType);
        parcel.writeString(detail);
        parcel.writeInt(age);
        parcel.writeDouble(pace);
        parcel.writeInt(level);
        parcel.writeString(additionRequirement);
        parcel.writeInt(volume);
        parcel.writeString(cyclingClub);

    }

    public void setCyclingClub(String cyclingClub) {
        this.cyclingClub = cyclingClub;
    }

    public String getCyclingClub() {
        return cyclingClub;
    }

    public ArrayList<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<Participant> participants) {
        this.participants = participants;
    }
}

