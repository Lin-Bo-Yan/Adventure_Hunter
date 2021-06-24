package com.example.mountaineering2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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

public class Search_Ing_Group_Screen_Page2 extends AppCompatActivity {
    ImageView mainImageViewSearch_Ing_Group_Screen_Page2;
    TextView titleSearch_Ing_Group_Screen_Page2, descriptionSearch_Ing_Group_Screen_Page2;
    String data3, data4, imagesPage4;
    int groupId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_ing_group_screen_page2);

        //抓 xml id
        mainImageViewSearch_Ing_Group_Screen_Page2 = findViewById(R.id.mainImageViewSearch_Ing_Group_Screen_Page2);
        titleSearch_Ing_Group_Screen_Page2 = findViewById(R.id.titleSearch_Ing_Group_Screen_Page2);
        descriptionSearch_Ing_Group_Screen_Page2 = findViewById(R.id.descriptionSearch_Ing_Group_Screen_Page2);

        getData();
        setData();
    }
    private void getData() {
        if (getIntent().hasExtra("myImagePage4") && getIntent().hasExtra("data1Page4") && getIntent().hasExtra("data2Page4")) {


            //產生資料
            data3 = getIntent().getStringExtra("data1Page4");
            data4 = getIntent().getStringExtra("data2Page4");
            imagesPage4 = getIntent().getStringExtra("myImagePage4");
            groupId = getIntent().getIntExtra("groupId",1);
            Log.v("joe", "Id= "+groupId);

        } else {
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }

    }

    private void setData() {

        //輸出版面
        titleSearch_Ing_Group_Screen_Page2.setText(data3);
        descriptionSearch_Ing_Group_Screen_Page2.setText(data4);
        Picasso.get().load(imagesPage4).into(mainImageViewSearch_Ing_Group_Screen_Page2);
    }
    public void CancelMission(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("確定要取消？");
        alert.setMessage("點擊後將取消此任務？");
        alert.setPositiveButton("確定取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateGroupInfo();
                Intent intent = new Intent();
                intent.setClass(Search_Ing_Group_Screen_Page2.this, Search_Ing_Group_Screen.class);
                startActivity(intent);
                Toast.makeText(Search_Ing_Group_Screen_Page2.this, "已取消此任務", Toast.LENGTH_SHORT).show();
            }
        });
        alert.setNegativeButton("我再想想", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.create().show();
    }

    private void updateGroupInfo() {
        SharedPreferences sp =getSharedPreferences("MyUser", MODE_PRIVATE);
        String url1 = sp.getString("url",null);
        String urlId =sp.getString("ID", "42");
        OkHttpClient client = new OkHttpClient();
        String url = url1+"/api/groups/";

        Map<String, String> map =new HashMap();
        map.put("attendee", "");

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), new JSONObject(map).toString());

        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()){
                }
            }
        });
    }



    //跳轉至任務頁面
    public void GoToDoMission(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("確定要開始任務？");
        alert.setMessage("點擊後將跳轉至任務畫面");
        alert.setPositiveButton("前往任務畫面", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Search_Ing_Group_Screen_Page2.this, "開始任務，祝您順利!!", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(Search_Ing_Group_Screen_Page2.this, task_Page.class);
                intent.putExtra("GroupId", groupId);
                startActivity(intent);
                //頁面跳去任務
            }
        });
        alert.setNegativeButton("等等再說", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.create().show();

    }
}