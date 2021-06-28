package com.example.mountaineering2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class homeScreen extends AppCompatActivity {

    private ImageView alishan, tataga,member_pic;
    String ID;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        sp=getApplicationContext().getSharedPreferences("MyUser", Context.MODE_PRIVATE);

        alishan = (ImageView) findViewById(R.id.tataga2);
        alishan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFindPeople();
            }
        });

        tataga = (ImageView) findViewById(R.id.tataga);
        tataga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { }
        });

        member_pic=(ImageView)findViewById(R.id.member_pic);
        member_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID=sp.getString("ID","");
                if(ID==""){
                    member_profile();
                }else {
                    go_member();
                }
            }
        });
    }

    //任務
    public void openFindPeople() {
        Intent intent = new Intent(this, findPeople.class);
        startActivity(intent);

    }

    //登入畫面
    public void member_profile() {
        Intent intent = new Intent(this, sign_in.class);
        startActivity(intent);
    }
    /*
    //登出會員
    public void go_member(View view) {  
        SharedPreferences.Editor editor=sp.edit();
        editor.remove("ID");
        editor.commit();
        Toast.makeText(homeScreen.this,"會員登出",Toast.LENGTH_SHORT);
    }

     */

    //去到會員資料
    public void go_member() {
        Intent intent = new Intent(this, member.class);
        startActivity(intent);

    }

    public void task(View view) {
        Intent intent = new Intent(this, task_Page.class);
        startActivity(intent);
    }

}