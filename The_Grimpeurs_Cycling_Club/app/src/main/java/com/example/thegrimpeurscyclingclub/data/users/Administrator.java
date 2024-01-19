package com.example.thegrimpeurscyclingclub.data.users;

import com.example.thegrimpeurscyclingclub.data.event_relative.EventType;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Administrator extends User{
    private String userId;
    private String password;
    private String role;
    public Administrator(String userId,String password,String role){
        super(userId,password,role);
    }

    public void addEventType(EventType eventType) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference eventTypeNameReference = database.getReference("eventType/");
        eventTypeNameReference.child(eventType.getType()).setValue(eventType);

    }
    public void updateEventType(EventType eventType){
        final String noUpdate="N/A";
        final int noUpdateInt=999;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference eventTypeDetailReference = database.getReference("eventType/" + eventType.getType() + "/detail");
        DatabaseReference eventTypeAgeReference = database.getReference("eventType/" + eventType.getType() + "/age");
        DatabaseReference eventTypePaceReference = database.getReference("eventType/" + eventType.getType() + "/pace");
        DatabaseReference eventTypeLevelReference = database.getReference("eventType/" + eventType.getType() + "/level");
        DatabaseReference eventTypeAdditionRequirementReference = database.getReference("eventType/" + eventType.getType() + "/additionRequirement");

        if(!eventType.getDetail().equals(noUpdate)){
            eventTypeDetailReference.setValue(eventType.getDetail());
        }
        if(!eventType.getAdditionRequirement().equals(noUpdate)){
            eventTypeAdditionRequirementReference.setValue(eventType.getAdditionRequirement());
        }
        if(eventType.getAge()!=noUpdateInt){
            eventTypeAgeReference.setValue(eventType.getAge());
        }
        if(eventType.getPace()!=noUpdateInt){
            eventTypePaceReference.setValue(eventType.getPace());
        }
        if(eventType.getLevel()!=noUpdateInt){
            eventTypeLevelReference.setValue(eventType.getLevel());
        }
    }

}
