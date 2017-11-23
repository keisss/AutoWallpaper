package cn.keiss.autowallpaper.service;

import android.graphics.BitmapFactory;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

import cn.keiss.autowallpaper.R;

/**
 * @author hekai
 * @date 2017/10/20
 */

public class TestService extends GLWallpaperService {
    public TestService() {
        super();
    }

    @Override
    public WallpaperService.Engine onCreateEngine() {
        MyEngine engine = new MyEngine();
        return engine;
    }

    class MyEngine extends GLWallpaperService.GLEngine {
        TestRenderer renderer ;


        public MyEngine() {
            super();
            renderer = new TestRenderer();
            renderer.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.hezhipin610039));
            // handle prefs, other initialization
            setEGLContextClientVersion(2);
            setEGLConfigChooser(8,8,8,0,0,0);
            setRenderer(renderer);
            setRenderMode(RENDERMODE_WHEN_DIRTY);
            requestRender();
        }


        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);

        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            renderer = null;
        }
    }
}
