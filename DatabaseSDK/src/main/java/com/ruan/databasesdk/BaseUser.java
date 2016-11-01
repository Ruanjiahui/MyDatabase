package com.ruan.databasesdk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.ruan.databasesdk.Operation.ClassHandler;
import com.ruan.databasesdk.Operation.Operation;

import java.util.ArrayList;

/**
 * Created by Ruanjiahui on 2016/10/21.
 * <p/>
 * 数据库各个方法的使用类
 */
public class BaseUser extends Operation {

    private Context context = null;

    public BaseUser(Context context) {
        this.context = context;
    }

    /**
     * 数据库插入的方法
     *
     * 支持对象传参 和 ContentValues
     */
    /************************************************* **********************************************/

    /**
     * 数据库插入数据
     *
     * @param database 数据库的名称
     * @param table    数据库的表名
     * @param object   插入数据的对象
     */
    public long INSERT(String database, String table, Object object) {
        return insert(context, database, table, new MappingClass().ClassToContentValue(object.getClass(), object));
    }

    /**
     * 数据库插入数据
     *
     * @param database      数据库的名称
     * @param table         数据库的表名
     * @param contentValues 插入数据库的ContentValues
     */
    public long INSERT(String database, String table, ContentValues contentValues) {
        return insert(context, database, table, contentValues);
    }

    /**
     * 数据库删除的方法
     */

    /************************************************  ************************************************/

    /**
     * 删除数据库的数据表全部数据
     *
     * @param database 数据库的名称
     * @param table    数据库的表名
     */
    public long DELETE(String database, String table) {
        return delete(context, database, table, null, null);
    }

    /**
     * 删除数据库表的指定的数据
     *
     * @param database    数据库的名称
     * @param table       数据库的表名
     * @param whereclause where后面的指定字段名
     * @param whereargs   where后面的指定字段名的参数
     */
    public long DELETE(String database, String table, String[] whereclause, String[] whereargs) {
        return delete(context, database, table, ClassHandler.ArrayToString(whereclause), whereargs);
    }


    /**
     * 数据库查询数据的方法
     *
     * 支持赋值对象获取数据
     */
    /******************************************  *************************************************/

    /**
     * 获取数据库表的全部内容
     *
     * @param database  数据库的名称
     * @param table     数据库表的名称
     * @param loadClass 存储的对象
     * @return 返回的一个链表对象
     */
    public ArrayList<Object> QUERYArray(String database, String table, Class loadClass) {
        return new MappingClass().CursorToArray(query(context, database, table, null, "", null, "", "", "", ""), loadClass);
    }

    /**
     * 获取数据库表的全部内容
     *
     * @param database  数据库的名称
     * @param table     数据库表的名称
     * @param loadClass 存储的对象
     * @return 返回的一个对象(返回第一个的对象)
     */
    public Object QUERY(String database, String table, Class loadClass) {
        ArrayList<Object> list = new MappingClass().CursorToArray(query(context, database, table, null, "", null, "", "", "", ""), loadClass);
        if (list != null && list.size() > 0)
            return list.get(0);
        return null;
    }

    /**
     * 获取数据库表的指定数据内容
     *
     * @param database    数据库的名称
     * @param table       数据库表的名称
     * @param whereclause where后面指定的字段名
     * @param whereargs   where后面指定的字段名的数值
     * @param loadClass   存储的对象
     * @return 返回一个链表的对象
     */
    public ArrayList<Object> QUERYArray(String database, String table, String[] whereclause, String[] whereargs, Class loadClass) {
        return new MappingClass().CursorToArray(query(context, database, table, null, ClassHandler.ArrayToString(whereclause), whereargs, "", "", "", ""), loadClass);
    }


    /**
     * 获取数据库表的指定数据内容
     *
     * @param database    数据库的名称
     * @param table       数据库表的名称
     * @param whereclause where后面指定的字段名
     * @param whereargs   where后面指定的字段名的数值
     * @param loadClass   存储的对象
     * @return 返回一个对象
     */
    public Object QUERY(String database, String table, String[] whereclause, String[] whereargs, Class loadClass) {
        ArrayList<Object> list = new MappingClass().CursorToArray(query(context, database, table, null, ClassHandler.ArrayToString(whereclause), whereargs, "", "", "", ""), loadClass);
        if (list != null && list.size() > 0)
            return list.get(0);
        return null;
    }


    /**
     * 获取数据库的数据（去重即就是去掉相同的数据）
     *
     * @param database     数据库的名称
     * @param table        数据库表的名称
     * @param distinctType 去重的字段名
     * @param loadClass    存储对象
     * @return 返回一个链表的对象
     */
    public ArrayList<Object> distinctQUERY(String database, String table, String[] distinctType, Class loadClass) {
        return new MappingClass().CursorToArray(distinctQuery(context, database, table, null, "", null, "", "", "", "", distinctType) , loadClass);
    }

    /**
     * 获取数据库的数据这个是满足所有要求的数据库查询，可以自定义的传数据
     *
     * @param database      数据库的名称
     * @param table         数据库表的名称
     * @param colums        获取数据库项名
     * @param selection     where后面指定查询的字段名
     * @param selectionArgs where后面指定查询的字段名值
     * @param groupBy       groupby后面的参数
     * @param having        having后面的参数
     * @param orderBy       orderBy后面的参数
     * @param limit         limit后面的参数
     * @return 返回一个链表的对象
     */
    public ArrayList<Object> QUERYArray(String database, String table, String[] colums, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit, Class loadClass) {
        return new MappingClass().CursorToArray(query(context, database, table, colums, selection, selectionArgs, groupBy, having, orderBy, limit), loadClass);
    }


    /**
     * 修改数据库的数据的方法
     *
     * 支持传参为对象 和 ContentValues
     */


    /*************************************************************         **************************************************************/
    /**
     * 更新数据库的数据 更新全部的数据
     *
     * @param database 数据库的名称
     * @param table    数据库表的名称
     * @param object   更新的对象
     * @return 返回执行状态1为成功
     */
    public long UPDATE(String database, String table, Object object) {
        return update(context, database, table, new MappingClass().ClassToContentValue(object.getClass(), object), "", null);
    }

    /**
     * 更新数据库的数据  更新指定的数据
     *
     * @param database    数据库的名称
     * @param table       数据库表的名称
     * @param whereclause where后面指定的字段名
     * @param whereargs   where后面指定的字段名的值
     * @param object      更新的对象
     * @return 返回执行的状态1为成功
     */
    public long UPDATA(String database, String table, String[] whereclause, String[] whereargs, Object object) {
        return update(context, database, table, new MappingClass().ClassToContentValue(object.getClass(), object), ClassHandler.ArrayToString(whereclause), whereargs);
    }


    /**
     * 自动判断数据库是更新还是插入的操作
     * 如果数据库的数据没有该条数据则执行 插入，
     * 如果有该条数据则进行更新操作
     *
     * @param database    数据库的名称
     * @param table       数据库表的名称
     * @param whereclause where后面的字段名
     * @param whereargs   where后面的字段名的值
     * @param object      更新的对象
     * @return 返回的执行状态  1 为更新成功
     */
    public long INSERTorUPDATE(String database, String table, String whereclause[], String[] whereargs, Object object) {
        //如果isEmptry返回来的是true 说明没有该数据这个时候应该是插入数据
        if (isEmptry(database, table, whereclause, whereargs))
            return insert(context, database, table, new MappingClass().ClassToContentValue(object.getClass(), object));
        return update(context, database, table, new MappingClass().ClassToContentValue(object.getClass(), object), ClassHandler.ArrayToString(whereclause), whereargs);
    }


    /**
     * 自动判断数据库是更新还是插入的操作
     * 如果数据库的数据没有该条数据则执行 插入，
     * 如果有该条数据则进行更新操作
     *
     * @param database      数据库的名称
     * @param table         数据库表的名称
     * @param whereclause   where后面的字段名
     * @param whereargs     where后面的字段名的值
     * @param contentValues 插入数据或者更新数据的对象
     * @return 返回的执行状态  1 为更新成功
     */
    public long INSERTorUPDATE(String database, String table, String whereclause[], String[] whereargs, ContentValues contentValues) {
        //如果isEmptry返回来的是true 说明没有该数据这个时候应该是插入数据
        if (isEmptry(database, table, whereclause, whereargs))
            return insert(context, database, table, contentValues);
        return update(context, database, table, contentValues, ClassHandler.ArrayToString(whereclause), whereargs);
    }


    /**
     * 判断该数据库表的是否存在此条数据
     *
     * @param database    数据库的名称
     * @param table       数据库表的名称
     * @param whereclause where后面指定的字段名
     * @param whereargs   where后面指定的字段名的值
     * @return 返回boolean  true 为空， false 不为空
     */
    public boolean isEmptry(String database, String table, String[] whereclause, String[] whereargs) {
        Cursor cursor = query(context, database, table, null, ClassHandler.ArrayToString(whereclause), whereargs, "", "", "", "");
        if (cursor == null || cursor.getCount() == 0)
            return true;
        return false;
    }

}
