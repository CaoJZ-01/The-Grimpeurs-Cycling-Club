package com.example.thegrimpeurscyclingclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thegrimpeurscyclingclub.data.EventTypeListAdapter;
import com.example.thegrimpeurscyclingclub.data.LoginRepository;
import com.example.thegrimpeurscyclingclub.data.event_relative.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeleteEvent extends AppCompatActivity {


    ListView listToSelect;
    ArrayList list = new ArrayList<>();
    String userId;

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
                Intent intent = new Intent(DeleteEvent.this, CyclingClub_Main.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("userid", userId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

            }
        });


        listToSelect.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String event = (String) list.get(i);
                showConfirmDialog(event);

                return true;
            }
        });
    }

    public void onStart() {
        super.onStart();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference eventTypeRef = databaseReference.child("event").child(userId);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    list.add(snapshot1.getKey());
                }
                EventTypeListAdapter adapter = new EventTypeListAdapter(DeleteEvent.this, list);
                listToSelect.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        eventTypeRef.addListenerForSingleValueEvent(eventListener);
    }
    private void showConfirmDialog(String eventToDelete) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.confirm_dialog, null);
        dialogBuilder.setView(dialogView);


        final Button buttonDelete = (Button) dialogView.findViewById(R.id.btn_delete);
        final Button buttonBack = (Button) dialogView.findViewById(R.id.btn_back);

        final AlertDialog b = dialogBuilder.create();
        b.show();
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEventType(eventToDelete);
                b.dismiss();
                onStart();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });
    }
    private void deleteEventType(String id) {
        Bundle extra=getIntent().getExtras();
        userId=extra.getString("userid");

        DatabaseReference dR=FirebaseDatabase.getInstance().getReference("event").child(userId).child(id);
        dR.removeValue();
        Toast.makeText(this,"Event  Deleted",Toast.LENGTH_SHORT).show();
    }
}