package com.fury.cv.view;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.fury.cv.R;
import com.google.firebase.crash.FirebaseCrash;

public class DialogStar {

    private Dialog mDialog;
    private TextView mDialogOKButton;
    private TextView dialog_universal_info_store;
    private TextView text1;
    private TextView text2;
    private ViewVideo mDialogUniversalInfoActivity;
    private Cut mDialogUniversalInfoActivity2;
    private Join mDialogUniversalInfoActivity3;
    private Create mDialogUniversalInfoActivity4;
    private Format mDialogUniversalInfoActivity5;
    private Gif mDialogUniversalInfoActivity6;
    private Logo mDialogUniversalInfoActivity7;
    private Music mDialogUniversalInfoActivity8;
    private Voice mDialogUniversalInfoActivity9;
    int s;

    public DialogStar(ViewVideo var1) {
        this.mDialogUniversalInfoActivity = var1;
        s = 1;
    }
    public DialogStar(Cut var1) {
        this.mDialogUniversalInfoActivity2 = var1;
        s = 2;
    }
    public DialogStar(Join var1) {
        this.mDialogUniversalInfoActivity3 = var1;
        s = 3;
    }
    public DialogStar(Create var1) {
        this.mDialogUniversalInfoActivity4 = var1;
        s = 4;
    }
    public DialogStar(Format var1) {
        this.mDialogUniversalInfoActivity5 = var1;
        s = 5;
    }
    public DialogStar(Gif var1) {
        this.mDialogUniversalInfoActivity6 = var1;
        s = 6;
    }
    public DialogStar(Logo var1) {
        this.mDialogUniversalInfoActivity7 = var1;
        s = 7;
    }
    public DialogStar(Music var1) {
        this.mDialogUniversalInfoActivity8 = var1;
        s = 8;
    }
    public DialogStar(Voice var1) {
        this.mDialogUniversalInfoActivity9 = var1;
        s = 9;
    }

    private void initDialogButtons() {
        this.mDialogOKButton.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {

                DialogStar.this.mDialog.dismiss();

            }
        });
        this.dialog_universal_info_store.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {

                    if (s==1){

                        try {
                            Uri uri = Uri.parse("market://details?id=" + mDialogUniversalInfoActivity9.getPackageName());
                            Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                            try {
                                PendingIntent pendingIntent = PendingIntent.getActivity(mDialogUniversalInfoActivity9, 0, myAppLinkToMarket, 0);
                                pendingIntent.send();
                            } catch (ActivityNotFoundException a) {

                                Intent myAppLinkToMarket2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="
                                        + mDialogUniversalInfoActivity9.getPackageName()));
                                PendingIntent pendingIntent2 = PendingIntent.getActivity(mDialogUniversalInfoActivity9, 0, myAppLinkToMarket2, 0);
                                pendingIntent2.send();

                                Toast.makeText(mDialogUniversalInfoActivity9, " unable to find market app", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception w) {
                            FirebaseCrash.report(new Exception("star in Google"));
                        }

                    }else if (s==2){

                        try {
                            Uri uri = Uri.parse("market://details?id=" + mDialogUniversalInfoActivity9.getPackageName());
                            Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                            try {
                                PendingIntent pendingIntent = PendingIntent.getActivity(mDialogUniversalInfoActivity9, 0, myAppLinkToMarket, 0);
                                pendingIntent.send();
                            } catch (ActivityNotFoundException a) {

                                Intent myAppLinkToMarket2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="
                                        + mDialogUniversalInfoActivity9.getPackageName()));
                                PendingIntent pendingIntent2 = PendingIntent.getActivity(mDialogUniversalInfoActivity9, 0, myAppLinkToMarket2, 0);
                                pendingIntent2.send();

                                Toast.makeText(mDialogUniversalInfoActivity9, " unable to find market app", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception w) {
                            FirebaseCrash.report(new Exception("star in Google"));
                        }

                    }else if (s==3){

                        try {
                            Uri uri = Uri.parse("market://details?id=" + mDialogUniversalInfoActivity9.getPackageName());
                            Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                            try {
                                PendingIntent pendingIntent = PendingIntent.getActivity(mDialogUniversalInfoActivity9, 0, myAppLinkToMarket, 0);
                                pendingIntent.send();
                            } catch (ActivityNotFoundException a) {

                                Intent myAppLinkToMarket2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="
                                        + mDialogUniversalInfoActivity9.getPackageName()));
                                PendingIntent pendingIntent2 = PendingIntent.getActivity(mDialogUniversalInfoActivity9, 0, myAppLinkToMarket2, 0);
                                pendingIntent2.send();

                                Toast.makeText(mDialogUniversalInfoActivity9, " unable to find market app", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception w) {
                            FirebaseCrash.report(new Exception("star in Google"));
                        }

                    }else if (s==4){

                        try {
                            Uri uri = Uri.parse("market://details?id=" + mDialogUniversalInfoActivity9.getPackageName());
                            Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                            try {
                                PendingIntent pendingIntent = PendingIntent.getActivity(mDialogUniversalInfoActivity9, 0, myAppLinkToMarket, 0);
                                pendingIntent.send();
                            } catch (ActivityNotFoundException a) {

                                Intent myAppLinkToMarket2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="
                                        + mDialogUniversalInfoActivity9.getPackageName()));
                                PendingIntent pendingIntent2 = PendingIntent.getActivity(mDialogUniversalInfoActivity9, 0, myAppLinkToMarket2, 0);
                                pendingIntent2.send();

                                Toast.makeText(mDialogUniversalInfoActivity9, " unable to find market app", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception w) {
                            FirebaseCrash.report(new Exception("star in Google"));
                        }

                    }else if (s==5){

                        try {
                            Uri uri = Uri.parse("market://details?id=" + mDialogUniversalInfoActivity9.getPackageName());
                            Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                            try {
                                PendingIntent pendingIntent = PendingIntent.getActivity(mDialogUniversalInfoActivity9, 0, myAppLinkToMarket, 0);
                                pendingIntent.send();
                            } catch (ActivityNotFoundException a) {

                                Intent myAppLinkToMarket2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="
                                        + mDialogUniversalInfoActivity9.getPackageName()));
                                PendingIntent pendingIntent2 = PendingIntent.getActivity(mDialogUniversalInfoActivity9, 0, myAppLinkToMarket2, 0);
                                pendingIntent2.send();

                                Toast.makeText(mDialogUniversalInfoActivity9, " unable to find market app", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception w) {
                            FirebaseCrash.report(new Exception("star in Google"));
                        }

                    }else if (s==6){

                        try {
                            Uri uri = Uri.parse("market://details?id=" + mDialogUniversalInfoActivity9.getPackageName());
                            Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                            try {
                                PendingIntent pendingIntent = PendingIntent.getActivity(mDialogUniversalInfoActivity9, 0, myAppLinkToMarket, 0);
                                pendingIntent.send();
                            } catch (ActivityNotFoundException a) {

                                Intent myAppLinkToMarket2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="
                                        + mDialogUniversalInfoActivity9.getPackageName()));
                                PendingIntent pendingIntent2 = PendingIntent.getActivity(mDialogUniversalInfoActivity9, 0, myAppLinkToMarket2, 0);
                                pendingIntent2.send();

                                Toast.makeText(mDialogUniversalInfoActivity9, " unable to find market app", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception w) {
                            FirebaseCrash.report(new Exception("star in Google"));
                        }

                    }else if (s==7){

                        try {
                            Uri uri = Uri.parse("market://details?id=" + mDialogUniversalInfoActivity9.getPackageName());
                            Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                            try {
                                PendingIntent pendingIntent = PendingIntent.getActivity(mDialogUniversalInfoActivity9, 0, myAppLinkToMarket, 0);
                                pendingIntent.send();
                            } catch (ActivityNotFoundException a) {

                                Intent myAppLinkToMarket2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="
                                        + mDialogUniversalInfoActivity9.getPackageName()));
                                PendingIntent pendingIntent2 = PendingIntent.getActivity(mDialogUniversalInfoActivity9, 0, myAppLinkToMarket2, 0);
                                pendingIntent2.send();

                                Toast.makeText(mDialogUniversalInfoActivity9, " unable to find market app", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception w) {
                            FirebaseCrash.report(new Exception("star in Google"));
                        }

                    }else if (s==8){

                        try {
                            Uri uri = Uri.parse("market://details?id=" + mDialogUniversalInfoActivity9.getPackageName());
                            Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                            try {
                                PendingIntent pendingIntent = PendingIntent.getActivity(mDialogUniversalInfoActivity9, 0, myAppLinkToMarket, 0);
                                pendingIntent.send();
                            } catch (ActivityNotFoundException a) {

                                Intent myAppLinkToMarket2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="
                                        + mDialogUniversalInfoActivity9.getPackageName()));
                                PendingIntent pendingIntent2 = PendingIntent.getActivity(mDialogUniversalInfoActivity9, 0, myAppLinkToMarket2, 0);
                                pendingIntent2.send();

                                Toast.makeText(mDialogUniversalInfoActivity9, " unable to find market app", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception w) {
                            FirebaseCrash.report(new Exception("star in Google"));
                        }

                    }else if (s==9) {

                        try {
                            Uri uri = Uri.parse("market://details?id=" + mDialogUniversalInfoActivity9.getPackageName());
                            Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                            try {
                                PendingIntent pendingIntent = PendingIntent.getActivity(mDialogUniversalInfoActivity9, 0, myAppLinkToMarket, 0);
                                pendingIntent.send();
                            } catch (ActivityNotFoundException a) {

                                Intent myAppLinkToMarket2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="
                                        + mDialogUniversalInfoActivity9.getPackageName()));
                                PendingIntent pendingIntent2 = PendingIntent.getActivity(mDialogUniversalInfoActivity9, 0, myAppLinkToMarket2, 0);
                                pendingIntent2.send();

                                Toast.makeText(mDialogUniversalInfoActivity9, " unable to find market app", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception w) {
                            FirebaseCrash.report(new Exception("star in Google"));
                        }



                }

                DialogStar.this.mDialog.dismiss();

            }
        });
    }


    public void dismissDialog() {
        this.mDialog.dismiss();
    }

    public void showDialog() {
        if (this.mDialog == null) {
            if (s == 1){
                this.mDialog = new Dialog(this.mDialogUniversalInfoActivity, R.style.CustomDialogTheme);
            }else if (s == 2){
                this.mDialog = new Dialog(this.mDialogUniversalInfoActivity2, R.style.CustomDialogTheme);
            }else if (s == 3){
                this.mDialog = new Dialog(this.mDialogUniversalInfoActivity3, R.style.CustomDialogTheme);
            }else if (s == 4){
                this.mDialog = new Dialog(this.mDialogUniversalInfoActivity4, R.style.CustomDialogTheme);
            }else if (s == 5){
                this.mDialog = new Dialog(this.mDialogUniversalInfoActivity5, R.style.CustomDialogTheme);
            }else if (s == 6){
                this.mDialog = new Dialog(this.mDialogUniversalInfoActivity6, R.style.CustomDialogTheme);
            }else if (s == 7){
                this.mDialog = new Dialog(this.mDialogUniversalInfoActivity7, R.style.CustomDialogTheme);
            }else if (s == 8){
                this.mDialog = new Dialog(this.mDialogUniversalInfoActivity8, R.style.CustomDialogTheme);
            }else if (s == 9){
                this.mDialog = new Dialog(this.mDialogUniversalInfoActivity9, R.style.CustomDialogTheme);
            }
        }
        this.mDialog.setContentView(R.layout.dialog_star);
        this.mDialog.setCancelable(true);
        this.mDialog.show();
        this.mDialogOKButton = (TextView) this.mDialog.findViewById(R.id.dialog_universal_info_cancel);
        this.dialog_universal_info_store = (TextView) this.mDialog.findViewById(R.id.dialog_universal_info_store);


        initDialogButtons();
    }
}
