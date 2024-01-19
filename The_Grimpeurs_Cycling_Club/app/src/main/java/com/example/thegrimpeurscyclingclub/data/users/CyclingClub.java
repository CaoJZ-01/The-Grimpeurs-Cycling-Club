package com.example.thegrimpeurscyclingclub.data.users;

import com.example.thegrimpeurscyclingclub.data.Profile;
import com.example.thegrimpeurscyclingclub.data.event_relative.Event;
import com.example.thegrimpeurscyclingclub.data.event_relative.EventType;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CyclingClub extends User{
    private String userId;
    private String password;
    private String role;

    private EventType[] eventType;

    public CyclingClub(String userId,String password,String role){
        super(userId,password,role);
    }



    public void addProfile(Profile profile) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference profileNameReference = database.getReference("profile/");
        profileNameReference.child(profile.getUserID()).setValue(profile);

    }
    public void updateProfile(Profile profile){
        final String noUpdate="N/A";
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference profileLinkReference = database.getReference("profile/" + profile.getUserID() + "/socialMediaLink");
        DatabaseReference profilePhoneReference = database.getReference("profile/" + profile.getUserID() + "/phoneNumber");
        DatabaseReference profileContactReference = database.getReference("profile/" + profile.getUserID() + "/contactName");

        if(!profile.getSocialMediaLink().equals(noUpdate)){
            profileLinkReference.setValue(profile.getSocialMediaLink());
        }

        if(!profile.getPhoneNumber().equals(noUpdate)){
            profilePhoneReference.setValue(profile.getPhoneNumber());
        }
        if(!profile.getContactName().equals(noUpdate)){
            profileContactReference.setValue(profile.getContactName());
        }

    }
    public void createEvent(Event event){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String CyclingClub=event.getCyclingClub()+"/";
        DatabaseReference eventReference = database.getReference("event/"+CyclingClub);
        eventReference.child(event.getName()).setValue(event);
    }
    public void updateEvent(Event event, boolean nameChanged,String originalName){
        if(nameChanged){
            DatabaseReference dR=FirebaseDatabase.getInstance().getReference("event").child(event.getCyclingClub()).child(originalName);
            dR.removeValue();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            String CyclingClub=event.getCyclingClub()+"/";
            DatabaseReference eventReference = database.getReference("event/"+CyclingClub);
            eventReference.child(event.getName()).setValue(event);
        }else{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference eventNameReference = database.getReference("event/" + event.getCyclingClub() + "/" + event.getName() + "/name");
            DatabaseReference eventAgeReference = database.getReference("event/" + event.getCyclingClub() + "/" + event.getName() + "/age");
            DatabaseReference eventLevelReference = database.getReference("event/" + event.getCyclingClub() + "/" + event.getName() + "/level");
            DatabaseReference eventPaceReference = database.getReference("event/" + event.getCyclingClub() + "/" + event.getName() + "/pace");
            DatabaseReference eventVolumeReference = database.getReference("event/" + event.getCyclingClub() + "/" + event.getName() + "/volume");
            DatabaseReference eventDetailReference = database.getReference("event/" + event.getCyclingClub() + "/" + event.getName() + "/detail");
            DatabaseReference eventAdditionReference = database.getReference("event/" + event.getCyclingClub() + "/" + event.getName() + "/additionRequirement");



            eventDetailReference.setValue(event.getDetail());
            eventAdditionReference.setValue(event.getAdditionRequirement());
            eventAgeReference.setValue(event.getAge());
            eventPaceReference.setValue(event.getPace());
            eventLevelReference.setValue(event.getLevel());
            eventNameReference.setValue(event.getName());
            eventVolumeReference.setValue(event.getVolume());

        }



    }

}
