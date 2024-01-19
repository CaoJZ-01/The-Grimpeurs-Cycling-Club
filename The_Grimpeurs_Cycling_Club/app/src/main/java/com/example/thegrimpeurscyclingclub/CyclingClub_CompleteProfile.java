package com.example.thegrimpeurscyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thegrimpeurscyclingclub.data.Profile;
import com.example.thegrimpeurscyclingclub.data.users.CyclingClub;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CyclingClub_CompleteProfile extends AppCompatActivity {
    final static String PHONE="^\\d{10}$";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cyclingclub_complete_profile);
    }
    public void onClickValidate(View view){
        String contactName_str=((EditText)findViewById(R.id.edit_contactName)).getText().toString();
        String phoneNumber_str=((EditText)findViewById(R.id.edit_phoneNumber)).getText().toString();
        String socialMediaLink_str=((EditText)findViewById(R.id.edit_socialLink)).getText().toString();
        Bundle extra=getIntent().getExtras();
        String userId=extra.getString("userid");

        boolean validFormat=true;
        String errorMessage="";
        Pattern phone=Pattern.compile(PHONE);
        Matcher phonematcher=phone.matcher(phoneNumber_str);
        if(!phonematcher.matches()){
            validFormat=false;
            errorMessage+="Please Enter a valid phone number\n";
        }
        if(!android.util.Patterns.WEB_URL.matcher(socialMediaLink_str).matches()){
            validFormat=false;
            errorMessage+="Please Enter a valid social media link\n";
        }
        if(validFormat){
            Profile newProfile=new Profile(userId,"Cycling Club");
            newProfile.setContactName(contactName_str);
            newProfile.setPhoneNumber(phoneNumber_str);
            newProfile.setSocialMediaLink(socialMediaLink_str);
            if(newProfile.validate()){
                new CyclingClub(userId,"fakePassword","Cycling Club").addProfile(newProfile);
                Intent intent=new Intent(CyclingClub_CompleteProfile.this, CyclingClub_Main.class);
                intent.putExtra("userid",userId);
                startActivity(intent,extra);

            }else{
                Toast.makeText(CyclingClub_CompleteProfile.this,"Please Fill The Mandatory Field",Toast.LENGTH_SHORT).show();
            }
        }else{
                Toast.makeText(CyclingClub_CompleteProfile.this,errorMessage,Toast.LENGTH_SHORT).show();
        }

    }

}