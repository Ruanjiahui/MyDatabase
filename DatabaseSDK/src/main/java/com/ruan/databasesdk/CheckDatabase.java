package com.ruan.databasesdk;

import android.content.Context;

import com.ruan.databasesdk.Operation.GetDatabaseData;
import com.ruan.databasesdk.Operation.CreateTable;
import com.ruan.databasesdk.api.Establish;

/**
 * Created by Administrator on 2016/3/17.
 */
public class CheckDatabase {

    private static GetDatabaseData getDatabaseData = new GetDatabaseData();
    /**
     * 检查数据库的表是否存在
     * 返回类型为boolean类型 <p>true<p/>说明有这个表存在
     * <p>false<p/>没有这个表存在
     * 一般不推荐使用这个方法
     *
     * @param context
     * @param database  数据库的名称
     * @param tablename 数据库的表名
     * @return 返回只是boolen
     */
    @Deprecated
    public static boolean CheckData(Context context, String database, String tablename) {
        if (!getDatabaseData.QueryTable(context, database, tablename))
            return false;
        return true;
    }

    /**
     * 检查数据库表是否存在
     * 这种方法唯一和上面的方法不一样的差别就是这个方法可以判断就是如果没有该表的话就会自动创建表不用手动的创建
     * 提供的接口就是获取用户提供创建表的结构，用户在接口实现方法上面传入表的结构这个方法就会自动执行创建表的操作
     *
     * @param context
     * @param database      数据库的名称
     * @param tablename     数据库表的名称
     * @param checkDatabase 数据库检查的接口
     */
    @Deprecated
    public static void CheckData(Context context, String database, String tablename, Database.Check checkDatabase) {
        if (!CheckData(context, database, tablename)) {
            Establish map = checkDatabase.CreateTable(database , tablename , false);
            //判断如果有数据则自动创建表
            if (map != null)
                CreateTable(context , database , tablename , map);
            return;
        }
        checkDatabase.CreateTable(database , tablename , true);
    }


    /**
     * 检查数据库表是否存在  (这个方法创建接受的对象)
     * 这种方法唯一和上面的方法不一样的差别就是这个方法可以判断就是如果没有该表的话就会自动创建表不用手动的创建
     * 提供的接口就是获取用户提供创建表的结构，用户在接口实现方法上面传入表的结构这个方法就会自动执行创建表的操作
     *
     * @param context
     * @param database      数据库的名称
     * @param tablename     数据库表的名称
     * @param databaseCallback 数据库检查的接口
     */
    public static void CheckData(Context context, String database, String tablename, DatabaseCallback databaseCallback) {
        if (!CheckData(context, database, tablename)) {
            Object object = databaseCallback.CreateTable(database , tablename , false);
            //判断如果有数据则自动创建表
            if (object != null)
                CreateTable(context , database , tablename , object);
            return;
        }
        databaseCallback.CreateTable(database , tablename , true);
    }


    /**
     * 根据数据创建表
     * @param map
     */
    private static void CreateTable(Context context , String database, String tablename , Establish map){
        CreateTable.TABLE(context, database, tablename, map, "", "");
    }


    /**
     * 根据数据创建表
     * @param object
     */
    private static void CreateTable(Context context , String database, String tablename , Object object){
        CreateTable.TABLE(context, database, tablename, object, "", "");
    }
}
