package com.example.thegrimpeurscyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thegrimpeurscyclingclub.data.LoginRepository;
import com.example.thegrimpeurscyclingclub.data.event_relative.Event;
import com.example.thegrimpeurscyclingclub.data.event_relative.EventType;
import com.example.thegrimpeurscyclingclub.data.users.CyclingClub;

import java.util.logging.Level;

public class UpdateEvent extends AppCompatActivity {
    private String userId;
    private Event event;

    private EventType eventType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_event);

        Bundle extra=getIntent().getExtras();
        userId=extra.getString("userid");

        event=getIntent().getParcelableExtra("event");
        TextView volume=findViewById(R.id.txt_event_volume);
        TextView name=findViewById(R.id.txt_name);
        TextView type=findViewById(R.id.txt_event_type);
        TextView detail=findViewById(R.id.txt_event_detail);
        TextView age=findViewById(R.id.txt_event_age);
        TextView level=findViewById(R.id.txt_event_level);
        TextView pace=findViewById(R.id.txt_event_pace);
        TextView additionalRequirement=findViewById(R.id.txt_event_addition);
        type.setText("Event Tye: "+event.getEventType());
        detail.setText("Event Detail:  "+event.getDetail());
        age.setText("Age Limit: "+event.getAge());
        level.setText("Level Limit: "+event.getLevel());
        pace.setText("Pace Limit: "+Double.toString(event.getPace()));
        additionalRequirement.setText("Additional Requirement: "+event.getAdditionRequirement());
        volume.setText("Event Volume: " + event.getVolume());
        name.setText("Event Name: " + event.getName());

        LoginRepository loginRepository=new LoginRepository();
        loginRepository.findEventTypeInfo(event.getEventType(), new Callback() {
            @Override
            public void onFetched(Object obj) {
                eventType=(EventType)obj;
            }

            @Override
            public void onError(Exception e) {

            }
        });

    }
    public void onClickBack(View view){
        Intent backToMain=new Intent(UpdateEvent.this,CyclingClub_Main.class);
        backToMain.putExtra("userid",userId);
        startActivity(backToMain);
    }

    public void onClickUpdateEvent(View view){
        String age_str=((EditText)findViewById(R.id.edit_event_Age)).getText().toString();
        int age_int=0;
        boolean error=false;
        if(age_str.equals("")||age_str.equals("Please Enter For Update Age Or Blank For No Change")){
            age_int=event.getAge();
        }else{
            try{
                age_int=Integer.parseInt(age_str);
            }catch (RuntimeException e){
                Toast.makeText(this, "Please enter a Number for Age",Toast.LENGTH_SHORT).show();
                error=true;
            }
        }



        String detail_str=((EditText)findViewById(R.id.edit_event_detail)).getText().toString();

        String level_str=((EditText)findViewById(R.id.edit_event_Level)).getText().toString();

        int level_int=0;
        if(level_str.equals("")||level_str.equals("Please Enter For Update Level Or Blank For No Change")) {
            level_int = event.getLevel();
        }else {
            try{
                level_int=Integer.parseInt(level_str);
            }catch (RuntimeException e){
                Toast.makeText(this, "Please enter a Number for Level",Toast.LENGTH_SHORT).show();
                error=true;
            }
        }


        String pace_str=((EditText)findViewById(R.id.edit_event_pace)).getText().toString();
        double  pace_double=0;
        if(pace_str.equals("")||pace_str.equals("Please Enter For Update Pace Or Blank For No Change")) {
            pace_double=event.getPace();
        }else{
            try{
                pace_double=Double.parseDouble(pace_str);
            }catch (RuntimeException e){
                Toast.makeText(this, "Please enter a Number for Pace",Toast.LENGTH_SHORT).show();
                error=true;
            }
        }

        String addition_str=((EditText)findViewById(R.id.edit_event_addition)).getText().toString();

        String volume_str=((EditText)findViewById(R.id.edit_event_volume)).getText().toString();
        int volume_int=0;
        if(volume_str.equals("")||volume_str.equals("Please Enter For Update Volume Or Blank For No Change")) {
            volume_int=event.getVolume();
        }else{
            try{
                volume_int=Integer.parseInt(volume_str);
            }catch (RuntimeException e){
                Toast.makeText(this, "Please enter a Number for Volume",Toast.LENGTH_SHORT).show();
                error=true;
            }
        }

        String name_str=((EditText)findViewById(R.id.edit_event_name)).getText().toString();


        boolean valid=true;
        String errorMessage="";
        boolean nameChanged=false;
        String originalName=event.getName();

        if(!error) {
            if (age_int < eventType.getAge()) {
                valid = false;
                errorMessage += "Event Age Can not be less than limit above         \n";
            }
            if (level_int < eventType.getLevel()) {
                valid = false;
                errorMessage += "Event Level Can not be less than limit above         \n";
            }
            if (pace_double < eventType.getPace()) {
                valid = false;
                errorMessage += "Event Pace Can not less than limit above         \n";
            }
            if (volume_int <= 0) {
                valid = false;
                errorMessage += "Event Volume Can not less than or equal 0          \n";
            }
            if (detail_str.equals("") || detail_str.equals("Please Enter For Update Detail Or Blank For No Change")) {
                detail_str = event.getDetail();
            }
            if (name_str.equals("") || name_str.equals("Please Enter For Update Name Or Blank For No Change")) {
                name_str = event.getName();
            }else{
                nameChanged=true;
            }
            if (addition_str.equals("") || addition_str.equals("Please Enter For Update Additional Requirement Or Blank For No Change")) {
                addition_str = event.getAdditionRequirement();
            }
            if (valid) {
                Event event = new Event(eventType);
                event.setVolume(volume_int);
                event.setAge(age_int);
                event.setDetail(detail_str);
                event.setName(name_str);
                event.setAdditionRequirement(addition_str);
                event.setPace(pace_double);
                event.setLevel(level_int);
                event.setCyclingClub(userId);
                CyclingClub cyclingClub = new CyclingClub(userId, "FAKE PASSWORD", "Cycling Club");
                cyclingClub.updateEvent(event,nameChanged,originalName);
                Toast.makeText(this, "Your Event is Updated and Published", Toast.LENGTH_SHORT).show();
                Intent backToMain = new Intent(UpdateEvent.this, CyclingClub_Main.class);
                backToMain.putExtra("userid", userId);
                startActivity(backToMain);
                finish();
            } else {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        }
    }

}