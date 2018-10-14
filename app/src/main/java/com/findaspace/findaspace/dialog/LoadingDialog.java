package com.findaspace.findaspace.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.findaspace.findaspace.app.R;

/**
 * loading window
 */
public class LoadingDialog {

    private TextView mLoadingTv;
    private Dialog mDialog;

    public LoadingDialog(Context context) {
        if (mDialog == null) {
            mDialog = new Dialog(context, R.style.LoadingDialog);
        }
        mDialog.setContentView(R.layout.dialog_loading);
        mLoadingTv = (TextView) mDialog.findViewById(R.id.loading_tv);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);
    }

    /**
     * show
     */
    public void show() {
        mDialog.show();
    }

    /**
     * dismiss
     */
    public void dismiss() {
        mDialog.dismiss();
    }

    /**
     * is showing or not
     */
    public boolean isShowing() {
        return mDialog.isShowing();
    }

    /**
     *
     */
    public void setText(String text) {
        mLoadingTv.setText(text);
    }
}
