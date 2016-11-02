package com.ruan.databasesdk;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.ruan.databasesdk.Operation.ClassHandler;
import com.ruan.databasesdk.api.LoadClass;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by Ruanjiahui on 2016/10/21.
 * <p/>
 * <p/>
 * 将对象一一利用反射机制将对象转化成自己需要的东西
 */
public class MappingClass extends LoadClass {
    /**
     * 将数据库Cursor游标封装成对象(这个获取对象的属性包括父类)
     *
     * @param cursor    数据库的游标（保存数据库的数据）
     * @param loadclass 封装的类的对象
     * @return
     */
    @Override
    protected ArrayList<Object> AnalysisCursors(Cursor cursor, Class loadclass) {


        return null;
    }

    /**
     * 将数据库Cursor游标封装成对象(这个获取对象的属性不包括父类)
     *
     * @param cursor    数据库的游标（保存数据库的数据）
     * @param loadclass 封装的类的对象
     * @return
     */
    @Override
    protected ArrayList<Object> AnalysisCursor(Cursor cursor, Class loadclass) {
        return null;
    }

    /**
     * 将数据库的Cursor转成对象列表
     *
     * @param cursor
     * @param loadClass
     * @return
     */
    @Override
    protected ArrayList<Object> CursorToArray(Cursor cursor, Class loadClass) {
        ArrayList<Object> arrayList = new ArrayList<>();
        Field[] fields = loadClass.getFields();
        Field[] declaredFields = loadClass.getDeclaredFields();

        ArrayList<Field> list = ClassHandler.FieldsToOne(fields, declaredFields);

        Log.e("System.out" , cursor.getColumnCount() + "---");
        if (cursor.getColumnNames() != null && cursor.getColumnNames().length > 0) {
            String[] colums = cursor.getColumnNames();

            Log.e("System.out" , Arrays.toString(colums) + "---");
            while (cursor.moveToNext()) {
                arrayList.add(ClassHandler.CursorToObject(list, cursor, colums, loadClass));
            }
        }

        return arrayList;
    }

    /**
     * 将对象的数据封装成可以插进数据库的数据(包括父类)
     *
     * @param loadClass 封装的类对象
     * @param objects   类对象实体类
     * @return
     */
    @Override
    protected ContentValues getContentValues(Class loadClass, Object objects) {

        return null;
    }

    /**
     * 将对象的数据封装成可以插进数据库的数据(包括父类)
     *
     * @param loadClass 封装的类对象
     * @param objects   类对象实体类
     * @param colums
     * @return
     */
    @Override
    protected ContentValues getContentValues(Class loadClass, Object objects, String[] colums) {
        return null;
    }

    /**
     * Class对象转ContentValues
     *
     * @param loadClass
     * @param objects
     * @return
     */
    @Override
    protected ContentValues ClassToContentValue(Class loadClass, Object objects) {

        Field[] fields = loadClass.getFields();
        Field[] declaredFields = loadClass.getDeclaredFields();

        ArrayList<Field> list = ClassHandler.FieldsToOne(fields, declaredFields);

        ContentValues contentValues = ClassHandler.ObjectToContentValues(list, objects);

        return contentValues;
    }

    /**
     * 将对象的数据封装成可以插进数据库的数据(不包括父类)
     *
     * @param loadClass 封装的类对象
     * @param objects   类对象实体类
     * @return
     */
    @Override
    protected ContentValues getContentValue(Class loadClass, Object objects) {
        return null;
    }

    /**
     * 将Map集合解析封装成对象
     *
     * @param map
     * @param loadClass
     * @return
     */
    @Override
    protected ArrayList<Object> AnalysisMap(ArrayList<Map<Object, Object>> map, Class loadClass) {
        return null;
    }
}
