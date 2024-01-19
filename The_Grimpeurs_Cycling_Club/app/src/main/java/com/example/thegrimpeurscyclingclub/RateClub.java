package com.example.thegrimpeurscyclingclub;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thegrimpeurscyclingclub.data.EventTypeListAdapter;
import com.example.thegrimpeurscyclingclub.data.LoginRepository;
import com.example.thegrimpeurscyclingclub.data.event_relative.EventType;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class RateClub extends AppCompatActivity {
    ListView listToSelect;
    String clubToRate;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_club);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Bundle extra = getIntent().getExtras();
        userId = extra.getString("userid");
        clubToRate=extra.getString("clubToRate");

        int width = dm.widthPixels;
        int depth = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.8), (int) (depth * .8));
        Button buttonBack = (Button) findViewById(R.id.btn_backToP);
        Button submit=(Button) findViewById(R.id.btn_submit);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RateClub.this, ParticipateMain.class);
                intent.putExtra("userid",userId);
                startActivity(intent);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment_str = ((EditText) findViewById(R.id.edit_comment)).getText().toString();
                String rate_str = ((EditText) findViewById(R.id.edit_rate)).getText().toString();
                boolean valid=true;
                int rate = 0;
                if (comment_str.equals("") || rate_str.equals("Please Leave A Comment To Club")) {
                    comment_str="N/A";
                }
                if (rate_str.equals("") || rate_str.equals("Please Rate The Club From 1 To 5")) {
                    Toast.makeText(RateClub.this, "Please Enter Your rate", Toast.LENGTH_SHORT).show();
                    valid=false;
                } else {
                    try {
                        rate = Integer.parseInt(rate_str);
                        if(rate>5||rate<1){
                            Toast.makeText(RateClub.this, "Please Enter a Integer From 1 to 5", Toast.LENGTH_SHORT).show();
                            valid=false;
                        }
                    } catch (Exception e) {
                        Toast.makeText(RateClub.this, "Please Enter a Integer for Rate", Toast.LENGTH_SHORT).show();
                        valid=false;
                    }
                }

                if(valid){
                    LoginRepository loginRepository=new LoginRepository();
                    loginRepository.rateAndCommentClub(userId,rate,clubToRate,comment_str);
                    Toast.makeText(RateClub.this, "Rate Completed", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(RateClub.this, ParticipateMain.class);
                    intent.putExtra("userid",userId);
                    startActivity(intent);
                }

            }
        });
    }



    // Test Only
    // test1, test2
    public static String validateRate(int testRate){
        String errorMessage = "";
        try{
            testRate = Integer.parseInt(String.valueOf(testRate));
            if(testRate>5||testRate<1){
                errorMessage += "Please Enter a Integer From 1 to 5";
                return errorMessage;
            }
        } catch (Exception e){
            errorMessage += "Please Enter a Integer for Rate";
            return errorMessage;
        }
        return "passed";
    }


}