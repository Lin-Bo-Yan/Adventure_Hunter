package com.example.mountaineering2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Group_History_Screen_Page2 extends AppCompatActivity {
    ImageView mainImageViewGroup_History_Screen_Page2;
    TextView titleGroup_History_Screen_Page2, descriptionGroup_History_Screen_Page2, dateText, groupText, peopleText, des;


    String data5, data6, imagesPage6, groupName, startDate, peopleNum, desText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_history_screen_page2);

        //抓 xml id
        mainImageViewGroup_History_Screen_Page2 = findViewById(R.id.mainImageViewGroup_History_Screen_Page2);
        titleGroup_History_Screen_Page2 = findViewById(R.id.titleGroup_History_Screen_Page2);
        descriptionGroup_History_Screen_Page2 = findViewById(R.id.descriptionGroup_History_Screen_Page2);
        dateText = findViewById(R.id.textView41);
        groupText = findViewById(R.id.textView47);
        peopleText = findViewById(R.id.textView48);
        des = findViewById(R.id.textView46);

        getData();
        setData();
    }
    private void  getData() {
        if (getIntent().hasExtra("mountain_name") && getIntent().hasExtra("point") && getIntent().hasExtra("groupName")){


            //產生資料
            data5 = getIntent().getStringExtra("mountain_name");
            data6 = getIntent().getStringExtra("point");
            groupName = getIntent().getStringExtra("groupName");
            startDate = getIntent().getStringExtra("startDate");
            peopleNum = getIntent().getStringExtra("peopleNum");
            desText = getIntent().getStringExtra("des");

            imagesPage6 = getIntent().getStringExtra("myImagePage6");

        }else{
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }

    }

    private void setData(){

        //輸出版面
        titleGroup_History_Screen_Page2.setText(data5);
        descriptionGroup_History_Screen_Page2.setText(data6);
        dateText.setText(startDate);
        groupText.setText(groupName);
        peopleText.setText(peopleNum);
        des.setText(desText);

        Picasso.get().load(imagesPage6).into(mainImageViewGroup_History_Screen_Page2);
    }

    public void GoBackLastPage(View view) {
        Intent intent = new Intent();
        intent.setClass(Group_History_Screen_Page2.this, Group_History_Screen.class);
        startActivity(intent);
    }


    public void GoToJoinOthers(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("離開歷史查詢？");
        alert.setMessage("確定要前往參加任務頁面嗎？");
        alert.setPositiveButton("前往", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setClass(Group_History_Screen_Page2.this, Join_Group_Screen.class);
                startActivity(intent);
            }
        });
        alert.setNegativeButton("等等再說", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.create().show();
    }
}