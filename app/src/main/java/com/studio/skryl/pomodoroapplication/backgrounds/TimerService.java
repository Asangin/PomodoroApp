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
    private int stageValue;

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
        intent = new Intent(Constants.BROADCAST_ACTION_TIMER);
        preferences = AppPreferences.getInstance(this);
        notificationUtils = NotificationUtils.getInstance(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(Constants.TAG, "TimerService. onStartCommand. ");
        timeValue = intent.getIntExtra(Constants.TIMER_BACKGROUND_VALUE, 0);
        stageValue = intent.getIntExtra(Constants.STAGE_VALUE, 1);
        if (stageValue == AppPreferences.POMO_ACT) pomoProcessId = notificationUtils.createProcessNotification("Pomodoro start!", "Focus on your work");
        else pomoProcessId = notificationUtils.createProcessNotification("Time to rest!", "Make some tea or walk outside!");

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
                onNextStage(stageValue);
                stopSelf();
            }
        }.start();
    }

    private void onNextStage(int stage) {
        int notifId;
        switch (stage) {
            case AppPreferences.POMO_ACT:
                notifId = notificationUtils.createPomodoroFinishNotification("Time to rest!", "Relax and make cup of tea.");
                intent.putExtra(Constants.BROADCAST_DIALOG, Constants.REST_DIALOG).putExtra(Constants.NOTIFICATION_ID, notifId);
                sendBroadcast(intent);
                break;
            case AppPreferences.REST_ACT:
                notifId = notificationUtils.createPomodoroFinishNotification("Back to work!", "That it for rest, time to back for the work.");
                intent.putExtra(Constants.BROADCAST_DIALOG, Constants.WORK_DIALOG).putExtra(Constants.NOTIFICATION_ID, notifId);
                sendBroadcast(intent);
                break;
        }
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

        intent.putExtra("TIC_TAC", timeLeft);
        sendBroadcast(intent);
    }
}
