package com.example.mountaineering2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class findPeoplePage5 extends AppCompatActivity {
    ImageView mainImageViewPage5;
    TextView titlePage5, descriptionPage5;
    String data3, data4;
    int imagesPage4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_people_page5);

        //        //抓 xml id
        mainImageViewPage5 = findViewById(R.id.mainImageViewPage5);
        titlePage5 = findViewById(R.id.titlePage5);
        descriptionPage5 = findViewById(R.id.descriptionPage5);

        getData();
        setData();
    }
    private void  getData() {
        if (getIntent().hasExtra("myImagePage4") && getIntent().hasExtra("data1Page4") && getIntent().hasExtra("data2Page4")){


            //產生資料
            data3 = getIntent().getStringExtra("data1Page4");
            data4 = getIntent().getStringExtra("data2Page4");
            imagesPage4 = getIntent().getIntExtra("myImagePage4", 1);

        }else{
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }

    }

    private void setData(){

        //輸出版面
        titlePage5.setText(data3);
        descriptionPage5.setText(data4);
        mainImageViewPage5.setImageResource(imagesPage4);
    }
}