package com.example.mountaineering2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.google.android.material.textfield.TextInputLayout;

public class sign_in extends AppCompatActivity {

    private TextView registered,forgetPassWord;
    private Button loginIn;
    //----------------宣告帳號密碼---------
    final String user = "root";
    final String pass = "123";
    //----------------------------------

    private TextInputLayout Account,passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        Account=(TextInputLayout)findViewById(R.id.Account);
        passWord=(TextInputLayout)findViewById(R.id.passWord);


        registered=(TextView)findViewById(R.id.Registered);
        registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openRegistered();}
        });

        loginIn=(Button)findViewById(R.id.loginIn);
        loginIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {judgment();}
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
        Intent intent=new Intent(this,forgetPage1.class);
        startActivity(intent);
    }
    //-------判斷帳號密碼
    public void judgment(){
        String AT="";
        AT=Account.getEditText().getText().toString();
        String PW="";
        PW=passWord.getEditText().getText().toString();
        if(AT.equals(user)&PW.equals(pass)){
            //成功
            openHomeScreen();
        }else {
            //失敗
        new AlertDialog.Builder(sign_in.this).setTitle("錯誤").setMessage("帳號或密碼錯誤")
        .setNegativeButton("ok",null).show();
        }
    }
    //-------鍵盤收縮
    public void Close_Input_Board(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(findViewById(R.id.Sign_in_Page).getWindowToken(), 0);
    }
}