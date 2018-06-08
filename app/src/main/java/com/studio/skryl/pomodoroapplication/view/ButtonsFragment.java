package com.studio.skryl.pomodoroapplication.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.studio.skryl.pomodoroapplication.R;
import com.studio.skryl.pomodoroapplication.utils.AppPreferences;

public class ButtonsFragment extends Fragment {

    AppPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        preferences = AppPreferences.getInstance(getActivity());
        View v = inflater.inflate(R.layout.button_fragment, null);
        Button btn = (Button) v.findViewById(R.id.action_button);

        if (preferences.getStageAct() == AppPreferences.POMO_ACT) {
            btn.setBackground(getResources().getDrawable(R.drawable.pomodoro_btn_shapes));
            if (preferences.getRunnigStatus()) btn.setText(R.string.txt_stop_pomodoro);
            else btn.setText(R.string.txt_start_pomodoro);
        } else {
            btn.setBackground(getResources().getDrawable(R.drawable.rest_btn_shapes));
            btn.setText(R.string.txt_stop_rest);
        }
        return v;
    }
}
