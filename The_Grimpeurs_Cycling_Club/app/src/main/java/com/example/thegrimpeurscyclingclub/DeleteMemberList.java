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

import com.example.thegrimpeurscyclingclub.data.MemberListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeleteMemberList extends AppCompatActivity {
    ListView listToDelete;
    ArrayList list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int depth=dm.heightPixels;
        getWindow().setLayout((int)(width*0.8),(int)(depth*.8));
        listToDelete=(ListView)findViewById(R.id.list_memberList);
        Button buttonBackToAdmin=(Button)findViewById(R.id.btn_backFromMemberList);
        buttonBackToAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToAdmin=new Intent(DeleteMemberList.this,Admin.class);
                backToAdmin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(backToAdmin);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });
        listToDelete.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String memberToDelete = ((String[]) list.get(i))[0];
                showConfirmDialog(memberToDelete);

                return true;
            }
        });
    }
    public void onStart(){
        super.onStart();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference eventTypeRef = databaseReference.child("users");
        ValueEventListener eventListener= new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    String[] memberInfo=new String[2];
                    memberInfo[0]=snapshot1.getKey();
                    memberInfo[1]=snapshot1.child("role").getValue(String.class);
                    if(!memberInfo[1].equals("administrator")){
                        list.add(memberInfo);
                    }

                }
                MemberListAdapter adapter=new MemberListAdapter(DeleteMemberList.this,list);
                listToDelete.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        eventTypeRef.addListenerForSingleValueEvent(eventListener);
    }
    private void deleteMember(String id) {

        DatabaseReference dR= FirebaseDatabase.getInstance().getReference("users").child(id);
        dR.removeValue();
        Toast.makeText(this,"Member deleted",Toast.LENGTH_SHORT).show();
    }
    private void showConfirmDialog(String memberToDelete) {

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
                deleteMember(memberToDelete);
                b.dismiss();
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });
    }
}