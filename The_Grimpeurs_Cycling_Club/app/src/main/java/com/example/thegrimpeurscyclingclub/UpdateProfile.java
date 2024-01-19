package com.example.thegrimpeurscyclingclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thegrimpeurscyclingclub.data.Profile;
import com.example.thegrimpeurscyclingclub.data.users.CyclingClub;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateProfile extends AppCompatActivity {
    final static String PHONE="^\\d{10}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        Bundle extra=getIntent().getExtras();
        String userId=extra.getString("userid");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference profileRef = databaseReference.child("profile").child(userId);
        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot info:snapshot.getChildren()){
                    switch (info.getKey()){
                        case("phoneNumber"):
                            String phoneNumber=info.getValue(String.class);
                            TextView phoneNumber_txt=(TextView) findViewById(R.id.txt_Phone);
                            phoneNumber_txt.setText("Phone Number: "+phoneNumber);
                            break;
                        case("socialMediaLink"):
                            String socialMediaLink=info.getValue(String.class);
                            TextView socialMediaLink_txt=(TextView) findViewById(R.id.txt_Link);
                            socialMediaLink_txt.setText("Social Media Link: "+socialMediaLink);
                            break;
                        case("contactName"):
                            String contactName=info.getValue(String.class);
                            TextView contactName_txt=(TextView) findViewById(R.id.txt_contact);
                            contactName_txt.setText("Contact Name: "+contactName);
                            break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        profileRef.addListenerForSingleValueEvent(valueEventListener);
    }
    public void onClickUpdateProfile(View view) {
        String contactName_str = ((EditText) findViewById(R.id.edit_updateName)).getText().toString();
        String phoneNumber_str = ((EditText) findViewById(R.id.edit_updatePhone)).getText().toString();
        String socialMediaLink_str = ((EditText) findViewById(R.id.edit_Link)).getText().toString();
        Bundle extra = getIntent().getExtras();
        String userId = extra.getString("userid");
        Pattern phone=Pattern.compile(PHONE);
        Matcher phonematcher=phone.matcher(phoneNumber_str);
        boolean validFormat = true;
        String errorMessage = "";
        if ((!phonematcher.matches())&&(!phoneNumber_str.equals(""))) {
            validFormat = false;
            errorMessage += "Please Enter a valid phone number or leave blank for no update            \n";
        }
        if ((!android.util.Patterns.WEB_URL.matcher(socialMediaLink_str).matches())&&(!socialMediaLink_str.equals(""))) {
            validFormat = false;
            errorMessage += "Please Enter a valid social media link or leave blank for no update         \n";
        }
        if (validFormat) {
            Profile newProfile = new Profile(userId, "Cycling Club");
            newProfile.setContactName(contactName_str);
            newProfile.setPhoneNumber(phoneNumber_str);
            newProfile.setSocialMediaLink(socialMediaLink_str);
            new CyclingClub(userId, "fakePassword", "Cycling Club").updateProfile(newProfile);
            Toast.makeText(UpdateProfile.this, "Updated Succeed", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdateProfile.this, CyclingClub_Main.class);
            intent.putExtra("userid", userId);
            startActivity(intent, extra);

        } else {
            Toast.makeText(UpdateProfile.this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }
    public void onClickBackToCycleClub_main(View view){
        Bundle extra = getIntent().getExtras();
        String userId = extra.getString("userid");
        Intent intent = new Intent(UpdateProfile.this, CyclingClub_Main.class);
        intent.putExtra("userid", userId);
        startActivity(intent, extra);

    }


}