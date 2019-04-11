package com.wisn.createprinterimage.aa;

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
        PrintParameter printParameter2 = new PrintParameter(PrintValue.Content_Line_full, sizeLevel);
        printParameter2.setLineWidth(lineWidth);
        return printParameter2;
    }

    public static PrintParameter getBarCode(String barStr, boolean isShowBarText, float barHeight) {
        PrintParameter printParameter2 = new PrintParameter(PrintValue.Content_barCode);
        printParameter2.setBarStr(barStr, isShowBarText, barHeight);
        return printParameter2;
    }

    public static PrintParameter getQRCode(String qrStr, float height) {
        PrintParameter printParameter2 = new PrintParameter(PrintValue.Content_QRCode);
        printParameter2.setQrContent(qrStr, height);
        return printParameter2;
    }

    public static PrintParameter getLinked(String leftStr, String rightStr,int sizeLevel) {
        PrintParameter printParameter2 = new PrintParameter(PrintValue.Content_StrLinkend);
        printParameter2.addResultContent(leftStr);
        printParameter2.addResultContent(rightStr);
        printParameter2.setSizeLevel(sizeLevel);
        return printParameter2;
    }
}
