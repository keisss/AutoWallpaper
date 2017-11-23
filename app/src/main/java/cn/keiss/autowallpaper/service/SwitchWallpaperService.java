package cn.keiss.autowallpaper.service;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Build;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import cn.keiss.autowallpaper.data.Fields;
import cn.keiss.autowallpaper.data.SharePreferenceControl;
import cn.keiss.autowallpaper.database.FleshPicManager;
import cn.keiss.autowallpaper.database.PicFileBean;
import static cn.keiss.autowallpaper.data.Fields.*;

/**
 *
 * @author hekai
 * @date 2017/10/10
 * 运行在后台的壁纸更新服务
 */
public class SwitchWallpaperService extends WallpaperService {

    @Override
    public Engine onCreateEngine() {
        return new DrawEngine();

    }


    private class DrawEngine extends Engine{

        private @SWITCH_ORDER int switchOrder;
        private @SWITCH_EFFECT int switchEffect;
        private @DISPLAY_EFFECT int displayEffect;
        private @DISPLAY_TYPE int displayType;
        private long switchTime;
        private SharePreferenceControl control;


        //是否可以进行第一次加载
        private boolean firstSwitchFlag = false;
        //每次变化之间的间隔
        private long delayTime = 10000L /2;
        //顺序切换时此时显示的图片位置
        private long nowPicId = -1;



        private ValueAnimator valueAnimator;
        private FleshPicManager manager;
        //正在显示的（第一期加载显示的）
        private Bitmap bitmapOut;
        //变换后显示的
        private Bitmap bitmapIn;
        private Rect region;
        private Paint paintIn;
        private Paint paintOut;



        DrawEngine() {
            Log.e("new","Engine");

            control = SharePreferenceControl.getInstance();
            manager = new FleshPicManager();
            region = new Rect();
            paintIn = new Paint(Paint.ANTI_ALIAS_FLAG);
            paintOut = new Paint(Paint.ANTI_ALIAS_FLAG);
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            //此时surface还没有准备好
            Log.e("create","engine");


            freshPresence();
            SharePreferenceControl.getInstance().setListener((sharedPreferences, key) -> freshPresence());

            paintIn.setFilterBitmap(true);
            paintIn.setDither(true);

            paintOut.setFilterBitmap(false);
            paintOut.setDither(true);
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            Log.e("surfaceCreate","engine");
            //surface准备好

            //初始化第一二张图片
            refreshPics();

            valueAnimator.addUpdateListener(animator -> {

                switch (switchEffect) {
                    case SWITCH_EFFECT_FADE_OVER:
                        drawAlpha(animator);
                        break;
                    case SWITCH_EFFECT_PAGE:
                        drawMove(animator);
                        break;
                    case SWITCH_EFFECT_CUBE:
                        drawCube(animator);

                    case Fields.SWITCH_EFFECT_DIAL:
                        break;
                    case Fields.SWITCH_EFFECT_JALOUSIE:
                        break;
                    case Fields.SWITCH_EFFECT_ROTATE:
                        break;
                    case Fields.SWITCH_EFFECT_STACK:
                        break;
                    default:
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.e("end","end");
                    super.onAnimationEnd(animation);
                    refreshPics();
                    if (isVisible()){
                        valueAnimator.start();
                    }
                }
            });
        }


        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            Log.e("sufacechange","engmine");

        }


        @Override
        public void onVisibilityChanged(boolean visible) {
            Log.e("surfaceVisiblyChange",String.valueOf(visible));
            //频繁被调用，初始化时会 调用一次Visible，调用一次Unvisible,再调用一次Visible
            if (visible){
                // 初始化
                if (firstSwitchFlag){
                    firstSwitchFlag = false;
                    Log.e("change","hiddenWhenShow");
                    drawStaticPic();
                    valueAnimator.start();
                }
                if (valueAnimator.isStarted() && valueAnimator.isPaused()){
                    valueAnimator.resume();
                }
            }else {
                if (valueAnimator.isStarted()){
                    valueAnimator.pause();
                }else {
                    firstSwitchFlag = true;
                }
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.e("sufacedestriye","engmine");
            valueAnimator.cancel();
            bitmapOut.recycle();
            bitmapIn.recycle();
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            Log.e("destroy","engine");
        }




        //进行渐变绘制
        private void drawAlpha(ValueAnimator animator){
            Canvas canvas = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                canvas = getSurfaceHolder().lockHardwareCanvas();
            }else {
                canvas = getSurfaceHolder().lockCanvas();
            }
            int alphaA = (int)animator.getAnimatedValue();
            paintIn.setAlpha(alphaA);
            paintOut.setAlpha(255 - alphaA);
           canvas.drawBitmap(bitmapIn, null, region, paintIn);
            canvas.drawBitmap(bitmapOut,null,region, paintOut);
            //Log.e("alphaaaa",String.valueOf(alphaA));
            getSurfaceHolder().unlockCanvasAndPost(canvas);

        }

        //进行移动绘制
        private void drawMove(ValueAnimator animator){
            float changeTag = (Float) animator.getAnimatedValue();


            Canvas canvas = getSurfaceHolder().lockCanvas();
            int changeLength = (int)(changeTag *canvas.getWidth());
            Rect rect1 = new Rect(0 - changeLength,0,canvas.getWidth()-changeLength,canvas.getHeight());
            Rect rect2 = new Rect(canvas.getWidth() - changeLength,0,canvas.getWidth()*2 - changeLength,canvas.getHeight());

            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            canvas.drawBitmap(bitmapOut, null, rect1, paintIn);
            canvas.drawBitmap(bitmapIn,null,rect2, paintOut);
            Log.e("alphaaaa",String.valueOf(changeLength));
            getSurfaceHolder().unlockCanvasAndPost(canvas);
        }

        /**
         * 进行cube绘制
         */
        private void drawCube(ValueAnimator animator){
            float changeTag = (Float) animator.getAnimatedValue();

            Camera camera = new Camera();
            Matrix matrix = new Matrix();


            Canvas canvas = getSurfaceHolder().lockCanvas();
            int changeLength = (int)(changeTag *canvas.getWidth());
            int angle = (int) (changeTag*90);


            camera.save();
            camera.translate(-changeLength,0,0);
            camera.rotateY(90- angle);


            camera.getMatrix(matrix);
            camera.restore();


            matrix.preTranslate(0, -canvas.getHeight()/2);
            matrix.postTranslate(canvas.getWidth(), canvas.getHeight()/2);

            canvas.save();
            canvas.concat(matrix);
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            canvas.drawBitmap(bitmapOut,null,region, paintIn);
            canvas.restore();


            matrix.reset();

            camera.save();
            camera.translate(canvas.getWidth()-changeLength ,0,0);
            camera.rotateY(-angle);
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-canvas.getWidth(), -canvas.getHeight()/2);
            matrix.postTranslate(0, canvas.getHeight()/2);


            canvas.save();
            canvas.concat(matrix);
            canvas.drawBitmap(bitmapIn,null,region, paintIn);
            canvas.restore();

            getSurfaceHolder().unlockCanvasAndPost(canvas);




        }


        private void drawStack(ValueAnimator valueAnimator){
            float changeTag = (Float) valueAnimator.getAnimatedValue();


            Camera camera = new Camera();
            Matrix matrix = new Matrix();


            Canvas canvas = getSurfaceHolder().lockCanvas();
            int changeLength = (int)(changeTag *canvas.getWidth());
            int angle = (int) (changeTag*90);


            camera.save();
            camera.translate(-changeLength,0,0);
            camera.rotateY(90- angle);


            camera.getMatrix(matrix);
            camera.restore();


            matrix.preTranslate(0, -canvas.getHeight()/2);
            matrix.postTranslate(canvas.getWidth(), canvas.getHeight()/2);

            canvas.save();
            canvas.concat(matrix);
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            canvas.drawBitmap(bitmapOut,null,region, paintIn);
            canvas.restore();


            matrix.reset();

            camera.save();
            camera.translate(canvas.getWidth()-changeLength ,0,0);
            camera.rotateY(-angle);
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-canvas.getWidth(), -canvas.getHeight()/2);
            matrix.postTranslate(0, canvas.getHeight()/2);


            canvas.save();
            canvas.concat(matrix);
            canvas.drawBitmap(bitmapIn,null,region, paintIn);
            canvas.restore();

            getSurfaceHolder().unlockCanvasAndPost(canvas);

        }


        //绘制静止的bitmap
        private void drawStaticPic(){
            Canvas canvas;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                canvas = getSurfaceHolder().lockHardwareCanvas();
            } else {
                canvas = getSurfaceHolder().lockCanvas();
            }
            if (canvas != null) {
                try {
                    region.set(0, 0, canvas.getWidth(), canvas.getHeight());
                    canvas.drawBitmap(bitmapOut, null, region, paintOut);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        getSurfaceHolder().unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        private void setRect(){

        }


        private Bitmap getInTurnNextPic(){
           PicFileBean bean = manager.getInTurnNextPic(nowPicId);
           if (bean == null){
               //数据库中没有图片
               return null;
           }else {
               nowPicId = bean.getId();
               String path = bean.getPicFilePath();
               FileInputStream fis = null;
               try {
                   fis = new FileInputStream(path);
               } catch (FileNotFoundException e) {
                   getInTurnNextPic();
               }
               return BitmapFactory.decodeStream(fis);
           }
        }

        private void refreshPics(){
            if (bitmapIn != null){
                bitmapOut = bitmapIn;
                bitmapIn = getInTurnNextPic();
            }else {
                bitmapOut = getInTurnNextPic();
                bitmapIn = getInTurnNextPic();
            }

        }


        /**
         * 更新设置并重建动画
         */
        private void freshPresence(){
            switchEffect = control.getSwitchEffect();
            switchOrder =  control.getSwitchOrder();
            displayEffect = control.getDisplayEffect();
            displayType = control.getDisplayType();
            switchTime = control.getSwitchTime();

            if (valueAnimator != null){
                valueAnimator.cancel();
                valueAnimator = null;
            }
            switch (switchEffect) {
                case SWITCH_EFFECT_FADE_OVER:
                    // 使用ValueAnimator创建一个过程
                    valueAnimator = ValueAnimator.ofInt(0, 255);
                    valueAnimator.setInterpolator(new AccelerateInterpolator());
                    break;
                case SWITCH_EFFECT_PAGE:
                    // 使用ValueAnimator创建一个过程
                    valueAnimator = ValueAnimator.ofFloat(0, 1);
                    valueAnimator.setInterpolator(new AccelerateInterpolator());
                    break;
                case SWITCH_EFFECT_CUBE:
                    //正方体变换
                    valueAnimator = ValueAnimator.ofFloat(0, 1);
                    valueAnimator.setInterpolator(new LinearInterpolator());
                    break;
                case Fields.SWITCH_EFFECT_DIAL:
                    break;
                case Fields.SWITCH_EFFECT_JALOUSIE:
                    break;
                case Fields.SWITCH_EFFECT_ROTATE:
                    break;
                case Fields.SWITCH_EFFECT_STACK:
                    break;
                default: break;
            }

            if (valueAnimator != null) {
                valueAnimator.setDuration(switchTime);
                valueAnimator.setStartDelay(delayTime);
                onVisibilityChanged(false);
            }
        }
    }



}
