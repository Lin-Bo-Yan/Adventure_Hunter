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

public class Search_Ing_Group_Screen_Page2 extends AppCompatActivity {
    ImageView mainImageViewSearch_Ing_Group_Screen_Page2;
    TextView titleSearch_Ing_Group_Screen_Page2, descriptionSearch_Ing_Group_Screen_Page2;
    String data3, data4;
    int imagesPage4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_ing_group_screen_page2);

        //抓 xml id
        mainImageViewSearch_Ing_Group_Screen_Page2 = findViewById(R.id.mainImageViewSearch_Ing_Group_Screen_Page2);
        titleSearch_Ing_Group_Screen_Page2 = findViewById(R.id.titleSearch_Ing_Group_Screen_Page2);
        descriptionSearch_Ing_Group_Screen_Page2 = findViewById(R.id.descriptionSearch_Ing_Group_Screen_Page2);

        getData();
        setData();
    }
    private void getData() {
        if (getIntent().hasExtra("myImagePage4") && getIntent().hasExtra("data1Page4") && getIntent().hasExtra("data2Page4")) {


            //產生資料
            data3 = getIntent().getStringExtra("data1Page4");
            data4 = getIntent().getStringExtra("data2Page4");
            imagesPage4 = getIntent().getIntExtra("myImagePage4", 1);

        } else {
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }

    }

    private void setData() {

        //輸出版面
        titleSearch_Ing_Group_Screen_Page2.setText(data3);
        descriptionSearch_Ing_Group_Screen_Page2.setText(data4);
        mainImageViewSearch_Ing_Group_Screen_Page2.setImageResource(imagesPage4);
    }
    public void CancelMission(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("確定要取消？");
        alert.setMessage("點擊後將取消此任務與任務？");
        alert.setPositiveButton("確定取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setClass(Search_Ing_Group_Screen_Page2.this, Search_Ing_Group_Screen.class);
                startActivity(intent);
                Toast.makeText(Search_Ing_Group_Screen_Page2.this, "已取消此任務與任務", Toast.LENGTH_SHORT).show();
            }
        });
        alert.setNegativeButton("我再想想", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.create().show();
    }


    //跳轉至任務頁面
    public void GoToDoMission(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("確定要開始任務？");
        alert.setMessage("點擊後將跳轉至任務畫面");
        alert.setPositiveButton("前往任務畫面", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent();
//                intent.setClass(Search_Ing_Group_Screen_Page2.this, xxx.class);
//                startActivity(intent);
                Toast.makeText(Search_Ing_Group_Screen_Page2.this, "開始任務，祝您順利!!", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(Search_Ing_Group_Screen_Page2.this,task_Page.class);
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