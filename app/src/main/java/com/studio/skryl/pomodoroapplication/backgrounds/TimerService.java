package com.studio.skryl.pomodoroapplication.backgrounds;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.studio.skryl.pomodoroapplication.utils.AppPreferences;
import com.studio.skryl.pomodoroapplication.utils.Constants;
import com.studio.skryl.pomodoroapplication.utils.NotificationUtils;
import com.studio.skryl.pomodoroapplication.utils.TimeConverter;

public class TimerService extends Service {

    private int pomoProcessId;

    Intent intent;
    NotificationUtils notificationUtils;
    AppPreferences preferences;
    private int timeValue;
    private CountDownTimer countDownTimer;
    private NotificationCompat.Builder builderPomoProcess; //TODO field or local? what about another notif

    public TimerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Log.d(Constants.TAG, "TimerService. onCreate. ");
        super.onCreate();
        preferences = AppPreferences.getInstance(this);
        notificationUtils = NotificationUtils.getInstance(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(Constants.TAG, "TimerService. onStartCommand. ");
        timeValue = intent.getIntExtra(Constants.TIMER_BACKGROUND_VALUE, 0);

        pomoProcessId = notificationUtils.createProcessNotification("Pomodoro start!", "Focus on your work");

        // TODO implement RestProcess notif, finish pomo notif and finish rest notif
        startTimer(timeValue, pomoProcessId);

        return super.onStartCommand(intent, flags, startId);
    }

    private void startTimer(int timeValue, final int pomoProcessId) {
        Log.d(Constants.TAG, "TimerService. startTimer. ");
        countDownTimer = new CountDownTimer(TimerService.this.timeValue, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(Constants.TAG, "TimerService. startTimer. onTick. " + millisUntilFinished + "running status - ");
                updateTimerView(millisUntilFinished, pomoProcessId);
            }

            @Override
            public void onFinish() {
                Log.d(Constants.TAG, "TimerService. startTimer. onFinish. ");
                notificationUtils.createPomodoroFinishNotification("Time to Rest", "Relax and make cup of tea!");
                stopSelf();
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        Log.d(Constants.TAG, "TimerService. onDestroy.");
        super.onDestroy();
        countDownTimer.cancel();
        notificationUtils.cancelProcessNotification(pomoProcessId);
    }

    private void updateTimerView(long millisUntilFinished, int pomoProcessId) {
        Log.d(Constants.TAG, "TimerService. updateTimerView.");
        String timeLeft = TimeConverter.fHourMinute(millisUntilFinished);
        notificationUtils.updateProcessNotification(pomoProcessId, millisUntilFinished);

        intent = new Intent(Constants.BROADCAST_ACTION_TIMER);
        intent.putExtra("TIC_TAC", timeLeft);
        sendBroadcast(intent);
    }
}
