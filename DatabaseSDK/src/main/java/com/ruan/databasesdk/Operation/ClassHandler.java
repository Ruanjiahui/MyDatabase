package com.ruan.databasesdk.Operation;

import android.content.ContentValues;
import android.database.Cursor;

import com.ruan.databasesdk.DataType;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/21.
 */
public class ClassHandler {


    /**
     * 将两个Fields的数组去重
     *
     * @param fields
     * @param declaredFields
     * @return
     */
    public static ArrayList<Field> FieldsToOne(Field[] fields, Field[] declaredFields) {
        ArrayList<Field> list = new ArrayList<>();

        // 首先将public 的属性全部添加到list里面之后进行去重
        for (Field field : fields)
            list.add(field);
        for (Field declaredField : declaredFields) {
            if (isSame(list, declaredField))
                list.add(declaredField);
        }
        return list;
    }

    /**
     * 判断两个属性是否一样
     *
     * @param list
     * @param declaredField
     * @return
     */
    private static boolean isSame(ArrayList<Field> list, Field declaredField) {
        for (Field field : list) {
            if (field.getName().equals(declaredField.getName()))
                return false;
        }
        return true;
    }

    /**
     * 将Object转化成ContentValues；
     *
     * @param list
     * @param o
     * @return
     */
    public static ContentValues ObjectToContentValues(ArrayList<Field> list, Object o) {
        ContentValues contentValues = new ContentValues();
        try {
            for (Field field : list) {
                field.setAccessible(true);

                //如果属性的修饰符是private 和 public
                if (DataType.PRIVATE.equals(Modifier.toString(field.getModifiers())) || DataType.PUBLIC.equals(Modifier.toString(field.getModifiers()))) {
                    switch (field.getType().toString()) {
                        case DataType.STRING:
                            contentValues.put(field.getName(), (String) field.get(o));
                            break;
                        case DataType.INTEGER:
                            contentValues.put(field.getName(), (Integer) field.get(o));
                            break;
                        case DataType.INT:
                            contentValues.put(field.getName(), (Integer) field.get(o));
                            break;
                        case DataType.ClassDOUBLE:
                            contentValues.put(field.getName(), (Double) field.get(o));
                            break;
                        case DataType.ClassSHORT:
                            contentValues.put(field.getName(), (Short) field.get(o));
                            break;
                        case DataType.ClassLONG:
                            contentValues.put(field.getName(), (Long) field.get(o));
                            break;
                        case DataType.ClassBOOLEAN:
                            contentValues.put(field.getName(), (Boolean) field.get(o));
                            break;
                        case DataType.ClassFLOAT:
                            contentValues.put(field.getName(), (Float) field.get(o));
                            break;
                        case DataType.ClassBYTE:
                            contentValues.put(field.getName(), (Byte) field.get(o));
                            break;
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return contentValues;
    }

    /**
     * 通过将Cursor 转化成 对象
     *
     * @param list
     * @param cursor
     * @param colums
     * @param loadClass
     * @return
     */
    public static Object CursorToObject(ArrayList<Field> list, Cursor cursor, String[] colums, Class loadClass) {
        Object object = null;
        try {
            object = loadClass.newInstance();
            for (String columName : colums) {
                for (Field field : list) {
                    //设置可以访问
                    field.setAccessible(true);

                    //如果属性的修饰符是private 和 public
                    if (DataType.PRIVATE.equals(Modifier.toString(field.getModifiers())) || DataType.PUBLIC.equals(Modifier.toString(field.getModifiers()))) {
                        //如果数据库的字段名和类的属性名称一样就说明是同一个变量
                        if (field.getName().equals(columName)) {
                            //将数据库的数据设置给类的属性
                            switch (field.getType().toString()) {
                                case DataType.STRING:
                                    field.set(object, cursor.getString(cursor.getColumnIndex(columName)));
                                    break;
                                case DataType.INTEGER:
                                    field.set(object, cursor.getInt(cursor.getColumnIndex(columName)));
                                    break;
                                case DataType.INT:
                                    field.set(object, cursor.getInt(cursor.getColumnIndex(columName)));
                                    break;
                                case DataType.ClassDOUBLE:
                                    field.set(object, cursor.getDouble(cursor.getColumnIndex(columName)));
                                    break;
                                case DataType.ClassSHORT:
                                    field.set(object, cursor.getShort(cursor.getColumnIndex(columName)));
                                    break;
                                case DataType.ClassLONG:
                                    field.set(object, cursor.getLong(cursor.getColumnIndex(columName)));
                                    break;
                                case DataType.ClassFLOAT:
                                    field.set(object, cursor.getFloat(cursor.getColumnIndex(columName)));
                                    break;
                            }
                        }
                        break;
                    }
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 将名称数据转化成字符串
     *
     * @param name
     * @return
     */
    public static String ArrayToString(String[] name) {
        String wherearg = "";
        for (int i = 0; i < name.length; i++) {
            wherearg += name[i] + "=?";
            if (i != name.length - 1)
                wherearg += "&";
        }
        return wherearg;
    }


    /**
     * 将名称数据转化成字符串
     *
     * @param distinctType
     * @return
     */
    public static String distinctArrayToString(String[] distinctType) {
        String wherearg = "";
        for (int i = 0; i < distinctType.length; i++) {
            wherearg += distinctType[i];
            if (i != distinctType.length - 1)
                wherearg += ",";
        }
        return wherearg;
    }
}
