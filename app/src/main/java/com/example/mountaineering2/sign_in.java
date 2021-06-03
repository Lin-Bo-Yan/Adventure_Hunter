package com.example.mountaineering2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class sign_in extends AppCompatActivity {
    private TextView registered;
    private Button loginIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        registered=(TextView)findViewById(R.id.Registered);
        registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegistered();
            }
        });

        loginIn=findViewById(R.id.loginIn);
        loginIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomeScreen();
            }
        });

    }

    public void openRegistered(){
        Intent intent =new Intent(this,registered.class);
        startActivity(intent);

    }
    public void openHomeScreen(){
        Intent intent =new Intent(this,homeScreen.class);
        startActivity(intent);

    }
}