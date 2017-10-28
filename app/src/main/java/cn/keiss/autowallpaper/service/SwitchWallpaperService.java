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
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import cn.keiss.autowallpaper.R;

/**
 * Created by hekai on 2017/10/10.
 * 运行在后台的壁纸更新服务
 */

public class SwitchWallpaperService extends WallpaperService {



    //淡出淡入
    private final int SWITCH_EFFECT_FADE_OVER = 1;
    //转盘
    private final int SWITCH_EFFECT_DIAL = 2;
    private final int SWITCH_EFFECT_PAGE = 3;
    private final int SWITCH_EFFECT_CUBE = 6;
    //层叠
    private final int SWITCH_EFFECT_STACK = 4;


    @Override
    public Engine onCreateEngine() {
        return new DrawEngine();

    }


    private class DrawEngine extends Engine{
        //正在显示的（第一期加载显示的）
        private Bitmap bitmap1;
        //变换后显示的
        private Bitmap bitmap2;

        //是否可以进行第一次加载
        private boolean firstChangeableFlag = false;

        //每次变化之间的间隔
        private long delayTime = 10000L /2;



        private Rect region;



        private Paint paintA;
        private Paint paintB;


        //用来绘制一次变换
        private Runnable onceRunnable;


        private long changeTime = 4*1000;

        private ValueAnimator valueAnimator;

        private int changeType = SWITCH_EFFECT_CUBE;

        Bitmap bitmapA = BitmapFactory.decodeResource(getResources(), R.drawable.hezhipin610039);

        Bitmap bitmapB = BitmapFactory.decodeResource(getResources(),R.drawable.u012861467);

        Bitmap bitmapC = BitmapFactory.decodeResource(getResources(),R.drawable.jike);

        int i =1;


        DrawEngine() {
            Log.e("new","Engine");
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            //此时surface还没有准备好
            Log.e("create","engine");



            paintA = new Paint(Paint.ANTI_ALIAS_FLAG);
            paintA.setFilterBitmap(true);
            paintA.setDither(true);

            paintB = new Paint();
            paintB.setFilterBitmap(false);


            region = new Rect();

            bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.hezhipin610039);
            bitmap2 = BitmapFactory.decodeResource(getResources(),R.drawable.u012861467);

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
                        i = (i+1) %3;
                        if (i == 0){
                            i = 1;
                        }
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
            //频繁被调用，初始化时会 调用一次Visible，调用一次Unvisible,在调用一次Visible

            if (visible){

                // 初始化
                if (firstChangeableFlag){
                    firstChangeableFlag = false;
                    Log.e("change","hiddenWhenShow");
                     drawStaticPic(getBitmap(1));
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





        private Bitmap getBitmap(int num){
            switch (num){
                case 1: return bitmapA;
                case 2: return bitmapB;
                case 3:return  bitmapC;
                default:
            }
            return null;
        }



        //进行渐变绘制
        private void drawAlpha(ValueAnimator animator){
            Canvas canvas = getSurfaceHolder().lockCanvas();
            int alphaA = (int)animator.getAnimatedValue();
            paintA.setAlpha(alphaA);
            paintB.setAlpha(255 - alphaA);
            canvas.drawBitmap(getBitmap(i+1), null, region, paintA);
            canvas.drawBitmap(getBitmap(i),null,region,paintB);
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
            canvas.drawBitmap(getBitmap(i), null, rect1, paintA);
            canvas.drawBitmap(getBitmap(i+1),null,rect2,paintB);
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
            canvas.drawBitmap(getBitmap(1),null,region,paintA);
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
            canvas.drawBitmap(getBitmap(1),null,region,paintA);
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
            canvas.drawBitmap(getBitmap(1),null,region,paintA);
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
            canvas.drawBitmap(getBitmap(1),null,region,paintA);
            canvas.restore();

            getSurfaceHolder().unlockCanvasAndPost(canvas);

        }

            //绘制精致的bitmap
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




    }



}
