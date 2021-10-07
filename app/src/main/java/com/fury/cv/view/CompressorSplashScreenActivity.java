package com.fury.cv.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.Toast;

import com.fury.cv.PageStart.PageStart;
import com.fury.cv.R;
import com.fury.cv.util.PermissionHandler;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.google.firebase.crash.FirebaseCrash;

public class CompressorSplashScreenActivity extends Activity {
    private static int SPLASH_TIME_OUT;

    SharedPreferences one_play_preferences;
    SharedPreferences.Editor one_play_editor;
    int one;
    /* renamed from: com.video.compressop.view.CompressorSplashScreenActivity.1 */
    class C10431 implements Runnable {
        C10431() {
        }

        public void run() {
            startActivity(new Intent(CompressorSplashScreenActivity.this, Page_org.class));
            finish();
        }
    }

    static {
        SPLASH_TIME_OUT = 3200;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lay_mainsplash_screen);

        ImageView gif = (ImageView)findViewById(R.id.gif);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(gif);
        Glide.with(this).load(R.raw.cv_start_3).into(imageViewTarget);

        one_play_preferences = getApplicationContext().getSharedPreferences("PROJECT_NAME", Context.MODE_PRIVATE);
        one_play_editor = one_play_preferences.edit();
        one = one_play_preferences.getInt("onebar", 0);
        if (one == 0){
            one_play_editor.putInt("onebar", 1);
            one_play_editor.putInt("COIN", 3);
            one_play_editor.putString("S64", "0100110101001001010010010100001001001001011010100100000101001110010000100110011101101011011100010110100001101011011010010100011100111001011101110011000001000010010000010101000101000101010001100100000101000001010011110100001101000001010100010011100001000001010011010100100101001001010000100100001101100111010010110100001101000001010100010100010101000001011001110110011001100111010101010111001100101111011000010101100101110111010011100011100101000001010010110101101000101011010100110100101001100010010011000100101001010110011101000110010101001100010000110111000001011000010010100111100001010100011100000110111000110110001100010101011001101011010100100101100001111001011110010111010101001111010000100110011101001000001110010100001101010001010010000100110100110110010100000011100001110110010100110110101001100100011100100100101000110010010001100111101001110010010011100011011100110111010011000101010000101011001101100101011001110111011011110100110101010011011100010011010000101011011001010110010101001001001100000101000001101000010000010111001101001110010010100110101101010000011010010101001101101100011110000101010001110101011101000011000001001101011110000011011101110110010001100111001001111010011101000110100101100101011011100101010101110100001100110100010001001111001101110110111001010111001101100111100000111000011011010011011001111001010001010010111101101000011110100111010001000110010101000100100101110110001101010011001001001110011001100110011100110010011001100101100001000111011101010111101000111000010001100100111001111010011110100110000101011000011011000100101001101011011011110011010101110111011101110110000100110101010100110111001001011001011101100100100000101111010010110011000001010111011001010010111101110101011110000100010101001110011001100111001101000111010011110011100001111000011000100110100101101011011010100011100001101110011001010110101101010110010110100110100001000001001100100111100100110001011010100100001001001001011001100110001001001111011010110101001001001000011011000110010001111010011101100110011101101000001110010110101101100001010000110100011101111001010010000100100001101000010011010100101101010000001101100100110001010000011101000110101001101111010010000110000101100101010010000011011101101001001100110010101100110101010010110111000001000101010010010111010000110101010011100101100000111000011110000111100101001010011011000111001001010100010000110011010001110000010010010110110001100100010101010011001100110110010011000101010101110001011001100110001101000001011011010110000101100111010011100111010100110111011100100110110001010000011101110100101001100010011110000110101000110011010011010011010001001100011001110110110100110100011011110111001001011010010101000110011001001011011010010110001001101100011100100111000100111001010100110011100101010111010100000100110001100011001100110011011101010101011000110101000001101101001010110110111101010001011101000011000100111001001101010111100001010100010101100111100001000010011100110011100101011010011000110110100101110010011101100110011001110111011000010111001001000011010100000111001001100111011101000110101001110111010010010100010001000001010100010100000101000010");
            one_play_editor.apply();

            startActivity(new Intent(CompressorSplashScreenActivity.this, PageStart.class));
            finish();

        }else {

            try {
                new Handler().postDelayed(new C10431(), (long) SPLASH_TIME_OUT);
            } catch (Exception e) {
                FirebaseCrash.report(new Exception("Handler"));
            }
        }
        FirebaseCrash.log("log 1");
    }

    protected void onStop() {
        super.onStop();
    }

    public void onBackPressed() {
        finish();
        System.exit(0);
    }

    protected void onDestroy() {
        System.gc();
        super.onDestroy();
    }

    private void checkPermissions(){

        String[] per = {android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS};

        new PermissionHandler().checkPermission(this, per, new PermissionHandler.OnPermissionResponse() {
            @Override
            public void onPermissionGranted() {
                // permission granted
                // your code
                new Handler().postDelayed(new C10431(), (long) 1000);
            }

            @Override
            public void onPermissionDenied() {
                // User canceled permission
                Toast.makeText(CompressorSplashScreenActivity.this,"If the application is rejected, the program will be in error!", Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Intent intent = new Intent("PERMISSION_RECEIVER");
        intent.putExtra("requestCode",requestCode);
        intent.putExtra("permissions",permissions);
        intent.putExtra("grantResults",grantResults);
        sendBroadcast(intent);
    }

}
