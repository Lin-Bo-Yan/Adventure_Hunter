package com.example.mountaineering2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class findPeople extends AppCompatActivity {
    private Button goToNotYetPage;
    private Button goToCreateGroup;
    private Button joinGroupIng;
    private Button groupHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_people);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");


        //參加揪團跳轉
        goToNotYetPage =findViewById(R.id.goToNotYetPage);
        goToNotYetPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(findPeople.this,findPeoplePage1.class);
                startActivity(intent);
            }
        });

        //創建揪團跳轉
        goToCreateGroup = findViewById(R.id.goToCreateGroup);
        goToCreateGroup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(findPeople.this, findPeoplePage3.class);
                startActivity(intent);
            }
        });

        //參加中揪團跳轉
        joinGroupIng = findViewById(R.id.joinGroupIng);
        joinGroupIng.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(findPeople.this, findPeoplePage4.class);
                startActivity(intent);
            }
        });

        groupHistory = findViewById(R.id.groupHistory);
        groupHistory.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(findPeople.this, findPeoplePage6.class);
                startActivity(intent);
            }
        });

    }
}