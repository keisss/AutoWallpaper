package cn.keiss.autowallpaper.service;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import cn.keiss.autowallpaper.R;
import cn.keiss.autowallpaper.database.FleshPicManager;
import cn.keiss.autowallpaper.database.PicFileBean;

/**
 * Created by hekai on 2017/10/10.
 * 运行在后台的壁纸更新服务
 */

public class SwitchWallpaperService extends WallpaperService {


    private static final String DISPLAY_EFFECT = "display_effect";
    private final String SWITCH_TIME  = "switch_time" ;
    //淡出淡入
    private final int SWITCH_EFFECT_FADE_OVER = 1;
    //转盘
    private final int SWITCH_EFFECT_DIAL = 2;
    private final int SWITCH_EFFECT_PAGE = 3;
    private final int SWITCH_EFFECT_CUBE = 6;
    //层叠
    private final int SWITCH_EFFECT_STACK = 4;

    private final int SWITCH_ORDER_RANDOM = 1;
    private final int SWITCH_ORDER_IN_TURN = 2;

    private SharedPreferences.OnSharedPreferenceChangeListener listener ;


    @Override
    public Engine onCreateEngine() {
        return new DrawEngine();

    }


    private class DrawEngine extends Engine{

        //是否可以进行第一次加载
        private boolean firstChangeableFlag = false;
        //每次变化之间的间隔
        private long delayTime = 10000L /2;
        //切换的顺序
        private int switchOrder = SWITCH_ORDER_IN_TURN;
        private long changeTime = 4*1000;
        private int changeType = SWITCH_EFFECT_FADE_OVER;
        //顺序切换时此时显示的图片位置
        private long nowPicId = 0;


        private Rect region;
        private Paint paintA;
        private Paint paintB;
        private ValueAnimator valueAnimator;
        private FleshPicManager manager;
        //正在显示的（第一期加载显示的）
        private Bitmap bitmap1;
        //变换后显示的
        private Bitmap bitmap2;



        DrawEngine() {
            Log.e("new","Engine");
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            //此时surface还没有准备好
            Log.e("create","engine");

            listener  = (sharedPreferences, key) -> {
                changeType = sharedPreferences.getInt(DISPLAY_EFFECT,0);
                changeTime = sharedPreferences.getInt(SWITCH_TIME,0);
            };

            manager = new FleshPicManager();


            paintA = new Paint(Paint.ANTI_ALIAS_FLAG);
            paintA.setFilterBitmap(true);
            paintA.setDither(true);

            paintB = new Paint();
            paintB.setFilterBitmap(false);


            region = new Rect();





            switch (changeType){
                case SWITCH_EFFECT_FADE_OVER:
                    // 使用ValueAnimator创建一个过程
                    valueAnimator = ValueAnimator.ofInt(0, 255);
                    valueAnimator.setDuration(changeTime);
                    valueAnimator.setInterpolator(new AccelerateInterpolator());
                    valueAnimator.setStartDelay(delayTime);
                    break;
                case SWITCH_EFFECT_PAGE:
                    // 使用ValueAnimator创建一个过程
                    valueAnimator = ValueAnimator.ofFloat(0,1);
                    valueAnimator.setDuration(changeTime/2);
                    valueAnimator.setInterpolator(new AccelerateInterpolator());
                    valueAnimator.setStartDelay(delayTime);
                    break;
                case SWITCH_EFFECT_CUBE:
                    //正方体变换
                    valueAnimator = ValueAnimator.ofFloat(0,1);
                    valueAnimator.setDuration(changeTime);
                    valueAnimator.setInterpolator(new LinearInterpolator());
                    valueAnimator.setStartDelay(delayTime);
                    default:
            }



        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            Log.e("surfaceCreate","engine");
            //surface准备好

            valueAnimator.addUpdateListener(animator -> {


                switch (changeType){
                    case SWITCH_EFFECT_FADE_OVER:
                        drawAlpha(animator);
                        break;
                    case SWITCH_EFFECT_PAGE:
                        drawMove(animator);
                        break;
                    case SWITCH_EFFECT_CUBE:
                        drawCube(animator);
                    default :
                }



            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.e("end","end");
                    super.onAnimationEnd(animation);

                    if (isVisible()){

                        refreshPics();
                        valueAnimator.start();
                    }

                }
            });

            //初始化第一二张图片
            refreshPics();
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
                if (firstChangeableFlag){
                    firstChangeableFlag = false;
                    Log.e("change","hiddenWhenShow");
                     drawStaticPic(bitmap1);
                    valueAnimator.start();
                }

                if (valueAnimator.isStarted()){
                    valueAnimator.resume();
                }

            }else {

                if (valueAnimator.isStarted()){
                    valueAnimator.pause();
                }else {
                    firstChangeableFlag = true;
                }

            }

        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.e("sufacedestriye","engmine");
            valueAnimator.cancel();
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            Log.e("destroy","engine");
        }









        //进行渐变绘制
        private void drawAlpha(ValueAnimator animator){
            Canvas canvas = getSurfaceHolder().lockCanvas();
            int alphaA = (int)animator.getAnimatedValue();
            paintA.setAlpha(alphaA);
            paintB.setAlpha(255 - alphaA);
            canvas.drawBitmap(bitmap2, null, region, paintA);
            canvas.drawBitmap(bitmap1,null,region,paintB);
            Log.e("alphaaaa",String.valueOf(alphaA));
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
            canvas.drawBitmap(bitmap1, null, rect1, paintA);
            canvas.drawBitmap(bitmap2,null,rect2,paintB);
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
            canvas.drawBitmap(bitmap1,null,region,paintA);
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
            canvas.drawBitmap(bitmap2,null,region,paintA);
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
            canvas.drawBitmap(bitmap1,null,region,paintA);
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
            canvas.drawBitmap(bitmap2,null,region,paintA);
            canvas.restore();

            getSurfaceHolder().unlockCanvasAndPost(canvas);

        }


        //绘制静止的bitmap
        private void drawStaticPic(Bitmap bitmap){

                Canvas canvas = getSurfaceHolder().lockCanvas();
                if (canvas != null) {
                    try {
                        region.set(0, 0, canvas.getWidth(), canvas.getHeight());
                        Paint paintA = new Paint();
                        paintA.setFilterBitmap(true);
                        canvas.drawBitmap(bitmap, null, region, paintA);

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
            if (bitmap2 != null){
                bitmap1 = bitmap2;
                bitmap2 = getInTurnNextPic();
            }else {
                bitmap1 = getInTurnNextPic();
                bitmap2 = getInTurnNextPic();
            }

        }

    }



}
