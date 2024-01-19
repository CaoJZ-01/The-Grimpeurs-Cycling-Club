package com.example.thegrimpeurscyclingclub;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class SelectSearchName extends AppCompatActivity {
    ListView listToSelect;
    ArrayList list = new ArrayList<>();
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_event_by_name);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Bundle extra = getIntent().getExtras();
        userId = extra.getString("userid");

        int width = dm.widthPixels;
        int depth = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.8), (int) (depth * .8));
        Button buttonBack = (Button) findViewById(R.id.btn_dismiss);
        Button search=(Button) findViewById(R.id.btn_search);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SelectSearchName.this, ParticipateMain.class);
                intent.putExtra("userid",userId);
                startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eventName=((EditText) findViewById(R.id.edit_searchEventName)).getText().toString();
                if(eventName.equals("")||eventName.equals("Please Enter Event Name")){
                    Toast.makeText(SelectSearchName.this,"Please Enter The Event Name",Toast.LENGTH_SHORT).show();
                }else{
                    LoginRepository loginRepository=new LoginRepository();
                    loginRepository.searchEventByName(eventName, new Callback() {
                        @Override
                        public void onFetched(Object obj) {
                            ArrayList<String> events=(ArrayList<String>)obj;
                            Bundle extra=getIntent().getExtras();
                            String userid= extra.getString("userid");
                            Intent intent=new Intent(SelectSearchName.this, ShowEvents.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("userid",userId);
                            intent.putExtra("events",(Serializable) events);
                            startActivity(intent,extra);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                            finish();
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
                }
            }
        });
    }
}