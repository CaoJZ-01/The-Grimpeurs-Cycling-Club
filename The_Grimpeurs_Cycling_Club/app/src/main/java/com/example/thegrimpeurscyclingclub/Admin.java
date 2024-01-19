package com.example.thegrimpeurscyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }
    public void onClickAddEvent(View view){
        Intent intent=new Intent(Admin.this, AddEventInfo.class);
        Bundle extras = getIntent().getExtras();
        String userid="";
        if (extras != null) {
            userid = extras.getString("userid");
        }else{
            throw new NullPointerException();
        }
        intent.putExtra("userid",userid);
        startActivity(intent);
    }
    public void onClickUpdateEvent(View view){
        Intent intent=new Intent(Admin.this, UpdateEventType.class);
        Bundle extras = getIntent().getExtras();
        String userid="";
        if (extras != null) {
            userid = extras.getString("userid");
        }else{
            throw new NullPointerException();
        }
        intent.putExtra("userid",userid);
        startActivity(intent);
    }
    public void onClickDeleteEventType(View view){
        Intent intent=new Intent(Admin.this, DeleteEventType.class);
        Bundle extras = getIntent().getExtras();
        String userid="";
        if (extras != null) {
            userid = extras.getString("userid");
        }else{
            throw new NullPointerException();
        }
        intent.putExtra("userid",userid);
        startActivity(intent);
    }
    public void onClickDeleteMember(View view){
        Intent intent=new Intent(Admin.this, DeleteMemberList.class);
        Bundle extras = getIntent().getExtras();
        String userid="";
        if (extras != null) {
            userid = extras.getString("userid");
        }else{
            throw new NullPointerException();
        }
        intent.putExtra("userid",userid);
        startActivity(intent);
    }

    public void onClickSignOff(View view){
        Intent intent=new Intent(Admin.this, MainActivity.class);
        startActivity(intent);
    }
}