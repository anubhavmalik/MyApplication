package com.example.anubhav.todo_list;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class ToDoDetails extends AppCompatActivity {
    TextView datetextview, timetextview;
    EditText titletextview, detailtextview;
    Button save_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_details);
        Intent i= new Intent();

        titletextview = (EditText) findViewById(R.id.titleEditText);
        titletextview.setText(i.getStringExtra(IntentConstants.to_do_title));
        detailtextview = (EditText) findViewById(R.id.detailEditText);
        detailtextview.setText(i.getStringExtra(IntentConstants.to_do_details));
        timetextview = (TextView) findViewById(R.id.timeEditText);
        timetextview.setText(i.getStringExtra(IntentConstants.to_do_time));
        timetextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(calendar.HOUR_OF_DAY);
                int minute = calendar.get(calendar.MINUTE);
                showTimePicker(ToDoDetails.this, hour, minute, false);
            }
        });
        datetextview = (TextView) findViewById(R.id.dateEditText);
        datetextview.setText(i.getStringExtra(IntentConstants.to_do_date));
        datetextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datetextview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar newCalendar = Calendar.getInstance();
                        int initialmonth = newCalendar.get(java.util.Calendar.MONTH);  // Current month
                        int initialyear = newCalendar.get(java.util.Calendar.YEAR);   // Current year
                        showDatePicker(ToDoDetails.this, initialyear, initialmonth, 1);
                    }
                });

            }
        });
        save_button=(Button) findViewById(R.id.submit_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_title=titletextview.getText().toString();
                String new_date=datetextview.getText().toString();
                String new_detail=detailtextview.getText().toString();
                String new_time= timetextview.getText().toString();
                ToDoOpenHelper toDoOpenHelper= ToDoOpenHelper.getTodoOpenHelperInstance(ToDoDetails.this);
                SQLiteDatabase database= toDoOpenHelper.getWritableDatabase();
                ContentValues contentValues= new ContentValues();
                contentValues.put(toDoOpenHelper.title,new_title);
                contentValues.put(toDoOpenHelper.date,new_date);
                contentValues.put(toDoOpenHelper.time,new_time);
                contentValues.put(toDoOpenHelper.detail,new_detail);

                database.insert(toDoOpenHelper.tablename,null,contentValues);
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    public void showDatePicker(Context context, int year, int month, int defaultdate) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                datetextview.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        }, year, month, 1);
        datePickerDialog.show();
    }

    public void showTimePicker(Context context, int hour, int minute, boolean mode) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time, minute_string = "";
                if (minute < 10) {
                    minute_string = "0" + minute;
                } else if (minute > 9) {
                    minute_string = "" + minute;
                }
                if (hourOfDay < 12) {
                    time = "am";

                } else {
                    time = "pm";
                    hourOfDay = hourOfDay - 12;

                }
                if (hourOfDay == 0) {
                    hourOfDay = 12;
                }
                timetextview.setText(hourOfDay + ":" + minute_string + " " + time);
            }
        }, hour, minute, false);
        timePickerDialog.show();
    }
}
