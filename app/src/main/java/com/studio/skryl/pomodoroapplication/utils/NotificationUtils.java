package com.studio.skryl.pomodoroapplication.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.studio.skryl.pomodoroapplication.MainActivity;
import com.studio.skryl.pomodoroapplication.R;

import java.util.HashMap;

public class NotificationUtils {
    private static NotificationUtils instance;

    private static Context context;
    private NotificationManager manager; // Системная утилита, упарляющая уведомлениями
    private int lastId = 0; //постоянно увеличивающееся поле, уникальный номер каждого уведомления
    private HashMap<Integer, Notification> notifications; //массив ключ-значение на все отображаемые пользователю уведомления
    private NotificationCompat.Builder processNotificationBuilder;


    //приватный контструктор для Singleton
    private NotificationUtils(Context context){
        this.context = context;
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notifications = new HashMap<Integer, Notification>();
    }
    /**
     * Получение ссылки на синглтон
     */
    public static NotificationUtils getInstance(Context context){
        if(instance==null){
            instance = new NotificationUtils(context);
        } else{
            instance.context = context;
        }
        return instance;
    }

    public int createProcessNotification(String messageTitle, String messageContext){
        Log.d(Constants.TAG, "NotificationUtils. createProcessNotification.");
        Intent notificationIntent = new Intent(context, MainActivity.class); // по клику на уведомлении откроется MainActivity
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        processNotificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_stat_name) //иконка уведомления
                .setContentTitle(messageTitle)  // заголовок уведомления
                .setContentText(messageContext) // Основной текст уведомления
                .setContentIntent(notificationPendingIntent);

        manager.notify(lastId, processNotificationBuilder.build()); // отображаем его пользователю.
        return lastId++; //TODO maybe delete autoincrement?
    }

    public void updateProcessNotification(int notificationId, long millisUntilFinished) {
        Log.d(Constants.TAG, "NotificationUtils. updateProcessNotification. notificationId = " + notificationId);
        processNotificationBuilder.setContentText(TimeConverter.fHourMinute(millisUntilFinished));
        manager.notify(notificationId, processNotificationBuilder.build());
    }

    public void cancelProcessNotification(int notificationId) {
        Log.d(Constants.TAG, "NotificationUtils. cancelProcessNotification.");
        manager.cancel(notificationId);
    }

    public int createPomodoroFinishNotification(String messageTitle, String messageContext){
        Log.d(Constants.TAG, "NotificationUtils. createPomodoroFinishNotification.");
        Intent notificationIntent = new Intent(context, MainActivity.class); // TODO impl Dialog massage about Rest, skiprest and so on...
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        processNotificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_pomo_finish) //иконка уведомления
                .setAutoCancel(true) //уведомление закроется по клику на него //TODO check how it work
                .setContentTitle(messageTitle)  // заголовок уведомления
                .setContentText(messageContext) // Основной текст уведомления
                .setContentIntent(notificationPendingIntent);

        manager.notify(lastId, processNotificationBuilder.build()); // отображаем его пользователю.
        return lastId++;
    }
}
