package com.wisn.createprinterimage.bb;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.ums.upos.sdk.exception.SdkException;
import com.ums.upos.sdk.system.BaseSystemManager;
import com.ums.upos.sdk.system.OnServiceStatusListener;

import com.ums.upos.sdk.exception.CallServiceException;
import com.ums.upos.sdk.printer.OnPrintResultListener;
import com.ums.upos.sdk.printer.PrinterManager;

/**
 * Created by Wisn on 2019/4/17 下午2:30.
 */
public class PrintTest {
    public static final String TAG = "PrintTest";

    /**
     * 设备硬件登录
     */
    public void deviceServiceLogin( Application application) {
        try {
            BaseSystemManager.getInstance().deviceServiceLogin(
                    application, null, "99999999",//设备ID，填写任意8位数字
                    new OnServiceStatusListener() {
                        @Override
                        public void onStatus(int resCode) {//arg0具体可参考常量类ServiceResult
                            if (ServiceResult.Success == resCode || ServiceResult.LOGIN_SUCCESS_NOT_EMV_FILE == resCode || ServiceResult.LOGIN_SUCCESS_HAS_LOGINED == resCode) {
                                Log.d(TAG, "设备硬件登录成功" + resCode);
                                startPrint();
                            }
                        }
                    });
        } catch (SdkException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设备硬件登出
     */
    private void deviceServiceLogout() {
        try {
            BaseSystemManager.getInstance().deviceServiceLogout();
        } catch (SdkException e) {
            e.printStackTrace();
        }
    }

    /**
     * 组织打印脚本
     * 此脚本是银商标准银行卡签购单所使用的格式，仅供参考。若不习惯使用PrintScriptUtil相关方法组脚本，亦可自行组织脚本：
     * String selfDefinedText = "!hz n\n!asc n\n!gray 8\n!yspace 6\n*image c 284*81 data:base64;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\n*text l  \n*text l 商户名称:银行卡商户\n*text l 商户编号:898330160120021\n*text l 终端编号:05315830\n*text l 班次号:201705070001 操作员号:001\n*text l 发卡行:浦发银行  收单行:银联商务\n*text l 有效期:30/11\n*text l 卡号:(借)\n!hz l\n!asc l\n!gray 8\n*text l 6225 2101 1521 2491/S\n*text l 交易类型:预授权\n!hz n\n!asc n\n!gray 8\n*text l 批次号:001281\n*text l 凭证号:002135  授权码:118525\n*text l 参考号:203009363026\n*text l 交易日期:2017/05/07 20:30:09\n*text l 金额:\n!hz l\n!asc l\n!gray 8\n*text l 　　RMB:0.01\n!hz n\n!asc n\n!gray 8\n*text l 备 注: 重打印 \n!hz s\n!asc s\n!gray 5\n*text c ...............................................\n!hz n\n!asc n\n!gray 8\n*text l 持卡人签名:\n*text l  \n*text l  \n!hz sn\n!asc sn\n!gray 5\n*text c ...............................................\n!hz s\n!asc s\n!gray 5\n*text l 本人确认以上交易，同意将其计入本卡账户\n*text l I ACKNOWLEDGE SATISFACTORY RECEIPT OF RELATIVE GOODS/SERVICES\n!hz sn\n!asc sn\n!gray 5\n*text c ...............................................\n*image c 200*200 data:base64;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\n*text l  \n*text r 商户存根\n*text c ...............................................\n*text l 服务热线:95534      LANDI-APOS A8-V3.1.202\n*text c   ";
     * 如上selfDefinedText是自行组织的脚本，亦可正确打印。
     * 如还有格式问题请阅读《银联商务智能终端产品第三方应用调用接口规范V1.6_SDK.doc》文档附录
     **/
    private String buildText() {
        String imageData = "data:base64;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";
        PrintScriptUtil psu = new PrintScriptUtil();
        psu.setNextFormat(ScriptConstant.NORMAL, ScriptConstant.NORMAL)
                .addImage(ScriptConstant.CENTER, "284*81", imageData)
                .addText(ScriptConstant.LEFT, "商户名称:银行卡商户")
                .addText(ScriptConstant.LEFT, "商户编号:898330160120021商户编号:898330160120021商户编号:898330160120021")
                .addText(ScriptConstant.LEFT, "终端编号:05315830")
                .addText(ScriptConstant.LEFT, "班次号:201705070001 操作员号:001")
                .addText(ScriptConstant.LEFT, "发卡行:浦发银行  收单行:银联商务")
                .addText(ScriptConstant.LEFT, "有效期:30/11")
                .addText(ScriptConstant.LEFT, "卡号:(借)")
                .setNextFormat(ScriptConstant.LARGE, ScriptConstant.LARGE)
                .addText(ScriptConstant.LEFT, "6225 2101 1521 2491/S")
                .addText(ScriptConstant.LEFT, "交易类型:预授权")
                .setNextFormat(ScriptConstant.NORMAL, ScriptConstant.NORMAL)
                .addText(ScriptConstant.LEFT, "批次号:001281")
                .addText(ScriptConstant.LEFT, "凭证号:002135  授权码:118525")
                .addText(ScriptConstant.LEFT, "参考号:203009363026")
                .addText(ScriptConstant.LEFT, "交易日期:2017/05/07 20:30:09")
                .addText(ScriptConstant.LEFT, "金额:")
                .setNextFormat(ScriptConstant.LARGE, ScriptConstant.LARGE, "8", "6")
                .addText(ScriptConstant.LEFT, "    RMB:0.01")
                .setNextFormat(ScriptConstant.NORMAL, ScriptConstant.LARGE)
                .addText(ScriptConstant.LEFT, "备 注: 重打印 ")
                .setNextFormat(ScriptConstant.SMALL, ScriptConstant.SMALL)
                .addText(ScriptConstant.CENTER, "...............................................")
                .setNextFormat(ScriptConstant.NORMAL, ScriptConstant.NORMAL)
                .addText(ScriptConstant.LEFT, "持卡人签名: ")
                .addText(ScriptConstant.LEFT, " ")
                .addText(ScriptConstant.LEFT, " ")
                .setNextFormat(ScriptConstant.SMALL_NORMAL, ScriptConstant.SMALL_NORMAL)
                .addText(ScriptConstant.CENTER, "...............................................")
                .setNextFormat(ScriptConstant.SMALL, ScriptConstant.SMALL)
                .addText(ScriptConstant.LEFT, "本人确认以上交易，同意将其计入本卡账户")
                .addText(ScriptConstant.LEFT, "I ACKNOWLEDGE SATISFACTORY RECEIPT OF RELATIVE GOODS/SERVICES");
        return psu.getString();
    }

    public void startPrint() {
        PrinterManager manage = new PrinterManager();
        String text = buildText();
        try {
            manage.initPrinter();
            manage.setPrnScript(text, "384");
            manage.startPrint(new OnPrintResultListener() {
                @Override
                public void onPrintResult(int resCode) {//arg0具体可参考常量类ServiceResult
                    Log.d(TAG, "打印结果:" + resCode);
                    //打印完成主动登出，避免持续占用设备硬件
                    deviceServiceLogout();
                }
            });
        } catch (CallServiceException e) {
            e.printStackTrace();
        } catch (SdkException e) {
            e.printStackTrace();
        }
    }

}
