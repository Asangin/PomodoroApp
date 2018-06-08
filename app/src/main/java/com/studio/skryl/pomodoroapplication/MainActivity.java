package com.studio.skryl.pomodoroapplication;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.studio.skryl.pomodoroapplication.utils.AppPreferences;
import com.studio.skryl.pomodoroapplication.utils.Constants;
import com.studio.skryl.pomodoroapplication.view.ButtonsFragment;
import com.studio.skryl.pomodoroapplication.view.ContentFragment;
import com.studio.skryl.pomodoroapplication.view.TimerFragment;

public class MainActivity extends AppCompatActivity implements ButtonsFragment.ButtonsFragmentListener {

    TimerFragment timerFragment;
    ContentFragment contentFragment;
    ButtonsFragment buttonsFragment;

    FragmentTransaction fragmentTransaction;
    AppPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(Constants.TAG, "MainActivity. onCreate. ");
        super.onCreate(savedInstanceState);
        preferences = AppPreferences.getInstance(this);
        setStageTheme(preferences.getStageAct());

        timerFragment = new TimerFragment();
        contentFragment = new ContentFragment();
        buttonsFragment = new ButtonsFragment();

        attachFragmentToActivity();
    }

    private void attachFragmentToActivity() {
        Log.d(Constants.TAG, "MainActivity. attachFragmentToActivity. ");
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.timer_frame, timerFragment);
        fragmentTransaction.add(R.id.content_frame, contentFragment);
        fragmentTransaction.add(R.id.button_frame, buttonsFragment);
        fragmentTransaction.commit();
    }

    private void setStageTheme(int stage) {
        Log.d(Constants.TAG, "MainActivity. setStageTheme. "+ stage);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setup_menu:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void refreshTime() {
        timerFragment.timeSetup();
    }
}
