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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class sign_in extends AppCompatActivity {

    private TextView registered,forgetPassWord;
    private Button loginIn;
    private RequestQueue queue;
    //----------------宣告帳號密碼---------
     String user ;
     String pass ;
     String acc;

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
            public void onClick(View v) {stringRequest(); }
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

    //-------stringRequest
    private void stringRequest(){
        queue = Volley.newRequestQueue(this);

        acc="yang9966";
        String url="https://1f8f02070c0a.ngrok.io/api/todos/account/"+acc;

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
               url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        //解析資料
                        parsing_json(response);


                        Log.e("brad", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("1.error:", error.toString());

            }
        });

        queue.add(stringRequest);



    }

    //----------判斷帳號密碼
    private void parsing_json(String json){
        try {
            //JSONArray roots=new JSONArray(json);
            JSONObject root =new JSONObject(json);
            String acc_json=root.getString("account");
            String pass_json=root.getString("password");

            user=Account.getEditText().getText().toString().trim();
            pass=passWord.getEditText().getText().toString().trim();
            String realname_json=root.getString("realname");
            String email_json=root.getString("email");
            String birth_json=root.getString("birthday");
            String cash_json=root.getString("cash");
            String id_json=root.getString("id");

            if(acc_json.equals(user)&&pass_json.equals(pass)){
                //成功

                Toast.makeText(sign_in.this,"登入成功", Toast.LENGTH_SHORT).show();
                //在做一件事情，把會員名字，點數取出來傳到會員資料頁

                   //建立意圖物件Intent
                Intent goToMember = new Intent(sign_in.this,member.class);
                   //用資料捆傳遞資料
                Bundle bundle = new Bundle();
                bundle.putString("realname",realname_json);
                bundle.putString("cash",cash_json);
                bundle.putString("email",email_json);
                bundle.putString("birth",birth_json);
                goToMember.putExtras(bundle);
                   //會帶你到該頁面
                startActivity(goToMember);


        /*
                Intent goToSetUp=new Intent(sign_in.this,set_up.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("id",id_json);
                goToSetUp.putExtras(bundle1);

                Intent goToHome=new Intent(sign_in.this,homeScreen.class);
                Bundle bundle2 = new Bundle();
                bundle2.putString("realname",realname_json);
                goToHome.putExtras(bundle2);

         */

            }else {
                //失敗

                new AlertDialog.Builder(sign_in.this).setTitle("錯誤").setMessage("帳號；"+acc_json+"密碼："+pass_json+"id："+id_json)
                        .setNegativeButton("ok",null).show();
            }


        } catch (JSONException e) {
            Log.e("2.error:", e.toString());
            //e.printStackTrace();
        }






    }


    //-------鍵盤收縮
    public void Close_Input_Board(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(findViewById(R.id.Sign_in_Page).getWindowToken(), 0);
    }

}