package com.studio.skryl.pomodoroapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.studio.skryl.pomodoroapplication.utils.AppPreferences;

public class MainActivity extends AppCompatActivity {

    AppPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = new AppPreferences(this);
        setStageTheme(preferences.getStageAct());
    }

    @Override
    protected void onStart() {
        super.onStart();
        preferences.getRunnigStatus();
    }

    private void setStageTheme(int stage) {
        switch (stage) {
            case AppPreferences.POMO_ACT :
                this.setTheme(R.style.PomoTheme);
                setContentView(R.layout.activity_main);
                break;
            case AppPreferences.REST_ACT :
                this.setTheme(R.style.RestTheme);
                setContentView(R.layout.activity_main);
                break;
        }
    }
}
