package com.enhan.sabina.speedy.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.enhan.sabina.speedy.SpeedyApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {
    public static Bitmap getCompressBitmap(String stringPath) {
        Context context = SpeedyApplication.getAppContext();
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
            float height = metrics.heightPixels;
            float width =  metrics.widthPixels;
            int noCompress = 1;
            if (originalWidth > originalHeight && originalWidth > width) {
                noCompress = (int) (originalWidth / width);
            } else if (originalWidth < originalHeight && originalHeight > height) {
                noCompress = (int) (originalHeight / height);
            }
            if (noCompress <= 0) noCompress = 1;

            options.inSampleSize = noCompress;
            options.inJustDecodeBounds = false;
            inputStream = context.getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
            inputStream.close();
            return compressBitmap(bitmap);
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

    private static Bitmap compressBitmap(Bitmap bitmap) {
        int options = 100;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        while (outputStream.toByteArray().length / 1024 > 350) {
            outputStream.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, outputStream);
            options -= 10;
        }

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        return BitmapFactory.decodeStream(inputStream, null, null);
    }
}
