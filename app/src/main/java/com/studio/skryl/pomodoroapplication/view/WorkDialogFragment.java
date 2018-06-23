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

import com.studio.skryl.pomodoroapplication.R;
import com.studio.skryl.pomodoroapplication.utils.Constants;

public class WorkDialogFragment extends DialogFragment {

    DialogEventListener listener;

    Button btnWork;

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
        Log.d(Constants.TAG, "WorkDialogFragment. onCreateView.");

        getDialog().setTitle("Back to work!");
        View root = inflater.inflate(R.layout.work_dialog_fragment, null);
        btnWork = root.findViewById(R.id.toWorkBtn);
        btnWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.workEvent();
                dismiss();
            }
        });

        return root;
    }
}
