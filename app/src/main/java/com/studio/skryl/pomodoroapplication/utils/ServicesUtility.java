package com.studio.skryl.pomodoroapplication.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

public class ServicesUtility {

    private static ServicesUtility _instance = null;

    Context context;

    public ServicesUtility(Context context) {
        this.context = context;
    }

    public synchronized static ServicesUtility getInstance(Context context) {
        if (_instance == null)
            _instance = new ServicesUtility(context);
        return _instance;
    }

    public Boolean checkService() {
        ActivityManager activityManager  = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> services = activityManager .getRunningServices(Integer.MAX_VALUE);
        boolean isServiceFound = false;
        for (int i = 0; i < services.size(); i++) {
            //Log.d(Constants.TAG, "Service Nr." + i + ":  " + services.get(i).service.getPackageName());
            if ("com.studio.skryl.pomodoroapplication".equals(services.get(i).service.getPackageName())){
                //Log.d(Constants.TAG, "Service CLASS  : " + services.get(i).service.getClassName());
                if ("com.studio.skryl.pomodoroapplication.backgrounds.TimerService".equals(services.get(i).service.getClassName())) isServiceFound = true;
            }
        }
        return isServiceFound;
    }
}
