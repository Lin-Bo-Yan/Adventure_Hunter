package com.example.mountaineering2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class forgetPage extends AppCompatActivity {
     ImageView fg_backspace1;
     Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_page);

        fg_backspace1=(ImageView)findViewById(R.id.fg_backspace1);
        fg_backspace1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {backpage();}
        });

        next=(Button)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {next();}
        });

    }
    private void backpage(){
        Intent intent =new Intent(this,sign_in.class);
        startActivity(intent);
    }
    private void next(){
        Intent intent =new Intent(this,forgetPage1.class);
        startActivity(intent);
    }
}