package com.studio.skryl.pomodoroapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AppPreferences {
    final static String SHARED_PREFS_FILE = "pomodoro_preferences";
    final static String RUN_TIMER_STATUS = "running_status";

    private SharedPreferences pref;

    private SharedPreferences.Editor getEdit() {
        return pref.edit();
    }

    public AppPreferences(Context context) {
        this.pref = context.getSharedPreferences(SHARED_PREFS_FILE, 0);
    }

    public Boolean getRunnigStatus() {
        return pref.getBoolean(RUN_TIMER_STATUS, false);
    }

    public void setRunningStatus(Boolean status) {
        getEdit().putBoolean(RUN_TIMER_STATUS, status).commit();
    }

    /**
     * @description From Stack overflow. This approach give as a behaver of EMUM saved in SharedPreferences.
     * @Url https://stackoverflow.com/questions/14750743/save-enum-to-sharedpreference
     * @author d_skryl
     */

    final static String STAGE_POMODORO_ACTIVITY = "status_activity";

    public String getPomoTime() {
        int time = 60000;
        return TimeConverter.fHourMinute(time);
    }

    public String getRestTime() {
        int time = 10000;
        return TimeConverter.fHourMinute(time);
    }

    public String getLongRestTime() {
        int time = 15000;
        return TimeConverter.fHourMinute(time);
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({POMO_ACT, REST_ACT, LONG_REST_ACT})
    public @interface StagesActivity {
    }

    public static final int LONG_REST_ACT = -1;
    public static final int REST_ACT = 0;
    public static final int POMO_ACT = 1;

    @StagesActivity
    public int getStageAct() {
        return pref.getInt(STAGE_POMODORO_ACTIVITY, POMO_ACT);
    }

    public void setStageAct(@StagesActivity int flag) {
        pref.edit()
                .putInt(STAGE_POMODORO_ACTIVITY, flag)
                .apply();
    }
}
