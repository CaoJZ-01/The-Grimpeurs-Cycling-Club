
package com.example.thegrimpeurscyclingclub;

import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.util.DisplayMetrics;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.Button;
        import android.widget.ListView;

        import com.example.thegrimpeurscyclingclub.data.EventTypeListAdapter;
        import com.example.thegrimpeurscyclingclub.data.LoginRepository;
import com.example.thegrimpeurscyclingclub.data.event_relative.Event;
import com.example.thegrimpeurscyclingclub.data.event_relative.EventType;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.io.Serializable;
        import java.util.ArrayList;

public class ShowEvents extends AppCompatActivity {
    ListView listToSelect;
    ArrayList list = new ArrayList<>();
    String userId;
    ArrayList<String> eventName=new ArrayList<>();
    ArrayList<String> eventClub=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Bundle extra=getIntent().getExtras();
        userId=extra.getString("userid");

        int width = dm.widthPixels;
        int depth = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.8), (int) (depth * .8));
        listToSelect = (ListView) findViewById(R.id.listToDelete);
        Button buttonBackToAdmin = (Button) findViewById(R.id.btn_back_to_admin);


        buttonBackToAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowEvents.this, ParticipateMain.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("userid", userId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                finish();
            }
        });


        listToSelect.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String eventToSelect = (String) eventName.get(i);
                String club=(String)eventClub.get(i);
                LoginRepository loginRepository=new LoginRepository();

                loginRepository.findEventInfo(club,eventToSelect, new Callback() {
                    @Override
                    public void onFetched(Object obj) {
                        Event event=(Event)obj;
                        Bundle extra=getIntent().getExtras();
                        ArrayList<String> events=(ArrayList<String>)extra.getSerializable("events");
                        String userid= extra.getString("userid");
                        Intent intent=new Intent(ShowEvents.this, FillPersonalInfo.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("userid",userId);
                        intent.putExtra("event",event);
                        intent.putExtra("events",(Serializable) events);
                        startActivity(intent,extra);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                        finish();

                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

                return true;
            }
        });
    }
    public void onStart() {
        super.onStart();

        Bundle extra=getIntent().getExtras();
        ArrayList<String> events=(ArrayList<String>)extra.getSerializable("events");
        list.clear();
        for(String nameAndClub:events){
            String[] splits=nameAndClub.split("/space/");
            eventName.add(splits[0]);
            eventClub.add(splits[1]);
            list.add(splits[0]+" Offered By: "+splits[1]);
        }

        EventTypeListAdapter adapter=new EventTypeListAdapter(ShowEvents.this,list);
        listToSelect.setAdapter(adapter);

    }
}