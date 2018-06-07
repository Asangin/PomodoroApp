package com.studio.skryl.pomodoroapplication.utils;

import java.util.Locale;

public class TimeConverter {
    public static String fDayHourMinute (long j) {
        String str;
        String str2;
        long j2 = j % 60;
        long j3 = (j / 60) % 60;
        long j4 = (j / 3600) % 24;
        if (j2 < 10) {
            str = "0" + j2;
        } else {
            str = String.valueOf(j2);
        }
        if (j3 < 10) {
            str2 = "0" + j3;
        } else {
            str2 = String.valueOf(j3);
        }
        String str3 = String.valueOf(j4);
        if (j4 > 0) {
            return str3 + ":" + str2 + ":" + str;
        }
        return str2 + ":" + str;
    }

    public static String fHourMinute (long mTimeLeftInMillis) {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }

    // Testing, must implement unit test
    // TODO Must inmplement Unit test
    public static void main(String[] args) {
        System.out.println(TimeConverter.fDayHourMinute(25));
        System.out.println(TimeConverter.fHourMinute(1000));
        System.out.println(Locale.getDefault());
    }

}
