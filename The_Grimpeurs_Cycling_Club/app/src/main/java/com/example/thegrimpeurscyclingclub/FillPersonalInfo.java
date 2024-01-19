package com.example.thegrimpeurscyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.thegrimpeurscyclingclub.data.LoginRepository;
import com.example.thegrimpeurscyclingclub.data.ParticipantInfo;
import com.example.thegrimpeurscyclingclub.data.event_relative.Event;
import com.example.thegrimpeurscyclingclub.data.event_relative.EventType;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;

public class FillPersonalInfo extends AppCompatActivity {

    private String userId;
    private Event event;
    ImageView logo;
    int RESULT_LOAD_IMAGE = 200;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_personal_info);

        Bundle extra=getIntent().getExtras();
        userId=extra.getString("userid");
        event=getIntent().getParcelableExtra("event");

        TextView volume=findViewById(R.id.txt_volumeSelectEvent);
        TextView name=findViewById(R.id.txt_nameSelectEvent);
        TextView type=findViewById(R.id.txt_typeSelectEvent);
        TextView detail=findViewById(R.id.txt_detailSelectEvent);
        TextView age=findViewById(R.id.txt_ageSelectEvent);
        TextView level=findViewById(R.id.txt_levelSelectEvent);
        TextView pace=findViewById(R.id.txt_paceSelectEvent);
        TextView additionalRequirement=findViewById(R.id.txt_additionSelectEvent);
        TextView club=findViewById(R.id.txt_clubSelectEvent);

        type.setText("Event Tye: "+event.getEventType());
        detail.setText("Event Detail:  "+event.getDetail());
        age.setText("Age Limit: "+event.getAge());
        level.setText("Level Limit: "+event.getLevel());
        pace.setText("Pace Limit: "+Double.toString(event.getPace()));
        additionalRequirement.setText("Additional Requirement: "+event.getAdditionRequirement());
        volume.setText("Event Volume: " + event.getVolume());
        club.setText("Offered By: "+event.getCyclingClub());
        name.setText("Event Name: " + event.getName());


        logo = (ImageView) findViewById(R.id.logo2);
        FirebaseStorage storageInstance = FirebaseStorage.getInstance();
        StorageReference storageReference = storageInstance.getReferenceFromUrl("gs://the-grimpeurs-cycling-cl-38857.appspot.com");

        Bundle extras = getIntent().getExtras();

        String imagePath = event.getCyclingClub() + "_logo.jpg";

        try{
            storageReference.child(imagePath).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(getApplicationContext()).load(uri.toString()).into(logo);
                }
            });
        }
        catch(Exception e){
            //use default logo
        }

    }
    public void onClickBack(View view){
        Bundle extra=getIntent().getExtras();
        userId=extra.getString("userid");
        Intent intent=new Intent(FillPersonalInfo.this, ParticipateMain.class);
        intent.putExtra("userid",userId);
        startActivity(intent,extra);
        finish();
    }

    public void onClickJoin(View view){
        boolean valid=true;
        String errorMessage="";

        String age_str=((EditText)findViewById(R.id.edit_p_age)).getText().toString();
        int age_int=0;
        if(age_str.equals("")||age_str.equals("Please Enter Your Age")){
            valid=false;
            errorMessage+="Please enter Enter Your Age        \n ";
        }else{
            try{
                age_int=Integer.parseInt(age_str);
            }catch (RuntimeException e){
                Toast.makeText(this, "Please enter a Number for Age",Toast.LENGTH_SHORT).show();
                valid=false;
            }
        }
        if(age_int<event.getAge()){
            errorMessage+="Your Are Under Age Limit       \n";
            valid=false;
        }

        String level_str=((EditText)findViewById(R.id.edit_p_level)).getText().toString();
        int level_int=0;
        if(level_str.equals("")||level_str.equals("Please Enter Your Level")){
            valid=false;
            errorMessage+="Please enter Your Level        \n ";
        }else{
            try{
                level_int=Integer.parseInt(level_str);
            }catch (RuntimeException e){
                Toast.makeText(this, "Please enter a Number for Level",Toast.LENGTH_SHORT).show();
                valid=false;
            }
        }
        if(level_int<event.getLevel()){
            errorMessage+="Your Are Under Level Limit       \n";
            valid=false;
        }

        String pace_str=((EditText)findViewById(R.id.edit_p_pace)).getText().toString();
        double pace_double=0;
        if(pace_str.equals("")||pace_str.equals("Please Enter Your Pace")){
            valid=false;
            errorMessage+="Please enter Your Pace        \n ";
        }else{
            try{
                pace_double=Double.parseDouble(pace_str);
            }catch (RuntimeException e){
                Toast.makeText(this, "Please enter a Number for Pace",Toast.LENGTH_SHORT).show();
                valid=false;
            }
        }
        if(pace_double<event.getPace()){
            errorMessage+="Your Are Under Pace Limit       \n";
            valid=false;
        }

        String name=((EditText)findViewById(R.id.edit_p_name)).getText().toString();
        if(name.equals("")||name.equals("Please Enter Your Name")){
            valid=false;
            errorMessage+="Please Enter Your Name";
        }
        if(valid){
            ParticipantInfo info=new ParticipantInfo(name,age_int,level_int,pace_double,userId);
            LoginRepository loginRepository=new LoginRepository();
            loginRepository.joinEvent(info,event);
            Toast.makeText(this, "Join Success",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(FillPersonalInfo.this,ParticipateMain.class);
            intent.putExtra("userid",userId);
            startActivity(intent);
        }else{
            Toast.makeText(this, errorMessage,Toast.LENGTH_SHORT).show();
        }

    }



    // Test Only
    // test 3-5
    public static String validateAge(String testAge){
        int testAgelimit = 20;
        String errorMessage = "";
        int testAge_int = 0;

        if (testAge.equals("") || testAge.equals("Please Enter Your Age")){
            errorMessage += "Please enter Enter Your Age        \n ";
            return errorMessage;
        }else{
            try{
                //testAge= String.valueOf(Integer.parseInt(testAge));
                testAge_int = Integer.parseInt(testAge);
            }catch (RuntimeException e){
                errorMessage += "Please enter a Number for Age";
                return errorMessage;
            }
        }

        if(testAge_int < testAgelimit){
            errorMessage+="Your Are Under Age Limit       \n";
            return errorMessage;
        }
        return "passed";

    }

    // test 6-8
    public static String validateLevel(String testLevel){
        int testLevelLimit = 20;
        String errorMessage = "";

        int test_level_int = 0;
        if(testLevel.equals("")||testLevel.equals("Please Enter Your Level")){
            errorMessage+="Please enter Your Level        \n ";
            return errorMessage;
        }else{
            try{
                test_level_int=Integer.parseInt(testLevel);
            }catch (RuntimeException e){
                errorMessage+="Please enter a Number for Level";
                return errorMessage;
            }
        }
        if(test_level_int<testLevelLimit){
            errorMessage+="Your Are Under Level Limit       \n";
            return errorMessage;
        }

        return "passed";
    }

    // test 9-11
    public static String validatePace(String testPace){
        double testPaceLimit = 20;
        String errorMessage = "";
        double test_pace_double = 0;

        if(testPace.equals("")||testPace.equals("Please Enter Your Pace")){
            errorMessage+="Please enter Your Pace        \n ";
            return errorMessage;
        }else{
            try{
                test_pace_double=Double.parseDouble(testPace);
            }catch (RuntimeException e){
                errorMessage+="Please enter a Number for Pace";
                return errorMessage;
            }
        }
        if(test_pace_double < testPaceLimit){
            errorMessage+="Your Are Under Pace Limit       \n";
            return errorMessage;
        }
        return "passed";
    }


}