package com.example.mountaineering2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Create_Check_Screen extends AppCompatActivity {
    TextView dateShow, mountainShow, peopleShow, sayShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_check_screen);

        dateShow = findViewById(R.id.dateCheck);
        mountainShow = findViewById(R.id.mountainShow);
        peopleShow = findViewById(R.id.peopleShow);
        sayShow = findViewById(R.id.sayShow);

        showResult();
    }
    private void showResult() {
        Bundle bundle = getIntent().getExtras();
        String date = bundle.getString("date");
        String mountain = bundle.getString("mountain");
        String people = bundle.getString("people");
        String say = bundle.getString("sayText");

        dateShow.setText(date);
        mountainShow.setText(mountain);
        peopleShow.setText(people);
        sayShow.setText(say);
    }
    public void backToCreate(View view) {
        Intent intent = new Intent();
        intent.setClass(Create_Check_Screen.this, Create_Group_Screen.class);
        startActivity(intent);
        Toast.makeText(Create_Check_Screen.this, "取消創建，重新填寫資料!",  Toast.LENGTH_SHORT).show();
    }
    public void goToGroupIngList(View view) {
        Intent intent = new Intent();
        intent.setClass(Create_Check_Screen.this, Search_Ing_Group_Screen.class);
        startActivity(intent);
        Toast.makeText(Create_Check_Screen.this, "創建成功，前往任務頁面執行任務吧!",  Toast.LENGTH_SHORT).show();
    }
}