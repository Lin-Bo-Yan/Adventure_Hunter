package com.example.mountaineering2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class registered extends AppCompatActivity {
    TextInputLayout reg_account,reg_password,reg_phon,reg_email,reg_userName;
    Button reg_GO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registered);

        reg_GO=(Button)findViewById(R.id.reg_GO);
        reg_GO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {next();}
        });

        /*
        //取得文字框字串
        String account =reg_account.getEditText().getText().toString().trim();
        String password=reg_password.getEditText().getText().toString().trim();
        String phon=reg_phon.getEditText().getText().toString().trim();
        String email=reg_email.getEditText().getText().toString().trim();
        String useName=reg_userName.getEditText().getText().toString().trim();
        */

    }
    private void next(){
        Intent intent=new Intent(this,registered1.class);
        startActivity(intent);
    }
    //-------鍵盤收縮
    public void Close_Input_Board(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(findViewById(R.id.registered).getWindowToken(), 0);
    }
}