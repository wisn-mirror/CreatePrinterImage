package com.wisn.createprinterimage.aa;

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


    public PrintParameter(String content, int sizeLevel, int gravity) {
        this.content = content;
        this.sizeLevel = sizeLevel;
        this.gravity = gravity;
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
}
