package com.wisn.createprinterimage.aa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wisn on 2019/4/10 下午5:36.
 */
public class ImagePHelper {

    private final static int WIDTH = 384;
    private final static int START_LEFT = 0;


    private static float x = START_LEFT, y;

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 生成图片
     *
     */
    public Bitmap StringListtoBitmap(Context context, ArrayList<PrintParameter> AllString) {
        if (AllString.size() <= 0)
            return Bitmap.createBitmap(WIDTH, WIDTH / 4, Bitmap.Config.RGB_565);
        Paint paint = new Paint();
        paint.setAntiAlias(false);
       /* Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/songti.TTF");// 仿宋打不出汉字
        Typeface font = Typeface.create(typeface, Typeface.NORMAL);
        paint.setTypeface(font);*/
        int FontHeightSum = 0;

        for (PrintParameter mParameter : AllString) {
            paint.setTextSize(sp2px(context, mParameter.getSize()));
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            int linespace = (int) Math.abs(fontMetrics.leading) + (int) Math.abs(fontMetrics.descent);
//            int linespace = (int) Math.abs(fontMetrics.leading) ;
            if(y==0){
                y= (int) Math.abs(fontMetrics.leading) + (int) Math.abs(fontMetrics.ascent);
            }
            int oneLineHeight = (int) Math.abs(fontMetrics.leading) + (int) Math.abs(fontMetrics.ascent) + (int) Math.abs(fontMetrics.descent);
            int ALineLength = paint.breakText(mParameter.getContent(), true, WIDTH, null);//检测一行多少字
            int lenght = mParameter.getContent().length();
            if (ALineLength < lenght) {
                String substring1 = mParameter.getContent().substring(0, ALineLength);
                mParameter.getDealResultContent().add(substring1);
                String substring = mParameter.getContent().substring(ALineLength);
                List<String> dealResultContent = mParameter.getDealResultContent();
                getMesureText(substring, paint, dealResultContent);
            } else {
                mParameter.addResultContent(mParameter.getContent());
            }
            FontHeightSum += mParameter.getDealResultContent().size() * (oneLineHeight + linespace);
        }
        Bitmap bitmap = Bitmap.createBitmap(WIDTH, FontHeightSum, Bitmap.Config.RGB_565);
        for (int i = 0; i < bitmap.getWidth(); i++) {
            for (int j = 0; j < bitmap.getHeight(); j++) {
                bitmap.setPixel(i, j, Color.WHITE);
            }
        }

        Canvas canvas = new Canvas(bitmap);
        for (PrintParameter mParameter : AllString) {
            for (String temp : mParameter.getDealResultContent()) {
                paint.setTextSize(sp2px(context, mParameter.getSize()));

                if (mParameter.getGravity() == PrintValue.Right) {
                    x = WIDTH - paint.measureText(temp);
                } else if (mParameter.getGravity() == PrintValue.Left) {
                    x = START_LEFT;
                } else if (mParameter.getGravity() == PrintValue.Center) {
                    x = (WIDTH - paint.measureText(temp)) / 2.0f;
                }
                Paint.FontMetrics fontMetrics = paint.getFontMetrics();
                int linespace = (int) Math.abs(fontMetrics.leading) + (int) Math.abs(fontMetrics.descent);
//                int linespace = (int) Math.abs(fontMetrics.leading) ;
                int FontHeighttemp = (int) Math.abs(fontMetrics.leading) + (int) Math.abs(fontMetrics.ascent) + (int) Math.abs(fontMetrics.descent);
//                y = y  + linespace;
                canvas.drawText(temp, x, y, paint);
                y = y + FontHeighttemp + linespace;
            }

        }
        canvas.save();
        canvas.restore();
        return bitmap;
    }

    public List<String> getMesureText(String content, Paint paint, List<String> data) {
        int ALineLength = paint.breakText(content, true, WIDTH, null);//检测一行多少字
        if (ALineLength < content.length()) {
            String substring = content.substring(0, ALineLength);
            data.add(substring);
            String newcontent = content.substring(ALineLength - 1);
            return getMesureText(newcontent, paint, data);
        } else {
            data.add(content);
            return data;
        }
    }

    /**
     * 合并图片
     */
    public static Bitmap addBitmapInHead(Bitmap first, Bitmap second) {
        int width = Math.max(first.getWidth(), second.getWidth());
        int startWidth = (width - first.getWidth()) / 2;
        int height = first.getHeight() + second.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        for (int i = 0; i < result.getWidth(); i++) {
            for (int j = 0; j < result.getHeight(); j++) {
                result.setPixel(i, j, Color.WHITE);
            }
        }
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(first, startWidth, 0, null);
        canvas.drawBitmap(second, 0, first.getHeight(), null);
        return result;
    }

    /***
     * 使用两个方法的原因是：
     * logo标志需要居中显示，如果直接使用同一个方法是可以显示的，但是不会居中
     */
    public static Bitmap addBitmapInFoot(Bitmap bitmap, Bitmap image) {
        int width = Math.max(bitmap.getWidth(), image.getWidth());
        int startWidth = (width - image.getWidth()) / 2;
        int height = bitmap.getHeight() + image.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        for (int i = 0; i < result.getWidth(); i++) {
            for (int j = 0; j < result.getHeight(); j++) {
                result.setPixel(i, j, Color.WHITE);
            }
        }
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.drawBitmap(image, startWidth, bitmap.getHeight(), null);
        return result;
    }

}
