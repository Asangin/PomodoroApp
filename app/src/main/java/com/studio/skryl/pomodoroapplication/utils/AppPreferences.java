package com.studio.skryl.pomodoroapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.IntDef;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AppPreferences {
    final static String SHARED_PREFS_FILE = "pomodoro_preferences";
    private static final String VIBRATION_SET = "vibration_set";
    private static final String POMODORO_TIME_SET = "pomodoro_time_set";
    private static final String POSITION_POMODORO_TIME = "position_pomodoro_time";
    private static final String POSITION_REST_TIME = "position_rest_time";
    private static final String POSITION_LONG_REST_TIME = "position_long_rest_time";
    private static final String REST_TIME_SET = "rest_time_set";
    private static final String LONG_REST_TIME_SET = "long_rest_time_set";

    private SharedPreferences pref;

    private SharedPreferences.Editor getEdit() {
        return pref.edit();
    }

    public AppPreferences(Context context) {
        this.pref = context.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
    }

    public Boolean getVibrateOption() { return pref.getBoolean(VIBRATION_SET, false); }

    public void setVibrateOption(Boolean opt) {getEdit().putBoolean(VIBRATION_SET, opt).commit();}

    public int getPositionPomodoro() { return pref.getInt(POSITION_POMODORO_TIME, 0);  }

    public void setTimePomodoro(int time, int pos) {
        Log.d(Constants.TAG, "AppPreferences. setTimePomodoro. "+ time +" "+pos);
        SharedPreferences.Editor edit = getEdit();
        edit.putInt(POMODORO_TIME_SET, time);
        edit.putInt(POSITION_POMODORO_TIME, pos);
        edit.commit();
    }

    public String getPomoTimeStr() {
        int time = pref.getInt(POMODORO_TIME_SET, 1200000);
        return TimeConverter.fHourMinute(time);
    }

    public int getPomoTime() { return pref.getInt(POMODORO_TIME_SET, 1200000);  }

    public int getPositionRest() {
        return pref.getInt(POSITION_REST_TIME, 0);
    }

    public void setTimeRest(int time, int pos) {
        SharedPreferences.Editor edit = getEdit();
        edit.putInt(REST_TIME_SET, time);
        edit.putInt(POSITION_REST_TIME, pos);
        edit.commit();
    }

    public String getRestTimeStr() {
        int time = pref.getInt(REST_TIME_SET, 300000);
        return TimeConverter.fHourMinute(time);
    }

    public int getRestTime() { return pref.getInt(REST_TIME_SET, 300000); }

    public int getPositionLongRest() {
        return pref.getInt(POSITION_LONG_REST_TIME, 0);
    }

    public void setTimeLongRest(int time, int pos) {
        SharedPreferences.Editor edit = getEdit();
        edit.putInt(LONG_REST_TIME_SET, time);
        edit.putInt(POSITION_LONG_REST_TIME, pos);
        edit.commit();
    }

    public String getLongRestTimeStr() {
        int time = pref.getInt(LONG_REST_TIME_SET, 900000);
        return TimeConverter.fHourMinute(time);
    }

    public int getLongRestTime() { return pref.getInt(LONG_REST_TIME_SET, 900000); }

    /**
     * @description From Stack overflow. This approach give as a behaver of EMUM saved in SharedPreferences.
     * @Url https://stackoverflow.com/questions/14750743/save-enum-to-sharedpreference
     * @author d_skryl
     */

    final static String STAGE_POMODORO_ACTIVITY = "status_activity";

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

    /**
     * Singleton
     */
    private static AppPreferences _instance = null;

    public synchronized static AppPreferences getInstance(Context context) {
        if (_instance == null)
            _instance = new AppPreferences(context);
        return _instance;
    }
}
