package com.studio.skryl.pomodoroapplication;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.studio.skryl.pomodoroapplication.utils.AppPreferences;

public class SettingActivity extends AppCompatActivity {

    String[] pomodoroTime;
    String[] restTime;
    String[] longRestTime;

    AppPreferences preferences;
    Switch vibrateSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        preferences = AppPreferences.getInstance(this);

        vibrateSet = findViewById(R.id.vibrate_set);
        vibrateSet.setChecked(preferences.getVibrateOption());
        vibrateSet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) preferences.setVibrateOption(true);
                else preferences.setVibrateOption(false);
            }
        });

        pomodoroTime = getResources().getStringArray(R.array.pomodoro_minutes);
        ArrayAdapter<String> adapter_pomodoro = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pomodoroTime);
        adapter_pomodoro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner pomodoroTimeList = (Spinner) findViewById(R.id.pomodoro_time_list);
        pomodoroTimeList.setAdapter(adapter_pomodoro);
        pomodoroTimeList.setPrompt("Pomodoro duration");
        pomodoroTimeList.setSelection(preferences.getPositionPomodoro());
        pomodoroTimeList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        preferences.setTimePomodoro(5000, position);
                        break;
                    case 1:
                        preferences.setTimePomodoro(900000, position);
                        break;
                    case 2:
                        preferences.setTimePomodoro(1200000, position);
                        break;
                    case 3:
                        preferences.setTimePomodoro(1500000, position);
                        break;
                    case 4:
                        preferences.setTimePomodoro(1800000, position);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        restTime = getResources().getStringArray(R.array.rest_minutes);
        ArrayAdapter<String> adapter_rest = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, restTime);
        adapter_rest.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner restTimeList = (Spinner) findViewById(R.id.rest_time_list);
        restTimeList.setAdapter(adapter_rest);
        restTimeList.setPrompt("Rest duration");
        restTimeList.setSelection(preferences.getPositionRest());
        restTimeList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        preferences.setTimeRest(5000, position);
                        break;
                    case 1:
                        preferences.setTimeRest(300000, position);
                        break;
                    case 2:
                        preferences.setTimeRest(600000, position);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        longRestTime = getResources().getStringArray(R.array.long_rest_minutes);
        ArrayAdapter<String> long_adapter_rest = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, longRestTime);
        long_adapter_rest.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner longRestTimeList = (Spinner) findViewById(R.id.long_rest_time_list);
        longRestTimeList.setAdapter(long_adapter_rest);
        longRestTimeList.setPrompt("Long rest duration");
        longRestTimeList.setSelection(preferences.getPositionLongRest());
        longRestTimeList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        preferences.setTimeLongRest(5000, position);
                        break;
                    case 1:
                        preferences.setTimeLongRest(600000, position);
                        break;
                    case 2:
                        preferences.setTimeLongRest(900000, position);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

}
