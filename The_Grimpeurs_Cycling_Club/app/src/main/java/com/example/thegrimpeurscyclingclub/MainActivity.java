package com.example.thegrimpeurscyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.thegrimpeurscyclingclub.data.LoginDataSource;
import com.example.thegrimpeurscyclingclub.data.LoginRepository;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void jumpToSignIn(View view){
        Intent intent = new Intent(MainActivity.this, SignIn.class);
        startActivity(intent);
    }

    public void jumpToRegister(View view){
        Intent intent = new Intent(MainActivity.this, Register.class);
        startActivity(intent);
    }
}