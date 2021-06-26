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
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Join_Group_Screen_Page2 extends AppCompatActivity {
    ImageView mainImageView;
    TextView title, descriptionJoin_Group_Screen_Page2, dateText, groupText, peopleText, desText;
    String data1, data2, date, groupName, people, des;
//    int myImage;
    String myImage;
    int groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_group_screen_page2);

        //抓 xml id
        mainImageView = findViewById(R.id.mainImageView);
        title = findViewById(R.id.title);
        descriptionJoin_Group_Screen_Page2 = findViewById(R.id.descriptionJoin_Group_Screen_Page2);
        dateText= findViewById(R.id.textView27);
        groupText= findViewById(R.id.textView32);
        peopleText= findViewById(R.id.textView33);
        desText= findViewById(R.id.textView34);

        getData();
        setData();
    }


    private void  getData() {
        if (getIntent().hasExtra("myImage") && getIntent().hasExtra("mountain") && getIntent().hasExtra("point")){


            //產生資料
            data1 = getIntent().getStringExtra("mountain");
            data2 = getIntent().getStringExtra("point");
            date = getIntent().getStringExtra("date");
            groupName = getIntent().getStringExtra("groupName");
            people = getIntent().getStringExtra("people");
            des = getIntent().getStringExtra("des");

            myImage = getIntent().getStringExtra("myImage");
            groupId = getIntent().getIntExtra("groupId", 1);
        }else{
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }

    }

    private void setData(){

        //輸出版面
        title.setText(data1);
        descriptionJoin_Group_Screen_Page2.setText(data2);
        dateText.setText(date);
        groupText.setText(groupName);
        peopleText.setText(people);
        desText.setText(des);
        Picasso.get().load(myImage).into(mainImageView);
    }


    public void goToGroupIngList(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("確定要參加嗎？");
        alert.setMessage("參加後將會通知任務創建者");
        alert.setPositiveButton("確定參加", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateGroupInfo();
                Intent intent = new Intent();
                intent.setClass(Join_Group_Screen_Page2.this, findPeople.class);
                startActivity(intent);
            }
        });
        alert.setNegativeButton("我再想想", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.show();


    }

    private void updateGroupInfo() {
        SharedPreferences sp =getSharedPreferences("MyUser", MODE_PRIVATE);
        String url1 = sp.getString("url",null);
        String urlId =sp.getString("ID", "42");
        OkHttpClient client = new OkHttpClient();
        String url = url1+"/api/groups/"+groupId;

        Map<String, String> map = new HashMap<>();
        map.put("attendee", urlId);

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
            }
        });
    }
}