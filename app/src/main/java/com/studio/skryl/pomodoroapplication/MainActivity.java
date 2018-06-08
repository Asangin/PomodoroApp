package com.studio.skryl.pomodoroapplication;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.studio.skryl.pomodoroapplication.utils.AppPreferences;
import com.studio.skryl.pomodoroapplication.view.ButtonsFragment;
import com.studio.skryl.pomodoroapplication.view.ContentFragment;
import com.studio.skryl.pomodoroapplication.view.TimerFragment;

public class MainActivity extends AppCompatActivity {

    TimerFragment timerFragment;
    ContentFragment contentFragment;
    ButtonsFragment buttonsFragment;

    FragmentTransaction fragmentTransaction;
    AppPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //preferences = new AppPreferences(this);
        preferences = AppPreferences.getInstance(this);
        setStageTheme(preferences.getStageAct());

        timerFragment = new TimerFragment();
        contentFragment = new ContentFragment();
        buttonsFragment = new ButtonsFragment();

        attachFragmentToActivity();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void attachFragmentToActivity() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.timer_frame, timerFragment);
        fragmentTransaction.add(R.id.content_frame, contentFragment);
        fragmentTransaction.add(R.id.button_frame, buttonsFragment);
        fragmentTransaction.commit();
    }

    private void setStageTheme(int stage) {
        switch (stage) {
            case AppPreferences.POMO_ACT :
                this.setTheme(R.style.PomoTheme);
                setContentView(R.layout.activity_main);
                break;
            case AppPreferences.REST_ACT :
            case AppPreferences.LONG_REST_ACT :
                this.setTheme(R.style.RestTheme);
                setContentView(R.layout.activity_main);
                break;
        }
    }
}
