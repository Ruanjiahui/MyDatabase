package com.ruan.databasesdk.Operation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ruan.databasesdk.api.DatabaseHelper;
import com.ruan.databasesdk.api.Establish;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/29.
 */
public class CreateTable {

    private static DatabaseHelper databaseHelper = null;
    private static SQLiteDatabase sqLiteDatabase = null;
    private static ArrayList<String> key = null;
    private static String db = "";

    private static String sql = "";
    private static String content = "";

    public static DatabaseHelper getInstance(Context context, String db_name) {
        if (!db.equals(db_name))
            databaseHelper = null;

        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context, db_name);
            synchronized (databaseHelper){}
        }
        return databaseHelper;
    }

    /**
     * 创建表的方法
     *
     * @param context
     * @param db_name     数据库名字
     * @param Table_name  表的名字
     * @param establish   封装数据的对象
     * @param auto_key    自动增长的语句如果没有则为空
     * @param primary_key 主键没有则为空
     */
    public static void TABLE(Context context, String db_name, String Table_name, Establish establish, String auto_key, String primary_key) {
        DatabaseHelper databaseHelper = getInstance(context, db_name);
        sqLiteDatabase = databaseHelper.getWritableDatabase();

        key = establish.getKey();

        content = "";
        content = getContent(key, establish, auto_key, primary_key);

        sql = "CREATE TABLE " + Table_name + "(" + content + ")";

        try {
            sqLiteDatabase.execSQL(sql);
        } catch (Exception e) {
            Log.e("Activity", "创建表不成功");
        }
    }

    /**
     * 将封装在establish这个对象的数据解析出来
     *
     * @param list        创建表的字段名
     * @param establish   创建表的字段参数
     * @param auto_key    创建表的自动增长
     * @param primary_key 创建表的主键
     * @return
     */
    private static String getContent(ArrayList<String> list, Establish establish, String auto_key, String primary_key) {
        for (int i = 0; i < list.size(); i++) {
            content += list.get(i) + " " + establish.get(list.get(i));
            if (list.get(i).equals(primary_key))
                content += " primary key";
            if (list.get(i).equals(auto_key)) {
                content += " auto_increment";
            }
            if (i != list.size() - 1)
                content += ",";
        }
        return content;
    }
}
