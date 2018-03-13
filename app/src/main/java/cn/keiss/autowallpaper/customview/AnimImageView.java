package cn.keiss.autowallpaper.customview;


import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

import cn.keiss.autowallpaper.data.Fields;

import static cn.keiss.autowallpaper.data.Fields.SWITCH_EFFECT_CUBE;
import static cn.keiss.autowallpaper.data.Fields.SWITCH_EFFECT_FADE_OVER;
import static cn.keiss.autowallpaper.data.Fields.SWITCH_EFFECT_PAGE;

/**
 * @author hekai
 * @date 2017/11/21
 */

public class AnimImageView extends android.support.v7.widget.AppCompatImageView {
    private Bitmap mBitmapOut;
    private Bitmap mBitmapIn;
    private Paint paintIn;
    private Paint paintOut;
    private Rect region ;
    private ValueAnimator valueAnimator;

    private boolean drawEnable = false;
    private  Object animatorValue = null;

    private @Fields.SWITCH_EFFECT int changeType = -1 ;

    public AnimImageView(Context context) {
        super(context);
        preDraw();
    }

    public AnimImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        preDraw();
    }

    public AnimImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        preDraw();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        region.set(0,0,canvas.getWidth(),canvas.getHeight());


        if (mBitmapIn == null || mBitmapOut == null){
            return;
        }
        if (mBitmapOut.getWidth() == 0 || mBitmapOut.getHeight() == 0
                || mBitmapIn.getWidth() == 0 || mBitmapIn.getHeight() == 0){
            return;
        }
        canvas.save();
        if (drawEnable){
            drawEnable = false;

            if (changeType == -1){
                throw new IllegalArgumentException("not set Anim type in setAnimType()");
            }
            switch (changeType) {
                case SWITCH_EFFECT_FADE_OVER:
                    drawAlpha(canvas);
                    break;
                case SWITCH_EFFECT_PAGE:
                    drawPage(canvas);
                    break;
                case SWITCH_EFFECT_CUBE:
                     drawCube(canvas);
                default:
                case Fields.SWITCH_EFFECT_DIAL:
                    break;
                case Fields.SWITCH_EFFECT_JALOUSIE:
                    break;
                case Fields.SWITCH_EFFECT_ROTATE:
                    break;
                case Fields.SWITCH_EFFECT_STACK:
                    break;
            }
        }
        canvas.restore();



        }


    public void setBitmapOut(Bitmap bitmapOut){
        this.mBitmapOut = bitmapOut;
        }

    public void setBitmapIn(Bitmap bitmapIn){
            this.mBitmapIn = bitmapIn;
        }



    public void startAnim(){
            valueAnimator.start();
        }

    public void setAnimType(@Fields.SWITCH_EFFECT int type){
        this.changeType = type;
        animatorValue = null;
        paintIn.reset();
        paintOut.reset();
        switch (type) {
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
            case Fields.SWITCH_EFFECT_DIAL:
                break;
            case Fields.SWITCH_EFFECT_ROTATE:
                break;
            case Fields.SWITCH_EFFECT_STACK:
                break;
            case Fields.SWITCH_EFFECT_JALOUSIE:
                break;
            default:
            }
        valueAnimator.setDuration(1500);
        valueAnimator.setRepeatCount(1);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);

        valueAnimator.addUpdateListener(animator -> {
            animatorValue =animator.getAnimatedValue();
            drawEnable = true;
            invalidate();
        });
        }


    /**
     * 进行渐变绘制
     */
    private void drawAlpha(Canvas canvas){
        int alpha = (int) animatorValue;

        Log.e(String.valueOf(alpha),"alpha");
        paintIn.setAlpha(alpha);
        paintOut.setAlpha(255 - alpha);
        canvas.drawBitmap(mBitmapIn,null, region, paintIn);
        canvas.drawBitmap(mBitmapOut,null,region,paintOut);
    }

    /**
     * 进行移动绘制
     */
    private void drawPage(Canvas canvas){
        float changeTag = (Float) animatorValue;

        int changeLength = (int)(changeTag *canvas.getWidth());
        Rect rectOut = new Rect(0 - changeLength,0,canvas.getWidth()-changeLength,canvas.getHeight());
        Rect rectIn = new Rect(canvas.getWidth() - changeLength,0,canvas.getWidth()*2 - changeLength,canvas.getHeight());

        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawBitmap(mBitmapIn, null, rectIn, paintIn);
        canvas.drawBitmap(mBitmapOut,null,rectOut,paintOut);
    }


    /**
     * 进行cube绘制
     */
    private void drawCube(Canvas canvas){
        float changeTag = (Float) animatorValue;

        Camera camera = new Camera();
        Matrix matrix = new Matrix();


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
        canvas.drawBitmap(mBitmapIn,null,region, paintIn);
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
        canvas.drawBitmap(mBitmapOut,null,region, paintIn);
        canvas.restore();





    }

    private void preDraw(){
        paintIn = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintIn.setFilterBitmap(true);
        paintIn.setDither(true);

        paintOut = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintOut.setFilterBitmap(false);
        paintOut.setDither(true);

        region = new Rect();
    }
}

