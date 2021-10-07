package com.fury.cv.util;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.fury.cv.view.Create;
import com.fury.cv.view.Cut;
import com.fury.cv.view.Format;
import com.fury.cv.view.Gif;
import com.fury.cv.view.Join;
import com.fury.cv.view.Logo;
import com.fury.cv.view.Music;
import com.fury.cv.view.ViewVideo;
import com.fury.cv.view.Voice;

/**
 * Created by fury on 5/8/2017.
 */
public class serend extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Intent stop = new Intent(getBaseContext(),VideoEngine.class);
            stopService(stop);
                try {
                    ViewVideo.in_activ();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Format.in_activ();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Gif.in_activ();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Music.in_activ();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Voice.in_activ();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Cut.in_activ();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Join.in_activ();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Logo.in_activ();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Create.in_activ();
                } catch (Exception e) {
                    e.printStackTrace();
                }

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "StopService", Toast.LENGTH_SHORT).show();
        }
    }
}
