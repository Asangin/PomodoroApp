package com.studio.skryl.pomodoroapplication.view;

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

public class WaitActionFragment extends Fragment {

    TextView timerView;
    AppPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.timer_fragment, null);
        timerView = v.findViewById(R.id.timer_view);

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

        return v;
    }
}
