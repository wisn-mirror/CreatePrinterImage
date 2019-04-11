package com.wisn.createprinterimage.aa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;


import com.wisn.createprinterimage.BitmapUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wisn on 2019/4/10 下午5:36.
 */
public class ImagePHelperV2 {

    public final static int WIDTH = 384;
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
     */
    public Bitmap StringListtoBitmap(Context context, ArrayList<PrintParameter> AllString) {
        if (AllString.size() <= 0)
            return Bitmap.createBitmap(WIDTH, WIDTH / 4, Bitmap.Config.RGB_565);
        Paint paint = new Paint();
        paint.setAntiAlias(false);
//        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/songti.TTF");
//        Typeface font = Typeface.create(typeface, Typeface.NORMAL);
//        paint.setTypeface(font);
        int FontHeightSum = 0;
        for (PrintParameter mParameter : AllString) {
            paint.setTextSize(sp2px(context, mParameter.getSize()));
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            int oneLineHeight = (int) Math.abs(fontMetrics.leading) + (int) Math.abs(fontMetrics.ascent) + (int) Math.abs(fontMetrics.descent);
            if (PrintValue.Content_Str == mParameter.getType()) {
                if (TextUtils.isEmpty(mParameter.getContent())) continue;
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
                FontHeightSum += mParameter.getDealResultContent().size() * oneLineHeight;
            } else if (PrintValue.Content_Line_dashed == mParameter.getType() || PrintValue.Content_Line_full == mParameter.getType() || PrintValue.Content_Line_star == mParameter.getType() || PrintValue.Content_line_space == mParameter.getType()) {
                FontHeightSum += oneLineHeight;
            } else if (PrintValue.Content_barCode == mParameter.getType()) {
                Bitmap bitmapTemp = BitmapUtil.createBarcode(mParameter.getBarStr(),  WIDTH, (int) mParameter.getBarHeight());
                if(bitmapTemp!=null){
                    FontHeightSum +=  bitmapTemp.getHeight();
                    mParameter.bitmap=bitmapTemp;
                }
            } else if (PrintValue.Content_QRCode == mParameter.getType()) {
                int height= (int) mParameter.getQrHeight();
                if(height>WIDTH){
                    height=WIDTH;
                }
                Bitmap bitmapTemp = BitmapUtil.createQRImage(mParameter.getqRStr(), height, height);
                if(bitmapTemp!=null){
                    FontHeightSum +=  bitmapTemp.getHeight();
                    mParameter.bitmap=bitmapTemp;
                }
            } else if (PrintValue.Content_image == mParameter.getType()) {
//                FontHeightSum += oneLineHeight;
            } else if (PrintValue.Content_StrLinkend == mParameter.getType()) {
                List<String> dealResultContent = mParameter.getDealResultContent();
                if(dealResultContent!=null&&dealResultContent.size()>0){
                    FontHeightSum += oneLineHeight;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(WIDTH, FontHeightSum, Bitmap.Config.RGB_565);
        for (int i = 0; i < bitmap.getWidth(); i++) {
            for (int j = 0; j < bitmap.getHeight(); j++) {
                bitmap.setPixel(i, j, Color.WHITE);
            }
        }

        Canvas canvas = new Canvas(bitmap);
        for (PrintParameter mParameter : AllString) {
            paint.setTextSize(sp2px(context, mParameter.getSize()));
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            int FontHeighttemp = (int) Math.abs(fontMetrics.leading) + (int) Math.abs(fontMetrics.ascent) + (int) Math.abs(fontMetrics.descent);
            if (PrintValue.Content_Str == mParameter.getType()) {
                if (TextUtils.isEmpty(mParameter.getContent())) continue;
                for (String temp : mParameter.getDealResultContent()) {
                    if (mParameter.getGravity() == PrintValue.Right) {
                        x = WIDTH - paint.measureText(temp);
                    } else if (mParameter.getGravity() == PrintValue.Left) {
                        x = START_LEFT;
                    } else if (mParameter.getGravity() == PrintValue.Center) {
                        x = (WIDTH - paint.measureText(temp)) / 2.0f;
                    }
                    y = y + FontHeighttemp;
                    canvas.drawText(temp, x, y, paint);
                }
            } else if (PrintValue.Content_Line_dashed == mParameter.getType()) {
                //    虚线dashed
                Rect mBounds = new Rect();
                paint.getTextBounds("---", 0, 3, mBounds);
                int measuredWidth = mBounds.width();
                int widthcount = WIDTH / measuredWidth;
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i <= widthcount; i++) {
                    sb.append("---");
                }
                y = y + FontHeighttemp;
                canvas.drawText(sb.toString(), 0, y, paint);
            } else if (PrintValue.Content_Line_full == mParameter.getType()) {
                //    实线full
                y = (float) (y + FontHeighttemp / 2.0);
                paint.setStrokeWidth(mParameter.getLineWidth());  //设置线宽
                canvas.drawLine(x, y, WIDTH, y, paint);
                paint.setStrokeWidth(1.0f);    //恢复线宽
                y = (float) (y + FontHeighttemp / 2.0);
            } else if (PrintValue.Content_line_space == mParameter.getType()) {
                //空格
                y = y + FontHeighttemp;
            } else if (PrintValue.Content_Line_star == mParameter.getType()) {
                //    ******
                Rect mBounds = new Rect();
                paint.getTextBounds("***", 0, 3, mBounds);
                int measuredWidth = mBounds.width();
                int widthcount = WIDTH / measuredWidth;
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i <= widthcount; i++) {
                    sb.append("***");
                }
                y = y + FontHeighttemp;
                canvas.drawText(sb.toString(), 0, y, paint);
            }else if (PrintValue.Content_barCode == mParameter.getType()) {
                if(mParameter.bitmap!=null){
                    int startx= (WIDTH-mParameter.bitmap.getWidth())/2;
                    canvas.drawBitmap(mParameter.bitmap, startx, y, null);
                    y = y +  mParameter.bitmap.getHeight();
                }
            }else if (PrintValue.Content_QRCode == mParameter.getType()) {
                if(mParameter.bitmap!=null){
                    int startx= (WIDTH-mParameter.bitmap.getWidth())/2;
                    canvas.drawBitmap(mParameter.bitmap, startx, y, null);
                    y = y +  mParameter.bitmap.getHeight();
                }
            }else if (PrintValue.Content_image == mParameter.getType()) {

            }else if (PrintValue.Content_StrLinkend == mParameter.getType()) {
                List<String> dealResultContent = mParameter.getDealResultContent();
                if(dealResultContent!=null&&dealResultContent.size()>0){
                    y = y + FontHeighttemp;
                    String left = dealResultContent.get(0);
                    canvas.drawText(left, 0, y, paint);
                    String right = dealResultContent.get(1);
                    x = WIDTH - paint.measureText(right);
                    canvas.drawText(right, x, y, paint);
                    x=0;
                }
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
