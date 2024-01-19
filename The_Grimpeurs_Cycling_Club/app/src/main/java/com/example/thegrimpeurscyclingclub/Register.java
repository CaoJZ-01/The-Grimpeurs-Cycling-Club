package com.example.thegrimpeurscyclingclub;

import static android.content.ContentValues.TAG;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thegrimpeurscyclingclub.data.LoginDataSource;
import com.example.thegrimpeurscyclingclub.data.LoginRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Register extends AppCompatActivity {
    private RadioButton role;
    private EditText user_id, password,email;
    private String user_id_str, password_str,roleType,email_str;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void onClickRegister(View view){
        boolean valid=true;
        String errorMessage="";
        user_id=(EditText) findViewById(R.id.user_id);
        if(user_id==null){
            valid=false;
            errorMessage+="User ID Can not be Empty\n";
        }
        password=(EditText) findViewById(R.id.password);
        if(user_id==null){
            valid=false;
            errorMessage+="Password Can not be Empty\n";
        }
        user_id_str = user_id.getText().toString();
        if(user_id_str.contains(" ")){
            valid=false;
            errorMessage+="User ID Contain Space\n";
        }
        password_str = password.getText().toString();
        if(password_str.contains(" ")){
            valid=false;
            errorMessage+="Password Contain Space\n";
        }
        if(role==null){
            valid=false;
            errorMessage+="Please Select a Role\n";
        }else{
            roleType = role.getText().toString();
        }
        email=(EditText) findViewById(R.id.email);
        email_str=email.getText().toString();
        if(email==null){
            errorMessage+="Email Can not be Empty\n";
        }else{
            Matcher matcher = pattern.matcher(email_str);
            if (!matcher.matches()){
                valid=false;
                errorMessage+="Email format is wrong\n";
            }

        }

        if (!valid){
            Toast.makeText(Register.this,errorMessage,Toast.LENGTH_LONG).show();

        }else {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            DatabaseReference userIdRef = databaseReference.child("users").child(user_id_str);


            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        LoginDataSource userInformation = new LoginDataSource(user_id_str, password_str, roleType,email_str);
                        LoginRepository newUser = new LoginRepository(userInformation);
                        newUser.register();
                        Toast.makeText(Register.this, "Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register.this, WelcomeScreen.class);
                        intent.putExtra("userid",user_id_str);
                        intent.putExtra("role",roleType);
                        startActivity(intent);

                    } else {
                        Toast.makeText(Register.this, "Your User ID is Occupied", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d(TAG, error.getMessage());

                }
            };
            userIdRef.addListenerForSingleValueEvent(eventListener);

        }
    }

    public void onClickRadioButton(View view){
        RadioGroup radioGroup = findViewById(R.id.radio_group_for_role);
        int radioId=radioGroup.getCheckedRadioButtonId();
        role = findViewById(radioId);
        TextView userRole = (TextView) findViewById(R.id.user_role);
        userRole.setText(role.getText().toString());
    }

    public void onClickEditTextUserId(View view){
        user_id = (EditText) findViewById(R.id.user_id);
        user_id_str = user_id.getText().toString();
    }

    public void onClickEditTextPassword(View view){
        password = (EditText) findViewById(R.id.password);
        password_str = password.getText().toString();
    }

    public void onClickEditTextEmail(View view){
        email= (EditText) findViewById(R.id.email);
        email_str = email.getText().toString();
    }


    // Test only
    public static String invalidUserID(String str){
        String errorMessage = "";

        if(str==null){
            errorMessage+="User ID Can not be Empty\n";
            return errorMessage;
        }

        if(str.contains(" ")){
            errorMessage+="User ID Contain Space\n";
            return errorMessage;
        }
        return null;

    }

    public static String invalidPassword(String str) {
        String errorMessage = "";

        if(str.contains(" ")) {
            errorMessage += "Password Contain Space\n";
            return errorMessage;
        }
        return null;
    }

}