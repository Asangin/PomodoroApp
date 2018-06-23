package com.studio.skryl.pomodoroapplication.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.studio.skryl.pomodoroapplication.R;
import com.studio.skryl.pomodoroapplication.utils.Constants;

public class RestDialogFragment extends DialogFragment implements View.OnClickListener{

    DialogEventListener listener;

    TextView longRestText;
    TextView skipRestText;
    Button restBtn;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogEventListener) context;
        } catch (ClassCastException ex) {
            throw new ClassCastException(context.toString() + "must implement DialogEventListaner interface");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(Constants.TAG, "RestDialogFragment. onCreateView.");
        View root = inflater.inflate(R.layout.rest_dialog_fragment, null);
        longRestText = root.findViewById(R.id.long_rest);
        skipRestText = root.findViewById(R.id.skip_rest);
        restBtn = root.findViewById(R.id.rest_btn);
        longRestText.setOnClickListener(this);
        skipRestText.setOnClickListener(this);
        restBtn.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rest_btn:
                listener.restEvent();
                dismiss();
                break;
            case R.id.skip_rest:
                listener.workEvent();
                dismiss();
                break;
            case R.id.long_rest:
                listener.longRestEvent();
                dismiss();
                break;
        }

    }
}
