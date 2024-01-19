package com.example.thegrimpeurscyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ParticipateMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participate_main);
    }
    public void OnClickSearchByType(View view){
        Bundle extra=getIntent().getExtras();
        String userId=extra.getString("userid");
        Intent intent=new Intent(ParticipateMain.this, SelectSearchEventType.class);
        intent.putExtra("userid",userId);
        startActivity(intent,extra);
    }

    public void OnClickSearchByName(View view){
        Bundle extra=getIntent().getExtras();
        String userId=extra.getString("userid");
        Intent intent=new Intent(ParticipateMain.this, SelectSearchName.class);
        intent.putExtra("userid",userId);
        startActivity(intent,extra);
    }

    public void OnClickSearchByClub(View view){
        Bundle extra=getIntent().getExtras();
        String userId=extra.getString("userid");
        Intent intent=new Intent(ParticipateMain.this, SelectSearchClub.class);
        intent.putExtra("userid",userId);
        startActivity(intent,extra);
    }
    public void OnClickRateClub(View view){
        Bundle extra=getIntent().getExtras();
        String userId=extra.getString("userid");
        Intent intent=new Intent(ParticipateMain.this, SelectClub.class);
        intent.putExtra("userid",userId);
        startActivity(intent,extra);
    }

    public void onClickSignOff(View view){
        Intent intent=new Intent(ParticipateMain.this, MainActivity.class);
        startActivity(intent);
    }
}