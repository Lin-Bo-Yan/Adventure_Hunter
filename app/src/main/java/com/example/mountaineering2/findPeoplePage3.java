package com.example.mountaineering2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class findPeoplePage3 extends AppCompatActivity {
    String s1[];
    String s2[];

    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_people_page3);

        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);

        s1 = getResources().getStringArray(R.array.mountain_list);
        s2 = getResources().getStringArray(R.array.mountain_people);

        ArrayAdapter<String> lunchList = new ArrayAdapter<>(findPeoplePage3.this, R.layout.support_simple_spinner_dropdown_item, s1);
        ArrayAdapter<String> lunchList2 = new ArrayAdapter<>(findPeoplePage3.this, R.layout.support_simple_spinner_dropdown_item, s2);

        spinner.setAdapter(lunchList);
        spinner2.setAdapter(lunchList2);



        final TextView dateText = (TextView)findViewById(R.id.dateText);
        Button dateButton = (Button)findViewById(R.id.dateButton);
        dateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                new DatePickerDialog(findPeoplePage3.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String format = "您設定的日期為:"+ setDateFormat(year,month,day);
                        dateText.setText(format);
                    }

                }, mYear,mMonth, mDay).show();
            }

        });

    }

    private String setDateFormat(int year,int monthOfYear,int dayOfMonth){
        return String.valueOf(year) + "-"
                + String.valueOf(monthOfYear + 1) + "-"
                + String.valueOf(dayOfMonth);
    }

}