package com.example.mountaineering2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class registered extends AppCompatActivity {
    TextInputLayout reg_account,reg_password,reg_phon,reg_email,reg_userName,reg_birthday;
    private String account,password,userName,phon,email,birthday;
    Button reg_GO;
    TextView textView;
    private JSONObject jsonObject;
    private Map<String, String> map;
    private RequestQueue requestQueue;
    String url ="https://1f8f02070c0a.ngrok.io/api/todos/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registered);
        //textView=(TextView)findViewById(R.id.ttt);


        reg_account=(TextInputLayout)findViewById(R.id.reg_Account);
        reg_password=(TextInputLayout)findViewById(R.id.reg_passWord);
        reg_userName=(TextInputLayout)findViewById(R.id.reg_useName);
        reg_phon=(TextInputLayout)findViewById(R.id.reg_phon);
        reg_email=(TextInputLayout)findViewById(R.id.reg_email);
        reg_birthday=(TextInputLayout)findViewById(R.id.reg_birthday);

        reg_GO=(Button)findViewById(R.id.reg_GO);
        reg_GO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postJsonObjectRequest();
                next();
            }
        });


    }
    //-------把資料轉JSon格式
    private void conversion_json()throws JSONException {
        account=reg_account.getEditText().getText().toString().trim();
        password=reg_password.getEditText().getText().toString().trim();
        userName=reg_userName.getEditText().getText().toString().trim();
        phon=reg_phon.getEditText().getText().toString().trim();
        email=reg_email.getEditText().getText().toString().trim();
        birthday=reg_birthday.getEditText().getText().toString().trim();

        map = new HashMap<String, String>();

        map.put("account", "TestPut");
        map.put("password", "password");

//        map.put("account", account);
//        map.put("password", password);
//        map.put("realname",userName);
//        map.put("email",email);
//        map.put("tel",phon);
//        map.put("birthday",birthday);

        jsonObject = new JSONObject(map);
    }

    //-------JsonObjectRequest
    public void postJsonObjectRequest(){

        try {
            conversion_json();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("joe","error"+e);
        }
        requestQueue = Volley.newRequestQueue(this);


        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Request.Method.POST,url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.v("joe","Json"+jsonObject);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //textView.setText("ErrorResponse:"+error.toString());
            }
        });
        requestQueue.add(jsonRequest);
    }

    //-------去到下一頁
    private void next(){
        Intent intent=new Intent(this,registered1.class);
        startActivity(intent);
    }
    //-------鍵盤收縮
    public void Close_Input_Board(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(findViewById(R.id.registered).getWindowToken(), 0);
    }
}