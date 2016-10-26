package com.ruan.databasesdk.api;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/1/26.
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    private Context context = null;
    private SQLiteDatabase sqlite = null;
    private static int version = 1;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

    public DatabaseHelper(Context context, String name) {
        this(context, name, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    /**
     * 更新数据库的操作
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != oldVersion) {
            onCreate(db);
        }
    }
}
