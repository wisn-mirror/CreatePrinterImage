package com.wisn.createprinterimage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button imageBtn;
    private ImageView mView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageBtn = (Button) findViewById(R.id.p_image);
        mView = (ImageView) findViewById(R.id.s_image);
        imageBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.p_image:
                ImageTask mImageTask = new ImageTask();
                mImageTask.execute("");
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
            imageBtn.setText("生成");
            mView.setImageBitmap(bitmap);
        }
    }


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

            ArrayList<StringBitmapParameter> mParametersEx = new ArrayList<>();/**如果是空的列表，也可以传入，会打印空行*/
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
    }
    
}
