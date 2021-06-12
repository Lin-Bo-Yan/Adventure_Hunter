package com.example.mountaineering2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class forgetPage3 extends AppCompatActivity {
    Button OK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_page3);

        OK=(Button)findViewById(R.id.OK);
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {okPage();}
        });
    }
    //-------------------下一頁
    private void okPage(){
        Intent intent =new Intent(this,sign_in.class);
        startActivity(intent);
    }
}