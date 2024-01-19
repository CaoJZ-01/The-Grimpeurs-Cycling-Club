package com.example.thegrimpeurscyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thegrimpeurscyclingclub.data.event_relative.Event;
import com.example.thegrimpeurscyclingclub.data.event_relative.EventType;
import com.example.thegrimpeurscyclingclub.data.users.CyclingClub;

public class CreateEvent extends AppCompatActivity {
    private String userId;
    private EventType eventType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createvent);

        Bundle extra=getIntent().getExtras();
        userId=extra.getString("userid");

        eventType=getIntent().getParcelableExtra("eventType");
        TextView type=findViewById(R.id.txt_type);
        TextView detail=findViewById(R.id.txt_detail);
        TextView age=findViewById(R.id.txt_age);
        TextView level=findViewById(R.id.txt_level);
        TextView pace=findViewById(R.id.txt_pace);
        TextView additionalRequirement=findViewById(R.id.txt_additionalRequirment);
        type.setText("Event Tye: "+eventType.getType());
        detail.setText("Event Type Detail:  "+eventType.getDetail());
        age.setText("Age Limit: "+eventType.getAge());
        level.setText("Level Limit: "+eventType.getLevel());
        pace.setText("Pace Limit: "+Double.toString(eventType.getPace()));
        additionalRequirement.setText("Additional Requirement: "+eventType.getAdditionRequirement());
    }
    public void onClickBack(View view){
        Intent backToMain=new Intent(CreateEvent.this,CyclingClub_Main.class);
        backToMain.putExtra("userid",userId);
        startActivity(backToMain);
    }
    public void onClickCreate(View view){
        String age_str=((EditText)findViewById(R.id.edit_eventAge)).getText().toString();
        int age_int=0;
        boolean error=false;
        try{
            age_int=Integer.parseInt(age_str);
        }catch (RuntimeException e){
            Toast.makeText(this, "Please enter a Number for Age",Toast.LENGTH_SHORT).show();
            error=true;
        }


        String detail_str=((EditText)findViewById(R.id.edit_eventDetail)).getText().toString();

        String level_str=((EditText)findViewById(R.id.edit_eventLevel)).getText().toString();

        int level_int=0;
        try{
            level_int=Integer.parseInt(level_str);
        }catch (RuntimeException e){
            Toast.makeText(this, "Please enter a Number for Level",Toast.LENGTH_SHORT).show();
            error=true;
        }

        String pace_str=((EditText)findViewById(R.id.edit_eventPace)).getText().toString();
        double  pace_double=0;
        try{
            pace_double=Double.parseDouble(pace_str);
        }catch (RuntimeException e){
            Toast.makeText(this, "Please enter a Number for Pace",Toast.LENGTH_SHORT).show();
            error=true;
        }

        String addition_str=((EditText)findViewById(R.id.edit_eventAdditionalRequirement)).getText().toString();

        String volume_str=((EditText)findViewById(R.id.edit_volume)).getText().toString();
        int volume_int=0;
        try{
            volume_int=Integer.parseInt(volume_str);
        }catch (RuntimeException e){
            Toast.makeText(this, "Please enter a Number for Volume",Toast.LENGTH_SHORT).show();
            error=true;
        }

        String name_str=((EditText)findViewById(R.id.edit_eventName)).getText().toString();


        boolean valid=true;
        String errorMessage="";
        if(!error){
            if(age_int<eventType.getAge()){
                valid=false;
                errorMessage+="Event Age Can not be less than limit above         \n";
            }
            if(level_int<eventType.getLevel()){
                valid=false;
                errorMessage+="Event Level Can not be less than limit above         \n";
            }
            if(pace_double<eventType.getPace()){
                valid=false;
                errorMessage+="Event Pace Can not less than limit above         \n";
            }
            if(volume_int<=0){
                valid=false;
                errorMessage+="Event Volume Can not less than or equal 0          \n";
            }
            if(detail_str.equals("")||detail_str.equals("Please Enter The Detail about The Event")){
                valid=false;
                errorMessage+="Please Enter Event Detail";
            }
            if(name_str.equals("")||name_str.equals("Please Enter Name for The Event")){
                valid=false;
                errorMessage+="Please Enter Event Name";
            }
            if(addition_str.equals("")||addition_str.equals("Please Additional Requirement For Your Event or Blank To Use Addition Requirement Above")){
                addition_str=eventType.getAdditionRequirement();
                Toast.makeText(this, "The Additional Requirement Will Remain As Above",Toast.LENGTH_SHORT).show();
            }
            if(valid){
                Event event=new Event(eventType);
                event.setVolume(volume_int);
                event.setAge(age_int);
                event.setDetail(detail_str);
                event.setName(name_str);
                event.setAdditionRequirement(addition_str);
                event.setPace(pace_double);
                event.setLevel(level_int);
                event.setCyclingClub(userId);
                CyclingClub cyclingClub=new CyclingClub(userId,"FAKE PASSWORD","Cycling Club");
                cyclingClub.createEvent(event);
                Toast.makeText(this, "Your Event is Created and Published",Toast.LENGTH_SHORT).show();
                Intent backToMain=new Intent(CreateEvent.this,CyclingClub_Main.class);
                backToMain.putExtra("userid",userId);
                startActivity(backToMain);
            }else{
                Toast.makeText(this, errorMessage,Toast.LENGTH_SHORT).show();
            }
        }




    }

}