package com.example.thegrimpeurscyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.thegrimpeurscyclingclub.data.LoginRepository;


public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome_screen);

        // display user info
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String userid = extras.getString("userid");
            String role = extras.getString("role");
            TextView welcomeName = (TextView)findViewById(R.id.welcomeName);
            welcomeName.setText("user name: "+userid);
            TextView welcomeRole = (TextView)findViewById(R.id.welcomeRole);
            welcomeRole.setText("role: "+role);
        }else{
            throw new NullPointerException();
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                String role = extras.getString("role");
                if(role.equals("administrator")) {

                    Intent intent = new Intent(WelcomeScreen.this, Admin.class);
                    intent.putExtra("userid", extras.getString(("userid")));
                    WelcomeScreen.this.startActivity(intent);
                    WelcomeScreen.this.finish();
                } else if (role.equals("Cycling Club")) {
                    LoginRepository loginRepository = new LoginRepository();
                    loginRepository.findCyclingClubProfile(extras.getString("userid"), new Callback() {
                        @Override
                        public void onFetched(Object obj) {
                            if((boolean)obj){
                                Intent intent = new Intent(WelcomeScreen.this, CyclingClub_Main.class);
                                intent.putExtra("userid", extras.getString(("userid")));
                                WelcomeScreen.this.startActivity(intent);
                                WelcomeScreen.this.finish();
                            }else{
                                Intent intent = new Intent(WelcomeScreen.this, CyclingClub_CompleteProfile.class);
                                intent.putExtra("userid", extras.getString(("userid")));
                                WelcomeScreen.this.startActivity(intent);
                                WelcomeScreen.this.finish();
                            }

                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
                    
                }else if(role.equals("Participant")){
                    Intent intent = new Intent(WelcomeScreen.this, ParticipateMain.class);
                    intent.putExtra("userid", extras.getString(("userid")));
                    WelcomeScreen.this.startActivity(intent);
                    WelcomeScreen.this.finish();
                }
            }
        }, 1000);
        
    }


}
