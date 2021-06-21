package com.example.mountaineering2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;


import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


public class sign_in extends AppCompatActivity {

    private TextView registered,forgetPassWord;
    private TextInputLayout Account,passWord;
    private Button loginIn;
    // 建立OkHttpClient
    OkHttpClient client = new OkHttpClient().newBuilder().build();
    //----------------宣告帳號密碼---------



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
            public void onClick(View v) {setGET(); }
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
    //----------會員頁面-------------------------
    public void openMember(){
        Intent intent =new Intent(this,member.class);
        startActivity(intent); }
    //----------忘記密碼----------------------
    public void openforgetPage(){
        Intent intent=new Intent(this,forgetPage1.class);
        startActivity(intent);
    }
    //----------登入失敗----------------------
    public void login_failed(){
        Intent intent=new Intent(this,login_failed.class);
        startActivity(intent);
    }

    //-------Restful API
    private void setGET(){

        String acc=Account.getEditText().getText().toString().trim();
/**設置傳送需求*/
        Request request = new Request.Builder()
                .url("https://b3f445e3a78f.ngrok.io"+"/api/users/account/"+acc)
                .build();
        /**設置回傳*/
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                /**如果傳送過程有發生錯誤*/
                e.printStackTrace();
                Log.v("joe", "Bad== " + e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                /**取得回傳*/
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    //Log.v("joe", "OK==  " + myResponse);
                    parsing_json(myResponse);
                }else {
                    //帳號錯誤 帶到錯誤畫面去
                    login_failed();
                }

            }
        });


    }


    //----------判斷帳號密碼
    private void parsing_json(String json){
            try {
                JSONObject root =new JSONObject(json);
                String acc_json=root.getString("account");
                String pass_json=root.getString("password");

                String user=Account.getEditText().getText().toString().trim();
                String pass=passWord.getEditText().getText().toString().trim();

                String realname_json=root.getString("realname");
                String email_json=root.getString("user_email");
                String birth_json=root.getString("birth");
                String cash_json=root.getString("cash");
                String id_json=root.getString("id");
                String phone_json= root.getString("phone");
                if(user.equals(acc_json)&pass.equals(pass_json)){
                    //建立意圖物件Intent
                    Intent goToMember = new Intent(sign_in.this,member.class);
                    //用資料捆傳遞資料
                    Bundle bundle = new Bundle();
                    bundle.putString("realname",realname_json);
                    bundle.putString("cash",cash_json);
                    bundle.putString("email",email_json);
                    bundle.putString("birth",birth_json);
                    bundle.putString("id",id_json);
                    bundle.putString("phone",phone_json);
                    goToMember.putExtras(bundle);
                    //會帶你到該頁面
                    startActivity(goToMember);
                }else {
                    //多做一個頁面顯示登入失敗，因為用警告窗會 beginning of crash
                    login_failed();
                }


            }catch (JSONException e){
                Log.e("joe",e.toString());
            }

    }



    //-------鍵盤收縮
    public void Close_Input_Board(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(findViewById(R.id.Sign_in_Page).getWindowToken(), 0);
    }

}