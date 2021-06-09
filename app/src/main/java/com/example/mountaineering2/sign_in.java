package com.example.mountaineering2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.google.android.material.textfield.TextInputLayout;

public class sign_in extends AppCompatActivity {
    private TextView registered,forgetPassWord;
    private Button loginIn;

    private TextInputLayout Account,passWord;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        Account=findViewById(R.id.Account);
        passWord=findViewById(R.id.passWord);


        registered=(TextView)findViewById(R.id.Registered);
        registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegistered();
            }
        });

        loginIn=(Button)findViewById(R.id.loginIn);
        loginIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomeScreen();
            }
        });

        forgetPassWord=(TextView)findViewById(R.id.forgetPassWord);
        forgetPassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {openforgetPage();}
        });

    }
    //----------註冊-------------------------
    public void openRegistered(){
        Intent intent =new Intent(this,registered.class);
        startActivity(intent); }
    //----------首頁-------------------------
    public void openHomeScreen(){
        Intent intent =new Intent(this,homeScreen.class);
        startActivity(intent); }
    //----------忘記密碼----------------------
    public void openforgetPage(){
        Intent intent=new Intent(this,forgetPage.class);
        startActivity(intent);
    }
}