package com.example.thegrimpeurscyclingclub.data.users;

import com.example.thegrimpeurscyclingclub.data.event_relative.Event;
import com.example.thegrimpeurscyclingclub.data.event_relative.EventPlayer;

public class Participant extends User{
    private String userId;
    private String password;
    private String role;

    private String name;

    private EventPlayer[] event;

    private int age;

    private double pace;

    private  int level;


    public Participant(String userId,String password,String role){
        super(userId,password,role);

    }
    public Participant accountCreation(String userId,String password,String role){
                Participant participant=new Participant(userId,password,role);
                return participant;
    }
    public String eventDiscovery(){
        return null;
    }
    public EventPlayer eventRegistration(Event event){
        return null;
    }

    public void routeTracking(Event event){

    }


}
