package com.example.thegrimpeurscyclingclub.data;

import androidx.annotation.NonNull;

import com.example.thegrimpeurscyclingclub.Callback;
import com.example.thegrimpeurscyclingclub.data.event_relative.Event;
import com.example.thegrimpeurscyclingclub.data.event_relative.EventType;
import com.example.thegrimpeurscyclingclub.data.users.Administrator;
import com.example.thegrimpeurscyclingclub.data.users.CyclingClub;
import com.example.thegrimpeurscyclingclub.data.users.Participant;
import com.example.thegrimpeurscyclingclub.data.users.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class LoginRepository {
    private LoginDataSource dataSource;
    public LoginRepository() {
    }

    public LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void register() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference newUserRoleRef = database.getReference("users/" + dataSource.getUserId() + "/role");
        DatabaseReference newUserPasswordRef = database.getReference("users/" + dataSource.getUserId() + "/password");
        DatabaseReference newUserUseridRef = database.getReference("users/" + dataSource.getUserId() + "/userID");
        DatabaseReference newUserEmailRef = database.getReference("users/" + dataSource.getUserId() + "/email");

        newUserRoleRef.setValue(dataSource.getRole());
        newUserPasswordRef.setValue(dataSource.getPassword());
        newUserUseridRef.setValue(dataSource.getUserId());
        newUserEmailRef.setValue(dataSource.getEmail());

    }

    public void findUserinfo(String userId, Callback callback ) {
        ArrayList list = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userIdRef = databaseReference.child("users").child(userId);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        list.add(snapshot1.getValue().toString());
                    }
                    User user;
                    switch ((String) list.get(2)) {
                        case "Participant":
                            user = new Participant((String) list.get(3), (String) list.get(1), (String) list.get(2));
                            break;
                        case "administrator":
                            user = new Administrator((String) list.get(3), (String) list.get(1), (String) list.get(2));
                            break;
                        case "Cycling Club":
                            user = new CyclingClub((String) list.get(3), (String) list.get(1), (String) list.get(2));
                            break;
                        default:
                            throw new RuntimeException();
                    }
                    callback.onFetched(user);
                } else {
                    callback.onError(new NullPointerException("User not found"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.toException());
            }
        };


        userIdRef.addListenerForSingleValueEvent(eventListener);
    }
    public void findEventExist(String eventType, Callback callback ) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference eventTypeRef = databaseReference.child("eventType").child(eventType);
        ValueEventListener eventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean exist=false;
                if (snapshot.exists()){
                    exist=true;
                }
                callback.onFetched(exist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        eventTypeRef.addListenerForSingleValueEvent(eventListener);
    }
    public void findCyclingClubProfile(String userId,Callback callback){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference eventTypeRef = databaseReference.child("profile").child(userId);
        ValueEventListener eventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean exist=false;
                if (snapshot.exists()){
                    exist=true;
                }
                callback.onFetched(exist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        eventTypeRef.addListenerForSingleValueEvent(eventListener);

    }
    public void findEventTypeInfo(String eventType, Callback callback ) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userIdRef = databaseReference.child("eventType").child(eventType);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String type=null;
                    String detail=null;
                    int age=0;
                    double pace=0;
                    int level=0;
                    String additionRequirement=null;
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        switch (snapshot1.getKey().toString()){
                            case "additionRequirement":
                                additionRequirement=snapshot1.getValue().toString();
                                break;
                            case "age":
                                age=Integer.parseInt(snapshot1.getValue().toString());
                                break;
                            case "detail":
                                detail=snapshot1.getValue().toString();
                                break;
                            case "level":
                                level=Integer.parseInt(snapshot1.getValue().toString());
                                break;
                            case "pace":
                                pace=Double.parseDouble(snapshot1.getValue().toString());
                                break;
                            case "type":
                                type=snapshot1.getValue().toString();;
                                break;
                        }
                    }

                    EventType eventType=new EventType(type,detail,age,pace,level,additionRequirement);
                    callback.onFetched(eventType);
                } else {
                    callback.onError(new NullPointerException("EventType not found"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.toException());
            }
        };


        userIdRef.addListenerForSingleValueEvent(eventListener);
    }
    public void findEventInfo(String userid, String event, Callback callback ) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userIdRef = databaseReference.child("event").child(userid).child(event);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String type=null;
                    String detail=null;
                    int age=0;
                    double pace=0;
                    int level=0;
                    String additionRequirement=null;
                    String name=null;
                    int volume=0;
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        switch (snapshot1.getKey().toString()){
                            case "additionRequirement":
                                additionRequirement=snapshot1.getValue().toString();
                                break;
                            case "age":
                                age=Integer.parseInt(snapshot1.getValue().toString());
                                break;
                            case "detail":
                                detail=snapshot1.getValue().toString();
                                break;
                            case "level":
                                level=Integer.parseInt(snapshot1.getValue().toString());
                                break;
                            case "pace":
                                pace=Double.parseDouble(snapshot1.getValue().toString());
                                break;
                            case "eventType":
                                type=snapshot1.getValue().toString();;
                                break;
                            case "name":
                                name=snapshot1.getValue().toString();;
                                break;
                            case "volume":
                                volume=Integer.parseInt(snapshot1.getValue().toString());
                                break;

                        }
                    }

                    Event event=new Event(type,detail,age,pace,level,additionRequirement,name,volume,userid);
                    callback.onFetched(event);
                } else {
                    callback.onError(new NullPointerException("Event not found"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.toException());
            }
        };


        userIdRef.addListenerForSingleValueEvent(eventListener);
    }
    public void searchEventByType(String type, Callback callback){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference totalEventRef = databaseReference.child("event");
        ValueEventListener valueEventListener= new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> events=new ArrayList<String>();
                for (DataSnapshot club:snapshot.getChildren()){ //get to clubs
                    for (DataSnapshot event:club.getChildren()){ //get to events
                        if (event.child("eventType").getValue().equals(type)){
                            events.add(event.child("name").getValue()+"/space/"+event.child("cyclingClub").getValue());
                        }
                    }
                }
                callback.onFetched(events);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        totalEventRef.addListenerForSingleValueEvent(valueEventListener);

    }
    public void joinEvent(ParticipantInfo info,Event event){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference infoRef = databaseReference.child("event").child(event.getCyclingClub()).child(event.getName());
        infoRef.child("Participant").child(info.getName()).setValue(info);
    }
    public void searchEventByName(String name,Callback callback){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference totalEventRef = databaseReference.child("event");

        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> events=new ArrayList<String>();
                for (DataSnapshot club:snapshot.getChildren()){ //get to clubs
                    for (DataSnapshot event:club.getChildren()){ //get to events
                        if (event.child("name").getValue().equals(name)){
                            events.add(event.child("name").getValue()+"/space/"+event.child("cyclingClub").getValue());
                        }
                    }
                }
                callback.onFetched(events);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        totalEventRef.addListenerForSingleValueEvent(valueEventListener);
    }
    public void searchEventByClub(String club,Callback callback){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference clubRef = databaseReference.child("event").child(club);

        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> events=new ArrayList<String>();
                for (DataSnapshot event:snapshot.getChildren()){
                    events.add(event.child("name").getValue()+"/space/"+event.child("cyclingClub").getValue());

                }
                callback.onFetched(events);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        clubRef.addListenerForSingleValueEvent(valueEventListener);
    }
    public void rateAndCommentClub(String userId, int rate,String club,String comment){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference rateRef=database.getReference("rate/"+club+"/"+userId+"/"+"rate");
        DatabaseReference commentRef=database.getReference("rate/"+club+"/"+userId+"/"+"comment");
        rateRef.setValue(rate);
        commentRef.setValue(comment);

    }

}
//
