package com.fury.cv.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ParseException;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.fury.cv.R;
import com.fury.cv.model.VideoPlayerState;
import com.fury.cv.util.FileUtils;
import com.fury.cv.util.VideoEngine;
import com.google.firebase.crash.FirebaseCrash;

import java.io.File;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by fury on 5/17/2017.
 */
public class Cut extends Activity implements SeekBar.OnSeekBarChangeListener {


    private static final int MY_NOTIFICATION_ID = 1;
    protected final int LOADING_DIALOG;
    ImageView btnBack;
    ImageView btnSetting;
    Button btnPlay;
    static ProgressDialog dialog;
    int duration;
    Handler handler;
    boolean isPlay,coin_alfa;
    public static Cut act;
    int f77k, start, end,coinint,work;
    boolean ok;
    Typeface face;
    View.OnClickListener onclickbtnPlay;
    protected static String outputformat, outputsize;
    String path;
    Boolean plypush;
    private PowerManager pm;
    RelativeLayout rl_videoplayer;
    SeekBar seekVideo, seek_in, seek_out;
    Runnable seekrunnable;
    TextView trimButton;
    TextView tvEndVideo;
    TextView eroor;
    EditText hEdit, secEdit, minEdit, hEdit2, secEdit2, minEdit2;
    TextView tvStartVideo;
    private VideoPlayerState videoPlayerState;
    private StateObserver videoStateObserver;
    VideoView vvScreen;
    private PowerManager.WakeLock wl;
    String MP3Path;

    boolean play1 = false;
    SharedPreferences one_play_preferences;
    SharedPreferences.Editor one_play_editor;


    ScheduledThreadPoolExecutor mDialogDaemon_time;

    /* renamed from: com.video.compressop.view.Format.1 */
    class C10551 implements Runnable {
        C10551() {
        }

        public void run() {
            if (Cut.this.vvScreen.isPlaying()) {
                try {
                    int curPos = Cut.this.vvScreen.getCurrentPosition();
                    Cut.this.seekVideo.setProgress(curPos);
                    try {
                        Cut.this.tvStartVideo.setText(Cut.formatTimeUnit((long) curPos));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (curPos == Cut.this.duration) {
                        Cut.this.seekVideo.setProgress(0);
                        Cut.this.btnPlay.setBackgroundResource(R.drawable.play);
                        Cut.this.tvStartVideo.setText("00:00");
                        Cut.this.handler.removeCallbacks(Cut.this.seekrunnable);
                        return;
                    }
                    Cut.this.handler.postDelayed(Cut.this.seekrunnable, 500);
                    return;
                } catch (Exception e) {
                    FirebaseCrash.report(new Exception("c10551"));
                }
            }
            Cut.this.seekVideo.setProgress(Cut.this.duration);
            try {
                Cut.this.tvStartVideo.setText(Cut.formatTimeUnit((long) Cut.this.duration));
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
            Cut.this.handler.removeCallbacks(Cut.this.seekrunnable);
        }
    }

    /* renamed from: com.video.compressop.view.Format.2 */
    class C10562 implements View.OnClickListener {
        C10562() {
        }

        public void onClick(View v) {
            if (Cut.this.isPlay) {
                Cut.this.vvScreen.pause();
                Cut.this.handler.removeCallbacks(Cut.this.seekrunnable);
                Cut.this.btnPlay.setBackgroundResource(R.drawable.play);
            } else {
                Cut.this.vvScreen.seekTo(Cut.this.seekVideo.getProgress());
                Cut.this.vvScreen.start();
                Cut.this.handler.postDelayed(Cut.this.seekrunnable, 500);
                Cut.this.btnPlay.setBackgroundResource(R.drawable.pause);
            }
            Cut.this.isPlay = !Cut.this.isPlay;
        }
    }

    /* renamed from: com.video.compressop.view.Format.3 */
    class C10573 implements MediaPlayer.OnErrorListener {
        C10573() {
        }

        public boolean onError(MediaPlayer mp, int what, int extra) {
            Toast.makeText(Cut.this.getApplicationContext(), "Video Player Not Supporting", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    /* renamed from: com.video.compressop.view.Format.4 */
    class C10584 implements MediaPlayer.OnPreparedListener {
        C10584() {
        }

        public void onPrepared(MediaPlayer mp) {
            Cut.this.duration = Cut.this.vvScreen.getDuration();
            Cut.this.seekVideo.setMax(Cut.this.duration);
            Cut.this.seek_in.setMax(Cut.this.duration);
            Cut.this.seek_out.setMax(Cut.this.duration);
            Cut.this.seek_out.setProgress(duration);
            try {
                hEdit2.setText(formatTimeUnit_ho((long) duration));
                minEdit2.setText(formatTimeUnit_min((long) duration));
                secEdit2.setText(formatTimeUnit_sec((long) duration));
            } catch (ParseException e) {
                FirebaseCrash.report(new Exception("1"));
            }
            Cut.this.tvStartVideo.setText("00:00");
            try {
                Cut.this.tvEndVideo.setText(Cut.formatTimeUnit((long) Cut.this.duration));
            } catch (ParseException e) {
                FirebaseCrash.report(new Exception("2"));
            }
        }
    }

    /* renamed from: com.video.compressop.view.Format.5 */
    class C10595 implements MediaPlayer.OnCompletionListener {
        C10595() {
        }

        public void onCompletion(MediaPlayer mp) {
            Cut.this.btnPlay.setBackgroundResource(R.drawable.play);
            Cut.this.vvScreen.seekTo(0);
            Cut.this.seekVideo.setProgress(0);
            Cut.this.tvStartVideo.setText("00:00");
            Cut.this.handler.removeCallbacks(Cut.this.seekrunnable);
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
            Cut.this.onBackPressed();
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

            try {
                stopService(new Intent(Cut.this, VideoEngine.class));
            } catch (Exception e) {
                FirebaseCrash.report(new Exception("3"));
            }

            if (start < end) {

                String hor_in = hEdit.getText().toString();
                String min_in = minEdit.getText().toString();
                String sec_in = secEdit.getText().toString();

                String hor_out = hEdit2.getText().toString();
                String min_out = minEdit2.getText().toString();
                String sec_out = secEdit2.getText().toString();

                int h = formatTimeUnit_ho_back(hor_in);
                int m = formatTimeUnit_min_back(min_in);
                int s = formatTimeUnit_sec_back(sec_in);

                int h2 = formatTimeUnit_ho_back(hor_out);
                int m2 = formatTimeUnit_min_back(min_out);
                int s2 = formatTimeUnit_sec_back(sec_out);

                int all_in = h + m + s;
                int all_out = h2 + m2 + s2;

                if (all_in < all_out) {
                    if (all_in < duration) {
                        if (all_out < duration) {
                            int timeReal = all_out - all_in;
                            String timeStartCut = formatTimeUnit((long) all_in);
                            String timeEndCut = formatTimeUnit((long) timeReal);

                            String inputFileName = videoPlayerState.getFilename();
                            path = FileUtils.getTargetFileNameCut(inputFileName, 1);

                            try {
                                start_activ();

                                if (coin_alfa){
                                    coinint = 100;
                                }
                                if (coinint <= 0){
                                    try {
                                        end_activ();
                                        (new DialogNoTicket(Cut.this)).showDialog();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                } else {
                                    one_play_editor.putInt("code", 6);
                                    one_play_editor.putInt("one", 1);
                                    one_play_editor.putString("inputFileName", inputFileName);
                                    one_play_editor.putString("quality", timeStartCut);
                                    one_play_editor.putString("format_ch", timeEndCut);
                                    one_play_editor.putString("path", path);
                                    one_play_editor.putString("b_S_t", "Cutting video ...");
                                    one_play_editor.putString("b_F_c", "Unfortunately, video cropping failed!");
                                    one_play_editor.putString("b_E_t", "Cut video");
                                    one_play_editor.putString("b_E_c", "Successfully completed");
                                    one_play_editor.commit();

                                    work = one_play_preferences.getInt("work", 0);
                                    if (work == 4){
                                        (new DialogFollow(Cut.this)).showDialog();
                                    }else if (work == 5){
                                        (new DialogStar(Cut.this)).showDialog();
                                    }

                                    startService(new Intent(Cut.this, VideoEngine.class));
                                }
                            } catch (Exception e) {
                                FirebaseCrash.report(new Exception("error work start service"));
                                Toast.makeText(Cut.this, "error 2", Toast.LENGTH_LONG).show();
                                File appVoice = new File(Cut.this.path);
                                if (appVoice.exists()) {
                                    appVoice.delete();
                                    Cut.this.finish();
                                }
                            }

                        } else {
                            Toast.makeText(Cut.this, "The cut-off time is longer than the whole video!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(Cut.this, "The cut off time is greater than the time of the whole video!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Cut.this, "The start and finish time is not correct!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(Cut.this, "The start and finish time is not correct!", Toast.LENGTH_LONG).show();
            }

        }
    }

    public void start_activ() {
        dialog = new ProgressDialog(Cut.this);
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

    public static void end_activ() {
        try {
            dialog.dismiss();
        } catch (Exception e) {
            FirebaseCrash.report(new Exception("4"));
        }
    }

    public static void in_activ() {
        try {
            dialog.dismiss();
        } catch (Exception e) {
            FirebaseCrash.report(new Exception("5"));
        }
    }

    private class StateObserver extends Handler {
        private boolean alreadyStarted;
        private Runnable observerWork;

        /* renamed from: com.video.compressop.view.Format.StateObserver.1 */
        class C10631 implements Runnable {
            C10631() {
            }

            public void run() {
                Cut.StateObserver.this.startVideoProgressObserving();
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

    public Cut() {
        this.LOADING_DIALOG = MY_NOTIFICATION_ID;
        this.ok = false;
        this.f77k = 0;
        this.videoPlayerState = new VideoPlayerState();
        this.path = null;
        this.duration = 0;
        this.start = 0;
        this.end = 0;
        this.isPlay = false;
        this.handler = new Handler();
        this.plypush = Boolean.valueOf(false);
        this.videoStateObserver = new StateObserver();
        this.seekrunnable = new C10551();
        this.onclickbtnPlay = new C10562();
        MP3Path = null;
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(this, SelectVideoActivity.class);
        intent.addFlags(335544320);
        intent.putExtra("activ", 6);
        startActivity(intent);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("VideoView", "In on create");
        act = this;
        setContentView(R.layout.cut_view1);

        try {
            MediaPlayer player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        } catch (Exception e) {
            FirebaseCrash.report(new Exception("6"));
        }

        one_play_preferences = getApplicationContext().getSharedPreferences("PROJECT_NAME", android.content.Context.MODE_PRIVATE);
        one_play_editor = one_play_preferences.edit();
        coin_alfa = one_play_preferences.getBoolean("COIN_Alfa", false);
        coinint = one_play_preferences.getInt("COIN", 0);
        try {
            this.pm = (PowerManager) getSystemService(POWER_SERVICE);
            this.wl = this.pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        } catch (Exception e) {
            FirebaseCrash.report(new Exception("7"));
        }
        Object lastState = getLastNonConfigurationInstance();
        if (lastState != null) {
            this.videoPlayerState = (VideoPlayerState) lastState;
        } else {
            Bundle extras = getIntent().getExtras();
            this.videoPlayerState.setFilename(extras.getString("videofilename"));
            this.path = extras.getString("videofilename");
        }

        findById();
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
        this.hEdit = (EditText) findViewById(R.id.hEdit);
        this.hEdit2 = (EditText) findViewById(R.id.hEdit2);
        this.minEdit = (EditText) findViewById(R.id.minEdit);
        this.minEdit2 = (EditText) findViewById(R.id.minEdit2);
        this.secEdit = (EditText) findViewById(R.id.secEdit);
        this.secEdit2 = (EditText) findViewById(R.id.secEdit2);
        this.tvEndVideo = (TextView) findViewById(R.id.right_pointer);
        this.eroor = (TextView) findViewById(R.id.error);
        this.seekVideo = (SeekBar) findViewById(R.id.sbVideo);
        this.seek_in = (SeekBar) findViewById(R.id.seek_in);
        this.seek_out = (SeekBar) findViewById(R.id.seek_out);
        this.seekVideo.setOnSeekBarChangeListener(this);
        this.seek_in.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int progress = seek_in.getProgress();
                vvScreen.seekTo(progress);
                try {
                    hEdit.setText(formatTimeUnit_ho((long) progress));
                    minEdit.setText(formatTimeUnit_min((long) progress));
                    secEdit.setText(formatTimeUnit_sec((long) progress));
                } catch (ParseException e) {
                    FirebaseCrash.report(new Exception("8"));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seek_in.getProgress();
                vvScreen.seekTo(progress);
                start = progress;
                try {
                    hEdit.setText(formatTimeUnit_ho((long) progress));
                    minEdit.setText(formatTimeUnit_min((long) progress));
                    secEdit.setText(formatTimeUnit_sec((long) progress));
                } catch (ParseException e) {
                    FirebaseCrash.report(new Exception("9"));
                }

                try {
                    tvStartVideo.setText(formatTimeUnit((long) progress));
                } catch (ParseException e) {
                    FirebaseCrash.report(new Exception("10"));
                }

            }
        });

        this.seek_out.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int progress = seek_out.getProgress();
                try {
                    hEdit2.setText(formatTimeUnit_ho((long) progress));
                    minEdit2.setText(formatTimeUnit_min((long) progress));
                    secEdit2.setText(formatTimeUnit_sec((long) progress));
                } catch (ParseException e) {
                    FirebaseCrash.report(new Exception("11"));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seek_out.getProgress();
                end = progress;
                try {
                    hEdit2.setText(formatTimeUnit_ho((long) progress));
                    minEdit2.setText(formatTimeUnit_min((long) progress));
                    secEdit2.setText(formatTimeUnit_sec((long) progress));
                } catch (ParseException e) {
                    FirebaseCrash.report(new Exception("12"));
                }

            }
        });
        this.btnBack = (ImageView) findViewById(R.id.btnBack);
        this.btnSetting = (ImageView) findViewById(R.id.btnSetting);
        this.btnBack.setOnClickListener(new C10617());
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new DialogHelp(Cut.this,"To cut the video you want, please enter the start time of the video first and then specify the time you want to finish and enter the end time field, then enter the desired value and hit the start button and wait for the task to be done \n Note that start time from end time should not be longer \n If you enter the time manually, note that the times are correct \n If you see a problem or error, let us know through the support to be addressed.")).showDialog();
            }
        });

        face = Typeface.createFromAsset(getAssets(), "fa_font_1.ttf");
        trimButton.setTypeface(face);
        TextView tvt = (TextView) findViewById(R.id.tvt);
        TextView txt_in = (TextView) findViewById(R.id.txt_in);
        TextView txt_out = (TextView) findViewById(R.id.txt_out);
        TextView min = (TextView) findViewById(R.id.min);
        TextView min2 = (TextView) findViewById(R.id.min2);
        TextView sec = (TextView) findViewById(R.id.sec);
        TextView sec2 = (TextView) findViewById(R.id.sec2);
        TextView miniSec = (TextView) findViewById(R.id.miniSec);
        TextView miniSec2 = (TextView) findViewById(R.id.miniSec2);
        tvt.setTypeface(face);
        txt_in.setTypeface(face);
        txt_out.setTypeface(face);
        hEdit.setTypeface(face);
        hEdit2.setTypeface(face);
        minEdit.setTypeface(face);
        minEdit2.setTypeface(face);
        secEdit.setTypeface(face);
        secEdit2.setTypeface(face);
        min.setTypeface(face);
        min2.setTypeface(face);
        sec.setTypeface(face);
        sec2.setTypeface(face);
        miniSec.setTypeface(face);
        miniSec2.setTypeface(face);

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
            FirebaseCrash.report(new Exception("14"));
            return null;
        }
    }

    public static String formatTimeUnit_ho(long millis) throws ParseException {
        try {
            String out;
            long hours = millis / 3600000;
            if (hours > 0) {
                out = new StringBuilder(String.valueOf(hours)).toString();
            } else {
                out = "00";
            }
            return out;
        } catch (Exception e) {
            FirebaseCrash.report(new Exception("15"));
            return null;
        }
    }

    public static String formatTimeUnit_min(long millis) throws ParseException {
        try {
            long hours = millis / 3600000;
            long remaining_minutes = (millis - (3600000 * hours)) / 60000;
            String minutes = String.valueOf(remaining_minutes);
            if (minutes.equals(0)) {
                minutes = "00";
            }
            return minutes;
        } catch (Exception e) {
            FirebaseCrash.report(new Exception("16"));
            return null;
        }
    }

    public static String formatTimeUnit_sec(long millis) throws ParseException {
        try {
            long hours = millis / 3600000;
            long remaining_minutes = (millis - (3600000 * hours)) / 60000;
            long seconds = (millis - (3600000 * hours)) - (60000 * remaining_minutes);
            seconds = (seconds / 1000) % 60 ;
            String seconds2 = String.valueOf(seconds);
            if (seconds2.length() < 2) {
                seconds2 = seconds2.substring(0, 1);
            } else {
                seconds2 = seconds2.substring(0, 2);
            }
            return seconds2;
        } catch (Exception e) {
            FirebaseCrash.report(new Exception("17"));
            return null;
        }
    }


    public static int formatTimeUnit_ho_back(String millis) throws ParseException {
        try {
            long s = Integer.parseInt(millis);
            long hours = s * 3600000;
            return (int) hours;
        } catch (Exception e) {
            FirebaseCrash.report(new Exception("18"));
            return 0;
        }
    }

    public static int formatTimeUnit_min_back(String millis) throws ParseException {
        try {
            long s = Integer.parseInt(millis);
            long remaining_minutes = s * 60000;
            return (int) remaining_minutes;
        } catch (Exception e) {
            FirebaseCrash.report(new Exception("19"));
            return 0;
        }
    }

    public static int formatTimeUnit_sec_back(String millis) throws ParseException {
        try {
            long s = Integer.parseInt(millis);
            return (int) s;
        } catch (Exception e) {
            FirebaseCrash.report(new Exception("20"));
            return 0;
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
            FirebaseCrash.report(new Exception("21"));
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
