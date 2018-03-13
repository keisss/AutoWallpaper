package cn.keiss.autowallpaper.service;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.Log;

/**
 * @author hekai
 * @date 2017/11/24
 */

public class DrawSwitchEffect {

    //进行渐变绘制
    private void drawAlpha(ValueAnimator animator, Canvas canvas, Paint paintIn, Paint paintOut, Rect region, Bitmap bitmapIn,Bitmap bitmapOut){
        int alphaA = (int)animator.getAnimatedValue();
        paintIn.setAlpha(alphaA);
        paintOut.setAlpha(255 - alphaA);
        canvas.drawBitmap(bitmapIn, null, region, paintIn);
        canvas.drawBitmap(bitmapOut,null,region, paintOut);

    }
//    //进行移动绘制
//    private void drawMove(ValueAnimator animator){
//        float changeTag = (Float) animator.getAnimatedValue();
//
//
//        Canvas canvas = getCanvas();
//        int changeLength = (int)(changeTag *canvas.getWidth());
//        Rect rect1 = new Rect(0 - changeLength,0,canvas.getWidth()-changeLength,canvas.getHeight());
//        Rect rect2 = new Rect(canvas.getWidth() - changeLength,0,canvas.getWidth()*2 - changeLength,canvas.getHeight());
//
//        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
//        canvas.drawBitmap(bitmapOut, null, rect1, paintIn);
//        canvas.drawBitmap(bitmapIn,null,rect2, paintOut);
//        Log.e("alphaaaa",String.valueOf(changeLength));
//        getSurfaceHolder().unlockCanvasAndPost(canvas);
//    }
//    /**
//     * 进行cube绘制
//     */
//    private void drawCube(ValueAnimator animator){
//        float changeTag = (Float) animator.getAnimatedValue();
//
//        Camera camera = new Camera();
//        Matrix matrix = new Matrix();
//
//
//        Canvas canvas = getCanvas();
//        int changeLength = (int)(changeTag *canvas.getWidth());
//        int angle = (int) (changeTag*90);
//
//
//        camera.save();
//        camera.translate(-changeLength,0,0);
//        camera.rotateY(90- angle);
//
//
//        camera.getMatrix(matrix);
//        camera.restore();
//
//
//        matrix.preTranslate(0, -canvas.getHeight()/2);
//        matrix.postTranslate(canvas.getWidth(), canvas.getHeight()/2);
//
//        canvas.save();
//        canvas.concat(matrix);
//        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
//        canvas.drawBitmap(bitmapOut,null,region, paintIn);
//        canvas.restore();
//
//
//        matrix.reset();
//
//        camera.save();
//        camera.translate(canvas.getWidth()-changeLength ,0,0);
//        camera.rotateY(-angle);
//        camera.getMatrix(matrix);
//        camera.restore();
//
//        matrix.preTranslate(-canvas.getWidth(), -canvas.getHeight()/2);
//        matrix.postTranslate(0, canvas.getHeight()/2);
//
//
//        canvas.save();
//        canvas.concat(matrix);
//        canvas.drawBitmap(bitmapIn,null,region, paintIn);
//        canvas.restore();
//
//        getSurfaceHolder().unlockCanvasAndPost(canvas);
//
//
//
//
//    }
//    private void drawStack(ValueAnimator valueAnimator){
//        float changeTag = (Float) valueAnimator.getAnimatedValue();
//
//
//        Camera camera = new Camera();
//        Matrix matrix = new Matrix();
//
//
//        Canvas canvas = getCanvas();
//        int changeLength = (int)(changeTag *canvas.getWidth());
//        int angle = (int) (changeTag*90);
//
//
//        camera.save();
//        camera.translate(-changeLength,0,0);
//        camera.rotateY(90- angle);
//
//
//        camera.getMatrix(matrix);
//        camera.restore();
//
//
//        matrix.preTranslate(0, -canvas.getHeight()/2);
//        matrix.postTranslate(canvas.getWidth(), canvas.getHeight()/2);
//
//        canvas.save();
//        canvas.concat(matrix);
//        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
//        canvas.drawBitmap(bitmapOut,null,region, paintIn);
//        canvas.restore();
//
//
//        matrix.reset();
//
//        camera.save();
//        camera.translate(canvas.getWidth()-changeLength ,0,0);
//        camera.rotateY(-angle);
//        camera.getMatrix(matrix);
//        camera.restore();
//
//        matrix.preTranslate(-canvas.getWidth(), -canvas.getHeight()/2);
//        matrix.postTranslate(0, canvas.getHeight()/2);
//
//
//        canvas.save();
//        canvas.concat(matrix);
//        canvas.drawBitmap(bitmapIn,null,region, paintIn);
//        canvas.restore();
//
//        getSurfaceHolder().unlockCanvasAndPost(canvas);
//
//    }
}
