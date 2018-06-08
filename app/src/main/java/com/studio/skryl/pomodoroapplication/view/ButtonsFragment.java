package com.studio.skryl.pomodoroapplication.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.studio.skryl.pomodoroapplication.R;
import com.studio.skryl.pomodoroapplication.backgrounds.TimerService;
import com.studio.skryl.pomodoroapplication.utils.AppPreferences;
import com.studio.skryl.pomodoroapplication.utils.Constants;
import com.studio.skryl.pomodoroapplication.utils.ServicesUtility;

public class ButtonsFragment extends Fragment implements View.OnClickListener {

    AppPreferences preferences;
    ServicesUtility servicesUtility;

    Button btn;

    public interface ButtonsFragmentListener {
        void refreshTime();
    }

    ButtonsFragmentListener onButtonsFragmentListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onButtonsFragmentListener = (ButtonsFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ButtonsFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(Constants.TAG, "ButtonFragment. onCreateView. ");
        preferences = AppPreferences.getInstance(getActivity());
        servicesUtility = ServicesUtility.getInstance(getActivity());
        View v = inflater.inflate(R.layout.button_fragment, null);
        btn = (Button) v.findViewById(R.id.action_button);

        setButtonText();

        btn.setOnClickListener(this);
        return v;
    }

    private void setButtonText() {
        Log.d(Constants.TAG, "ButtonFragment. setButtonText. ");
        if (preferences.getStageAct() == AppPreferences.POMO_ACT) {
            btn.setBackground(getResources().getDrawable(R.drawable.pomodoro_btn_shapes));
            if (servicesUtility.checkService()) btn.setText(R.string.txt_stop_pomodoro);
            else btn.setText(R.string.txt_start_pomodoro);
        } else {
            btn.setBackground(getResources().getDrawable(R.drawable.rest_btn_shapes));
            btn.setText(R.string.txt_stop_rest);
        }
    }

    @Override
    public void onClick(View view) {
        Log.d(Constants.TAG, "ButtonFragment. onClick. ");
        if (!servicesUtility.checkService()) {
            switch (preferences.getStageAct()) {
                case AppPreferences.POMO_ACT:
                    getActivity()
                            .startService(new Intent(getContext(), TimerService.class)
                                    .putExtra(Constants.TIMER_BACKGROUND_VALUE, preferences.getPomoTime()));
                    break;
                case AppPreferences.REST_ACT:
                    getActivity()
                            .startService(new Intent(getContext(), TimerService.class)
                                    .putExtra(Constants.TIMER_BACKGROUND_VALUE, preferences.getRestTime()));
                    break;
                case AppPreferences.LONG_REST_ACT:
                    getActivity()
                            .startService(new Intent(getContext(), TimerService.class)
                                    .putExtra(Constants.TIMER_BACKGROUND_VALUE, preferences.getLongRestTime()));
                    break;
            }

        } else {
            getActivity().stopService(new Intent(getContext(), TimerService.class));
            onButtonsFragmentListener.refreshTime();
        }
        setButtonText();
    }
}
