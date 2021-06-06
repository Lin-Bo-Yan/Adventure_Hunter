package com.example.mountaineering2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class findPeoplePage2 extends AppCompatActivity {

    ImageView mainImageView;
    TextView title, descriptionPage2;
    String data1, data2;
    int myImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_people_page2);

        //抓 xml id
        mainImageView = findViewById(R.id.mainImageView);
        title = findViewById(R.id.title);
        descriptionPage2 = findViewById(R.id.descriptionPage2);

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
        descriptionPage2.setText(data2);
        mainImageView.setImageResource(myImage);
    }
}