package cn.keiss.autowallpaper.service;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.Button;

import cn.keiss.autowallpaper.R;

/**
 * Created by hekai on 2017/10/10.
 */

public class SwitchWallpaperService extends WallpaperService{
    @Override
    public Engine onCreateEngine() {
        Log.e("33","ee");
        return new DrawEngine();
    }

    private class DrawEngine extends Engine{
        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);



            Canvas canvas =  surfaceHolder.lockCanvas();
            Bitmap mBitmap = ((BitmapDrawable) getDrawable(R.drawable.ic_android_black_24dp)).getBitmap();
            Paint paint =  new Paint(Paint.ANTI_ALIAS_FLAG);
            canvas.drawBitmap(mBitmap,0,0,paint);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
}
