package com.enhan.sabina.speedy.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.enhan.sabina.speedy.SpeedyApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {
    public static Bitmap getCompressBitmap(Context context, String stringPath) {
        Uri imageUri = Uri.parse(stringPath);
        InputStream inputStream = null;
        try {
            inputStream = context.getContentResolver().openInputStream(imageUri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            BitmapFactory.decodeStream(inputStream, null, options);
            int originalWidth = options.outWidth;
            int originalHeight = options.outHeight;
            if (originalHeight == -1 || originalWidth == -1) return null;

            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

            DisplayMetrics metrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(metrics);


            float height = metrics.heightPixels / 0.8f;
            float width =  metrics.widthPixels;
            int noCompress = 1;
            if (originalWidth > originalHeight && originalWidth > width) {
                noCompress = (int) (originalWidth / width);
            } else if (originalWidth < originalHeight && originalHeight > height) {
                noCompress = (int) (originalHeight / height);
            }
            if (noCompress <= 0) noCompress = 1;
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inSampleSize = noCompress;
            options.inJustDecodeBounds = true;
            bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
            inputStream = context.getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, bitmapOptions);
            inputStream.close();
            return CompressBitmap(bitmap);
//            return  bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    private static Bitmap CompressBitmap(Bitmap bitmap) {
        int options = 100;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);  //先不压缩，数据保存到outputStream；
//        LogUtils.e("yasuo", outputStream.toByteArray().length / 1024 + "11================");
        while (outputStream.toByteArray().length / 1024 > 350) {//循环判断如果压缩后图片是否大于400kb,大于继续压缩
            outputStream.reset(); //重置
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, outputStream);
//            LogUtils.e("yasuo", outputStream.toByteArray().length / 1024 + "================" + options);
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        Bitmap decodeStream = BitmapFactory.decodeStream(inputStream, null, null);
        return decodeStream;
    }
}
