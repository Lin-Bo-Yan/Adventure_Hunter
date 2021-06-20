package com.example.mountaineering2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class member extends AppCompatActivity {
    String realname,cash,email,birth,ID,phone;
    TextView member_name,member_Points,member_birthBay,member_email,member_ID,member_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member);


        //把從登入那邊的資料傳到這裡顯示出來
        Bundle bundle = this.getIntent().getExtras();
        Bundle bundle1 = this.getIntent().getExtras();
        if(bundle!=null){
            realname=bundle.getString("realname");
            cash=bundle.getString("cash");
            email=bundle.getString("email");
            birth=bundle.getString("birth");
            ID=bundle.getString("id");
            phone=bundle.getString("phone");
            ff();
        }else if(bundle1!=null){
            realname=bundle.getString("realname");
            cash=bundle.getString("cash");
            email=bundle.getString("email");
            birth=bundle.getString("birth");
            ID=bundle.getString("id");
            phone=bundle.getString("phone");
            ff();
        }

    }


    private void ff(){
        member_name=(TextView)findViewById(R.id.realName);
        member_name.setText(realname);
        member_Points=(TextView)findViewById(R.id.Points);
        member_Points.setText(cash+"點");
        member_birthBay=(TextView)findViewById(R.id.birth);
        member_birthBay.setText(birth);
        member_email=(TextView)findViewById(R.id.email);
        member_email.setText(email);
        member_ID=(TextView)findViewById(R.id.ID);
        member_ID.setText("⭐\uD83C\uDF1F"+ID+"\uD83C\uDF1F⭐");
        member_phone=(TextView)findViewById(R.id.phone);
        member_phone.setText(phone);
    }


    //-------去到設定
    public void go_set_up(View view) {
        //Intent intent=new Intent(this,set_up.class);
        //startActivity(intent);
        Intent goToSetUp = new Intent(member.this,set_up.class);
        Bundle bundle = new Bundle();
        bundle.putString("id",ID);
        goToSetUp.putExtras(bundle);
        startActivity(goToSetUp);
    }
}