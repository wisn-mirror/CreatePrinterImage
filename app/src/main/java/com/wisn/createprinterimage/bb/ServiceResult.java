package com.wisn.createprinterimage.bb;

public class ServiceResult {
	public final static int Success = 0;
	public final static int Fail = -1;
	public final static int Param_In_Invalid = -2;
	public final static int TimeOut = -3;
	
	//---- device login error ----
	/** 设备未登入 */
	public final static int Device_Not_Ready = -4;	
	/** 登录成功且有EMV文件*/
	public final static int LOGIN_SUCCESS = 0;
	/** 登录失败*/
	public final static int LOGIN_FAIL = 1;
	/** 登录成功且无EMV文件*/
	public final static int LOGIN_SUCCESS_NOT_EMV_FILE = 2;
	/** 已经是登录状态*/
	public final static int LOGIN_SUCCESS_HAS_LOGINED = 100;
	
	//---- Printer Error -----
	public final static int Printer_Base_Error = -1000;
	/** 打印失败*/
	public final static int Printer_Print_Fail = Printer_Base_Error -1;
	/** 设置字符串缓冲失败*/
	public final static int Printer_AddPrnStr_Fail = Printer_Base_Error -2;
	/** 设置图片缓冲失败*/
	public final static int Printer_AddImg_Fail = Printer_Base_Error -3;
	/**打印机忙*/
	public final static int Printer_Busy = Printer_Base_Error - 4;	
	/**打印机缺纸*/
	public final static int Printer_PaperLack = Printer_Base_Error - 5;	
	/**打印数据包格式错*/
	public final static int Printer_Wrong_Package = Printer_Base_Error - 6;	
	/**打印机故障*/
	public final static int Printer_Fault = Printer_Base_Error - 7;	
	/**打印机过热*/
	public final static int Printre_TooHot = Printer_Base_Error - 8;	
	/**打印未完成*/
	public final static int Printer_UnFinished = Printer_Base_Error - 9;	
	/**打印机未装字库*/
	public final static int Printer_NoFontLib = Printer_Base_Error - 10;	
	/**数据包过长*/
	public final static int Printer_OutOfMemory = Printer_Base_Error - 11;
	/**无打印机*/
	public final static int Printer_No_Printer = Printer_Base_Error - 12;	
	/**打印机电量低*/
	public final static int Printer_Low_Power = Printer_Base_Error - 13;
	/**其他异常错误*/
	public final static int Printer_Other_Error = Printer_Base_Error-999;
	
}
