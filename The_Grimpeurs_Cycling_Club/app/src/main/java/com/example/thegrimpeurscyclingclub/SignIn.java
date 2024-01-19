package com.example.thegrimpeurscyclingclub;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {
    private EditText user_id, password;
    private String user_id_str, password_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    public void onClickEditTextUserId(View view){
        user_id = (EditText) findViewById(R.id.user_id_signin);
        user_id_str = user_id.getText().toString();
    }

    public void onClickEditTextPassword(View view){
        password = (EditText) findViewById(R.id.password_signin);
        password_str = password.getText().toString();
    }

    public void onClickSignIn(View view){
        user_id=(EditText) findViewById(R.id.user_id_signin);
        String userid=user_id.getText().toString();
        password = (EditText) findViewById(R.id.password_signin);
        String passwordStr = password.getText().toString();
        DatabaseReference userRef= FirebaseDatabase.getInstance().getReference().child("users").child(userid);

        ValueEventListener eventListener=new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    String passwordInDatabase=snapshot.child("password").getValue(String.class);
                    String roleInDatabase=snapshot.child("role").getValue(String.class);
                    if(passwordInDatabase!=null&&passwordInDatabase.equals(passwordStr)){
                        Intent intent = new Intent(SignIn.this, WelcomeScreen.class);
                        intent.putExtra("userid",userid);
                        intent.putExtra("role",roleInDatabase);
                        startActivity(intent);
                    }else{
                        Toast.makeText(SignIn.this,"Password Incorrect",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(SignIn.this,"Account not Exist",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.getMessage());
            }
        };

        userRef.addListenerForSingleValueEvent(eventListener);




    }

}