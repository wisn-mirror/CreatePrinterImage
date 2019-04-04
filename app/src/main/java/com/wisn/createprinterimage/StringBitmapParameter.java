package com.wisn.createprinterimage;

import android.support.annotation.Nullable;

/**
 * Created by Wisn on 2019/4/4 上午10:41.
 */
public class StringBitmapParameter {

    @Nullable
    private String text;
    private int isRightOrLeft;
    private int isSmallOrLarge;

    /**
     * @param text 字段
     */
    public StringBitmapParameter(String text) {
        this.text = text;
        this.isRightOrLeft = PrintImageUtils.IS_LEFT;
        this.isSmallOrLarge = PrintImageUtils.IS_SMALL;
    }

    /**
     * @param text          字段
     * @param isRightOrLeft 可空，默认右边
     */
    public StringBitmapParameter(String text, int isRightOrLeft) {
        this.text = text;
        this.isRightOrLeft = isRightOrLeft;
        this.isSmallOrLarge = PrintImageUtils.IS_SMALL;
    }

    /**
     * @param text           字段
     * @param isRightOrLeft  可空，默认右边
     * @param isSmallOrLarge 可空，默认小字
     */
    public StringBitmapParameter(String text, int isRightOrLeft, int isSmallOrLarge) {
        this.text = text;
        this.isRightOrLeft = isRightOrLeft;
        this.isSmallOrLarge = isSmallOrLarge;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIsRightOrLeft() {
        return isRightOrLeft;
    }

    public void setIsRightOrLeft(int isRightOrLeft) {
        this.isRightOrLeft = isRightOrLeft;
    }

    public int getIsSmallOrLarge() {
        return isSmallOrLarge;
    }

    public void setIsSmallOrLarge(int isSmallOrLarge) {
        this.isSmallOrLarge = isSmallOrLarge;
    }

}
