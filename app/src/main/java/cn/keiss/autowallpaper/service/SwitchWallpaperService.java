package cn.keiss.autowallpaper.service;

import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.Button;

import cn.keiss.autowallpaper.R;

import static android.content.ContentValues.TAG;

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
        private final ContentObserver observer = new ContentObserver(null) {
            @Override
            public void onChange(boolean selfChange) {
                Log.d(TAG, "new earth ready, drawing...");
                draw();
            }
        };
        private Rect region;

        public DrawEngine() {

        }

        private void draw(){



            Canvas canvas =  getSurfaceHolder().lockCanvas();

            region = new Rect();
            region.set(0, 0, canvas.getWidth(), canvas.getHeight());
            if (region.width() > region.height()) {
                region.inset((region.width() - region.height()) / 2, 0);
            } else {
                region.inset(0, (region.height() - region.width()) / 2);
            }

            region.inset(0, 0);
            Paint paint ;
            paint = new Paint();
            paint.setFilterBitmap(true);

            Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hezhipin610039);
            canvas.drawBitmap(mBitmap,null,region,paint);
            getSurfaceHolder().unlockCanvasAndPost(canvas);
            mBitmap.recycle();
        }


        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
           draw();

        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            draw();
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            draw();
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
        }
    }


}
