package com.example.mountaineering2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class homeScreen extends AppCompatActivity {

      private ImageView alishan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        alishan=findViewById(R.id.alishan);
        alishan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFindPeople();
            }
        });

    }
    //揪團
    public void openFindPeople(){
        Intent intent =new Intent(this,findPeople.class);
        startActivity(intent);

    }

}