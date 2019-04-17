package com.wisn.createprinterimage;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.ums.AppHelper;
import com.ums.upos.sdk.exception.CallServiceException;
import com.ums.upos.sdk.exception.SdkException;
import com.ums.upos.sdk.printer.OnPrintResultListener;
import com.ums.upos.sdk.printer.PrinterManager;
import com.ums.upos.sdk.system.BaseSystemManager;
import com.ums.upos.sdk.system.OnServiceStatusListener;
import com.wisn.createprinterimage.aa.ImagePHelperV2;
import com.wisn.createprinterimage.aa.PrintParameter;
import com.wisn.createprinterimage.aa.PrintParameterFac;
import com.wisn.createprinterimage.aa.PrintValue;
import com.wisn.createprinterimage.aa.SpliteCombination;
import com.wisn.createprinterimage.bb.PrintTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Button imageBtn, p_printer,p_printer_byScript;
    private ImageView mView;
    private Bitmap bitmapr;
    private PrinterManager printerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageBtn = (Button) findViewById(R.id.p_image);
        p_printer = (Button) findViewById(R.id.p_printer);
        p_printer_byScript = (Button) findViewById(R.id.p_printer_byScript);
        mView = (ImageView) findViewById(R.id.s_image);
        imageBtn.setOnClickListener(this);
        p_printer.setOnClickListener(this);
        p_printer_byScript.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.p_printer_byScript:
                PrintTest printTest=new PrintTest();
                printTest.deviceServiceLogin(this.getApplication());
                break;
            case R.id.p_image:
                ImageTask mImageTask = new ImageTask();
                mImageTask.execute("");
                break;
            case R.id.p_printer:

                if (bitmapr == null) {
                    Log.d(TAG, "bitmap is null");
                    return;
                }
                String fname = "/sdcard/ddd.png";
                try {
                    FileOutputStream out = new FileOutputStream(fname);
                    bitmapr.compress(Bitmap.CompressFormat.PNG, 100, out);
                    Log.d(TAG, "file" + fname + "output done.");
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                AppHelper.callPrint(this, fname);
                try {
                    BaseSystemManager.getInstance().deviceServiceLogin(MainActivity.this.getApplicationContext(),
                            null, "99999999", new OnServiceStatusListener() {

                                @Override
                                public void onStatus(int status) {
                                    printerManager = new PrinterManager();
                                    try {
                                        int i = printerManager.initPrinter();
//                                        printerManager.setBitmap(bitmapr);
                                        Log.e(TAG, "打印机arg0===" + status+"  i:"+i);
                                        printerManager.setPrnTplText("测试");
                                        printerManager.startPrint(new OnPrintResultListener() {
                                            @Override
                                            public void onPrintResult(int i) {

                                            }
                                        });
                                    } catch (SdkException e) {
                                        e.printStackTrace();
                                    } catch (CallServiceException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                } catch (SdkException e) {
                    e.printStackTrace();
                }
                /*try {
                    printerManager.setBitmap(bitmapr);
                    printerManager.startPrint(new OnPrintResultListener() {
                        @Override
                        public void onPrintResult(int i) {

                        }
                    });
                } catch (SdkException e) {
                    e.printStackTrace();
                } catch (CallServiceException e) {
                    e.printStackTrace();
                }*/
                break;
        }
    }


    private class ImageTask extends AsyncTask<String, Integer, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            return creatImage();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imageBtn.setText("正在生成 - 请等待 不要着急");
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            bitmapr = bitmap;
            imageBtn.setText("生成");
            mView.setImageBitmap(bitmap);
        }
    }


    private Bitmap creatImage() {
        try {
            InputStream ins = getAssets().open("res" + File.separator + "ic_launcher.png");
            Bitmap imageBitmap = BitmapFactory.decodeStream(ins);
            ArrayList<PrintParameter> mParameters = new ArrayList<>();
         /*   mParameters.add(PrintParameterFac.getContent("测试文字排版", PrintValue.TextSizeLevel_5, PrintValue.Center));
            mParameters.add(PrintParameterFac.getChartLine(PrintValue.Content_Line_dashed, PrintValue.TextSizeLevel_2));

            mParameters.add(PrintParameterFac.getSpliteCombination(1, 5, PrintValue.TextSizeLevel_1)
                    .addPrintParameter(new SpliteCombination(3, "商品名"))
                    .addPrintParameter(new SpliteCombination(1, "数量"))
                    .addPrintParameter(new SpliteCombination(1, "价格")));
            mParameters.add(PrintParameterFac.getChartLine(PrintValue.Content_Line_star, PrintValue.TextSizeLevel_3));

            mParameters.add(PrintParameterFac.getSpliteCombination(1, 5, PrintValue.TextSizeLevel_1)
                    .addPrintParameter(new SpliteCombination(3, "2列商品名称很长"))
                    .addPrintParameter(new SpliteCombination(1, "X1"))
                    .addPrintParameter(new SpliteCombination(1, "¥33.2")));
            mParameters.add(PrintParameterFac.getChartLine(PrintValue.Content_line_space, PrintValue.TextSizeLevel_2));

            mParameters.add(PrintParameterFac.getSpliteCombination(2, 5, PrintValue.TextSizeLevel_1)
                    .addPrintParameter(new SpliteCombination(3, "2列商品名称很长2列商品名称很长2列商品名称很长2列商品名称很长"))
                    .addPrintParameter(new SpliteCombination(1, "X3"))
                    .addPrintParameter(new SpliteCombination(1, "¥525.2")));
            mParameters.add(PrintParameterFac.getChartLine(PrintValue.Content_line_space, PrintValue.TextSizeLevel_2));

            mParameters.add(PrintParameterFac.getSpliteCombination(2, 5, PrintValue.TextSizeLevel_1)
                    .addPrintParameter(new SpliteCombination(3, "2列商品名称很长2列商品名称很长2列商品名称很长2列商品名称很长"))
                    .addPrintParameter(new SpliteCombination(1, "X3"))
                    .addPrintParameter(new SpliteCombination(1, "¥55.2")));
            mParameters.add(PrintParameterFac.getChartLine(PrintValue.Content_line_space, PrintValue.TextSizeLevel_2));

            mParameters.add(PrintParameterFac.getSpliteCombination(2, 5, PrintValue.TextSizeLevel_1)
                    .addPrintParameter(new SpliteCombination(3, "2列商品名称很长2列商品名称很长2列商品名称很长2列商品名称很长2列商品名称很长2列商品名称很长"))
                    .addPrintParameter(new SpliteCombination(1, "X2"))
                    .addPrintParameter(new SpliteCombination(1, "¥33.2")));
            mParameters.add(PrintParameterFac.getChartLine(PrintValue.Content_line_space, PrintValue.TextSizeLevel_2));
            mParameters.add(PrintParameterFac.getChartLine(PrintValue.Content_Line_dashed, PrintValue.TextSizeLevel_2));
*/
            mParameters.add(PrintParameterFac.getSpliteCombination(1, 4, PrintValue.TextSizeLevel_1)
                    .addPrintParameter(new SpliteCombination(1, "商品名"))
                    .addPrintParameter(new SpliteCombination(1, "分摊价格"))
                    .addPrintParameter(new SpliteCombination(1, "数量"))
                    .addPrintParameter(new SpliteCombination(1, "价格")));
            mParameters.add(PrintParameterFac.getChartLine(PrintValue.Content_Line_star, PrintValue.TextSizeLevel_3));

            mParameters.add(PrintParameterFac.getSpliteCombination(2, 4, PrintValue.TextSizeLevel_1)
                    .addPrintParameter(new SpliteCombination(1, "3列商品名称很长"))
                    .addPrintParameter(new SpliteCombination(1, "¥121.2"))
                    .addPrintParameter(new SpliteCombination(1, "X22"))
                    .addPrintParameter(new SpliteCombination(1, "¥33.2")));
//            mParameters.add(PrintParameterFac.getChartLine(PrintValue.Content_line_space, PrintValue.TextSizeLevel_2));

            mParameters.add(PrintParameterFac.getSpliteCombination(2, 4, PrintValue.TextSizeLevel_1)
                    .addPrintParameter(new SpliteCombination(1, "3列商品名称3列商品名称很长3列商品名称很长3列商品名称很长很长3列商品名称3列商品名称很长3列商品名称很长3列商品名称很长很长"))
                    .addPrintParameter(new SpliteCombination(1, "¥10.2"))
                    .addPrintParameter(new SpliteCombination(1, "X2"))
                    .addPrintParameter(new SpliteCombination(1, "¥33.2")));
//            mParameters.add(PrintParameterFac.getChartLine(PrintValue.Content_line_space, PrintValue.TextSizeLevel_2));
            mParameters.add(PrintParameterFac.getSpliteCombination(2, 4, PrintValue.TextSizeLevel_1)
                    .addPrintParameter(new SpliteCombination(1, "[水果]在有些情名，"))
                    .addPrintParameter(new SpliteCombination(1, "¥10.2"))
                    .addPrintParameter(new SpliteCombination(1, "X2"))
                    .addPrintParameter(new SpliteCombination(1, "¥33.2")));
            mParameters.add(PrintParameterFac.getSpliteCombination(2, 4, PrintValue.TextSizeLevel_1)
                    .addPrintParameter(new SpliteCombination(1, "[水果]在有情名，"))
                    .addPrintParameter(new SpliteCombination(1, "¥10.2"))
                    .addPrintParameter(new SpliteCombination(1, "X2"))
                    .addPrintParameter(new SpliteCombination(1, "¥33.2")));
            mParameters.add(PrintParameterFac.getSpliteCombination(2, 4, PrintValue.TextSizeLevel_1)
                    .addPrintParameter(new SpliteCombination(1, "[rewqk地跌九号线九"))
                    .addPrintParameter(new SpliteCombination(1, "¥10.2"))
                    .addPrintParameter(new SpliteCombination(1, "X2"))
                    .addPrintParameter(new SpliteCombination(1, "¥33.2")));
//            mParameters.add(PrintParameterFac.getChartLine(PrintValue.Content_line_space, PrintValue.TextSizeLevel_2));
/*
            mParameters.add(PrintParameterFac.getBarCode("123456789012345678", false, 100));
            mParameters.add(PrintParameterFac.getContent("123456789012345678", PrintValue.TextSizeLevel_2, PrintValue.Center));
            mParameters.add(PrintParameterFac.getContent("测试文字排版左", PrintValue.TextSizeLevel_1, PrintValue.Left));
            mParameters.add(PrintParameterFac.getContent("测试文字排版中", PrintValue.TextSizeLevel_1, PrintValue.Center));
            mParameters.add(PrintParameterFac.getContent("测试文字排版右", PrintValue.TextSizeLevel_1, PrintValue.Right));
            mParameters.add(PrintParameterFac.getContent("测试文字排版(TEBMINAL NO): 10300751测试文字排版(TEBMINAL NO):10300751", PrintValue.TextSizeLevel_1, PrintValue.Left));
            mParameters.add(PrintParameterFac.getChartLineFull(PrintValue.TextSizeLevel_1, 3f));
            mParameters.add(PrintParameterFac.getContent("测试文字排版(TEBMINAL NO): 10300751测试文字排版(TEBMINAL NO):10300751", PrintValue.TextSizeLevel_2, PrintValue.Left));
            mParameters.add(PrintParameterFac.getChartLine(PrintValue.Content_Line_dashed, PrintValue.TextSizeLevel_2));
            mParameters.add(PrintParameterFac.getChartLine(PrintValue.Content_Line_full, PrintValue.TextSizeLevel_2));
            mParameters.add(PrintParameterFac.getQRCode("123456789012345678", 200));
            mParameters.add(PrintParameterFac.getChartLine(PrintValue.Content_Line_star, PrintValue.TextSizeLevel_2));
            mParameters.add(PrintParameterFac.getContent("测试文字排版(TEBMINAL NO): 10300751测试文字排版(TEBMINAL NO):10300751", PrintValue.TextSizeLevel_3, PrintValue.Left));
            mParameters.add(PrintParameterFac.getChartLine(PrintValue.Content_line_space, PrintValue.TextSizeLevel_3));
            mParameters.add(PrintParameterFac.getChartLine(PrintValue.Content_Line_dashed, PrintValue.TextSizeLevel_3));
            mParameters.add(PrintParameterFac.getChartLine(PrintValue.Content_Line_full, PrintValue.TextSizeLevel_3));
            mParameters.add(PrintParameterFac.getChartLine(PrintValue.Content_Line_star, PrintValue.TextSizeLevel_3));
            mParameters.add(PrintParameterFac.getContent("测试文字排版(TEBMINAL NO): 10300751测试文字排版(TEBMINAL NO):10300751", PrintValue.TextSizeLevel_5, PrintValue.Left));

            mParameters.add(PrintParameterFac.getContent("测试文字排版(TEBMINAL NO): 10300751测试文字排版(TEBMINAL NO):10300751", PrintValue.TextSizeLevel_4, PrintValue.Left));
            mParameters.add(PrintParameterFac.getLinked("满20减1", "¥-1", PrintValue.TextSizeLevel_2));
            mParameters.add(PrintParameterFac.getLinked("配送费：", "¥1002", PrintValue.TextSizeLevel_3));
            mParameters.add(PrintParameterFac.getLinked("总价：", "¥1002", PrintValue.TextSizeLevel_5));*/
            Bitmap textBitmap = new ImagePHelperV2().StringListtoBitmap(MainActivity.this, mParameters);
            return textBitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
/*

    private Bitmap creatImage() {
        try {
            InputStream ins = getAssets().open("res" + File.separator + "ic_launcher.png");
            Bitmap imageBitmap = BitmapFactory.decodeStream(ins);
            ArrayList<StringBitmapParameter> mParameters = new ArrayList<>();

            mParameters.add(new StringBitmapParameter("商户存根联"));
            mParameters.add(new StringBitmapParameter("用户名称(MERCHANT NAME):来伊份"));
            mParameters.add(new StringBitmapParameter("\n"));
            mParameters.add(new StringBitmapParameter("商户编号(MERCHANT NO): 304301057328106"));
            mParameters.add(new StringBitmapParameter("终端编号(TEBMINAL NO): 00706937"));
            mParameters.add(new StringBitmapParameter("操作员号: 01"));
            mParameters.add(new StringBitmapParameter("卡号(CARD NO)"));
            mParameters.add(new StringBitmapParameter("12345678901212(C)", PrintImageUtils.IS_RIGHT));
            mParameters.add(new StringBitmapParameter("\n"));
            mParameters.add(new StringBitmapParameter("发卡行(ISSUER):来伊份"));
            mParameters.add(new StringBitmapParameter("收单行(ACQUIRER):来伊份"));
            mParameters.add(new StringBitmapParameter("交易类别(TXN TYPE):"));
            mParameters.add(new StringBitmapParameter(" 消费撤销(VOID)"));
            mParameters.add(new StringBitmapParameter("-------------------------"));
            mParameters.add(new StringBitmapParameter("持卡人签名CARD HOLDER SIGNATURE:"));
            mParameters.add(new StringBitmapParameter("\n"));
            mParameters.add(new StringBitmapParameter("\n"));
            mParameters.add(new StringBitmapParameter("\n"));

            mParameters.add(new StringBitmapParameter("来伊份", PrintImageUtils.IS_CENTER, PrintImageUtils.IS_LARGE));
            mParameters.add(new StringBitmapParameter("\n"));
            mParameters.add(new StringBitmapParameter("\n"));
            mParameters.add(new StringBitmapParameter("商户存根联           请妥善保管"));
            mParameters.add(new StringBitmapParameter("-------------------------"));
            mParameters.add(new StringBitmapParameter("用户名称(MERCHANT NAME):"));
            mParameters.add(new StringBitmapParameter("来伊份", PrintImageUtils.IS_RIGHT));
            mParameters.add(new StringBitmapParameter("商户编号(MERCHANT NO):"));
            mParameters.add(new StringBitmapParameter("113320583980037", PrintImageUtils.IS_RIGHT));
            mParameters.add(new StringBitmapParameter("终端编号(TEBMINAL NO): 10300751"));
            mParameters.add(new StringBitmapParameter("终端编号(TEBMINAL NO): 10300751"));
            mParameters.add(new StringBitmapParameter("终端编号(TEBMINAL NO): 10300751"));
            mParameters.add(new StringBitmapParameter("终端编号(TEBMINAL NO): 10300751"));
            mParameters.add(new StringBitmapParameter("终端编号(TEBMINAL NO): 10300751"));
            mParameters.add(new StringBitmapParameter("终端编号(TEBMINAL NO): 10300751"));
            mParameters.add(new StringBitmapParameter("终端编号(TEBMINAL NO): 10300751"));
            mParameters.add(new StringBitmapParameter("终端编号(TEBMINAL NO): 10300751"));
            mParameters.add(new StringBitmapParameter("终端编号(TEBMINAL NO): 10300751"));
            mParameters.add(new StringBitmapParameter("终端编号(TEBMINAL NO): 10300751"));
            mParameters.add(new StringBitmapParameter("终端编号(TEBMINAL NO): 10300751"));
            mParameters.add(new StringBitmapParameter("终端编号(TEBMINAL NO): 10300751"));
            mParameters.add(new StringBitmapParameter("终端编号(TEBMINAL NO): 10300751"));
            mParameters.add(new StringBitmapParameter("终端编号(TEBMINAL NO): 10300751"));
            mParameters.add(new StringBitmapParameter("终端编号(TEBMINAL NO): 10300751"));
            mParameters.add(new StringBitmapParameter("终端编号(TEBMINAL NO): 10300751"));
            mParameters.add(new StringBitmapParameter("终端编号(TEBMINAL NO): 10300751"));
            mParameters.add(new StringBitmapParameter("终端编号(TEBMINAL NO): 10300751"));
            mParameters.add(new StringBitmapParameter("终端编号(TEBMINAL NO): 10300751"));
            mParameters.add(new StringBitmapParameter("终端编号(TEBMINAL NO): 10300751"));
            mParameters.add(new StringBitmapParameter("终端编号(TEBMINAL NO): 10300751"));
            mParameters.add(new StringBitmapParameter("终端编号(TEBMINAL NO): 10300751"));
            mParameters.add(new StringBitmapParameter("操作员号(OPERATOR NO):     01"));
            mParameters.add(new StringBitmapParameter("-------------------------"));
            mParameters.add(new StringBitmapParameter("发卡行(ISSUER)"));
            mParameters.add(new StringBitmapParameter("来伊份", PrintImageUtils.IS_RIGHT));
            mParameters.add(new StringBitmapParameter("收单行(ACQUIRER)"));
            mParameters.add(new StringBitmapParameter("来伊份", PrintImageUtils.IS_RIGHT));
            mParameters.add(new StringBitmapParameter("卡号(CARD NO)"));
            mParameters.add(new StringBitmapParameter("12345678901212(C)", PrintImageUtils.IS_RIGHT, PrintImageUtils.IS_LARGE));
            mParameters.add(new StringBitmapParameter("卡有效期(EXP DATE)     2023/10"));
            mParameters.add(new StringBitmapParameter("交易类型(TXN TYPE)"));
            mParameters.add(new StringBitmapParameter("消费", PrintImageUtils.IS_RIGHT, PrintImageUtils.IS_LARGE));
            mParameters.add(new StringBitmapParameter("-------------------------"));
            mParameters.add(new StringBitmapParameter("交易金额未超过300.00元，无需签名"));

            ArrayList<StringBitmapParameter> mParametersEx = new ArrayList<>();*//**如果是空的列表，也可以传入，会打印空行*//*
            mParametersEx.add(new StringBitmapParameter("\n"));
//            mParametersEx.add(new StringBitmapParameter("\n"));
//            mParametersEx.add(new StringBitmapParameter("\n"));

            Bitmap textBitmap = PrintImageUtils.StringListtoBitmap(MainActivity.this, mParameters);
            Bitmap textBitmap2 = PrintImageUtils.StringListtoBitmap(MainActivity.this, mParametersEx);

            Bitmap mergeBitmap = PrintImageUtils.addBitmapInHead(imageBitmap, textBitmap);

            Bitmap mergeBitmap2 = PrintImageUtils.addBitmapInFoot(mergeBitmap, imageBitmap);
            Bitmap mergeBitmap3 = PrintImageUtils.addBitmapInFoot(mergeBitmap2, textBitmap2);

            Log.e("fmx", "argb_8888 =  " + mergeBitmap3.getHeight() * mergeBitmap3.getWidth() * 32);
            Log.e("fmx", "rgb_565 =  " + mergeBitmap3.getHeight() * mergeBitmap3.getWidth() * 16);
            return mergeBitmap3;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/

}
