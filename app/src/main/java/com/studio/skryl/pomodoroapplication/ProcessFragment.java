package com.studio.skryl.pomodoroapplication;

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

import com.studio.skryl.pomodoroapplication.utils.Constants;

public class ProcessFragment extends Fragment {

    BroadcastReceiver broadcastReceiver;

    TextView timerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.timer_fragment, null);
        timerView = v.findViewById(R.id.timer_view);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String timeUpdate = intent.getStringExtra("TIC_TAC");
                timerView.setText(timeUpdate);
            }
        };
        IntentFilter intentFilter = new IntentFilter(Constants.BROADCAST_ACTION_TIMER);
        getActivity().registerReceiver(broadcastReceiver, intentFilter);

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
    }
}
