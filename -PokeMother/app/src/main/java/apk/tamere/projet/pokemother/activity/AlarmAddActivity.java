package apk.tamere.projet.pokemother.activity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import apk.tamere.projet.pokemother.R;
import apk.tamere.projet.pokemother.alarm.AlarmReceiver;
import apk.tamere.projet.pokemother.metier.Alarm;

public class AlarmAddActivity extends AppCompatActivity implements View.OnClickListener {

    private AlarmManager alarmManager;
    private PendingIntent pending_intent;

    public final static String ACTION = "action", ACTION_LAUNCH = "launch", ACTION_STOP = "stop", GET_NEW = "newAlarm", SAVE_REPEATS = "repeatsDays";

    private TextView newDateHour, newDateDay;

    private int mYear, mMonth, mDay, mHour, mMinute;
    private boolean isOkDate, isOkTime;

    private ArrayList<Integer> repeats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        repeats = new ArrayList<>();

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        newDateDay = findViewById(R.id.newDateDay);
        newDateHour = findViewById(R.id.newDateHour);

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
                    alarm.setRepeats(repeats);

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
    protected void onSaveInstanceState(Bundle outState) {
        outState.putIntegerArrayList(SAVE_REPEATS, repeats);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        repeats = savedInstanceState.getIntegerArrayList(SAVE_REPEATS);
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
                newDateDay.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                isOkDate = true;

                TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        newDateHour.setText(hourOfDay + ":" + String.format("%02d",  minute));
                        isOkTime = true;
                    }
                }, mHour, mMinute, true);
                timePickerDialog.show();

            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.cbL:
                if(!checked) {
                    repeats.add(Calendar.MONDAY);
                }
                else
                    repeats.remove((Integer) Calendar.MONDAY);
                break;
            case R.id.cbMa:
                if(!checked) {
                    repeats.add(Calendar.TUESDAY);
                }
                else
                    repeats.remove((Integer) Calendar.TUESDAY);
                break;
            case R.id.cbMe:
                if(!checked) {
                    repeats.add(Calendar.WEDNESDAY);
                }
                else
                    repeats.remove((Integer) Calendar.WEDNESDAY);
                break;
            case R.id.cbJ:
                if(!checked) {
                    repeats.add(Calendar.THURSDAY);
                }
                else
                    repeats.remove((Integer) Calendar.THURSDAY);
                break;
            case R.id.cbV:
                if(!checked) {
                    repeats.add(Calendar.FRIDAY);
                }
                else
                    repeats.remove((Integer) Calendar.FRIDAY);
                break;
            case R.id.cbS:
                if(!checked) {
                    repeats.add(Calendar.SATURDAY);
                }
                else
                    repeats.remove((Integer) Calendar.SATURDAY);
                break;
            case R.id.cbD:
                if(!checked) {
                    repeats.add(Calendar.SUNDAY);
                }
                else
                    repeats.remove((Integer) Calendar.SUNDAY);
                break;
        }
    }
}
