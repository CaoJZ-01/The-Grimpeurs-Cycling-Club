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

public class UpdateEventType extends AppCompatActivity {
    private RadioButton type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_event_type);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int depth=dm.heightPixels;
        getWindow().setLayout((int)(width*0.8),(int)(depth*.8));
    }
    public void onClickRadioButton(View view) {
        RadioGroup radioGroup = findViewById(R.id.update_event_type);
        int radioId = radioGroup.getCheckedRadioButtonId();
        type = findViewById(radioId);
    }
    public void onClickBack(View view) {
        Intent backToAdmin=new Intent(UpdateEventType.this,Admin.class);
        backToAdmin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(backToAdmin);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }
    public void onClickUpdate(View view){
        LoginRepository loginRepository = new LoginRepository();

        if (type==null){
            Toast.makeText(UpdateEventType.this,"Please Select a EventType",Toast.LENGTH_LONG).show();
        }else{
            loginRepository.findEventExist(type.getText().toString(), new Callback() {
                @Override
                public void onFetched(Object obj) {
                    if (!(boolean) obj){
                        Toast.makeText(UpdateEventType.this,"This Event Type is not exist, use add for event adding",Toast.LENGTH_LONG).show();
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                Intent intent = new Intent(UpdateEventType.this, Admin.class);
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
                        String detail=((EditText)findViewById(R.id.editUpdateDetail)).getText().toString();
                        if(detail.equals("Enter detail about event")||errorMessage.equals("Detail")){
                            detail="N/A";
                        }
                        try{
                            String age_str= ((EditText)findViewById(R.id.editUpdateAge)).getText().toString();
                            if(age_str.equals("")||age_str.equals("Enter event age constrain")){
                                age_str="999";
                            }
                            age=Integer.parseInt(age_str);
                        }catch (Exception e){
                            valid=false;
                            errorMessage+="Please integer for age or leave it blank for no change         \n";
                        }
                        try {
                            String pace_str=((EditText)findViewById(R.id.editUpdatePace)).getText().toString();
                            if(pace_str.equals("")||pace_str.equals("Enter number of pace")){
                                pace_str="999";
                            }
                            pace=Double.parseDouble(pace_str);
                        }catch (Exception e){
                            valid=false;
                            errorMessage+="Please number for pace or leave it blank for no change       \n";
                        }
                        try{
                            String level_str=((EditText)findViewById(R.id.editUpdateLevel)).getText().toString();
                            if(level_str.equals("")||level_str.equals("Enter level of event")){
                                level_str="999";
                            }
                            level=Integer.parseInt(level_str);
                        }catch (Exception e){
                            valid=false;
                            errorMessage+="Please integer for Level or leave it blank for no change      \n";
                        }
                        String addition=((EditText)findViewById(R.id.editUpdateAdditional)).getText().toString();
                        if(addition.equals("")||addition.equals("Enter addition requirement if any or blank")){
                            addition="N/A";
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
                                    admin.updateEventType(eventEventType);
                                }

                                @Override
                                public void onError(Exception e) {
                                    e.printStackTrace();
                                }
                            });
                            Toast.makeText(UpdateEventType.this,"Event Type Updated Successfully",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(UpdateEventType.this,errorMessage,Toast.LENGTH_LONG).show();
                        }
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                Intent intent = new Intent(UpdateEventType.this, Admin.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                            }
                        },1000);
                    }
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }

    }
}