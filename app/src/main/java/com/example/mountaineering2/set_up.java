package com.example.mountaineering2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class set_up extends AppCompatActivity {
    String ID;
    TextInputLayout passWord,set_email,phon;
    // 建立OkHttpClient
    OkHttpClient client = new OkHttpClient().newBuilder().build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_up);

        passWord=(TextInputLayout)findViewById(R.id.set_passWord);
        set_email=(TextInputLayout)findViewById(R.id.set_email);
        phon=(TextInputLayout)findViewById(R.id.set_phon);

        Bundle bundle = this.getIntent().getExtras();
        if(bundle!=null){
            ID=bundle.getString("id");
            sendPUT();
        }

    }






    private void sendPUT() {



        String password=passWord.getEditText().getText().toString().trim();
        String email=set_email.getEditText().getText().toString().trim();
        String tel=phon.getEditText().getText().toString().trim();


        // RequestBody放要傳的參數和值
        Map<String, String> map = new HashMap();
        map.put("password",password);
        map.put("user_email",email);
        map.put("phone",tel);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), new JSONObject(map).toString());

        // 建立Request，設置連線資訊
        Request request = new Request.Builder()
                .url("https://5d237dac21c6.ngrok.io/"+"/api/users/"+ID)
                .put(body)
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
                    final String myResponse = response.body().string();
                    Log.v("joe", "會員資料:  " + myResponse);
                    //做到這裡
                }
            }
        });
    }









    //-------去到下一頁
    public void next(View view) {
        Intent intent=new Intent(this,set_up1.class);
        startActivity(intent);
    }
    //-------鍵盤收縮
    public void Close_Input_Board(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(findViewById(R.id.set_up).getWindowToken(), 0);
    }
}