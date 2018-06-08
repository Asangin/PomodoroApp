package com.studio.skryl.pomodoroapplication.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.studio.skryl.pomodoroapplication.R;
import com.studio.skryl.pomodoroapplication.utils.AppPreferences;
import com.studio.skryl.pomodoroapplication.utils.Constants;

public class TimerFragment extends Fragment {

    TextView timerView;
    AppPreferences preferences;
    BroadcastReceiver broadcastReceiver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.timer_fragment, null);
        timerView = v.findViewById(R.id.timer_view);
        preferences = AppPreferences.getInstance(getActivity());

        if (preferences.getRunnigStatus() == true) {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String timeUpdate = intent.getStringExtra("TIC_TAC");
                    timerView.setText(timeUpdate);
                }
            };
            IntentFilter intentFilter = new IntentFilter(Constants.BROADCAST_ACTION_TIMER);
            getActivity().registerReceiver(broadcastReceiver, intentFilter);
        } else {
            switch (preferences.getStageAct()) {
                case AppPreferences.POMO_ACT:
                    timerView.setText(preferences.getPomoTime());
                    break;
                case AppPreferences.REST_ACT:
                    timerView.setText(preferences.getRestTime());
                    break;
                case AppPreferences.LONG_REST_ACT:
                    timerView.setText(preferences.getLongRestTime());
                    break;
            }
        }
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // TODO if status, maybe check for registry == null?
        if (preferences.getRunnigStatus() == true) getActivity().unregisterReceiver(broadcastReceiver);
    }
}
