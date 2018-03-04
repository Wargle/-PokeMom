package apk.tamere.projet.pokemother.activity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import apk.tamere.projet.pokemother.R;
import apk.tamere.projet.pokemother.alarm.AlarmReceiver;
import apk.tamere.projet.pokemother.metier.Alarm;

public class AlarmAddActivity extends AppCompatActivity implements View.OnClickListener {

    private AlarmManager alarmManager;
    private PendingIntent pending_intent;

    public final static String ACTION = "action", ACTION_LAUNCH = "launch", ACTION_STOP = "stop", GET_NEW = "newAlarm";
    //private final String ALARM_CLOCK_H = "alarm_clock_h", ALARM_CLOCK_M = "alarm_clock_m";

    /*Button buttonSetAlarm, btnTimePicker*/;
    TextView newDateHour, newDateDay;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private boolean isOkDate, isOkTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //buttonSetAlarm = findViewById(R.id.buttonSetAlarm);
        //btnTimePicker = findViewById(R.id.btn_time);
        //txtDate = findViewById(R.id.in_date);
        //txtTime = findViewById(R.id.in_time);
        newDateDay = findViewById(R.id.newDateDay);
        newDateHour = findViewById(R.id.newDateHour);

        //btnDatePicker.setOnClickListener(this);
        newDateHour.setOnClickListener(this);
        newDateDay.setOnClickListener(this);

        final Intent myIntent = new Intent(this, AlarmReceiver.class);
        final Calendar calendar = Calendar.getInstance();

        Button start_alarm = findViewById(R.id.start_alarm);
        start_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOkDate && isOkTime) {
                    Alarm alarm = new Alarm(mDay, mMonth, mYear, mHour, mMinute, true);

                    calendar.set(mYear, mMonth, mDay, mHour, mMinute);
                    calendar.set(Calendar.SECOND, 0);

                    myIntent.putExtra(AlarmAddActivity.ACTION, AlarmAddActivity.ACTION_LAUNCH);
                    pending_intent = PendingIntent.getBroadcast(AlarmAddActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending_intent);

                    Intent intent = new Intent();
                    intent.putExtra(GET_NEW, alarm);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        final Context context = this;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear += 1;
                newDateHour.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                isOkDate = true;

                TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        newDateDay.setText(hourOfDay + ":" + String.format("%02d",  minute));
                        isOkTime = true;
                    }
                }, mHour, mMinute, true);
                timePickerDialog.show();

            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
