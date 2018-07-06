package com.studio.skryl.pomodoroapplication.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.studio.skryl.pomodoroapplication.R;
import com.studio.skryl.pomodoroapplication.utils.AppPreferences;
import com.studio.skryl.pomodoroapplication.utils.Constants;
import com.studio.skryl.pomodoroapplication.utils.ServicesUtility;

public class TimerFragment extends Fragment {

    TextView timerView;
    AppPreferences preferences;
    ServicesUtility servicesUtility;
    BroadcastReceiver broadcastReceiver;

    @Override
    public void onResume() {
        super.onResume();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(Constants.TAG, "TimerFragment. onReceive. ");
                String timeUpdate = intent.getStringExtra("TIC_TAC");
                timerView.setText(timeUpdate);
            }
        };
        IntentFilter intentFilter = new IntentFilter(Constants.BROADCAST_ACTION_TIMER);
        getActivity().registerReceiver(broadcastReceiver, intentFilter);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(Constants.TAG, "TimerFragment. onCreateView. ");
        View v = inflater.inflate(R.layout.timer_fragment, null);
        timerView = v.findViewById(R.id.timer_view);
        preferences = AppPreferences.getInstance(getContext());
        servicesUtility = ServicesUtility.getInstance(getActivity());

        if (!servicesUtility.checkService()) timeSetup();
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        timeSetup();
    }

    public void timeSetup() {
        Log.d(Constants.TAG, "TimerFragment. onReceive. Else statement");
        switch (preferences.getStageAct()) {
            case AppPreferences.POMO_ACT:
                timerView.setText(preferences.getPomoTimeStr());
                break;
            case AppPreferences.REST_ACT:
                timerView.setText(preferences.getRestTimeStr());
                break;
            case AppPreferences.LONG_REST_ACT:
                timerView.setText(preferences.getLongRestTimeStr());
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(Constants.TAG, "TimerFragment. onDestroy. ");
        // TODO if status, maybe check for registry == null?
        getActivity().unregisterReceiver(broadcastReceiver);
    }
}
