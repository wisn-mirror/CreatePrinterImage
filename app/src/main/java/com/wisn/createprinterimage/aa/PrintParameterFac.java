package com.wisn.createprinterimage.aa;

import java.util.List;

/**
 * Created by Wisn on 2019/4/11 下午1:41.
 */
public class PrintParameterFac {

    public static PrintParameter getContent(String content, int sizeLevel, int gravity) {
        return new PrintParameter(content, sizeLevel, gravity);
    }

    public static PrintParameter getChartLine(int Type, int sizeLevel) {
        return new PrintParameter(Type, sizeLevel);
    }

    public static PrintParameter getChartLineFull(int sizeLevel, float lineWidth) {
        PrintParameter printParameter = new PrintParameter(PrintValue.Content_Line_full, sizeLevel);
        printParameter.setLineWidth(lineWidth);
        return printParameter;
    }

    public static PrintParameter getBarCode(String barStr, boolean isShowBarText, float barHeight) {
        PrintParameter printParameter = new PrintParameter(PrintValue.Content_barCode);
        printParameter.setBarStr(barStr, isShowBarText, barHeight);
        return printParameter;
    }

    public static PrintParameter getQRCode(String qrStr, float height) {
        PrintParameter printParameter = new PrintParameter(PrintValue.Content_QRCode);
        printParameter.setQrContent(qrStr, height);
        return printParameter;
    }

    public static PrintParameter getLinked(String leftStr, String rightStr,int sizeLevel) {
        PrintParameter printParameter = new PrintParameter(PrintValue.Content_StrLinkend);
        printParameter.addResultContent(leftStr);
        printParameter.addResultContent(rightStr);
        printParameter.setSizeLevel(sizeLevel);
        return printParameter;
    }
    
    public static PrintParameter getSpliteCombination(int spliteFirstMaxLength, float spliteCombinationWeightSum, int sizeLevel) {
        if(spliteFirstMaxLength<1) spliteFirstMaxLength=1;
        PrintParameter printParameter = new PrintParameter(PrintValue.Content_SpliteCombination);
        printParameter.setSizeLevel(sizeLevel);
        printParameter.setSpliteCombinationList(spliteFirstMaxLength,spliteCombinationWeightSum);
        return printParameter;
    }
}
