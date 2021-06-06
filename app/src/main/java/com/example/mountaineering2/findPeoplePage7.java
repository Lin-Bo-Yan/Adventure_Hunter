package com.example.mountaineering2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class findPeoplePage7 extends AppCompatActivity {

    ImageView mainImageViewPage7;
    TextView titlePage7, descriptionPage7;


    String data5, data6;
    int imagesPage6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_people_page7);

        //抓 xml id
        mainImageViewPage7 = findViewById(R.id.mainImageViewPage7);
        titlePage7 = findViewById(R.id.titlePage7);
        descriptionPage7 = findViewById(R.id.descriptionPage7);

        getData();
        setData();
    }
    private void  getData() {
        if (getIntent().hasExtra("myImagePage6") && getIntent().hasExtra("data1Page6") && getIntent().hasExtra("data1Page6")){


            //產生資料
            data5 = getIntent().getStringExtra("data1Page6");
            data6 = getIntent().getStringExtra("data1Page6");
            imagesPage6 = getIntent().getIntExtra("myImagePage6", 1);

        }else{
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }

    }

    private void setData(){

        //輸出版面
        titlePage7.setText(data5);
        descriptionPage7.setText(data6);
        mainImageViewPage7.setImageResource(imagesPage6);
    }
}