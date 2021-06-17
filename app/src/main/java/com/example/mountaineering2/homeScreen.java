package com.example.mountaineering2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class homeScreen extends AppCompatActivity {

    private ImageView alishan, tataga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

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
            public void onClick(View v) {member_profile();}
        });

    }

    //任務
    public void openFindPeople() {
        Intent intent = new Intent(this, findPeople.class);
        startActivity(intent);

    }

    //會員資料
    public void member_profile() {
        Intent intent = new Intent(this, sign_in.class);
        startActivity(intent);
    }
}