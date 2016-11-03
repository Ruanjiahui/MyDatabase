package com.ruan.databasesdk.Operation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.ruan.databasesdk.api.DatabaseHelper;

/**
 * Created by Administrator on 2016/2/29.
 * <p/>
 * 该表用于数据库操作的类
 */
public class Operation {

    private static SQLiteDatabase sqLiteDatabase = null;
    private static DatabaseHelper databaseHelper = null;

    /**
     * 添加数据库数据
     *
     * @param context
     * @param db
     * @param table
     * @param contentValues
     */
    protected long insert(Context context, String db, String table, ContentValues contentValues) {
        databaseHelper = CreateTable.getInstance(context, db);
        sqLiteDatabase = databaseHelper.getWritableDatabase();

        return sqLiteDatabase.insert(table, null, contentValues);
    }

    /**
     * 更新数据库数据
     *
     * @param context
     * @param db
     * @param table
     * @param contentValues
     * @param whereclause
     * @param whereargs
     */
    protected int update(Context context, String db, String table, ContentValues contentValues, String whereclause, String[] whereargs) {
        databaseHelper = CreateTable.getInstance(context, db);
        sqLiteDatabase = databaseHelper.getWritableDatabase();

        return sqLiteDatabase.update(table, contentValues, whereclause, whereargs);
    }

    /**
     * 查询数据库
     *
     * @param context
     * @param db
     * @param Table_Name
     * @param colums        获取数据的列名称
     * @param selection     where后面的内容
     * @param selectionArgs where后面内容的参数
     * @param groupBy       GROUP BY后面的字符串
     * @param having        HAVING后面的字符串
     * @param orderBy       ORDER BY后面的字符串
     * @param limit         返回的数量
     * @return
     */
    protected Cursor query(Context context, String db, String Table_Name, String[] colums, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        databaseHelper = CreateTable.getInstance(context, db);
        sqLiteDatabase = databaseHelper.getReadableDatabase();

        try {
            return sqLiteDatabase.query(Table_Name, colums, selection, selectionArgs, groupBy, having, orderBy, limit);
        } catch (Exception e) {
            return null;
        }

    }

//    /**
//     * 查询去重数据库
//     *
//     * @param context
//     * @param db
//     * @param Table_Name
//     * @param colums        获取数据的列名称
//     * @param selection     where后面的内容
//     * @param selectionArgs where后面内容的参数
//     * @param groupBy       GROUP BY后面的字符串
//     * @param having        HAVING后面的字符串
//     * @param orderBy       ORDER BY后面的字符串
//     * @param limit         返回的数量
//     * @param distinct      是否去重
//     * @return
//     */
//    protected Cursor distinctQuery(Context context, String db, String Table_Name, String[] colums, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit, boolean distinct) {
//        databaseHelper = CreateTable.getInstance(context, db);
//        sqLiteDatabase = databaseHelper.getReadableDatabase();
//        try {
//            String distincts = "";
//            if (distinctType != null && distinctType.length > 0) {
//                distincts += distinctType[0];
//                for (int i = 1; i < distinctType.length; i++) {
//                    distincts += "," + distinctType[i];
//                }
//            }
//            return sqLiteDatabase.query(Table_Name, colums, selection, selectionArgs, groupBy, having, distincts, limit);
//        } catch (Exception e) {
//            return null;
//        }
//    }

    /**
     * 查询去重数据库
     *
     * @param context
     * @param db
     * @param Table_Name
     * @param colums        获取数据的列名称
     * @param selection     where后面的内容
     * @param selectionArgs where后面内容的参数
     * @param groupBy       GROUP BY后面的字符串
     * @param having        HAVING后面的字符串
     * @param orderBy       ORDER BY后面的字符串
     * @param limit         返回的数量
     * @return
     */
    protected Cursor distinctQuery(Context context, String db, String Table_Name, String[] colums, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit, String[] distinctType) {
        databaseHelper = CreateTable.getInstance(context, db);
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        try {
            String distincts = "";
            if (distinctType != null && distinctType.length > 0) {
                distincts += distinctType[0];
                for (int i = 1; i < distinctType.length; i++) {
                    distincts += "," + distinctType[i];
                }
            }
            Log.e("System.out", distincts);
            return sqLiteDatabase.query(Table_Name, colums, selection, selectionArgs, distincts, having, orderBy, limit);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 删除表数据和删除但条数据
     *
     * @param context
     * @param db
     * @param Table_Name
     * @param whereclause
     * @param whereargs
     */

    protected int delete(Context context, String db, String Table_Name, String whereclause, String[] whereargs) {
        databaseHelper = CreateTable.getInstance(context, db);
        sqLiteDatabase = databaseHelper.getWritableDatabase();

        return sqLiteDatabase.delete(Table_Name, whereclause, whereargs);
    }

    /**
     * 给表增加字段
     *
     * @param context
     * @param db
     * @param Table_Name
     * @param value
     */
    protected void onUpgrade(Context context, String db, String Table_Name, String value) {
        databaseHelper = CreateTable.getInstance(context, db);
        sqLiteDatabase = databaseHelper.getWritableDatabase();

        sqLiteDatabase.execSQL("alter table " + Table_Name + " add column " + value);
    }


    /**
     * 判断有没有这个表
     *
     * @param context
     * @param db
     * @param table
     * @return
     */
    protected boolean TableVisiable(Context context, String db, String table) {
        //这句的意思就是"select count(*) from table"
        Cursor cursor = query(context, db, table, new String[]{"count(*)"}, null, null, null, null, null, null);
        //循环读取cursor的数据
        if (cursor.moveToNext()) {
            //如果数据为0则视为没有该表
            if (cursor.getInt(0) == 0) {
                return false;
            }
            //否则则有该表
            return true;
        }
        return false;
    }

    /**
     * 判断表有没有这条数据
     *
     * @param context
     * @param db
     * @param table
     * @param key
     * @param value
     * @return
     */
    protected boolean DataVisiable(Context context, String db, String table, String key, String[] value) {
        //这句的意思就是"select count(*) from table"
        Cursor cursor = query(context, db, table, new String[]{"count(*)"}, key + "=?", value, null, null, null, null);
        //循环读取cursor的数据
        if (cursor.moveToNext()) {
            //如果数据为0则视为没有该表
            if (cursor.getInt(0) == 0) {
                return false;
            }
            //否则则有该表
            return true;
        }
        return false;
    }
}
