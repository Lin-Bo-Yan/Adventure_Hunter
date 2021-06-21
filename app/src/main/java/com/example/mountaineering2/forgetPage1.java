package com.example.mountaineering2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class forgetPage1 extends AppCompatActivity {
    RelativeLayout relativeLayout1,relativeLayout2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_page1);

        relativeLayout1=(RelativeLayout)findViewById(R.id.relativeLayout1);
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {phonPage();}
        });

        relativeLayout2=(RelativeLayout)findViewById(R.id.relativeLayout2);
        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {emailPage();}
        });
    }

    //-------------------到手機認證頁面
    private void phonPage(){
        Intent intent =new Intent(this,forgetPage4.class);
        startActivity(intent);
    }
    //-------------------到信箱認證頁面
    private void emailPage(){
        Intent intent =new Intent(this,forgetPage.class);
        startActivity(intent);
    }

}