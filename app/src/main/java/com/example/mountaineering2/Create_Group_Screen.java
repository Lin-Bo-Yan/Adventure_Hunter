package com.example.mountaineering2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Create_Group_Screen extends AppCompatActivity {

    String s1[];
    String s2[];
    EditText createSay;
    String spinner1;
    String spinner2;
    TextView dateText;
    String data;

    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group_screen);

        createSay = findViewById(R.id.createSay);
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);

        spinner.setSelection(0, true);
        spinner.setOnItemSelectedListener(listener);

        spinner2.setSelection(0, true);
        spinner2.setOnItemSelectedListener(listener2);

        s1 = getResources().getStringArray(R.array.mountain_list);
        s2 = getResources().getStringArray(R.array.mountain_people);


        ArrayAdapter<String> lunchList = new ArrayAdapter<>(Create_Group_Screen.this, R.layout.support_simple_spinner_dropdown_item, s1);
        ArrayAdapter<String> lunchList2 = new ArrayAdapter<>(Create_Group_Screen.this, R.layout.support_simple_spinner_dropdown_item, s2);

        spinner.setAdapter(lunchList);
        spinner2.setAdapter(lunchList2);


        dateText = (TextView) findViewById(R.id.dateText);
        Button dateButton = (Button) findViewById(R.id.dateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog pickerDialog =new DatePickerDialog(Create_Group_Screen.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String format = "您所選的日期為:" + setDateFormat(year, month, day);
                        dateText.setText(format);
                        data = setDateFormat(year, month, day);
                    }
                }, mYear, mMonth, mDay);

                pickerDialog.getDatePicker().setMinDate(c.getTime().getTime());
                pickerDialog.show();
            }
        });
    }
    Spinner.OnItemSelectedListener listener = new Spinner.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //
            spinner1 = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    Spinner.OnItemSelectedListener listener2 = new Spinner.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //抓spinner2選的值
            spinner2 = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    private String setDateFormat(int year, int monthOfYear, int dayOfMonth) {
        return String.valueOf(year) + "-"
                + String.valueOf(monthOfYear + 1) + "-"
                + String.valueOf(dayOfMonth);
    }

    public void submitCreate(View view) {

        if (data != null){
            String date = data.toString();
            String mountain = spinner1.toString();
            String people = spinner2.toString();
            String saySome;

            saySome = createSay.getText().toString();
            Intent intent = new Intent(this, Create_Check_Screen.class);
            Bundle bundle = new Bundle();
            bundle.putString("date", date);
            bundle.putString("mountain", mountain);
            bundle.putString("people", people);
            bundle.putString("sayText", saySome);

            intent.putExtras(bundle);
            startActivity(intent);
        }else{
            Toast.makeText(Create_Group_Screen.this, "請檢查是否填寫正確資訊", Toast.LENGTH_SHORT).show();
        }
    }
    public void CloseInputKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(findViewById(R.id.Create_bg).getWindowToken(), 0);
    }
}