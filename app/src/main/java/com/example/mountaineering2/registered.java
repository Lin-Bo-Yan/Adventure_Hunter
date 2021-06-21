package com.example.mountaineering2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class registered extends AppCompatActivity {
    TextInputLayout reg_account,reg_password,reg_phon,reg_email,reg_userName,reg_birthday;
    // 建立OkHttpClient
    OkHttpClient client = new OkHttpClient().newBuilder().build();
    Button go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registered);
        reg_account=(TextInputLayout)findViewById(R.id.reg_Account);
        reg_password=(TextInputLayout)findViewById(R.id.reg_passWord);
        reg_phon=(TextInputLayout)findViewById(R.id.reg_phon);
        reg_email=(TextInputLayout)findViewById(R.id.reg_email);
        reg_userName=(TextInputLayout)findViewById(R.id.reg_useName);
        reg_birthday=(TextInputLayout)findViewById(R.id.reg_birthday);


        go=(Button)findViewById(R.id.reg_GO);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPOST();
            }
        });

    }

    private void sendPOST() {


        String account=reg_account.getEditText().getText().toString().trim();
        String password=reg_password.getEditText().getText().toString().trim();
        String email=reg_email.getEditText().getText().toString().trim();
        String tel=reg_phon.getEditText().getText().toString().trim();
        String realName=reg_userName.getEditText().getText().toString().trim();
        String birthday=reg_birthday.getEditText().getText().toString().trim();


        // RequestBody放要傳的參數和值
        Map<String, String> map = new HashMap();
        map.put("account",account);
        map.put("password",password);
        map.put("user_email",email);
        map.put("phone",tel);
        map.put("birthday", birthday);
        map.put("realname", realName);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), new JSONObject(map).toString());

        // 建立Request，設置連線資訊
        Request request = new Request.Builder()
                .url("https://b3f445e3a78f.ngrok.io"+"/api/users/")
                .post(body)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.v("joe", "Bad== " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    //final String myResponse = response.body().string();
                   // Log.v("joe", "ID:  " + myResponse);
                    next();
                }
            }
        });
    }






    //-------去到下一頁
    public void next() {
        Intent intent=new Intent(this,registered1.class);
        startActivity(intent);
    }
    //-------鍵盤收縮
    public void Close_Input_Board(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(findViewById(R.id.registered).getWindowToken(), 0);
    }
}