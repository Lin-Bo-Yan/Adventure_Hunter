package com.example.mountaineering2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
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

public class Create_Check_Screen extends AppCompatActivity {
    TextView dateShow, mountainShow, peopleShow, sayShow, name, point;
    String pointString;
    String date, mountain, people, say, name_mountain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_check_screen);

        dateShow = findViewById(R.id.dateCheck);
        mountainShow = findViewById(R.id.mountainShow);
        peopleShow = findViewById(R.id.peopleShow);
        name = findViewById(R.id.textname);
        point = findViewById(R.id.textpoint);
        sayShow =findViewById(R.id.sayShow);

        showResult();
    }

    private void showResult() {
        Bundle bundle = getIntent().getExtras();
        date = bundle.getString("date");
        mountain = bundle.getString("mountain");
        people = bundle.getString("people");
        say = bundle.getString("sayText");
        name_mountain = bundle.getString("namemountain");
        int i = Integer.parseInt(people);
        pointString = Integer.toString(i * 100);


        dateShow.setText(date);
        mountainShow.setText(mountain);
        sayShow.setText(say);
        name.setText(name_mountain);
        point.setText(pointString);
        Log.v("joe", "point== " + pointString);


    }

    public void backToCreate(View view) {
        Intent intent = new Intent();
        intent.setClass(Create_Check_Screen.this, Create_Group_Screen.class);
        startActivity(intent);
        Toast.makeText(Create_Check_Screen.this, "取消創建，重新填寫資料!", Toast.LENGTH_SHORT).show();
    }

    public void goToGroupIngList(View view) {
        Intent intent = new Intent();
        intent.setClass(Create_Check_Screen.this, Search_Ing_Group_Screen.class);
        postHttp();
        Toast.makeText(Create_Check_Screen.this, "創建成功，前往任務頁面執行任務吧!", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    private void postHttp() {
        OkHttpClient client = new OkHttpClient();
        String url = "https://64a039731c6b.ngrok.io/api/groups";

        Integer ran = ((int)(Math.random()*49+1));

        Map<String, String> map =new HashMap();
        map.put("creator_id", ran.toString());
        map.put("start_date", date);
        map.put("mountain_name", mountain);
        map.put("description", say);
        map.put("name", name_mountain);
        map.put("points", pointString);


        RequestBody body = RequestBody.create(MediaType.parse("application/json"), new JSONObject(map).toString());

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()){
                    Log.v("joe", "response== "+response.body().string());
                }
            }
        });
    }
}