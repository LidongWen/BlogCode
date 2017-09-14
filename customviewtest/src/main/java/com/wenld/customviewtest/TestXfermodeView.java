package com.wenld.customviewtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

/**
 * <p/>
 * Author: wenld on 2017/9/14 10:50.
 * blog: http://www.jianshu.com/u/99f514ea81b3
 * github: https://github.com/LidongWen
 */

public class TestXfermodeView extends View {
//    Paint paint;
//    PorterDuff.Mode mode = PorterDuff.Mode.CLEAR;
//
//    public TestXfermodeView(Context context) {
//        this(context, null);
//    }
//
//    public TestXfermodeView(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public TestXfermodeView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        paint = new Paint();
//    }
//
//    public void transfromXfermode(PorterDuff.Mode mode) {
//        this.mode = mode;
//        invalidate();
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//        Bitmap circle = getCircleBitmap();
//        Bitmap rectangle = getRetangleBitmap();
//
////        int sc = canvas.saveLayer(0, 0, 400, 400, null,
////                Canvas.MATRIX_SAVE_FLAG |
////                        Canvas.CLIP_SAVE_FLAG |
////                        Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
////                        Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
////                        Canvas.CLIP_TO_LAYER_SAVE_FLAG);
//
//        /**
//         * 开启硬件离屏缓存
//         */
//        setLayerType(LAYER_TYPE_HARDWARE, null);
//
//        /**
//         * 画bitmap的也透明
//         */
//        canvas.drawARGB(0, 0, 0, 0);
////        canvas.drawCircle(100, 100, 100, paint);
//        canvas.drawBitmap(circle, 0, 0, paint);
//        paint.setXfermode(new PorterDuffXfermode(mode));
//        canvas.drawBitmap(rectangle, 100, 100, paint);
////        Bitmap b= BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
////        Rect rect = new Rect(0, 0, 100, 100);
////        canvas.drawBitmap(b,rect, rect, paint);
////        canvas.restoreToCount(sc);
//
//    }
//
//    @NonNull
//    private Bitmap getRetangleBitmap() {
//        /**
//         * bm1 在bitmap上面画正方形
//         */
//        Bitmap rectangle = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
//        Canvas c1 = new Canvas(rectangle);
//        Paint p1 = new Paint(Paint.ANTI_ALIAS_FLAG);
//        p1.setColor(getResources().getColor(R.color.colorAccent));
//        /**
//         * 设置透明
//         */
//        c1.drawARGB(0, 0, 0, 0);
//        c1.drawRect(0, 0, 200, 200, p1);
//        return rectangle;
//    }
//
//    @NonNull
//    private Bitmap getCircleBitmap() {
//        /**
//         * bm 在bitmap上面画圆
//         */
//        Bitmap circle = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
//        Canvas c = new Canvas(circle);
//        /**
//         * 设置透明
//         */
//        c.drawARGB(0, 0, 0, 0);
//        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
//        p.setColor(getResources().getColor(R.color.colorPrimary));
//        c.drawCircle(100, 100, 100, p);
//        return circle;
//    }



    // create a bitmap with a circle, used for the "dst" image
    static Bitmap makeDst(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(0xFFFFCC44);
        c.drawOval(new RectF(0, 0, w*3/4, h*3/4), p);
        return bm;
    }

    // create a bitmap with a rect, used for the "src" image
    static Bitmap makeSrc(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(0xFF66AAFF);
        c.drawRect(w/3, h/3, w*19/20, h*19/20, p);
        return bm;
    }

        private static final int W = 64;
        private static final int H = 64;
        private static final int ROW_MAX = 4;   // number of samples per row

        private Bitmap mSrcB;
        private Bitmap mDstB;
        private Shader mBG;     // background checker-board pattern

        public static final Xfermode[] sModes = {
                new PorterDuffXfermode(PorterDuff.Mode.CLEAR),
                new PorterDuffXfermode(PorterDuff.Mode.SRC),
                new PorterDuffXfermode(PorterDuff.Mode.DST),
                new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),
                new PorterDuffXfermode(PorterDuff.Mode.DST_OVER),
                new PorterDuffXfermode(PorterDuff.Mode.SRC_IN),
                new PorterDuffXfermode(PorterDuff.Mode.DST_IN),
                new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),
                new PorterDuffXfermode(PorterDuff.Mode.DST_OUT),
                new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),
                new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP),
                new PorterDuffXfermode(PorterDuff.Mode.XOR),
                new PorterDuffXfermode(PorterDuff.Mode.DARKEN),
                new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),
                new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY),
                new PorterDuffXfermode(PorterDuff.Mode.SCREEN)
        };

        public static final String[] sLabels = {
                "Clear", "Src", "Dst", "SrcOver",
                "DstOver", "SrcIn", "DstIn", "SrcOut",
                "DstOut", "SrcATop", "DstATop", "Xor",
                "Darken", "Lighten", "Multiply", "Screen"
        };
    PorterDuff.Mode mode = PorterDuff.Mode.CLEAR;

    public TestXfermodeView(Context context) {
        this(context, null);
    }

    public TestXfermodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestXfermodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSrcB = makeSrc(W, H);
        mDstB = makeDst(W, H);

        // make a ckeckerboard pattern
        Bitmap bm = Bitmap.createBitmap(new int[] { 0xFFFFFFFF, 0xFFCCCCCC,
                        0xFFCCCCCC, 0xFFFFFFFF }, 2, 2,
                Bitmap.Config.RGB_565);
        mBG = new BitmapShader(bm,
                Shader.TileMode.REPEAT,
                Shader.TileMode.REPEAT);
        Matrix m = new Matrix();
        m.setScale(6, 6);
        mBG.setLocalMatrix(m);
    }

        @Override protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.WHITE);

            Paint labelP = new Paint(Paint.ANTI_ALIAS_FLAG);
            labelP.setTextAlign(Paint.Align.CENTER);

            Paint paint = new Paint();
            paint.setFilterBitmap(false);

            canvas.translate(15, 35);

            int x = 0;
            int y = 0;
            for (int i = 0; i < sModes.length; i++) {
                // draw the border
                paint.setStyle(Paint.Style.STROKE);
                paint.setShader(null);
                canvas.drawRect(x - 0.5f, y - 0.5f,
                        x + W + 0.5f, y + H + 0.5f, paint);

                // draw the checker-board pattern
                paint.setStyle(Paint.Style.FILL);
                paint.setShader(mBG);
                canvas.drawRect(x, y, x + W, y + H, paint);

                // draw the src/dst example into our offscreen bitmap
                int sc = canvas.saveLayer(x, y, x + W, y + H, null,
                        Canvas.MATRIX_SAVE_FLAG |
                                Canvas.CLIP_SAVE_FLAG |
                                Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                                Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                                Canvas.CLIP_TO_LAYER_SAVE_FLAG);
                canvas.translate(x, y);
                canvas.drawBitmap(mDstB, 0, 0, paint);
                paint.setXfermode(sModes[i]);
                canvas.drawBitmap(mSrcB, 0, 0, paint);
                paint.setXfermode(null);
                canvas.restoreToCount(sc);

                // draw the label
                canvas.drawText(sLabels[i],
                        x + W/2, y - labelP.getTextSize()/2, labelP);

                x += W + 10;

                // wrap around when we've drawn enough for one row
                if ((i % ROW_MAX) == ROW_MAX - 1) {
                    x = 0;
                    y += H + 30;
                }
            }
        }

}
