package cn.keiss.autowallpaper.service;

import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

/**
 * Created by hekai on 2017/10/20.
 */

public class TestService extends GLWallpaperService {
    public TestService() {
        super();
    }

    public WallpaperService.Engine onCreateEngine() {
        MyEngine engine = new MyEngine();
        return engine;
    }

    class MyEngine extends GLWallpaperService.GLEngine {
        TestRenderer renderer;
        public MyEngine() {
            super();
            // handle prefs, other initialization
            renderer = new TestRenderer();
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

        public void onDestroy() {
            super.onDestroy();
            renderer = null;
        }
    }
}
