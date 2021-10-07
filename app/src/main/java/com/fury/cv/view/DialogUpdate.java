package com.fury.cv.view;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.fury.cv.R;

public class DialogUpdate {

    private Dialog mDialog;
    private TextView mDialogOKButton;
    private TextView dialog_universal_info_store;
    private Page_org mDialogUniversalInfoActivity;


    public DialogUpdate(Page_org var1) {
        this.mDialogUniversalInfoActivity = var1;
    }

    private void initDialogButtons() {
        this.mDialogOKButton.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {

                DialogUpdate.this.mDialog.dismiss();

            }
        });
        this.dialog_universal_info_store.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {

                final String appPackageName = mDialogUniversalInfoActivity.getPackageName(); // getPackageName() from Context or Activity object
                try {
                    mDialogUniversalInfoActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    mDialogUniversalInfoActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }

                DialogUpdate.this.mDialog.dismiss();

            }
        });
    }


    public void dismissDialog() {
        this.mDialog.dismiss();
    }

    public void showDialog() {
        if (this.mDialog == null) {
            this.mDialog = new Dialog(this.mDialogUniversalInfoActivity, R.style.CustomDialogTheme);
        }
        this.mDialog.setContentView(R.layout.dialog_universal_update);
        this.mDialog.setCancelable(true);
        this.mDialog.show();
        this.mDialogOKButton = (TextView) this.mDialog.findViewById(R.id.dialog_universal_info_cancel);
        this.dialog_universal_info_store = (TextView) this.mDialog.findViewById(R.id.dialog_universal_info_store);

        initDialogButtons();
    }
}
