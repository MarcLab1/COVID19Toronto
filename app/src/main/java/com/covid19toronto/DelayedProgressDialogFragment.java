package com.covid19toronto;

import android.view.WindowManager;

import androidx.fragment.app.Fragment;

public class DelayedProgressDialogFragment extends Fragment {
    private DelayedProgressDialog progressDialog;

    public DelayedProgressDialogFragment() {
        super();
        progressDialog = new DelayedProgressDialog();

    }

    public void startDialog() {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressDialog.show(getActivity().getSupportFragmentManager(), "working");
    }

    public void stopDialog() {
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressDialog.cancel();
    }

    public DelayedProgressDialog getProgressDialog() {
        return progressDialog;
    }

}

