package com.example.mountaineering2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class findPeople extends AppCompatActivity {
    private Button goToNotYetPage;
    private Button goToCreateGroup;
    private Button joinGroupIng;
    private Button groupHistory;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_people);


        //存放api網址
        SharedPreferences sp = getSharedPreferences("MyUser", MODE_PRIVATE);
        String Id =sp.getString("ID", "42");
        Log.v("joe","showID= "+Id);

        //創建任務跳轉
        goToCreateGroup = findViewById(R.id.goToCreateGroup);
        goToCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Id != null){
                    Intent intent = new Intent();
                    intent.setClass(findPeople.this, Create_Group_Screen.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent();
                    intent.setClass(findPeople.this, sign_in.class);
                    startActivity(intent);
                    Toast.makeText(findPeople.this,"請先登入會員",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //參加任務跳轉
        goToNotYetPage = findViewById(R.id.goToNotYetPage);
        goToNotYetPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Id != null){
                    Intent intent = new Intent();
                    intent.setClass(findPeople.this, Join_Group_Screen.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent();
                    intent.setClass(findPeople.this, sign_in.class);
                    startActivity(intent);
                    Toast.makeText(findPeople.this,"請先登入會員",Toast.LENGTH_SHORT).show();
                }
            }
        });




        //歷史參加任務查詢
        groupHistory = findViewById(R.id.groupHistory);
        groupHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Id != null){
                    Intent intent = new Intent();
                    intent.setClass(findPeople.this, Group_History_Screen.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent();
                    intent.setClass(findPeople.this, sign_in.class);
                    startActivity(intent);
                    Toast.makeText(findPeople.this,"請先登入會員",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //正在進行中任務跳轉
        joinGroupIng = findViewById(R.id.joinGroupIng);
        joinGroupIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Id != null){
                    Intent intent = new Intent();
                    intent.setClass(findPeople.this, Search_Ing_Group_Screen.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent();
                    intent.setClass(findPeople.this, sign_in.class);
                    startActivity(intent);
                    Toast.makeText(findPeople.this,"請先登入會員",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}