package com.example.mountaineering2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Animation topAnim,bottonAnim;
    ImageView image;
    TextView logo,slogan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottonAnim=AnimationUtils.loadAnimation(this,R.anim.botton_animation);

        image=findViewById(R.id.imageView);
        logo=findViewById(R.id.logo);
        slogan=findViewById(R.id.slogan);

        image.setAnimation(topAnim);
        logo.setAnimation(bottonAnim);
        slogan.setAnimation(bottonAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =new Intent(MainActivity.this,homeScreen.class);
                startActivity(intent);
                finish();
            }
        },3000);


    }
}