package com.fury.cv.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap.Config;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fury.cv.R;
import com.fury.cv.adapter.VideoCursorAdapter;
import com.fury.cv.model.VideoData;
import com.fury.cv.util.ContentUtill;
import com.google.firebase.crash.FirebaseCrash;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.io.File;
import java.util.ArrayList;

@TargetApi(5)
public class VideoListActivity extends Activity {
    private static final int BACK_FROM_VIDEOSHARE = 99;
    GridView VideogridView;
    VideoCursorAdapter adapter;
    ImageView btnBack,btn_plus;
    ImageLoader imgLoader;
    TextView textnot;
    private PowerManager pm;
    ArrayList<VideoData> videoList;
    public static VideoListActivity act;
    private WakeLock wl;

    /* renamed from: com.video.compressop.view.VideoListActivity.1 */
    class C10541 implements OnClickListener {
        C10541() {
        }

        public void onClick(View arg0) {
            VideoListActivity.this.onBackPressed();
        }
    }

    @SuppressLint({"NewApi"})
    private class loadVideo extends AsyncTask<Void, Void, Boolean> {
        ProgressDialog pd;

        private loadVideo() {
            this.pd = null;
        }

        protected void onPreExecute() {
            this.pd = new ProgressDialog(VideoListActivity.this);
            this.pd.setMessage("please wait...");
            this.pd.setCancelable(false);
            this.pd.show();
        }

        protected Boolean doInBackground(Void... params) {
            return Boolean.valueOf(VideoListActivity.this.getVideoList());
        }

        protected void onPostExecute(Boolean result) {
            this.pd.dismiss();
            if (result.booleanValue()) {
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                int f100w = dm.widthPixels;
                int f99h = dm.heightPixels;
                VideoListActivity.this.adapter = new VideoCursorAdapter(f100w,f99h,VideoListActivity.this, VideoListActivity.this.videoList, VideoListActivity.this.imgLoader,1);
                VideoListActivity.this.VideogridView.setAdapter(VideoListActivity.this.adapter);
            }
        }
    }

    public VideoListActivity() {
        this.videoList = new ArrayList();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_video_list);
        try {
            this.pm = (PowerManager) getSystemService(POWER_SERVICE);
            this.wl = this.pm.newWakeLock(6, "My Tag");
        } catch (Exception e) {
            FirebaseCrash.report(new Exception("1"));
        }
        FindbyId();
        this.videoList.clear();
        act = this;
        Typeface face = Typeface.createFromAsset(getAssets(), "fa_font_1.ttf");
        TextView tvt = (TextView) findViewById(R.id.tvt2);
        textnot.setTypeface(face);
        tvt.setTypeface(face);

        initImageLoader();
        new loadVideo().execute();

        FirebaseCrash.log("log 1");
    }

    private void FindbyId() {
        VideogridView = (GridView) findViewById(R.id.VideogridView);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btn_plus = (ImageView) findViewById(R.id.btn_plus);
        textnot = (TextView) findViewById(R.id.textnot);
        btnBack.setOnClickListener(new C10541());
        btn_plus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent(VideoListActivity.this,SelectVideoActivity.class);
                n.putExtra("activ",1);
                startActivity(n);
            }
        });
    }

    private boolean getVideoList() {
        Uri MEDIA_EXTERNAL_CONTENT_URI = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String _ID = "_id";
        String MEDIA_DATA = "_data";
        String _NAME = "_display_name";
        String _DURATION = "duration";
        String _DATEVIDEO = "date_added";
        String[] proj = new String[]{"_id", "_data", "_display_name", "duration", "date_added"};
        String[] strArr = new String[1];
        String str = "%";
        strArr[0] = "%" + getResources().getString(R.string.folder_name) +"/" + "Compressed" + str;
        Cursor cursor = managedQuery(MEDIA_EXTERNAL_CONTENT_URI, proj, "_data like ? ", strArr, "datetaken DESC");
        int count = cursor.getCount();
        if (count <= 0) {
            textnot.setVisibility(View.VISIBLE);
            return false;
        }
        cursor.moveToFirst();
        for (int i = 0; i < count; i++) {
            Uri uri = Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, ContentUtill.getLong(cursor));
            String videoName = cursor.getString(cursor.getColumnIndexOrThrow("_display_name"));
            String str2 = videoName;
            Uri uri2 = uri;
            String string = cursor.getString(cursor.getColumnIndex("_data"));
            this.videoList.add(new VideoData(str2, uri2, string, ContentUtill.getTime(cursor, "duration")));
            cursor.moveToNext();
        }
        return true;
    }

    public int dpToPx(int dp) {
        return Math.round(((float) dp) * getApplicationContext().getResources().getDisplayMetrics().density);
    }

    public void deleteTmpFile(int videopath) {
        String s = videoList.get(videopath).videoPath;
        File file = new File(s);
        if (file.exists()) {
            try {
                file.delete();
                getContentResolver().delete(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, "_data =?", new String[]{s});
                Toast.makeText(getApplicationContext(), "Movie deletion was successful.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                FirebaseCrash.report(new Exception("2"));
            }
        }
    }
    public void shareTmpFile(int videopath) {
        try {
            String s = videoList.get(videopath).videoPath;
            Intent share = new Intent("android.intent.action.SEND");
            share.setType("video/*");
            share.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(s)));
            startActivity(Intent.createChooser(share, "What app"));
        } catch (Exception e) {
            FirebaseCrash.report(new Exception("3"));
        }
    }
    public void callVideo(int position) {
        try {
            Intent intent = new Intent(this, ShareVideoActivity.class);
            intent.putExtra("videofilename", ((VideoData) this.videoList.get(position)).videoPath);
            intent.putExtra("position", position);
            intent.putExtra("isfrommain", false);
            startActivityForResult(intent, BACK_FROM_VIDEOSHARE);
        } catch (Exception e) {
            FirebaseCrash.report(new Exception("4"));
        }
    }

    public void detailFile(int position){
        try {
            String name = videoList.get(position).videoName;
            String uri =  videoList.get(position).videoPath;
            File file = new File(uri);
            String Duration = videoList.get(position).Duration;
            String size = null;
            long Filesize = getFolderSize(file)/1024;
            if (Filesize >= 1024){
                size = Filesize/1024 + "MB";
            }else {
                size = Filesize + "KB";
            }
            DialogUtils.showDetailDialog(VideoListActivity.act,name,size,uri,Duration);
        } catch (Exception e) {
            FirebaseCrash.report(new Exception("5"));
        }
    }

    public static long getFolderSize(File f){
        long size = 0;
        if (f.isDirectory()){
            for (File file : f.listFiles()){
                size += getFolderSize(file);
            }
        }else {
            size = f.length();
        }
        return size;
    }

    protected void onPause() {
        this.wl.release();
        super.onPause();
    }

    protected void onResume() {
        this.wl.acquire();
        super.onResume();
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.imgLoader != null) {
            this.imgLoader.clearDiscCache();
            this.imgLoader.clearMemoryCache();
        }
    }

    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).memoryCache(new WeakMemoryCache()).defaultDisplayImageOptions(new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc().bitmapConfig(Config.RGB_565).displayer(new FadeInBitmapDisplayer(50)).build()).build();
        this.imgLoader = ImageLoader.getInstance();
        this.imgLoader.init(config);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            switch (requestCode) {
                case BACK_FROM_VIDEOSHARE /*99*/:
                    int pos = data.getIntExtra("position", 0);
                    this.videoList.remove(pos);
                    this.adapter.removeItem(pos);
                    break;
            }
        }
        this.adapter.notifyDataSetChanged();
    }

    public void onBackPressed() {
        finish();
    }
}
