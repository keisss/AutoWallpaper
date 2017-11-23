package cn.keiss.autowallpaper;


import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
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
                throw new IllegalArgumentException("don't set Anim type in setAnimType()");
            }
            switch (changeType) {
                case SWITCH_EFFECT_FADE_OVER:
                    drawAlpha(canvas);
                    break;
                case SWITCH_EFFECT_PAGE:
                    drawPage(canvas);
                    break;
                case SWITCH_EFFECT_CUBE:
                    // drawCube(animator);
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


    //进行渐变绘制
    private void drawAlpha(Canvas canvas){
        int alpha = (int) animatorValue;

        Log.e(String.valueOf(alpha),"alpha");
        paintIn.setAlpha(alpha);
        paintOut.setAlpha(255 - alpha);
        canvas.drawBitmap(mBitmapIn,null, region, paintIn);
        canvas.drawBitmap(mBitmapOut,null,region,paintOut);
    }

    //进行移动绘制
    private void drawPage(Canvas canvas){
        float changeTag = (Float) animatorValue;

        int changeLength = (int)(changeTag *canvas.getWidth());
        Rect rect1 = new Rect(0 - changeLength,0,canvas.getWidth()-changeLength,canvas.getHeight());
        Rect rect2 = new Rect(canvas.getWidth() - changeLength,0,canvas.getWidth()*2 - changeLength,canvas.getHeight());

        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawBitmap(mBitmapIn, null, rect1, paintIn);
        canvas.drawBitmap(mBitmapOut,null,rect2,paintOut);
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

