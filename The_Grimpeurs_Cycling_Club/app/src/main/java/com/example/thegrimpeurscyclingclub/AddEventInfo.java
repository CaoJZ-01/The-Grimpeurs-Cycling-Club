package com.example.thegrimpeurscyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.thegrimpeurscyclingclub.data.LoginRepository;
import com.example.thegrimpeurscyclingclub.data.event_relative.EventType;
import com.example.thegrimpeurscyclingclub.data.users.Administrator;

public class AddEventInfo extends AppCompatActivity {
    private RadioButton type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_info);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int depth=dm.heightPixels;
        getWindow().setLayout((int)(width*0.8),(int)(depth*.8));
    }
    public void onClickRadioButton(View view) {
        RadioGroup radioGroup = findViewById(R.id.event_type);
        int radioId = radioGroup.getCheckedRadioButtonId();
        type = findViewById(radioId);

    }
    public void onBack(View view) {
        Intent backToAdmin = new Intent(AddEventInfo.this, Admin.class);
        backToAdmin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(backToAdmin);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    public void onClickAddToRepo(View view){
        LoginRepository loginRepository = new LoginRepository();

        if (type==null){
            Toast.makeText(AddEventInfo.this,"Please Select a EventType",Toast.LENGTH_LONG).show();
        }else{
            loginRepository.findEventExist(type.getText().toString(), new Callback() {
                @Override
                public void onFetched(Object obj) {
                    if ((boolean) obj){
                        Toast.makeText(AddEventInfo.this,"This Event Type is exist, use update for changing",Toast.LENGTH_LONG).show();
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                Intent intent = new Intent(AddEventInfo.this, Admin.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                            }
                        },1000);
                    }else{
                        boolean valid=true;
                        String errorMessage="";
                        int age=0;
                        double pace=0;
                        int level=0;
                        String detail=((EditText)findViewById(R.id.editDetail)).getText().toString();
                        if(detail.equals("")||detail.equals("Enter detail about event")){
                            valid=false;
                            errorMessage+="Please Enter N/A if no detail     \n";
                        }
                        try{
                            String age_str=((EditText)findViewById(R.id.editAge)).getText().toString();
                            if(age_str.equals("")||age_str.equals("Enter event age constrain")){
                                valid=false;
                                errorMessage+="Please Enter integer for age          \n";
                            }else{
                                age=Integer.parseInt(age_str);
                            }
                        }catch (Exception e){
                            valid=false;
                            errorMessage+="Please Enter integer for age          \n";
                        }
                        try {
                            String pace_str=(((EditText)findViewById(R.id.editPace)).getText().toString());
                            if(pace_str.equals("")||pace_str.equals("Enter number of pace")){
                                valid=false;
                                errorMessage+="Please Enter number for pace        \n";
                            }else{
                                pace=Double.parseDouble(pace_str);
                            }
                        }catch (Exception e){
                            valid=false;
                            errorMessage+="Please Enter number for pace        \n";
                        }
                        try{
                            String level_str=((EditText)findViewById(R.id.editLevel)).getText().toString();
                            if(level_str.equals("")||level_str.equals("Enter level of event")){
                                valid=false;
                                errorMessage+="Please Enter integer for Level        \n";
                            }else{
                                level=Integer.parseInt(level_str);
                            }
                        }catch (Exception e){
                            valid=false;
                            errorMessage+="Please Enter integer for Level        \n";
                        }
                        String addition=((EditText)findViewById(R.id.editAddition)).getText().toString();
                        if(addition.equals("")||addition.equals("Enter additional requirement if any")){
                            valid=false;
                            errorMessage+="Please Enter N/A if no addition requirement\n";
                        }


                        if (valid) {
                            EventType eventEventType = new EventType(type.getText().toString(), detail, age, pace, level, addition);
                            Bundle extras = getIntent().getExtras();
                            String userid = "";
                            if (extras != null) {
                                userid = extras.getString("userid");
                            } else {
                                throw new NullPointerException();
                            }
                            loginRepository.findUserinfo(userid, new Callback() {
                                @Override
                                public void onFetched(Object obj) {
                                    Administrator admin = (Administrator) obj;
                                    admin.addEventType(eventEventType);
                                }

                                @Override
                                public void onError(Exception e) {
                                    e.printStackTrace();
                                }
                            });
                            Toast.makeText(AddEventInfo.this,"Event Type published Successfully",Toast.LENGTH_LONG).show();
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    Intent intent = new Intent(AddEventInfo.this, Admin.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                                }
                            },1000);
                        }else{
                            Toast.makeText(AddEventInfo.this,errorMessage,Toast.LENGTH_LONG).show();
                        }

                    }
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }

    }
}