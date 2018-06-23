package com.studio.skryl.pomodoroapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.studio.skryl.pomodoroapplication.backgrounds.TimerService;
import com.studio.skryl.pomodoroapplication.utils.AppPreferences;
import com.studio.skryl.pomodoroapplication.utils.Constants;
import com.studio.skryl.pomodoroapplication.utils.NotificationUtils;
import com.studio.skryl.pomodoroapplication.view.ButtonsFragment;
import com.studio.skryl.pomodoroapplication.view.ContentFragment;
import com.studio.skryl.pomodoroapplication.view.DialogEventListener;
import com.studio.skryl.pomodoroapplication.view.RestDialogFragment;
import com.studio.skryl.pomodoroapplication.view.TimerFragment;
import com.studio.skryl.pomodoroapplication.view.WorkDialogFragment;

public class MainActivity extends AppCompatActivity implements ButtonsFragment.ButtonsFragmentListener, DialogEventListener {

    TimerFragment timerFragment;
    ContentFragment contentFragment;
    ButtonsFragment buttonsFragment;

    FragmentTransaction fragmentTransaction;
    AppPreferences preferences;
    NotificationUtils notificationUtils;
    private int notifID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(Constants.TAG, "MainActivity. onCreate. ");
        super.onCreate(savedInstanceState);
        preferences = AppPreferences.getInstance(this);

        timerFragment = new TimerFragment();
        contentFragment = new ContentFragment();
        buttonsFragment = new ButtonsFragment();

        notificationUtils = NotificationUtils.getInstance(this);

        attachFragmentToActivity();
        setStageTheme(preferences.getStageAct());
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
                this.setTheme(R.style.RestTheme);
                setContentView(R.layout.activity_main);
                break;
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
    protected void onResume() {
        super.onResume();
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(Constants.TAG, "MainActivity. onResume. onReceive. ");
                String str = intent.getStringExtra(Constants.BROADCAST_DIALOG);
                if (str != null) {
                    dialogWorkRest(str);
                    notifID = intent.getIntExtra(Constants.NOTIFICATION_ID, 0);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter(Constants.BROADCAST_ACTION_TIMER);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    private void dialogWorkRest(String dialog) {
        DialogFragment dg;
        switch (dialog) {
            case Constants.WORK_DIALOG:
                dg = new WorkDialogFragment();
                dg.show(getSupportFragmentManager(), Constants.WORK_DIALOG);
                break;
            case Constants.REST_DIALOG:
                dg = new RestDialogFragment();
                dg.show(getSupportFragmentManager(), Constants.REST_DIALOG);
                break;
        }
    }

    @Override
    public void refreshTime() {
        timerFragment.timeSetup();
    }

    @Override
    public void workEvent() {
        preferences.setStageAct(AppPreferences.POMO_ACT);
        notificationUtils.cancelProcessNotification(notifID);
        recreate();
    }

    @Override
    public void restEvent() {
        preferences.setStageAct(AppPreferences.REST_ACT);
        notificationUtils.cancelProcessNotification(notifID);
        recreate();
        startService(new Intent(this, TimerService.class)
                .putExtra(Constants.TIMER_BACKGROUND_VALUE, preferences.getRestTime())
                .putExtra(Constants.STAGE_VALUE, preferences.REST_ACT));
    }

    @Override
    public void longRestEvent() {
        preferences.setStageAct(AppPreferences.LONG_REST_ACT);
        notificationUtils.cancelProcessNotification(notifID);
        recreate();
        startService(new Intent(this, TimerService.class)
                .putExtra(Constants.TIMER_BACKGROUND_VALUE, preferences.getLongRestTime())
                .putExtra(Constants.STAGE_VALUE, preferences.LONG_REST_ACT));
    }
}
