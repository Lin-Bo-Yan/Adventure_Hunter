package com.example.mountaineering2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.net.URL;

public class homeScreen extends AppCompatActivity {

//    private ImageView alishan, tataga,member_pic;
    CardView member_card, mission_card, rank_card, weather_card, point_card;
    String ID;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        sp=getApplicationContext().getSharedPreferences("MyUser", Context.MODE_PRIVATE);

        //會員系統
        member_card = findViewById(R.id.member_card);
        member_card.setOnClickListener(new View.OnClickListener() {
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

        //任務系統
        mission_card = findViewById(R.id.mission_card);
        mission_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              openFindPeople();
            }
        });

        //排行系統
        rank_card = findViewById(R.id.rank_card);
        rank_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Open_rank();
            }
        });

        //天氣系統
        weather_card = findViewById(R.id.weather_card);
        weather_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weater_Page();
            }
        });

        //交易系統
        point_card = findViewById(R.id.point_card);
        point_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotUrl("https://traveladventurehunter.com/%e7%92%b0%e4%bf%9d%e8%a3%9d%e5%82%99%e5%95%86%e5%9f%8e/");
            }
        });


//        alishan = (ImageView) findViewById(R.id.tataga2);
//        alishan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openFindPeople();
//            }
//        });

//        tataga = (ImageView) findViewById(R.id.tataga);
//        tataga.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) { }
//        });

//        member_pic=(ImageView)findViewById(R.id.member_pic);
//        member_pic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ID=sp.getString("ID","");
//                if(ID==""){
//                    member_profile();
//                }else {
//                    go_member();
//                }
//            }
//        });
    }

    private void gotUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
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

    public void Open_rank() {
        Intent intent = new Intent(this, Rank_page.class);
        startActivity(intent);
    }

    public void weater_Page() {
        Intent intent = new Intent(this, weater_Page.class);
        startActivity(intent);
    }

}