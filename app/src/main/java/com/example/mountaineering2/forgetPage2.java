package com.example.mountaineering2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;

public class forgetPage2 extends AppCompatActivity {
    Button OK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_page2);

        OK=(Button)findViewById(R.id.OK);
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {okPage();}
        });
    }
    //-------------------下一頁
    private void okPage(){
        Intent intent =new Intent(this,forgetPage3.class);
        startActivity(intent);
    }
    //-------鍵盤收縮
    public void Close_Input_Board(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(findViewById(R.id.forget_page2).getWindowToken(), 0);
    }
}