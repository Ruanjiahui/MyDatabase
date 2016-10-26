package com.ruan.databasesdk.Crash;

import android.util.Log;

/**
 * Created by Administrator on 2016/8/12.
 */
public class LogException {

    public static String Top = "";

    public static void ThrowRunTime(String msg) {
        Log.e(Top , msg , new Throwable());
    }


}
