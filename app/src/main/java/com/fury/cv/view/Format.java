package com.fury.cv.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.ParseException;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.InterstitialCallbacks;
import com.fury.cv.R;
import com.fury.cv.model.VideoPlayerState;
import com.fury.cv.util.FileUtils;
import com.fury.cv.util.VideoEngine;
import com.google.firebase.crash.FirebaseCrash;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by fury on 5/7/2017.
 */
public class Format extends Activity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    private static final int MY_NOTIFICATION_ID = 1;
    protected final int LOADING_DIALOG;
    ImageView btnBack;
    ImageView btnSetting;
    Button btnPlay;
    TextView f_DV,f_NUT,f_ISMV,f_MPEG,f_TS,f_M3U8,f_WMV,f_VOB,f_ASF,f_FLV,f_MOV,f_avi,f_MKV,f_MPG,f_mp4,f_pal_dvd,f_pal_vcd,f_pal_dv,f_pal_svcd,f_libx264,f_pal, f_ntsc ;
    static ProgressDialog dialog;
    int duration;
    Handler handler;
    boolean isPlay;
    public static Format act;
    int f77k,coinint,work;
    int ch,dvd,vcd,svcd,libx264,dv,n,pn,form;

    boolean play1 = false;
    boolean ok;
    View.OnClickListener onclickbtnPlay;
    protected static String outputformat,sendcode;
    String path;
    Boolean plypush,coin_alfa;
    private PowerManager pm;
    RelativeLayout rl_videoplayer;
    SeekBar seekVideo;
    Runnable seekrunnable;
    TextView trimButton;
    TextView tvEndVideo;
    TextView eroor;
    TextView tvStartVideo;
    private VideoPlayerState videoPlayerState;
    private StateObserver videoStateObserver;
    VideoView vvScreen;
    private PowerManager.WakeLock wl;
    Typeface face;

    ScheduledThreadPoolExecutor mDialogDaemon_time;


    SharedPreferences one_play_preferences;
    SharedPreferences.Editor one_play_editor;

    /* renamed from: com.video.compressop.view.Format.1 */
    class C10551 implements Runnable {
        C10551() {
        }

        public void run() {
            if (Format.this.vvScreen.isPlaying()) {
                int curPos = Format.this.vvScreen.getCurrentPosition();
                Format.this.seekVideo.setProgress(curPos);
                try {
                    Format.this.tvStartVideo.setText(Format.formatTimeUnit((long) curPos));
                } catch (ParseException e) {
                    FirebaseCrash.report(new Exception("1"));
                }
                if (curPos == Format.this.duration) {
                    Format.this.seekVideo.setProgress(0);
                    Format.this.btnPlay.setBackgroundResource(R.drawable.play);
                    Format.this.tvStartVideo.setText("00:00");
                    Format.this.handler.removeCallbacks(Format.this.seekrunnable);
                    return;
                }
                Format.this.handler.postDelayed(Format.this.seekrunnable, 500);
                return;
            }
            Format.this.seekVideo.setProgress(Format.this.duration);
            try {
                Format.this.tvStartVideo.setText(Format.formatTimeUnit((long) Format.this.duration));
            } catch (ParseException e2) {
                FirebaseCrash.report(new Exception("2"));
            }
            Format.this.handler.removeCallbacks(Format.this.seekrunnable);
        }
    }

    /* renamed from: com.video.compressop.view.Format.2 */
    class C10562 implements View.OnClickListener {
        C10562() {
        }

        public void onClick(View v) {
            if (Format.this.isPlay) {
                Format.this.vvScreen.pause();
                Format.this.handler.removeCallbacks(Format.this.seekrunnable);
                Format.this.btnPlay.setBackgroundResource(R.drawable.play);
            } else {
                Format.this.vvScreen.seekTo(Format.this.seekVideo.getProgress());
                Format.this.vvScreen.start();
                Format.this.handler.postDelayed(Format.this.seekrunnable, 500);
                Format.this.btnPlay.setBackgroundResource(R.drawable.pause);
            }
            Format.this.isPlay = !Format.this.isPlay;
        }
    }

    /* renamed from: com.video.compressop.view.Format.3 */
    class C10573 implements MediaPlayer.OnErrorListener {
        C10573() {
        }

        public boolean onError(MediaPlayer mp, int what, int extra) {
            Toast.makeText(Format.this.getApplicationContext(), "Video Player Not Supporting", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    /* renamed from: com.video.compressop.view.Format.4 */
    class C10584 implements MediaPlayer.OnPreparedListener {
        C10584() {
        }

        public void onPrepared(MediaPlayer mp) {
            Format.this.duration = Format.this.vvScreen.getDuration();
            Format.this.seekVideo.setMax(Format.this.duration);
            Format.this.tvStartVideo.setText("00:00");
            try {
                Format.this.tvEndVideo.setText(Format.formatTimeUnit((long) Format.this.duration));
            } catch (ParseException e) {
                FirebaseCrash.report(new Exception("3"));
            }
        }
    }

    /* renamed from: com.video.compressop.view.Format.5 */
    class C10595 implements MediaPlayer.OnCompletionListener {
        C10595() {
        }

        public void onCompletion(MediaPlayer mp) {
            Format.this.btnPlay.setBackgroundResource(R.drawable.play);
            Format.this.vvScreen.seekTo(0);
            Format.this.seekVideo.setProgress(0);
            Format.this.tvStartVideo.setText("00:00");
            Format.this.handler.removeCallbacks(Format.this.seekrunnable);
        }
    }

    /* renamed from: com.video.compressop.view.Format.6 */
    class C10606 implements View.OnTouchListener {
        C10606() {
        }

        public boolean onTouch(View arg0, MotionEvent arg1) {
            return false;
        }
    }

    /* renamed from: com.video.compressop.view.Format.7 */
    class C10617 implements View.OnClickListener {
        C10617() {
        }

        public void onClick(View arg0) {
            Format.this.onBackPressed();
        }
    }

    /* renamed from: com.video.compressop.view.Format.8 */
    class C10628 implements View.OnClickListener {
        C10628() {
        }

        public void onClick(View arg0) {
            if (vvScreen.isPlaying()) {
                vvScreen.pause();
                handler.removeCallbacks(seekrunnable);
                btnPlay.setBackgroundResource(R.drawable.play);
                isPlay = false;
            }
            if (outputformat != null) {


                try {
                    stopService(new Intent(Format.this, VideoEngine.class));
                } catch (Exception e) {
                    FirebaseCrash.report(new Exception("4"));
                }


                //outputformat format
                //form form format // fard bod as pal else ntsc

                if (Objects.equals(outputformat, "dv")){
                    one_play_editor.putInt("format", 2);
                    one_play_editor.commit();
                    if (form == 5){
                        sendcode = "pal-dv";
                    }else if (form == 6){
                        sendcode = "ntsc-dv";
                    }else {
                        one_play_editor.putInt("format", 1);
                        one_play_editor.commit();
                    }
                }else if (Objects.equals(outputformat, "mpeg")){
                    one_play_editor.putInt("format", 2);
                    one_play_editor.commit();
                    if (form == 1){
                        sendcode = "pal-dvd";
                    }else if (form == 2){
                        sendcode = "ntsc-dvd";
                    }else if (form == 3){
                        sendcode = "pal-vcd";
                    }else if (form == 4){
                        sendcode = "ntsc-vcd";
                    }else if (form == 7){
                        sendcode = "pal-svcd";
                    }else if (form == 8){
                        sendcode = "ntsc-svcd";
                    }else if (form == 10){
                        one_play_editor.putInt("format", 3);
                        one_play_editor.commit();
                    }else {
                        one_play_editor.putInt("format", 1);
                        one_play_editor.commit();
                    }
                }else if (Objects.equals(outputformat, "ts")){
                    one_play_editor.putInt("format", 2);
                    one_play_editor.commit();
                    if (form == 1){
                        sendcode = "pal-dvd";
                    }else if (form == 2){
                        sendcode = "ntsc-dvd";
                    }else if (form == 3){
                        sendcode = "pal-vcd";
                    }else if (form == 4){
                        sendcode = "ntsc-vcd";
                    }else if (form == 7){
                        sendcode = "pal-svcd";
                    }else if (form == 8){
                        sendcode = "ntsc-svcd";
                    }else if (form == 10){
                        one_play_editor.putInt("format", 3);
                        one_play_editor.commit();
                    }else {
                        one_play_editor.putInt("format", 1);
                        one_play_editor.commit();
                    }
                }else if (Objects.equals(outputformat, "vob")){
                    one_play_editor.putInt("format", 2);
                    one_play_editor.commit();
                    if (form == 1){
                        sendcode = "pal-dvd";
                    }else if (form == 2){
                        sendcode = "ntsc-dvd";
                    }else if (form == 3){
                        sendcode = "pal-vcd";
                    }else if (form == 4){
                        sendcode = "ntsc-vcd";
                    }else if (form == 7){
                        sendcode = "pal-svcd";
                    }else if (form == 8){
                        sendcode = "ntsc-svcd";
                    }else {
                        one_play_editor.putInt("format", 1);
                        one_play_editor.commit();
                    }
                }else if (Objects.equals(outputformat, "flv")){
                    one_play_editor.putInt("format", 2);
                    one_play_editor.commit();
                    if (form == 1){
                        sendcode = "pal-dvd";
                    }else if (form == 2){
                        sendcode = "ntsc-dvd";
                    }else if (form == 3){
                        sendcode = "pal-vcd";
                    }else if (form == 4){
                        sendcode = "ntsc-vcd";
                    }else if (form == 7){
                        sendcode = "pal-svcd";
                    }else if (form == 8){
                        sendcode = "ntsc-svcd";
                    }else {
                        one_play_editor.putInt("format", 1);
                        one_play_editor.commit();
                    }
                }else if (Objects.equals(outputformat, "mov")){
                    one_play_editor.putInt("format", 2);
                    one_play_editor.commit();
                    if (form == 1){
                        sendcode = "pal-dvd";
                    }else if (form == 2){
                        sendcode = "ntsc-dvd";
                    }else if (form == 3){
                        sendcode = "pal-vcd";
                    }else if (form == 4){
                        sendcode = "ntsc-vcd";
                    }else if (form == 7){
                        sendcode = "pal-svcd";
                    }else if (form == 8){
                        sendcode = "ntsc-svcd";
                    }else if (form == 10){
                        one_play_editor.putInt("format", 3);
                        one_play_editor.commit();
                    }else {
                        one_play_editor.putInt("format", 1);
                        one_play_editor.commit();
                    }
                }else if (Objects.equals(outputformat, "avi")){
                    one_play_editor.putInt("format", 2);
                    one_play_editor.commit();
                    if (form == 1){
                        sendcode = "pal-dvd";
                    }else if (form == 2){
                        sendcode = "ntsc-dvd";
                    }else if (form == 3){
                        sendcode = "pal-vcd";
                    }else if (form == 4){
                        sendcode = "ntsc-vcd";
                    }else if (form == 7){
                        sendcode = "pal-svcd";
                    }else if (form == 8){
                        sendcode = "ntsc-svcd";
                    }else if (form == 10){
                        one_play_editor.putInt("format", 3);
                        one_play_editor.commit();
                    }else {
                        one_play_editor.putInt("format", 1);
                        one_play_editor.commit();
                    }
                }else if (Objects.equals(outputformat, "mkv")){
                    one_play_editor.putInt("format", 2);
                    one_play_editor.commit();
                    if (form == 1){
                        sendcode = "pal-dvd";
                    }else if (form == 2){
                        sendcode = "ntsc-dvd";
                    }else if (form == 3){
                        sendcode = "pal-vcd";
                    }else if (form == 4){
                        sendcode = "ntsc-vcd";
                    }else if (form == 7){
                        sendcode = "pal-svcd";
                    }else if (form == 8){
                        sendcode = "ntsc-svcd";
                    }else if (form == 10){
                        one_play_editor.putInt("format", 3);
                        one_play_editor.commit();
                    }else {
                        one_play_editor.putInt("format", 1);
                        one_play_editor.commit();
                    }
                }else if (Objects.equals(outputformat, "mp4")){
                    one_play_editor.putInt("format", 2);
                    one_play_editor.commit();
                    if (form == 1){
                        sendcode = "pal-dvd";
                    }else if (form == 2){
                        sendcode = "ntsc-dvd";
                    }else if (form == 3){
                        sendcode = "pal-vcd";
                    }else if (form == 4){
                        sendcode = "ntsc-vcd";
                    }else if (form == 7){
                        sendcode = "pal-svcd";
                    }else if (form == 8){
                        sendcode = "ntsc-svcd";
                    }else if (form == 10){
                        one_play_editor.putInt("format", 3);
                        one_play_editor.commit();
                    }else {
                        one_play_editor.putInt("format", 1);
                        one_play_editor.commit();
                    }
                }else if (Objects.equals(outputformat, "mpg")){
                    one_play_editor.putInt("format", 2);
                    one_play_editor.commit();
                    if (form == 1){
                        sendcode = "pal-dvd";
                    }else if (form == 2){
                        sendcode = "ntsc-dvd";
                    }else if (form == 3){
                        sendcode = "pal-vcd";
                    }else if (form == 4){
                        sendcode = "ntsc-vcd";
                    }else if (form == 7){
                        sendcode = "pal-svcd";
                    }else if (form == 8){
                        sendcode = "ntsc-svcd";
                    }else if (form == 10){
                        one_play_editor.putInt("format", 3);
                        one_play_editor.commit();
                    }else {
                        one_play_editor.putInt("format", 1);
                        one_play_editor.commit();
                    }
                }else {
                    one_play_editor.putInt("format", 1);
                    one_play_editor.commit();
                }

                if (outputformat != null) {

                    String inputFileName = videoPlayerState.getFilename();
                    path = FileUtils.getTargetFileNameFormat(inputFileName,1,"." + outputformat);

                    try {
                        start_activ();

                        if (coin_alfa){
                            coinint = 100;
                        }
                        if (coinint <= 0){
                            try {
                                end_activ();
                                (new DialogNoTicket(Format.this)).showDialog();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            one_play_editor.putInt("code", 2);
                            one_play_editor.putInt("one", 1);
                            one_play_editor.putString("inputFileName", inputFileName);
                            one_play_editor.putString("quality", outputformat);
                            one_play_editor.putString("format_ch", sendcode);
                            one_play_editor.putString("path", path);
                            one_play_editor.putString("b_S_t", "Changing the video format");
                            one_play_editor.putString("b_F_c", "Unfortunately, formatting failed!");
                            one_play_editor.putString("b_E_t", "Change format");
                            one_play_editor.putString("b_E_c", "Successfully completed");
                            one_play_editor.commit();

                            work = one_play_preferences.getInt("work", 0);
                            if (work == 4){
                                (new DialogFollow(Format.this)).showDialog();
                            }else if (work == 5){
                                (new DialogStar(Format.this)).showDialog();
                            }

                            startService(new Intent(Format.this, VideoEngine.class));

                            try {
                                String appKey = "fd397a30f8c27af492a6dda5e074ae8464c18d3441207e3a";
                                Appodeal.initialize(Format.this, appKey, Appodeal.INTERSTITIAL );

                                Appodeal.setInterstitialCallbacks(new InterstitialCallbacks() {
                                    @Override
                                    public void onInterstitialLoaded(boolean isPrecache) {
                                        if (!play1) {
                                            play1 = true;
                                            Appodeal.show(Format.this, Appodeal.INTERSTITIAL);
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
                        Toast.makeText(Format.this, "error 2", Toast.LENGTH_LONG).show();
                        File appmusic = new File(Format.this.path);
                        if (appmusic.exists()) {
                            appmusic.delete();
                            Format.this.finish();
                        }
                    }
                } else {
                    Toast.makeText(Format.this, "Please specify a format!", Toast.LENGTH_LONG).show();
                }


            } else {
                Toast.makeText(Format.this, "Please specify a format!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void start_activ(){
        dialog = new ProgressDialog(Format.this);
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
                                if (VideoEngine.cancel_pro == 0){
                                }else if (VideoEngine.cancel_pro == 1){
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
        outputformat = null;
    }

    private class StateObserver extends Handler {
        private boolean alreadyStarted;
        private Runnable observerWork;

        /* renamed from: com.video.compressop.view.Format.StateObserver.1 */
        class C10631 implements Runnable {
            C10631() {
            }

            public void run() {
                StateObserver.this.startVideoProgressObserving();
            }
        }

        private StateObserver() {
            this.alreadyStarted = false;
            this.observerWork = new C10631();
        }

        private void startVideoProgressObserving() {
            if (!this.alreadyStarted) {
                this.alreadyStarted = true;
                sendEmptyMessage(0);
            }
        }
    }

    public Format() {
        this.LOADING_DIALOG = MY_NOTIFICATION_ID;
        this.ok = false;
        this.f77k = 0;
        this.videoPlayerState = new VideoPlayerState();
        this.path = null;
        this.duration = 0;
        this.isPlay = false;
        this.handler = new Handler();
        this.plypush = Boolean.valueOf(false);
        this.videoStateObserver = new StateObserver();
        this.seekrunnable = new C10551();
        this.onclickbtnPlay = new C10562();
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(this, SelectVideoActivity.class);
        intent.addFlags(335544320);
        intent.putExtra("activ",2);
        startActivity(intent);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("VideoView", "In on create");
        act = this;
        setContentView(R.layout.format_view);
        one_play_preferences = getApplicationContext().getSharedPreferences("PROJECT_NAME", android.content.Context.MODE_PRIVATE);
        one_play_editor = one_play_preferences.edit();
        coin_alfa = one_play_preferences.getBoolean("COIN_Alfa", false);
        coinint = one_play_preferences.getInt("COIN", 0);
        try {
            this.pm = (PowerManager) getSystemService(POWER_SERVICE);
            this.wl = this.pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        } catch (Exception e) {
            FirebaseCrash.report(new Exception("9"));
        }
        Object lastState = getLastNonConfigurationInstance();
        if (lastState != null) {
            this.videoPlayerState = (VideoPlayerState) lastState;
        } else {
            Bundle extras = getIntent().getExtras();
            this.videoPlayerState.setFilename(extras.getString("videofilename"));
            this.path = extras.getString("videofilename");
        }

        dvd = 1;
        vcd= 1;
        svcd= 1;
        libx264= 1;
        dv= 1;
        n = 0;
        findById();
        outputformat = "mp4";
        this.vvScreen.setVideoPath(this.path);
        this.vvScreen.seekTo(100);
        this.vvScreen.setOnErrorListener(new C10573());
        this.vvScreen.setOnPreparedListener(new C10584());
        this.vvScreen.setOnCompletionListener(new C10595());
        this.vvScreen.setOnTouchListener(new C10606());

        FirebaseCrash.log("log 1");
    }

    private void findById() {
        this.rl_videoplayer = (RelativeLayout) findViewById(R.id.rl_videoplayer);
        this.trimButton = (TextView) findViewById(R.id.trimbut);
        this.trimButton.setOnClickListener(trimClickListener());
        this.vvScreen = (VideoView) findViewById(R.id.videoView1);
        this.btnPlay = (Button) findViewById(R.id.buttonply);
        this.btnPlay.setOnClickListener(this.onclickbtnPlay);
        this.rl_videoplayer.setOnClickListener(this.onclickbtnPlay);
        this.tvStartVideo = (TextView) findViewById(R.id.left_pointer);
        this.tvEndVideo = (TextView) findViewById(R.id.right_pointer);
        this.eroor = (TextView) findViewById(R.id.error);
        this.seekVideo = (SeekBar) findViewById(R.id.sbVideo);
        this.seekVideo.setOnSeekBarChangeListener(this);
        this.f_MPG = (TextView) findViewById(R.id.f_MPG);
        this.f_mp4 = (TextView) findViewById(R.id.f_mp4);
        this.f_MKV = (TextView) findViewById(R.id.f_MKV);
        this.f_avi = (TextView) findViewById(R.id.f_avi);
        this.f_MOV = (TextView) findViewById(R.id.f_MOV);
        this.f_FLV = (TextView) findViewById(R.id.f_FLV);
        this.f_ASF = (TextView) findViewById(R.id.f_ASF);
        this.f_VOB = (TextView) findViewById(R.id.f_VOB);
        this.f_WMV = (TextView) findViewById(R.id.f_WMV);
        this.f_M3U8 = (TextView) findViewById(R.id.f_M3U8);
        this.f_TS = (TextView) findViewById(R.id.f_TS);
        this.f_MPEG = (TextView) findViewById(R.id.f_MPEG);
        this.f_ISMV = (TextView) findViewById(R.id.f_ISMV);
        this.f_NUT = (TextView) findViewById(R.id.f_NUT);
        this.f_DV = (TextView) findViewById(R.id.f_DV);
        this.f_pal_dvd = (TextView) findViewById(R.id.f_pal_dvd);
        this.f_pal_vcd = (TextView) findViewById(R.id.f_pal_vcd);
        this.f_pal_dv = (TextView) findViewById(R.id.f_pal_dv);
        this.f_pal_svcd = (TextView) findViewById(R.id.f_pal_svcd);
        this.f_libx264 = (TextView) findViewById(R.id.f_libx264);
        this.f_pal = (TextView) findViewById(R.id.f_pal);
        this.f_ntsc = (TextView) findViewById(R.id.f_ntsc);
        this.f_MPG.setOnClickListener(this);
        this.f_mp4.setOnClickListener(this);
        this.f_MKV.setOnClickListener(this);
        this.f_avi.setOnClickListener(this);
        this.f_MOV.setOnClickListener(this);
        this.f_FLV.setOnClickListener(this);
        this.f_ASF.setOnClickListener(this);
        this.f_VOB.setOnClickListener(this);
        this.f_WMV.setOnClickListener(this);
        this.f_M3U8.setOnClickListener(this);
        this.f_TS.setOnClickListener(this);
        this.f_MPEG.setOnClickListener(this);
        this.f_ISMV.setOnClickListener(this);
        this.f_NUT.setOnClickListener(this);
        this.f_DV.setOnClickListener(this);
        this.f_pal_dvd.setOnClickListener(this);
        this.f_pal_vcd.setOnClickListener(this);
        this.f_pal_dv .setOnClickListener(this);
        this.f_pal_svcd .setOnClickListener(this);
        this.f_libx264 .setOnClickListener(this);
        this.f_pal .setOnClickListener(this);
        this.f_ntsc .setOnClickListener(this);
        this.btnBack = (ImageView) findViewById(R.id.btnBack);
        this.btnSetting = (ImageView) findViewById(R.id.btnSetting);
        this.btnBack.setOnClickListener(new C10617());
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new DialogHelp(Format.this,"General Description: To change the video format, first select the desired format and select the codecs among the active options to convert (better use the h.264 codec) and then click start and wait for the format change. It may be time. \n Formats: \n MP4: Part of MPEG-4. Easily playable on all devices. High quality and appropriate movie size with this extension. \n MPG: For image and sound compression designed by the group of image experts group. The movie has a good volume and quality. No additional codecs can be played by Windows Media Player and Home VCards \n MKV: The abbreviation for Matoraska is the ability to put multiple audio and subtitles. Easily playable. High quality and appropriate movie size with this extension \n FLV: Used to produce short and small movies. \\n MOV: Introduced by apple company since 1991. \n AVI: The Audio Video Interleave stands for subtitling capability for storing high quality and low-volume movies. The main problem with this format is not played on home vcd \n WMV: The abbreviation for Windows Media Video is a very low-volume file used to make long and low-volume movies of this format, but it's not good quality \n VOB: stands for the Video Object Base and is suitable for DVD and is applicable to all players \n ASF: The middle of Microsoft is provided and is usually used for Windows Media Player \n MPEG: Suitable for DVDs and 325 × 288 quality at 25 frames per second. MPEG misplaced is the inappropriate amount of videos. \n TS: Applicable on VCDs and Smart TVs. Good quality movies and high volume can be used by this extension. This high volume format is not available on most devices \n M3U8: Medium quality videos are good for websites and high-speed live streaming \n DV: Suitable for shooting and recording video with HD quality. \n NUT: A simple format that can store many sounds and subtitles. Little error. The problem is that this format can not be played on all devices. \n ISMV: An abbreviation for IIS Smooth Streaming Video is provided by Microsoft and is suitable for high-speed server and online streaming, featuring low-volume, high-quality content in the form of multiple cut-outs in an extension.")).showDialog();
            }
        });

       face = Typeface.createFromAsset(getAssets(), "fa_font_1.ttf");
        trimButton.setTypeface(face);
        TextView tvt = (TextView) findViewById(R.id.tvt);
        TextView txt_r = (TextView) findViewById(R.id.txt_r);
        TextView txt_co = (TextView) findViewById(R.id.txt_co);
        TextView txt_format = (TextView) findViewById(R.id.txt_format);
        TextView text_help = (TextView) findViewById(R.id.text_help);
        TextView ts_check = (TextView) findViewById(R.id.ts_check);
        final ImageView img_check = (ImageView) findViewById(R.id.img_check);
        LinearLayout click_check = (LinearLayout) findViewById(R.id.click_check);
        LinearLayout col_help = (LinearLayout) findViewById(R.id.col_help);
        tvt.setTypeface(face);
        text_help.setTypeface(face);
        txt_r.setTypeface(face);
        txt_co.setTypeface(face);
        txt_format.setTypeface(face);
        ts_check.setTypeface(face);

        try {
            f_pal_dvd.setBackgroundResource(R.drawable.btn_off_gone);
            f_pal_vcd.setBackgroundResource(R.drawable.btn_off_gone);
            f_pal_svcd.setBackgroundResource(R.drawable.btn_off_gone);
            f_libx264.setBackgroundResource(R.drawable.btn_off_gone);
            f_pal_dv.setBackgroundResource(R.drawable.btn_off_gone);
        } catch (Exception e) {
            FirebaseCrash.report(new Exception("10"));
        }

        try {
            f_pal_dvd.setTextColor(getResources().getColor(R.color.app_color_hint));
            f_pal_vcd.setTextColor(getResources().getColor(R.color.app_color_hint));
            f_pal_svcd.setTextColor(getResources().getColor(R.color.app_color_hint));
            f_libx264.setTextColor(getResources().getColor(R.color.app_color_hint));
            f_pal_dv.setTextColor(getResources().getColor(R.color.app_color_hint));
        } catch (Resources.NotFoundException e) {
            FirebaseCrash.report(new Exception("11"));
        }

        try {
            f_pal.setBackgroundResource(R.drawable.btn_off_gone);
            f_pal.setTextColor(getResources().getColor(R.color.app_color_hint));
            f_ntsc.setBackgroundResource(R.drawable.btn_off_gone);
            f_ntsc.setTextColor(getResources().getColor(R.color.app_color_hint));
        } catch (Resources.NotFoundException e) {
            FirebaseCrash.report(new Exception("12"));
        }

        int check = one_play_preferences.getInt("check_2",0);
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
                    one_play_editor.putInt("check_2", 1);
                    one_play_editor.apply();
                }else {
                    ch = 0;
                    img_check.setImageResource(R.drawable.sport_me_tic_music_off);
                    one_play_editor.putInt("check_2", 0);
                    one_play_editor.apply();
                }
            }
        });
    }

    public Object onRetainNonConfigurationInstance() {
        Log.i("VideoView", "In on retain");
        return this.videoPlayerState;
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

    public static String formatTimeUnit(long millis) throws ParseException {
        try {
            String out;
            long hours = millis / 3600000;
            long remaining_minutes = (millis - (3600000 * hours)) / 60000;
            String minutes = String.valueOf(remaining_minutes);
            if (minutes.equals(Integer.valueOf(0))) {
                minutes = "00";
            }
            long seconds = (millis - (3600000 * hours)) - (60000 * remaining_minutes);
            seconds = (seconds / 1000) % 60 ;
            String seconds2 = String.valueOf(seconds);
            if (seconds2.length() < 2) {
                seconds2 = seconds2.substring(0, 1);
            } else {
                seconds2 = seconds2.substring(0, 2);
            }
            if (hours > 0) {
                out = new StringBuilder(String.valueOf(hours)).append(":").append(minutes).append(":").append(seconds2).toString();
            } else {
                out = new StringBuilder(String.valueOf(minutes)).append(":").append(seconds2).toString();
            }
            return out;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.f_MPG /*2131099663*/:

                try {
                    this.f_MPG.setBackgroundResource(R.drawable.btn_on);
                    this.f_mp4.setBackgroundResource(R.drawable.btn_off);
                    this.f_MKV.setBackgroundResource(R.drawable.btn_off);
                    this.f_avi.setBackgroundResource(R.drawable.btn_off);
                    this.f_MOV.setBackgroundResource(R.drawable.btn_off);
                    this.f_FLV.setBackgroundResource(R.drawable.btn_off);
                    this.f_ASF.setBackgroundResource(R.drawable.btn_off);
                    this.f_VOB.setBackgroundResource(R.drawable.btn_off);
                    this.f_WMV.setBackgroundResource(R.drawable.btn_off);
                    this.f_M3U8.setBackgroundResource(R.drawable.btn_off);
                    this.f_TS.setBackgroundResource(R.drawable.btn_off);
                    this.f_MPEG.setBackgroundResource(R.drawable.btn_off);
                    this.f_ISMV.setBackgroundResource(R.drawable.btn_off);
                    this.f_NUT.setBackgroundResource(R.drawable.btn_off);
                    this.f_DV.setBackgroundResource(R.drawable.btn_off);

                    f_pal_dvd.setBackgroundResource(R.drawable.btn_off);
                    f_pal_vcd.setBackgroundResource(R.drawable.btn_off);
                    f_pal_svcd.setBackgroundResource(R.drawable.btn_off);
                    f_libx264.setBackgroundResource(R.drawable.btn_off);
                    f_pal_dv.setBackgroundResource(R.drawable.btn_off_gone);

                    f_pal_dvd.setTextColor(getResources().getColor(R.color.app_color));
                    f_pal_vcd.setTextColor(getResources().getColor(R.color.app_color));
                    f_pal_svcd.setTextColor(getResources().getColor(R.color.app_color));
                    f_libx264.setTextColor(getResources().getColor(R.color.app_color));
                    f_pal_dv.setTextColor(getResources().getColor(R.color.app_color_hint));

                    f_pal.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_ntsc.setBackgroundResource(R.drawable.btn_off_gone);
                    f_ntsc.setTextColor(getResources().getColor(R.color.app_color_hint));
                } catch (Resources.NotFoundException e) {
                    FirebaseCrash.report(new Exception("13"));
                }

                dvd = 2;
                vcd = 2;
                svcd = 2;
                libx264 = 2;
                dv = 1;

                outputformat = "mpg";

                break;
            case R.id.f_mp4 /*2131099664*/:

                try {
                    this.f_MPG.setBackgroundResource(R.drawable.btn_off);
                    this.f_mp4.setBackgroundResource(R.drawable.btn_on);
                    this.f_MKV.setBackgroundResource(R.drawable.btn_off);
                    this.f_avi.setBackgroundResource(R.drawable.btn_off);
                    this.f_MOV.setBackgroundResource(R.drawable.btn_off);
                    this.f_FLV.setBackgroundResource(R.drawable.btn_off);
                    this.f_ASF.setBackgroundResource(R.drawable.btn_off);
                    this.f_VOB.setBackgroundResource(R.drawable.btn_off);
                    this.f_WMV.setBackgroundResource(R.drawable.btn_off);
                    this.f_M3U8.setBackgroundResource(R.drawable.btn_off);
                    this.f_TS.setBackgroundResource(R.drawable.btn_off);
                    this.f_MPEG.setBackgroundResource(R.drawable.btn_off);
                    this.f_ISMV.setBackgroundResource(R.drawable.btn_off);
                    this.f_NUT.setBackgroundResource(R.drawable.btn_off);
                    this.f_DV.setBackgroundResource(R.drawable.btn_off);

                    f_pal_dvd.setBackgroundResource(R.drawable.btn_off);
                    f_pal_vcd.setBackgroundResource(R.drawable.btn_off);
                    f_pal_svcd.setBackgroundResource(R.drawable.btn_off);
                    f_libx264.setBackgroundResource(R.drawable.btn_off);
                    f_pal_dv.setBackgroundResource(R.drawable.btn_off_gone);

                    f_pal_dvd.setTextColor(getResources().getColor(R.color.app_color));
                    f_pal_vcd.setTextColor(getResources().getColor(R.color.app_color));
                    f_pal_svcd.setTextColor(getResources().getColor(R.color.app_color));
                    f_libx264.setTextColor(getResources().getColor(R.color.app_color));
                    f_pal_dv.setTextColor(getResources().getColor(R.color.app_color_hint));

                    f_pal.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_ntsc.setBackgroundResource(R.drawable.btn_off_gone);
                    f_ntsc.setTextColor(getResources().getColor(R.color.app_color_hint));
                } catch (Resources.NotFoundException e) {
                    FirebaseCrash.report(new Exception("14"));
                }

                dvd = 2;
                vcd = 2;
                svcd = 2;
                libx264 = 2;
                dv = 1;

                outputformat = "mp4";
                break;
            case R.id.f_MKV /*2131099664*/:

                try {
                    this.f_MPG.setBackgroundResource(R.drawable.btn_off);
                    this.f_mp4.setBackgroundResource(R.drawable.btn_off);
                    this.f_MKV.setBackgroundResource(R.drawable.btn_on);
                    this.f_avi.setBackgroundResource(R.drawable.btn_off);
                    this.f_MOV.setBackgroundResource(R.drawable.btn_off);
                    this.f_FLV.setBackgroundResource(R.drawable.btn_off);
                    this.f_ASF.setBackgroundResource(R.drawable.btn_off);
                    this.f_VOB.setBackgroundResource(R.drawable.btn_off);
                    this.f_WMV.setBackgroundResource(R.drawable.btn_off);
                    this.f_M3U8.setBackgroundResource(R.drawable.btn_off);
                    this.f_TS.setBackgroundResource(R.drawable.btn_off);
                    this.f_MPEG.setBackgroundResource(R.drawable.btn_off);
                    this.f_ISMV.setBackgroundResource(R.drawable.btn_off);
                    this.f_NUT.setBackgroundResource(R.drawable.btn_off);
                    this.f_DV.setBackgroundResource(R.drawable.btn_off);

                    f_pal_dvd.setBackgroundResource(R.drawable.btn_off);
                    f_pal_vcd.setBackgroundResource(R.drawable.btn_off);
                    f_pal_svcd.setBackgroundResource(R.drawable.btn_off);
                    f_libx264.setBackgroundResource(R.drawable.btn_off);
                    f_pal_dv.setBackgroundResource(R.drawable.btn_off_gone);

                    f_pal_dvd.setTextColor(getResources().getColor(R.color.app_color));
                    f_pal_vcd.setTextColor(getResources().getColor(R.color.app_color));
                    f_pal_svcd.setTextColor(getResources().getColor(R.color.app_color));
                    f_libx264.setTextColor(getResources().getColor(R.color.app_color));
                    f_pal_dv.setTextColor(getResources().getColor(R.color.app_color_hint));

                    f_pal.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_ntsc.setBackgroundResource(R.drawable.btn_off_gone);
                    f_ntsc.setTextColor(getResources().getColor(R.color.app_color_hint));
                } catch (Resources.NotFoundException e) {
                    FirebaseCrash.report(new Exception("15"));
                }

                dvd = 2;
                vcd = 2;
                svcd = 2;
                libx264 = 2;
                dv = 1;

                outputformat = "mkv";

                break;
            case R.id.f_avi /*2131099664*/:

                try {
                    this.f_MPG.setBackgroundResource(R.drawable.btn_off);
                    this.f_mp4.setBackgroundResource(R.drawable.btn_off);
                    this.f_MKV.setBackgroundResource(R.drawable.btn_off);
                    this.f_avi.setBackgroundResource(R.drawable.btn_on);
                    this.f_MOV.setBackgroundResource(R.drawable.btn_off);
                    this.f_FLV.setBackgroundResource(R.drawable.btn_off);
                    this.f_ASF.setBackgroundResource(R.drawable.btn_off);
                    this.f_VOB.setBackgroundResource(R.drawable.btn_off);
                    this.f_WMV.setBackgroundResource(R.drawable.btn_off);
                    this.f_M3U8.setBackgroundResource(R.drawable.btn_off);
                    this.f_TS.setBackgroundResource(R.drawable.btn_off);
                    this.f_MPEG.setBackgroundResource(R.drawable.btn_off);
                    this.f_ISMV.setBackgroundResource(R.drawable.btn_off);
                    this.f_NUT.setBackgroundResource(R.drawable.btn_off);
                    this.f_DV.setBackgroundResource(R.drawable.btn_off);

                    f_pal_dvd.setBackgroundResource(R.drawable.btn_off);
                    f_pal_vcd.setBackgroundResource(R.drawable.btn_off);
                    f_pal_svcd.setBackgroundResource(R.drawable.btn_off);
                    f_libx264.setBackgroundResource(R.drawable.btn_off);
                    f_pal_dv.setBackgroundResource(R.drawable.btn_off_gone);

                    f_pal_dvd.setTextColor(getResources().getColor(R.color.app_color));
                    f_pal_vcd.setTextColor(getResources().getColor(R.color.app_color));
                    f_pal_svcd.setTextColor(getResources().getColor(R.color.app_color));
                    f_libx264.setTextColor(getResources().getColor(R.color.app_color));
                    f_pal_dv.setTextColor(getResources().getColor(R.color.app_color_hint));


                    f_pal.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_ntsc.setBackgroundResource(R.drawable.btn_off_gone);
                    f_ntsc.setTextColor(getResources().getColor(R.color.app_color_hint));
                } catch (Resources.NotFoundException e) {
                    FirebaseCrash.report(new Exception("16"));
                }

                dvd = 2;
                vcd = 2;
                svcd = 2;
                libx264 = 2;
                dv = 1;

                outputformat = "avi";

                break;
            case R.id.f_MOV /*2131099664*/:

                try {
                    this.f_MPG.setBackgroundResource(R.drawable.btn_off);
                    this.f_mp4.setBackgroundResource(R.drawable.btn_off);
                    this.f_MKV.setBackgroundResource(R.drawable.btn_off);
                    this.f_avi.setBackgroundResource(R.drawable.btn_off);
                    this.f_MOV.setBackgroundResource(R.drawable.btn_on);
                    this.f_FLV.setBackgroundResource(R.drawable.btn_off);
                    this.f_ASF.setBackgroundResource(R.drawable.btn_off);
                    this.f_VOB.setBackgroundResource(R.drawable.btn_off);
                    this.f_WMV.setBackgroundResource(R.drawable.btn_off);
                    this.f_M3U8.setBackgroundResource(R.drawable.btn_off);
                    this.f_TS.setBackgroundResource(R.drawable.btn_off);
                    this.f_MPEG.setBackgroundResource(R.drawable.btn_off);
                    this.f_ISMV.setBackgroundResource(R.drawable.btn_off);
                    this.f_NUT.setBackgroundResource(R.drawable.btn_off);
                    this.f_DV.setBackgroundResource(R.drawable.btn_off);

                    f_pal_dvd.setBackgroundResource(R.drawable.btn_off);
                    f_pal_vcd.setBackgroundResource(R.drawable.btn_off);
                    f_pal_svcd.setBackgroundResource(R.drawable.btn_off);
                    f_libx264.setBackgroundResource(R.drawable.btn_off);
                    f_pal_dv.setBackgroundResource(R.drawable.btn_off_gone);

                    f_pal_dvd.setTextColor(getResources().getColor(R.color.app_color));
                    f_pal_vcd.setTextColor(getResources().getColor(R.color.app_color));
                    f_pal_svcd.setTextColor(getResources().getColor(R.color.app_color));
                    f_libx264.setTextColor(getResources().getColor(R.color.app_color));
                    f_pal_dv.setTextColor(getResources().getColor(R.color.app_color_hint));

                    f_pal.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_ntsc.setBackgroundResource(R.drawable.btn_off_gone);
                    f_ntsc.setTextColor(getResources().getColor(R.color.app_color_hint));
                } catch (Resources.NotFoundException e) {
                    FirebaseCrash.report(new Exception("17"));
                }

                dvd = 2;
                vcd = 2;
                svcd = 2;
                libx264 = 2;
                dv = 1;

                outputformat = "mov";

                break;
            case R.id.f_FLV /*2131099664*/:

                try {
                    this.f_MPG.setBackgroundResource(R.drawable.btn_off);
                    this.f_mp4.setBackgroundResource(R.drawable.btn_off);
                    this.f_MKV.setBackgroundResource(R.drawable.btn_off);
                    this.f_avi.setBackgroundResource(R.drawable.btn_off);
                    this.f_MOV.setBackgroundResource(R.drawable.btn_off);
                    this.f_FLV.setBackgroundResource(R.drawable.btn_on);
                    this.f_ASF.setBackgroundResource(R.drawable.btn_off);
                    this.f_VOB.setBackgroundResource(R.drawable.btn_off);
                    this.f_WMV.setBackgroundResource(R.drawable.btn_off);
                    this.f_M3U8.setBackgroundResource(R.drawable.btn_off);
                    this.f_TS.setBackgroundResource(R.drawable.btn_off);
                    this.f_MPEG.setBackgroundResource(R.drawable.btn_off);
                    this.f_ISMV.setBackgroundResource(R.drawable.btn_off);
                    this.f_NUT.setBackgroundResource(R.drawable.btn_off);
                    this.f_DV.setBackgroundResource(R.drawable.btn_off);

                    f_pal_dvd.setBackgroundResource(R.drawable.btn_off);
                    f_pal_vcd.setBackgroundResource(R.drawable.btn_off);
                    f_pal_svcd.setBackgroundResource(R.drawable.btn_off);
                    f_libx264.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal_dv.setBackgroundResource(R.drawable.btn_off_gone);

                    f_pal_dvd.setTextColor(getResources().getColor(R.color.app_color));
                    f_pal_vcd.setTextColor(getResources().getColor(R.color.app_color));
                    f_pal_svcd.setTextColor(getResources().getColor(R.color.app_color));
                    f_libx264.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_pal_dv.setTextColor(getResources().getColor(R.color.app_color_hint));

                    f_pal.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_ntsc.setBackgroundResource(R.drawable.btn_off_gone);
                    f_ntsc.setTextColor(getResources().getColor(R.color.app_color_hint));
                } catch (Resources.NotFoundException e) {
                    FirebaseCrash.report(new Exception("18"));
                }

                dvd = 2;
                vcd = 2;
                svcd = 2;
                libx264 = 1;
                dv = 1;

                outputformat = "flv";

                break;
            case R.id.f_ASF /*2131099664*/:

                try {
                    this.f_MPG.setBackgroundResource(R.drawable.btn_off);
                    this.f_mp4.setBackgroundResource(R.drawable.btn_off);
                    this.f_MKV.setBackgroundResource(R.drawable.btn_off);
                    this.f_avi.setBackgroundResource(R.drawable.btn_off);
                    this.f_MOV.setBackgroundResource(R.drawable.btn_off);
                    this.f_FLV.setBackgroundResource(R.drawable.btn_off);
                    this.f_ASF.setBackgroundResource(R.drawable.btn_on);
                    this.f_VOB.setBackgroundResource(R.drawable.btn_off);
                    this.f_WMV.setBackgroundResource(R.drawable.btn_off);
                    this.f_M3U8.setBackgroundResource(R.drawable.btn_off);
                    this.f_TS.setBackgroundResource(R.drawable.btn_off);
                    this.f_MPEG.setBackgroundResource(R.drawable.btn_off);
                    this.f_ISMV.setBackgroundResource(R.drawable.btn_off);
                    this.f_NUT.setBackgroundResource(R.drawable.btn_off);
                    this.f_DV.setBackgroundResource(R.drawable.btn_off);

                    f_pal_dvd.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal_vcd.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal_svcd.setBackgroundResource(R.drawable.btn_off_gone);
                    f_libx264.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal_dv.setBackgroundResource(R.drawable.btn_off_gone);

                    f_pal_dvd.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_pal_vcd.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_pal_svcd.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_libx264.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_pal_dv.setTextColor(getResources().getColor(R.color.app_color_hint));

                    f_pal.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_ntsc.setBackgroundResource(R.drawable.btn_off_gone);
                    f_ntsc.setTextColor(getResources().getColor(R.color.app_color_hint));
                } catch (Resources.NotFoundException e) {
                    FirebaseCrash.report(new Exception("19"));
                }

                dvd = 1;
                vcd = 1;
                svcd = 1;
                libx264 = 1;
                dv = 1;

                outputformat = "asf";

                break;
            case R.id.f_VOB /*2131099664*/:

                try {
                    this.f_MPG.setBackgroundResource(R.drawable.btn_off);
                    this.f_mp4.setBackgroundResource(R.drawable.btn_off);
                    this.f_MKV.setBackgroundResource(R.drawable.btn_off);
                    this.f_avi.setBackgroundResource(R.drawable.btn_off);
                    this.f_MOV.setBackgroundResource(R.drawable.btn_off);
                    this.f_FLV.setBackgroundResource(R.drawable.btn_off);
                    this.f_ASF.setBackgroundResource(R.drawable.btn_off);
                    this.f_VOB.setBackgroundResource(R.drawable.btn_on);
                    this.f_WMV.setBackgroundResource(R.drawable.btn_off);
                    this.f_M3U8.setBackgroundResource(R.drawable.btn_off);
                    this.f_TS.setBackgroundResource(R.drawable.btn_off);
                    this.f_MPEG.setBackgroundResource(R.drawable.btn_off);
                    this.f_ISMV.setBackgroundResource(R.drawable.btn_off);
                    this.f_NUT.setBackgroundResource(R.drawable.btn_off);
                    this.f_DV.setBackgroundResource(R.drawable.btn_off);

                    f_pal_dvd.setBackgroundResource(R.drawable.btn_off);
                    f_pal_vcd.setBackgroundResource(R.drawable.btn_off);
                    f_pal_svcd.setBackgroundResource(R.drawable.btn_off);
                    f_libx264.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal_dv.setBackgroundResource(R.drawable.btn_off_gone);

                    f_pal_dvd.setTextColor(getResources().getColor(R.color.app_color));
                    f_pal_vcd.setTextColor(getResources().getColor(R.color.app_color));
                    f_pal_svcd.setTextColor(getResources().getColor(R.color.app_color));
                    f_libx264.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_pal_dv.setTextColor(getResources().getColor(R.color.app_color_hint));

                    f_pal.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_ntsc.setBackgroundResource(R.drawable.btn_off_gone);
                    f_ntsc.setTextColor(getResources().getColor(R.color.app_color_hint));
                } catch (Resources.NotFoundException e) {
                    FirebaseCrash.report(new Exception("20"));
                }

                dvd = 2;
                vcd = 2;
                svcd = 2;
                libx264 = 1;
                dv = 1;

                outputformat = "vob";

                break;
            case R.id.f_WMV /*2131099664*/:

                try {
                    this.f_MPG.setBackgroundResource(R.drawable.btn_off);
                    this.f_mp4.setBackgroundResource(R.drawable.btn_off);
                    this.f_MKV.setBackgroundResource(R.drawable.btn_off);
                    this.f_avi.setBackgroundResource(R.drawable.btn_off);
                    this.f_MOV.setBackgroundResource(R.drawable.btn_off);
                    this.f_FLV.setBackgroundResource(R.drawable.btn_off);
                    this.f_ASF.setBackgroundResource(R.drawable.btn_off);
                    this.f_VOB.setBackgroundResource(R.drawable.btn_off);
                    this.f_WMV.setBackgroundResource(R.drawable.btn_on);
                    this.f_M3U8.setBackgroundResource(R.drawable.btn_off);
                    this.f_TS.setBackgroundResource(R.drawable.btn_off);
                    this.f_MPEG.setBackgroundResource(R.drawable.btn_off);
                    this.f_ISMV.setBackgroundResource(R.drawable.btn_off);
                    this.f_NUT.setBackgroundResource(R.drawable.btn_off);
                    this.f_DV.setBackgroundResource(R.drawable.btn_off);

                    f_pal_dvd.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal_vcd.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal_svcd.setBackgroundResource(R.drawable.btn_off_gone);
                    f_libx264.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal_dv.setBackgroundResource(R.drawable.btn_off_gone);

                    f_pal_dvd.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_pal_vcd.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_pal_svcd.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_libx264.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_pal_dv.setTextColor(getResources().getColor(R.color.app_color_hint));

                    f_pal.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_ntsc.setBackgroundResource(R.drawable.btn_off_gone);
                    f_ntsc.setTextColor(getResources().getColor(R.color.app_color_hint));
                } catch (Resources.NotFoundException e) {
                    FirebaseCrash.report(new Exception("21"));
                }

                dvd = 1;
                vcd = 1;
                svcd = 1;
                libx264 = 1;
                dv = 1;

                outputformat = "wmv";

                break;
            case R.id.f_M3U8 /*2131099664*/:

                try {
                    this.f_MPG.setBackgroundResource(R.drawable.btn_off);
                    this.f_mp4.setBackgroundResource(R.drawable.btn_off);
                    this.f_MKV.setBackgroundResource(R.drawable.btn_off);
                    this.f_avi.setBackgroundResource(R.drawable.btn_off);
                    this.f_MOV.setBackgroundResource(R.drawable.btn_off);
                    this.f_FLV.setBackgroundResource(R.drawable.btn_off);
                    this.f_ASF.setBackgroundResource(R.drawable.btn_off);
                    this.f_VOB.setBackgroundResource(R.drawable.btn_off);
                    this.f_WMV.setBackgroundResource(R.drawable.btn_off);
                    this.f_M3U8.setBackgroundResource(R.drawable.btn_on);
                    this.f_TS.setBackgroundResource(R.drawable.btn_off);
                    this.f_MPEG.setBackgroundResource(R.drawable.btn_off);
                    this.f_ISMV.setBackgroundResource(R.drawable.btn_off);
                    this.f_NUT.setBackgroundResource(R.drawable.btn_off);
                    this.f_DV.setBackgroundResource(R.drawable.btn_off);

                    f_pal_dvd.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal_vcd.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal_svcd.setBackgroundResource(R.drawable.btn_off_gone);
                    f_libx264.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal_dv.setBackgroundResource(R.drawable.btn_off_gone);

                    f_pal_dvd.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_pal_vcd.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_pal_svcd.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_libx264.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_pal_dv.setTextColor(getResources().getColor(R.color.app_color_hint));

                    f_pal.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_ntsc.setBackgroundResource(R.drawable.btn_off_gone);
                    f_ntsc.setTextColor(getResources().getColor(R.color.app_color_hint));
                } catch (Resources.NotFoundException e) {
                    FirebaseCrash.report(new Exception("22"));
                }

                dvd = 1;
                vcd = 1;
                svcd = 1;
                libx264 = 1;
                dv = 1;

                outputformat = "m3u8";

                break;
            case R.id.f_TS /*2131099664*/:

                try {
                    this.f_MPG.setBackgroundResource(R.drawable.btn_off);
                    this.f_mp4.setBackgroundResource(R.drawable.btn_off);
                    this.f_MKV.setBackgroundResource(R.drawable.btn_off);
                    this.f_avi.setBackgroundResource(R.drawable.btn_off);
                    this.f_MOV.setBackgroundResource(R.drawable.btn_off);
                    this.f_FLV.setBackgroundResource(R.drawable.btn_off);
                    this.f_ASF.setBackgroundResource(R.drawable.btn_off);
                    this.f_VOB.setBackgroundResource(R.drawable.btn_off);
                    this.f_WMV.setBackgroundResource(R.drawable.btn_off);
                    this.f_M3U8.setBackgroundResource(R.drawable.btn_off);
                    this.f_TS.setBackgroundResource(R.drawable.btn_on);
                    this.f_MPEG.setBackgroundResource(R.drawable.btn_off);
                    this.f_ISMV.setBackgroundResource(R.drawable.btn_off);
                    this.f_NUT.setBackgroundResource(R.drawable.btn_off);
                    this.f_DV.setBackgroundResource(R.drawable.btn_off);

                    f_pal_dvd.setBackgroundResource(R.drawable.btn_off);
                    f_pal_vcd.setBackgroundResource(R.drawable.btn_off);
                    f_pal_svcd.setBackgroundResource(R.drawable.btn_off);
                    f_libx264.setBackgroundResource(R.drawable.btn_off);
                    f_pal_dv.setBackgroundResource(R.drawable.btn_off_gone);

                    f_pal_dvd.setTextColor(getResources().getColor(R.color.app_color));
                    f_pal_vcd.setTextColor(getResources().getColor(R.color.app_color));
                    f_pal_svcd.setTextColor(getResources().getColor(R.color.app_color));
                    f_libx264.setTextColor(getResources().getColor(R.color.app_color));
                    f_pal_dv.setTextColor(getResources().getColor(R.color.app_color_hint));

                    f_pal.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_ntsc.setBackgroundResource(R.drawable.btn_off_gone);
                    f_ntsc.setTextColor(getResources().getColor(R.color.app_color_hint));
                } catch (Resources.NotFoundException e) {
                    FirebaseCrash.report(new Exception("23"));
                }

                dvd = 2;
                vcd = 2;
                svcd = 2;
                libx264 = 2;
                dv = 1;

                outputformat = "ts";

                break;
            case R.id.f_MPEG /*2131099664*/:

                try {
                    this.f_MPG.setBackgroundResource(R.drawable.btn_off);
                    this.f_mp4.setBackgroundResource(R.drawable.btn_off);
                    this.f_MKV.setBackgroundResource(R.drawable.btn_off);
                    this.f_avi.setBackgroundResource(R.drawable.btn_off);
                    this.f_MOV.setBackgroundResource(R.drawable.btn_off);
                    this.f_FLV.setBackgroundResource(R.drawable.btn_off);
                    this.f_ASF.setBackgroundResource(R.drawable.btn_off);
                    this.f_VOB.setBackgroundResource(R.drawable.btn_off);
                    this.f_WMV.setBackgroundResource(R.drawable.btn_off);
                    this.f_M3U8.setBackgroundResource(R.drawable.btn_off);
                    this.f_TS.setBackgroundResource(R.drawable.btn_off);
                    this.f_MPEG.setBackgroundResource(R.drawable.btn_on);
                    this.f_ISMV.setBackgroundResource(R.drawable.btn_off);
                    this.f_NUT.setBackgroundResource(R.drawable.btn_off);
                    this.f_DV.setBackgroundResource(R.drawable.btn_off);

                    f_pal_dvd.setBackgroundResource(R.drawable.btn_off);
                    f_pal_vcd.setBackgroundResource(R.drawable.btn_off);
                    f_pal_svcd.setBackgroundResource(R.drawable.btn_off);
                    f_libx264.setBackgroundResource(R.drawable.btn_off);
                    f_pal_dv.setBackgroundResource(R.drawable.btn_off_gone);

                    f_pal_dvd.setTextColor(getResources().getColor(R.color.app_color));
                    f_pal_vcd.setTextColor(getResources().getColor(R.color.app_color));
                    f_pal_svcd.setTextColor(getResources().getColor(R.color.app_color));
                    f_libx264.setTextColor(getResources().getColor(R.color.app_color));
                    f_pal_dv.setTextColor(getResources().getColor(R.color.app_color_hint));

                    f_pal.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_ntsc.setBackgroundResource(R.drawable.btn_off_gone);
                    f_ntsc.setTextColor(getResources().getColor(R.color.app_color_hint));
                } catch (Resources.NotFoundException e) {
                    FirebaseCrash.report(new Exception("24"));
                }

                dvd = 2;
                vcd = 2;
                svcd = 2;
                libx264 = 2;
                dv = 1;

                outputformat = "mpeg";

                break;
            case R.id.f_ISMV /*2131099664*/:

                try {
                    this.f_MPG.setBackgroundResource(R.drawable.btn_off);
                    this.f_mp4.setBackgroundResource(R.drawable.btn_off);
                    this.f_MKV.setBackgroundResource(R.drawable.btn_off);
                    this.f_avi.setBackgroundResource(R.drawable.btn_off);
                    this.f_MOV.setBackgroundResource(R.drawable.btn_off);
                    this.f_FLV.setBackgroundResource(R.drawable.btn_off);
                    this.f_ASF.setBackgroundResource(R.drawable.btn_off);
                    this.f_VOB.setBackgroundResource(R.drawable.btn_off);
                    this.f_WMV.setBackgroundResource(R.drawable.btn_off);
                    this.f_M3U8.setBackgroundResource(R.drawable.btn_off);
                    this.f_TS.setBackgroundResource(R.drawable.btn_off);
                    this.f_MPEG.setBackgroundResource(R.drawable.btn_off);
                    this.f_ISMV.setBackgroundResource(R.drawable.btn_on);
                    this.f_NUT.setBackgroundResource(R.drawable.btn_off);
                    this.f_DV.setBackgroundResource(R.drawable.btn_off);

                    f_pal_dvd.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal_vcd.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal_svcd.setBackgroundResource(R.drawable.btn_off_gone);
                    f_libx264.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal_dv.setBackgroundResource(R.drawable.btn_off_gone);

                    f_pal_dvd.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_pal_vcd.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_pal_svcd.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_libx264.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_pal_dv.setTextColor(getResources().getColor(R.color.app_color_hint));

                    f_pal.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_ntsc.setBackgroundResource(R.drawable.btn_off_gone);
                    f_ntsc.setTextColor(getResources().getColor(R.color.app_color_hint));
                } catch (Resources.NotFoundException e) {
                    FirebaseCrash.report(new Exception("25"));
                }

                dvd = 1;
                vcd = 1;
                svcd = 1;
                libx264 = 1;
                dv = 1;

                outputformat = "ismv";

                break;
            case R.id.f_NUT /*2131099664*/:

                try {
                    this.f_MPG.setBackgroundResource(R.drawable.btn_off);
                    this.f_mp4.setBackgroundResource(R.drawable.btn_off);
                    this.f_MKV.setBackgroundResource(R.drawable.btn_off);
                    this.f_avi.setBackgroundResource(R.drawable.btn_off);
                    this.f_MOV.setBackgroundResource(R.drawable.btn_off);
                    this.f_FLV.setBackgroundResource(R.drawable.btn_off);
                    this.f_ASF.setBackgroundResource(R.drawable.btn_off);
                    this.f_VOB.setBackgroundResource(R.drawable.btn_off);
                    this.f_WMV.setBackgroundResource(R.drawable.btn_off);
                    this.f_M3U8.setBackgroundResource(R.drawable.btn_off);
                    this.f_TS.setBackgroundResource(R.drawable.btn_off);
                    this.f_MPEG.setBackgroundResource(R.drawable.btn_off);
                    this.f_ISMV.setBackgroundResource(R.drawable.btn_off);
                    this.f_NUT.setBackgroundResource(R.drawable.btn_on);
                    this.f_DV.setBackgroundResource(R.drawable.btn_off);

                    f_pal_dvd.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal_vcd.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal_svcd.setBackgroundResource(R.drawable.btn_off_gone);
                    f_libx264.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal_dv.setBackgroundResource(R.drawable.btn_off_gone);

                    f_pal_dvd.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_pal_vcd.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_pal_svcd.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_libx264.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_pal_dv.setTextColor(getResources().getColor(R.color.app_color_hint));

                    f_pal.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_ntsc.setBackgroundResource(R.drawable.btn_off_gone);
                    f_ntsc.setTextColor(getResources().getColor(R.color.app_color_hint));
                } catch (Resources.NotFoundException e) {
                    FirebaseCrash.report(new Exception("26"));
                }

                dvd = 1;
                vcd = 1;
                svcd = 1;
                libx264 = 1;
                dv = 1;

                outputformat = "nut";

                break;
            case R.id.f_DV /*2131099664*/:

                try {
                    this.f_MPG.setBackgroundResource(R.drawable.btn_off);
                    this.f_mp4.setBackgroundResource(R.drawable.btn_off);
                    this.f_MKV.setBackgroundResource(R.drawable.btn_off);
                    this.f_avi.setBackgroundResource(R.drawable.btn_off);
                    this.f_MOV.setBackgroundResource(R.drawable.btn_off);
                    this.f_FLV.setBackgroundResource(R.drawable.btn_off);
                    this.f_ASF.setBackgroundResource(R.drawable.btn_off);
                    this.f_VOB.setBackgroundResource(R.drawable.btn_off);
                    this.f_WMV.setBackgroundResource(R.drawable.btn_off);
                    this.f_M3U8.setBackgroundResource(R.drawable.btn_off);
                    this.f_TS.setBackgroundResource(R.drawable.btn_off);
                    this.f_MPEG.setBackgroundResource(R.drawable.btn_off);
                    this.f_ISMV.setBackgroundResource(R.drawable.btn_off);
                    this.f_NUT.setBackgroundResource(R.drawable.btn_off);
                    this.f_DV.setBackgroundResource(R.drawable.btn_on);

                    f_pal_dvd.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal_vcd.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal_svcd.setBackgroundResource(R.drawable.btn_off_gone);
                    f_libx264.setBackgroundResource(R.drawable.btn_off_gone);
                    f_pal_dv.setBackgroundResource(R.drawable.btn_on);

                    f_pal_dvd.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_pal_vcd.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_pal_svcd.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_libx264.setTextColor(getResources().getColor(R.color.app_color_hint));
                    f_pal_dv.setTextColor(getResources().getColor(R.color.app_color));
                } catch (Resources.NotFoundException e) {
                    FirebaseCrash.report(new Exception("27"));
                }

                dvd = 1;
                vcd = 1;
                svcd = 1;
                libx264 = 1;
                dv = 2;

                f_pal.setBackgroundResource(R.drawable.btn_off_gone);
                f_pal.setTextColor(getResources().getColor(R.color.app_color_hint));
                f_ntsc.setBackgroundResource(R.drawable.btn_off_gone);
                f_ntsc.setTextColor(getResources().getColor(R.color.app_color_hint));

                f_pal.setBackgroundResource(R.drawable.btn_off);
                f_pal.setTextColor(getResources().getColor(R.color.app_color));
                f_ntsc.setBackgroundResource(R.drawable.btn_on);
                f_ntsc.setTextColor(getResources().getColor(R.color.app_color));

                outputformat = "dv";

                break;
            case R.id.f_pal_dvd /*2131099664*/:

                if (dvd == 2){
                    try {
                        f_pal_dvd.setBackgroundResource(R.drawable.btn_on);
                        f_pal_vcd.setBackgroundResource(R.drawable.btn_off);
                        f_pal_svcd.setBackgroundResource(R.drawable.btn_off);
                        f_libx264.setBackgroundResource(R.drawable.btn_off);
                        f_pal_dv.setBackgroundResource(R.drawable.btn_off_gone);

                        f_pal_dvd.setTextColor(getResources().getColor(R.color.app_color));
                        f_pal_vcd.setTextColor(getResources().getColor(R.color.app_color));
                        f_pal_svcd.setTextColor(getResources().getColor(R.color.app_color));
                        f_libx264.setTextColor(getResources().getColor(R.color.app_color));
                        f_pal_dv.setTextColor(getResources().getColor(R.color.app_color_hint));

                        f_pal.setBackgroundResource(R.drawable.btn_off);
                        f_pal.setTextColor(getResources().getColor(R.color.app_color));
                        f_ntsc.setBackgroundResource(R.drawable.btn_on);
                        f_ntsc.setTextColor(getResources().getColor(R.color.app_color));
                    } catch (Resources.NotFoundException e) {
                        FirebaseCrash.report(new Exception("28"));
                    }

                    n = 1;
                    pn = 1;

                    form = 2;

                }

                break;
            case R.id.f_pal_vcd /*2131099664*/:

                if (vcd == 2){
                    try {
                        f_pal_dvd.setBackgroundResource(R.drawable.btn_off);
                        f_pal_vcd.setBackgroundResource(R.drawable.btn_on);
                        f_pal_svcd.setBackgroundResource(R.drawable.btn_off);
                        f_libx264.setBackgroundResource(R.drawable.btn_off);
                        f_pal_dv.setBackgroundResource(R.drawable.btn_off_gone);

                        f_pal_dvd.setTextColor(getResources().getColor(R.color.app_color));
                        f_pal_vcd.setTextColor(getResources().getColor(R.color.app_color));
                        f_pal_svcd.setTextColor(getResources().getColor(R.color.app_color));
                        f_libx264.setTextColor(getResources().getColor(R.color.app_color));
                        f_pal_dv.setTextColor(getResources().getColor(R.color.app_color_hint));

                        f_pal.setBackgroundResource(R.drawable.btn_off);
                        f_pal.setTextColor(getResources().getColor(R.color.app_color));
                        f_ntsc.setBackgroundResource(R.drawable.btn_on);
                        f_ntsc.setTextColor(getResources().getColor(R.color.app_color));
                    } catch (Resources.NotFoundException e) {
                        FirebaseCrash.report(new Exception("29"));
                    }

                    n = 1;
                    pn = 2;

                    form = 4;

                }

                break;
            case R.id.f_pal_dv /*2131099664*/:

                if (dv == 2){
                    try {
                        f_pal_dvd.setBackgroundResource(R.drawable.btn_off_gone);
                        f_pal_vcd.setBackgroundResource(R.drawable.btn_off_gone);
                        f_pal_svcd.setBackgroundResource(R.drawable.btn_off_gone);
                        f_libx264.setBackgroundResource(R.drawable.btn_off_gone);
                        f_pal_dv.setBackgroundResource(R.drawable.btn_on);

                        f_pal_dvd.setTextColor(getResources().getColor(R.color.app_color_hint));
                        f_pal_vcd.setTextColor(getResources().getColor(R.color.app_color_hint));
                        f_pal_svcd.setTextColor(getResources().getColor(R.color.app_color_hint));
                        f_libx264.setTextColor(getResources().getColor(R.color.app_color_hint));
                        f_pal_dv.setTextColor(getResources().getColor(R.color.app_color));

                        f_pal.setBackgroundResource(R.drawable.btn_off);
                        f_pal.setTextColor(getResources().getColor(R.color.app_color));
                        f_ntsc.setBackgroundResource(R.drawable.btn_on);
                        f_ntsc.setTextColor(getResources().getColor(R.color.app_color));
                    } catch (Resources.NotFoundException e) {
                        FirebaseCrash.report(new Exception("30"));
                    }
                    n = 1;
                    pn = 3;

                    form = 6;

                }

                break;
            case R.id.f_pal_svcd /*2131099664*/:

                if (svcd == 2){
                    try {
                        f_pal_dvd.setBackgroundResource(R.drawable.btn_off);
                        f_pal_vcd.setBackgroundResource(R.drawable.btn_off);
                        f_pal_svcd.setBackgroundResource(R.drawable.btn_on);
                        f_libx264.setBackgroundResource(R.drawable.btn_off);
                        f_pal_dv.setBackgroundResource(R.drawable.btn_off_gone);

                        f_pal_dvd.setTextColor(getResources().getColor(R.color.app_color));
                        f_pal_vcd.setTextColor(getResources().getColor(R.color.app_color));
                        f_pal_svcd.setTextColor(getResources().getColor(R.color.app_color));
                        f_libx264.setTextColor(getResources().getColor(R.color.app_color));
                        f_pal_dv.setTextColor(getResources().getColor(R.color.app_color_hint));

                        f_pal.setBackgroundResource(R.drawable.btn_off);
                        f_pal.setTextColor(getResources().getColor(R.color.app_color));
                        f_ntsc.setBackgroundResource(R.drawable.btn_on);
                        f_ntsc.setTextColor(getResources().getColor(R.color.app_color));
                    } catch (Resources.NotFoundException e) {
                        FirebaseCrash.report(new Exception("31"));
                    }
                    n = 1;
                    pn = 4;
                    form = 8;

                }

                break;
            case R.id.f_libx264 /*2131099664*/:

                if (libx264 == 2){
                    try {
                        f_pal_dvd.setBackgroundResource(R.drawable.btn_off);
                        f_pal_vcd.setBackgroundResource(R.drawable.btn_off);
                        f_pal_svcd.setBackgroundResource(R.drawable.btn_off);
                        f_libx264.setBackgroundResource(R.drawable.btn_on);
                        f_pal_dv.setBackgroundResource(R.drawable.btn_off_gone);

                        f_pal_dvd.setTextColor(getResources().getColor(R.color.app_color));
                        f_pal_vcd.setTextColor(getResources().getColor(R.color.app_color));
                        f_pal_svcd.setTextColor(getResources().getColor(R.color.app_color));
                        f_libx264.setTextColor(getResources().getColor(R.color.app_color));
                        f_pal_dv.setTextColor(getResources().getColor(R.color.app_color_hint));

                        f_pal.setBackgroundResource(R.drawable.btn_off_gone);
                        f_pal.setTextColor(getResources().getColor(R.color.app_color_hint));
                        f_ntsc.setBackgroundResource(R.drawable.btn_off_gone);
                        f_ntsc.setTextColor(getResources().getColor(R.color.app_color_hint));
                    } catch (Resources.NotFoundException e) {
                        FirebaseCrash.report(new Exception("32"));
                    }
                    n = 0;
                    form = 10;

                }

                break;

            case R.id.f_pal /*2131099664*/:

                if (n == 1){
                    f_pal.setBackgroundResource(R.drawable.btn_on);
                    f_ntsc.setBackgroundResource(R.drawable.btn_off);
                if (pn == 1){
                    form = 1;
                }else if (pn == 2){
                    form = 3;
                }else if (pn == 3){
                    form = 5;
                }else if (pn == 4){
                    form = 7;
                }}
                break;

            case R.id.f_ntsc /*2131099664*/:

                if (n == 1){
                    f_pal.setBackgroundResource(R.drawable.btn_off);
                    f_ntsc.setBackgroundResource(R.drawable.btn_on);
                if (pn == 1){
                    form = 2;
                }else if (pn == 2){
                    form = 4;
                }else if (pn == 3){
                    form = 6;
                }else if (pn == 4){
                    form = 8;
                }}
                break;

        }
    }

    public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
    }

    public void onStartTrackingTouch(SeekBar arg0) {
    }

    public void onStopTrackingTouch(SeekBar seekbar) {
        int progress = seekbar.getProgress();
        this.vvScreen.seekTo(progress);
        try {
            this.tvStartVideo.setText(formatTimeUnit((long) progress));
        } catch (ParseException e) {
            FirebaseCrash.report(new Exception("33"));
        }
    }

    class C03981 implements DialogUtils.DialogBtnClickListener_set {

        C03981() {
        }

        public void onPositiveClick(String s) {
            outputformat = s;
        }
    }

}
