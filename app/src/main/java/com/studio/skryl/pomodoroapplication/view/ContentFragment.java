package com.studio.skryl.pomodoroapplication.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studio.skryl.pomodoroapplication.utils.AppPreferences;

public class ContentFragment extends Fragment {

    AppPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        preferences = AppPreferences.getInstance(getContext());
        LinearLayout layout = new LinearLayout(getActivity());
        View view;

        if (preferences.getStageAct() == AppPreferences.POMO_ACT) {
            TextView list = new TextView(getActivity());
            list.setText("Its List for now...");
            layout.addView(list);
            view = layout;
        } else {
            TextView list = new TextView(getActivity());
            list.setText("Its Content of Rest recomendation!");
            layout.addView(list);
            view = layout;
        }

        return view;
    }
}
