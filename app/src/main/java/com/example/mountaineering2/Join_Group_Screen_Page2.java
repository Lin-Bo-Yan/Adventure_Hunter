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

public class Join_Group_Screen_Page2 extends AppCompatActivity {
    ImageView mainImageView;
    TextView title, descriptionJoin_Group_Screen_Page2;
    String data1, data2;
    int myImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_group_screen_page2);

        //抓 xml id
        mainImageView = findViewById(R.id.mainImageView);
        title = findViewById(R.id.title);
        descriptionJoin_Group_Screen_Page2 = findViewById(R.id.descriptionJoin_Group_Screen_Page2);

        getData();
        setData();
    }
    private void  getData() {
        if (getIntent().hasExtra("myImage") && getIntent().hasExtra("data1") && getIntent().hasExtra("data2")){


            //產生資料
            data1 = getIntent().getStringExtra("data1");
            data2 = getIntent().getStringExtra("data2");
            myImage = getIntent().getIntExtra("myImage", 1);

        }else{
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }

    }

    private void setData(){

        //輸出版面
        title.setText(data1);
        descriptionJoin_Group_Screen_Page2.setText(data2);
        mainImageView.setImageResource(myImage);
    }


    public void goToGroupIngList(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("確定要參加嗎？");
        alert.setMessage("參加後將會通知揪團創建者");
        alert.setPositiveButton("確定參加", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setClass(Join_Group_Screen_Page2.this, Search_Ing_Group_Screen.class);
                startActivity(intent);
            }
        });
        alert.setNegativeButton("我再想想", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.show();


    }
}