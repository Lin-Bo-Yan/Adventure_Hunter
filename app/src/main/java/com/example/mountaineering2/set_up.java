package com.example.mountaineering2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
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
    SharedPreferences sp;
    String ID,url;
    TextInputLayout passWord,set_email,phon,birthday;
    // 建立OkHttpClient
    OkHttpClient client = new OkHttpClient().newBuilder().build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_up);

        passWord=(TextInputLayout)findViewById(R.id.set_passWord);
        set_email=(TextInputLayout)findViewById(R.id.set_email);
        phon=(TextInputLayout)findViewById(R.id.set_phon);
        birthday=(TextInputLayout)findViewById(R.id.set_birth);

        Bundle bundle = this.getIntent().getExtras();
        if(bundle!=null){
            ID=bundle.getString("id");
        }
        //存資料
        sp=getSharedPreferences("MyUser", Context.MODE_PRIVATE);

        //取資料
        sp=getApplicationContext().getSharedPreferences("MyUser", Context.MODE_PRIVATE);
        url=sp.getString("url","");
    }






    private void sendPUT() {

        String password=passWord.getEditText().getText().toString().trim();
        String email=set_email.getEditText().getText().toString().trim();
        String tel=phon.getEditText().getText().toString().trim();
        String birth=birthday.getEditText().getText().toString().trim();

        // RequestBody放要傳的參數和值
        Map<String, String> map = new HashMap();
        map.put("password",password);
        map.put("user_email",email);
        map.put("phone",tel);
        map.put("birth",birth);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), new JSONObject(map).toString());

        // 建立Request，設置連線資訊
        Request request = new Request.Builder()
                .url(url+"/api/users/"+ID)
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
                    //Log.v("joe", "會員資料:  " + myResponse);
                    parsing_json(myResponse);
                }
            }
        });
    }
     //-------解析更改後的資料
    private void parsing_json(String json){
        try {
            JSONObject root =new JSONObject(json);
            String realname_json=root.getString("realname");
            String email_json=root.getString("user_email");
            String birth_json=root.getString("birth");
            String cash_json=root.getString("cash");
            String id_json=root.getString("id");
            String phone_json= root.getString("phone");

            //利用 SharedPreferences 儲存變數
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("realname",realname_json);
            editor.putString("cash",cash_json);
            editor.putString("email",email_json);
            editor.putString("birth",birth_json);
            editor.putString("ID",id_json);
            editor.putString("phone",phone_json);
            editor.commit();

            openMember();

        }catch (JSONException e){
            Log.e("joe",e.toString());
        }
    }

    //-------去到下一頁
    public void next(View view) {
        sendPUT();
    }
    //----------會員頁面-------------------------
    public void openMember(){
        Intent intent =new Intent(this,member.class);
        startActivity(intent); }
    //-------鍵盤收縮
    public void Close_Input_Board(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(findViewById(R.id.set_up).getWindowToken(), 0);
    }

    /*
            //建立意圖物件Intent
            Intent goToMember = new Intent(set_up.this,member.class);
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
     */
}