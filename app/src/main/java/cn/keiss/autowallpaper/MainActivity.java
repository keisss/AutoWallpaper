package cn.keiss.autowallpaper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.keiss.autowallpaper.service.SwitchWallpaperService;

public class MainActivity extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        button = (Button) findViewById(R.id.btn);
        Intent intent = new Intent(this,SwitchWallpaperService.class);
        startService(intent);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLiveWallpaperPrevivew(MainActivity.this,MainActivity.this);
            }
        });

    }
    /**
     * 去往某个动态壁纸的预览页面,那里可以设置壁纸
     *
     *

     *            动态壁纸service类的类全名
     */
    public static void startLiveWallpaperPrevivew(Activity activity,Context context) {
        ComponentName componentName = new ComponentName(context, SwitchWallpaperService.class);
        Intent intent;
        if (android.os.Build.VERSION.SDK_INT < 16) {
            intent = new Intent(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
        } else {
            intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
            intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, componentName);
            //intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        }
        activity.startActivity(intent);
    }
}
