package com.fury.cv.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.InterstitialCallbacks;
import com.fury.cv.R;
import com.fury.cv.adapter.listviewAdapter;
import com.fury.cv.util.FileUtils;
import com.fury.cv.util.VideoEngine;
import com.fury.cv.util.VideoEngineJoin;
import com.google.firebase.crash.FirebaseCrash;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by fury on 5/17/2017.
 */
public class Join extends Activity{


    private static final int MY_NOTIFICATION_ID = 1;
    protected final int LOADING_DIALOG;
    ImageView btnBack;
    ImageView btnSetting;
    EditText NameEdit;
    static ProgressDialog dialog;
    File root;
    int duration,coinint;
    Handler handler;
    public static HashMap<String, String> temp;
    public static Join act;
    int f77k,ch,work;
    boolean ok;
    Boolean plypush,coin_alfa;
    Typeface face;
    private PowerManager pm;
    TextView trimButton;
    public static TextView text_help;
    public static ArrayList<HashMap<String, String>> list;
    private PowerManager.WakeLock wl;
    String vi1,vi2,path;
    ListView lview;
    SharedPreferences one_play_preferences;
    SharedPreferences.Editor one_play_editor;
    int adada ;

    boolean play1 = false;
    ScheduledThreadPoolExecutor mDialogDaemon_time;
    public static listviewAdapter adapter;
    /* renamed from: com.video.compressop.view.Format.7 */
    class C10617 implements View.OnClickListener {
        C10617() {
        }

        public void onClick(View arg0) {
            Join.this.onBackPressed();
        }
    }

    /* renamed from: com.video.compressop.view.Format.8 */
    class C10628 implements View.OnClickListener {
        C10628() {
        }

        public void onClick(View arg0) {
            if (vi2 != null) {

                try {
                    stopService(new Intent(Join.this, VideoEngine.class));
                } catch (Exception e) {
                    FirebaseCrash.report(new Exception("1"));
                }

                String name = NameEdit.getText().toString();
                String text_kol = null;
                if (name.length() > 1) {
                    text_kol = "file " + "\'" + vi1 + "\'" + "\n"+"file "+ "\'" + vi2 + "\'" ;

                    try {
                        path = FileUtils.getTargetFileNameJoin(name,1);
                    } catch (Exception e) {
                        FirebaseCrash.report(new Exception("2"));
                    }

                    String name_now = null;
                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd");
                        Date now = new Date();
                        String hours = String.valueOf(now.getHours());
                        String minutes = String.valueOf(now.getMinutes());
                        String seconds = String.valueOf(now.getSeconds());
                        String fileName = formatter.format(now);
                        name_now = fileName + "_" + hours + "_" + minutes + "_" + seconds + ".txt";
                    } catch (Exception e) {
                        FirebaseCrash.report(new Exception("3"));
                    }

                    try {
                        generateNoteOnSD(getApplicationContext(), name_now, text_kol);
                    } catch (Exception e) {
                        FirebaseCrash.report(new Exception("4"));
                    }

                    String ad = String.valueOf(new File(Environment.getExternalStorageDirectory(), "Android/data/com.android.fury.cv/Code/" + name_now));

                    try {
                        start_activ();

                        if (coin_alfa){
                            coinint = 100;
                        }
                        if (coinint <= 0){
                            try {
                                end_activ();
                                (new DialogNoTicket(Join.this)).showDialog();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            one_play_editor.putInt("code", 7);
                            one_play_editor.putInt("one", 1);
                            one_play_editor.putString("inputFileName", ad);
                            one_play_editor.putString("path", path);
                            one_play_editor.putString("quality", vi1);
                            one_play_editor.putString("format_ch", vi2);
                            one_play_editor.putString("b_S_t", "Join videos ...");
                            one_play_editor.putString("b_F_c", "Sorry join video not work");
                            one_play_editor.putString("b_E_t", "Join the video");
                            one_play_editor.putString("b_E_c", "Successfully completed");
                            one_play_editor.commit();

                            work = one_play_preferences.getInt("work", 0);
                            if (work == 4){
                                (new DialogFollow(Join.this)).showDialog();
                            }else if (work == 5){
                                (new DialogStar(Join.this)).showDialog();
                            }

                            startService(new Intent(Join.this, VideoEngineJoin.class));

                            try {
                                String appKey = "fd397a30f8c27af492a6dda5e074ae8464c18d3441207e3a";
                                Appodeal.initialize(Join.this, appKey, Appodeal.INTERSTITIAL );

                                Appodeal.setInterstitialCallbacks(new InterstitialCallbacks() {
                                    @Override
                                    public void onInterstitialLoaded(boolean isPrecache) {
                                        if (!play1) {
                                            play1 = true;
                                            Appodeal.show(Join.this, Appodeal.INTERSTITIAL);
                                            Appodeal.isLoaded(Appodeal.INTERSTITIAL);
                                            FirebaseCrash.report(new Exception("Show Post Interstitial"));
                                        }
                                        Log.d("Appodeal", "onInterstitialLoaded");
                                    }
                                    @Override
                                    public void onInterstitialFailedToLoad() {
                                        Log.d("Appodeal", "onInterstitialFailedToLoad");
                                    }
                                    @Override
                                    public void onInterstitialShown() {
                                        Log.d("Appodeal", "onInterstitialShown");
                                    }
                                    @Override
                                    public void onInterstitialClicked() {
                                        FirebaseCrash.report(new Exception("Interstitial Clicked Post"));
                                        Log.d("Appodeal", "onInterstitialClicked");
                                    }
                                    @Override
                                    public void onInterstitialClosed() {
                                        Log.d("Appodeal", "onInterstitialClosed");
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    } catch (Exception e) {
                        FirebaseCrash.report(new Exception("5"));
                        Toast.makeText(Join.this, "error 2", Toast.LENGTH_LONG).show();
                        File appVoice = new File(Join.this.path);
                        if (appVoice.exists()) {
                            appVoice.delete();
                            Join.this.finish();
                        }
                    }
                } else {
                    Toast.makeText(Join.this, "Please give a name to the video", Toast.LENGTH_LONG).show();
                }


            } else {
                Toast.makeText(Join.this, "Select at least two videos to connect!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void start_activ(){
        dialog = new ProgressDialog(Join.this);
        dialog.setMessage("in working ...");
        dialog.setCancelable(false);
        dialog.show();

        if (mDialogDaemon_time != null) {
            try {
                mDialogDaemon_time.shutdown();
                mDialogDaemon_time = null;
            } catch (Exception e) {
                FirebaseCrash.report(new Exception("12"));
            }
        }
        try {
            mDialogDaemon_time = new ScheduledThreadPoolExecutor(1);
        } catch (Exception e) {
            FirebaseCrash.report(new Exception("13"));
        }
        try {
            mDialogDaemon_time.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try{
                                if (VideoEngineJoin.cancel_pro == 0){
                                }else if (VideoEngineJoin.cancel_pro == 1){
                                    dialog.dismiss();
                                    finish();
                                }else {
                                    dialog.dismiss();
                                    finish();
                                }
                            }catch (Exception e){
                                mDialogDaemon_time.shutdown();
                                mDialogDaemon_time = null;
                                FirebaseCrash.report(new Exception("14"));
                            }

                        }
                    });
                }
            }, 0L, 1000L, TimeUnit.MILLISECONDS);

        } catch (Exception e) {
            mDialogDaemon_time.shutdown();
            mDialogDaemon_time = null;
            FirebaseCrash.report(new Exception("14"));
        }

    }

    public static void end_activ(){
        try {
            dialog.dismiss();
        } catch (Exception e) {
            FirebaseCrash.report(new Exception("6"));
        }
    }

    public static void in_activ(){
        try {
            dialog.dismiss();
        } catch (Exception e) {
            FirebaseCrash.report(new Exception("7"));
        }
    }

    public Join() {
        this.LOADING_DIALOG = MY_NOTIFICATION_ID;
        this.ok = false;
        this.f77k = 0;
        this.duration = 0;
        this.handler = new Handler();
        this.plypush = Boolean.valueOf(false);
        vi1 = null;
        vi2 = null;
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("VideoView", "In on create");
        act = this;
        setContentView(R.layout.join_view);
        adada = 0;
        list = new ArrayList<HashMap<String, String>>();
        lview = (ListView) findViewById(R.id.VideogridView);
        adapter = new listviewAdapter(this, list);
        lview.setAdapter(adapter);
        one_play_preferences = getApplicationContext().getSharedPreferences("PROJECT_NAME", android.content.Context.MODE_PRIVATE);
        one_play_editor = one_play_preferences.edit();
        coin_alfa = one_play_preferences.getBoolean("COIN_Alfa", false);
        coinint = one_play_preferences.getInt("COIN", 0);
        try {
            this.pm = (PowerManager) getSystemService(POWER_SERVICE);
            this.wl = this.pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        } catch (Exception e) {
            FirebaseCrash.report(new Exception("8"));
        }
        findById();

        FirebaseCrash.log("log 1");
    }

    private void findById() {
        this.trimButton = (TextView) findViewById(R.id.trimbut);
        this.trimButton.setOnClickListener(trimClickListener());
        this.btnBack = (ImageView) findViewById(R.id.btnBack);
        this.NameEdit = (EditText) findViewById(R.id.NameEdit);
        this.btnSetting = (ImageView) findViewById(R.id.btnSetting);
        this.btnBack.setOnClickListener(new C10617());
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new DialogHelp(Join.this,"Enter a name for your video and then select the two videos you want to paste together (you can add more than 2 videos at a glance soon) \n Note that you need to double the size of your videos to empty the video. \n If you have any questions or problems, contact us through our support")).showDialog();
            }
        });

        face = Typeface.createFromAsset(getAssets(), "fa_font_1.ttf");
        trimButton.setTypeface(face);
        TextView tvt = (TextView) findViewById(R.id.tvt);
        text_help = (TextView) findViewById(R.id.text_help);
        TextView ts_check = (TextView) findViewById(R.id.ts_check);
        TextView select2 = (TextView) findViewById(R.id.select2);
        TextView txtname = (TextView) findViewById(R.id.txtname);
        TextView select = (TextView) findViewById(R.id.select);
        final ImageView img_check = (ImageView) findViewById(R.id.img_check);
        LinearLayout click_check = (LinearLayout) findViewById(R.id.click_check);
        LinearLayout col_help = (LinearLayout) findViewById(R.id.col_help);
        tvt.setTypeface(face);
        select2.setTypeface(face);
        NameEdit.setTypeface(face);
        txtname.setTypeface(face);
        ts_check.setTypeface(face);
        select.setTypeface(face);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);

                if (adada == 0){
                    startActivityForResult(i,1);
                }else if (adada == 1){
                    startActivityForResult(i,2);
                }else if (adada == 2){
                    Toast.makeText(Join.this, "Only two video connections are given!", Toast.LENGTH_LONG).show();
                }

                //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                //intent.setType("audio/mp3"); // specify "audio/mp3" to filter only mp3 files
                //startActivityForResult(intent,1);

            }
        });

        int check = one_play_preferences.getInt("check_7",0);
        if (check == 1){
            col_help.setVisibility(View.GONE);
        }
        ch = 0;
        click_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ch == 0){
                    ch = 1;
                    img_check.setImageResource(R.drawable.sport_me_tic_music_on);
                    one_play_editor.putInt("check_7", 1);
                    one_play_editor.apply();
                }else {
                    ch = 0;
                    img_check.setImageResource(R.drawable.sport_me_tic_music_off);
                    one_play_editor.putInt("check_7", 0);
                    one_play_editor.apply();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            try {
                Uri audio = data.getData();
                adada = adada + 1;
                vi1 = String.valueOf(new File(getRealPathFromURI(audio)));
                Cursor cursor = getContentResolver().query(audio,null,null,null,null);
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME);
                String name = cursor.getString(idx);
                temp = new HashMap<String, String>();
                temp.put("Column 1", name);
                temp.put("Column 2", String.valueOf(adada));
                list.add(temp);
                lview.invalidateViews();
            } catch (Exception e) {
                FirebaseCrash.report(new Exception("9"));
            }
        }else if (requestCode == 2 && resultCode == Activity.RESULT_OK){
            try {
                Uri audio = data.getData();
                adada = adada + 1;
                vi2 = String.valueOf(new File(getRealPathFromURI(audio)));
                Cursor cursor = getContentResolver().query(audio,null,null,null,null);
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME);
                String name = cursor.getString(idx);
                temp = new HashMap<String, String>();
                temp.put("Column 1", name);
                temp.put("Column 2", String.valueOf(adada));
                list.add(temp);
                lview.invalidateViews();
            } catch (Exception e) {
                FirebaseCrash.report(new Exception("10"));
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            result = cursor.getString(cursor.getColumnIndex("_data"));
            cursor.close();
        }
        return result;
    }

    protected void onResume() {
        super.onResume();
        this.wl.acquire();
        Log.i("VideoView", "In on resume");
    }

    protected void onPause() {
        this.wl.release();
        super.onPause();
        Log.i("VideoView", "In on pause");
    }


    private View.OnClickListener trimClickListener() {
        return new C10628();
    }

    class C03981 implements DialogUtils.DialogBtnClickListener_set {

        C03981() {
        }

        public void onPositiveClick(String s) {

        }
    }

    public void generateNoteOnSD(Context context, String sFileName, String sBody) {
        try {

            root = new File(Environment.getExternalStorageDirectory(), "Android/data/com.android.fury.cv/Code");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            FirebaseCrash.report(new Exception("11"));
        }
    }

}
