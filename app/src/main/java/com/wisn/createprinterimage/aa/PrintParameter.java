package com.wisn.createprinterimage.aa;

import android.graphics.Bitmap;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wisn on 2019/4/10 下午5:37.
 */
public class PrintParameter {
    private String content;
    private List<String> dealResultContent;
    private int sizeLevel;
    private int gravity;
    private int Type;
    //line
    private float LineWidth = 1;
    private String barStr;
    private boolean isShowBarText;
    private float barHeight;
    private float qrHeight;
    private String qRStr;
    public Bitmap bitmap;

    public PrintParameter(int type) {
        Type = type;
    }

    public PrintParameter(String content, int sizeLevel, int gravity) {
        Type = PrintValue.Content_Str;
        this.content = content;
        this.sizeLevel = sizeLevel;
        this.gravity = gravity;
    }


    public PrintParameter(int Type, int sizeLevel) {
        this.Type = Type;
        this.sizeLevel = sizeLevel;
    }

    public boolean addResultContent(String lineContet) {
        if (TextUtils.isEmpty(lineContet)) return false;
        if (dealResultContent == null) {
            dealResultContent = new ArrayList<>();
        }
        return dealResultContent.add(lineContet);
    }

    public List<String> getDealResultContent() {
        if (dealResultContent == null) {
            dealResultContent = new ArrayList<>();
        }
        return dealResultContent;
    }

    public float getLineWidth() {
        return LineWidth;
    }

    public void setLineWidth(float lineWidth) {
        LineWidth = lineWidth;
    }

    public void setDealResultContent(List<String> dealResultContent) {
        this.dealResultContent = dealResultContent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSize() {
        return sizeLevel;
    }

    public void setSizeLevel(int sizeLevel) {
        this.sizeLevel = sizeLevel;
    }

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public int getType() {
        return Type;
    }

    public String getBarStr() {
        return barStr;
    }

    public void setBarStr(String barStr, boolean showBarText, float barHeight) {
        this.barStr = barStr;
        isShowBarText = showBarText;
        this.barHeight = barHeight;
    }

    public boolean isShowBarText() {
        return isShowBarText;
    }

    public float getBarHeight() {
        return barHeight;
    }

    public String getqRStr() {
        return qRStr;
    }

    public float getQrHeight() {
        return qrHeight;
    }

    public void setQrContent(String qRStr,float qrHeight) {
        this.qrHeight = qrHeight;
        this.qRStr = qRStr;
    }
}
