package com.example.mountaineering2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class forgetPage1 extends AppCompatActivity {
    ImageView fg_backspace1;
    RelativeLayout relativeLayout1,relativeLayout2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_page1);

        fg_backspace1=(ImageView)findViewById(R.id.fg_backspace1);
        fg_backspace1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {backpage();}
        });

        relativeLayout1=(RelativeLayout)findViewById(R.id.relativeLayout1);
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {nextPage();}
        });

        relativeLayout2=(RelativeLayout)findViewById(R.id.relativeLayout2);
        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {nextPage();}
        });
    }
    //-------------------回上一頁
    private void backpage(){
        Intent intent =new Intent(this,forgetPage.class);
        startActivity(intent);
    }
    //-------------------到第2頁
    private void nextPage(){
        Intent intent =new Intent(this,forgetPage2.class);
        startActivity(intent);
    }

}