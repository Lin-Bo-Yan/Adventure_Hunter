package com.example.mountaineering2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class forgetPage3 extends AppCompatActivity {
    Button OK;
    ImageView fg_backspace1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_page3);

        OK=(Button)findViewById(R.id.OK);
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {okPage();}
        });

        fg_backspace1=(ImageView)findViewById(R.id.fg_backspace1);
        fg_backspace1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {backpage();}
        });
    }
    //-------------------下一頁
    private void okPage(){
        Intent intent =new Intent(this,sign_in.class);
        startActivity(intent);
    }
    //-------------------回上一頁
    private void backpage(){
        Intent intent =new Intent(this,forgetPage.class);
        startActivity(intent);
    }
}