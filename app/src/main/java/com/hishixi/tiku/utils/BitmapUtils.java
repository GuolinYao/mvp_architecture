package com.hishixi.tiku.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import com.hishixi.tiku.mvp.model.entity.SelectedImageBean;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 裁剪成一个圆形头像
 */
public class BitmapUtils {
    public Bitmap creatCircle(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int left = 0, top = 0, right = width, bottom = height;
        float roundPx = width / 2;
        if (width > height) {
            left = (width - height) / 2;
            top = 0;
            right = left + height;
            bottom = height;
            roundPx = height / 2;

        } else if (width < height) {
            left = 0;
            top = (height - width) / 2;
            right = width;
            bottom = top + width;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        // final int color = 0xff424242;
        final int color = 0xFFFFFFFF;
        final Paint paint = new Paint();
        final Rect rect = new Rect(left, top, right, bottom);
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 将个人头像图片保存至sd卡
     *
     * @param bitmap  bitmap
     * @param context context
     * @return File
     */
    @SuppressLint("NewApi")
    public static File writeToSDCard(Bitmap bitmap, Context context) {
        File mFile = null;
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }

        if (bitmap == null) {
            return null;
        }
        FileOutputStream fos = null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytes = stream.toByteArray();
        try {
            File dir = context.getExternalFilesDir(null);
            // mFile = new File(dir, "_FILES" + CacheUtils.getAccountId(context)
            // + ".jpg");
            mFile = new File(dir, "headpic.jpg");
            if (mFile.exists()) {
                mFile.delete();
            }
            mFile.createNewFile();
            fos = new FileOutputStream(mFile);
            fos.write(bytes);
            fos.close();
            stream.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bitmap != null) {
                if (!bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            }
        }
        return mFile;
    }

    /**
     * 将图片保存至sd卡
     *
     * @param bitmap  bitmap
     * @param context context
     * @param path    path
     * @return File
     */
    public static File writeImageToSDCard(Bitmap bitmap, Context context, String path,
                                          boolean forceSave) {
        File mFile = null;
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }
        if (bitmap == null) {
            return null;
        }
        FileOutputStream fos = null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytes = stream.toByteArray();
        try {
            mFile = new File(path);
            if (mFile.exists() && !forceSave) {
                return mFile;
            }
            if (!mFile.getParentFile().exists())
                mFile.getParentFile().mkdirs();
            mFile.createNewFile();
            fos = new FileOutputStream(mFile);
            fos.write(bytes);
            fos.close();
            stream.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bitmap != null) {
                if (!bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            }
        }
        return mFile;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {

        Bitmap bitmap = Bitmap.createBitmap(

                drawable.getIntrinsicWidth(),

                drawable.getIntrinsicHeight(),

                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888

                        : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);

        // canvas.setBitmap(bitmap);

        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());

        drawable.draw(canvas);

        return bitmap;

    }

    public static byte[] bitmap2byte(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    // 生成圆角图片
    public static Bitmap GetRoundedCornerBitmap(Bitmap bitmap, float rPx) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = rPx;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }


    public static Bitmap getReverseBitmapById(int resId, Context context) {
        Bitmap sourceBitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        //绘制原图的下一半图片
        Matrix matrix = new Matrix();
        //倒影翻转
        matrix.setScale(1, -1);

        Bitmap inverseBitmap = Bitmap.createBitmap(sourceBitmap, 0, sourceBitmap
                .getHeight() / 2, sourceBitmap.getWidth(), sourceBitmap.getHeight() /
                3, matrix, false);
        //合成图片
        Bitmap groupbBitmap = Bitmap.createBitmap(sourceBitmap.getWidth(), sourceBitmap
                .getHeight() + sourceBitmap.getHeight() / 3 + 60, sourceBitmap
                .getConfig());
        //以合成图片为画布
        Canvas gCanvas = new Canvas(groupbBitmap);
        //将原图和倒影图片画在合成图片上
        gCanvas.drawBitmap(sourceBitmap, 0, 0, null);
        gCanvas.drawBitmap(inverseBitmap, 0, sourceBitmap.getHeight() + 50, null);
        //添加遮罩
        Paint paint = new Paint();
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        LinearGradient shader = new LinearGradient(0, sourceBitmap.getHeight() + 50, 0,
                groupbBitmap.getHeight(), Color.BLACK, Color.TRANSPARENT, tileMode);
        paint.setShader(shader);
        //这里取矩形渐变区和图片的交集
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        gCanvas.drawRect(0, sourceBitmap.getHeight() + 50, sourceBitmap.getWidth(),
                groupbBitmap.getHeight(), paint);
        return groupbBitmap;
    }

    public static void saveBitmap(ArrayList<SelectedImageBean> paths, String filePath,
                                  String newName) throws IOException {
        Bitmap mBitmap = null;
        for (int i = 0; i < paths.size(); i++) {
            String path = paths.get(i).getPath();
            String fileName = path.substring(path.lastIndexOf("/") + 1);
            mBitmap = revitionImageSize(path);// 如果图片显示较小，重新写一个由路径压缩的方法
            if (null != mBitmap) {
                FileModel.copyToSDCard(filePath, fileName, mBitmap, 100, newName + i);
            }
        }
    }

    public static void saveBitmap(ArrayList<SelectedImageBean> paths, String filePath) throws
            IOException {
        Bitmap mBitmap = null;
        for (int i = 0; i < paths.size(); i++) {
            String path = paths.get(i).getPath();
            String fileName = path.substring(path.lastIndexOf("/") + 1,path.lastIndexOf("."));
            mBitmap = revitionImageSize(path);// 如果图片显示较小，重新写一个由路径压缩的方法
            if (null != mBitmap) {
                FileModel.copyToSDCard(filePath, fileName, mBitmap, 100, fileName);
            }
        }
    }

    private static Bitmap revitionImageSize(String path) throws IOException {
        File file = new File(path);
        if (!file.exists())
            file.mkdir();
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File
                (path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {// TODO: 17/4/13
            if ((options.outWidth >> i <= 500) && (options.outHeight >> i <= 500)) {
                in = new BufferedInputStream(new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }

    /**
     * 根据路径 二次采样并压缩
     * @param filePath 图片路径
     * @param destWidth 目标宽度
     * @param destHeight 目标高度
     */
    public static Bitmap convertToBitmap(String filePath,int destWidth,int destHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
         BitmapFactory.decodeFile(filePath, options);
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        int sampleSize = 1;
        while((outWidth/sampleSize>destWidth)||(outHeight/sampleSize>destHeight)){
            sampleSize*=2;
        }
//        二次采样
        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
       return BitmapFactory.decodeFile(filePath, options);
    }
}
