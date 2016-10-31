package com.ruan.databasesdk.Operation;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.ruan.databasesdk.api.DataType;

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
                        case DataType.BOOLEAN:
                            contentValues.put(field.getName(), (boolean) field.get(o));
                            break;
                        case DataType.SHORT:
                            contentValues.put(field.getName(), (short) field.get(o));
                            break;
                        case DataType.DOUBLE:
                            contentValues.put(field.getName(), (double) field.get(o));
                            break;
                        case DataType.FLOAT:
                            contentValues.put(field.getName(), (float) field.get(o));
                            break;
                        case DataType.LONG:
                            contentValues.put(field.getName(), (long) field.get(o));
                            break;
                        case DataType.BYTE:
                            contentValues.put(field.getName(), (byte) field.get(o));
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
                            case DataType.BOOLEAN:
                                if (cursor.getInt(cursor.getColumnIndex(columName)) == 0)
                                    field.set(object, false);
                                else
                                    field.set(object, true);
                                break;
                            case DataType.SHORT:
                                field.set(object, cursor.getInt(cursor.getColumnIndex(columName)));
                                break;
                            case DataType.DOUBLE:
                                field.set(object, cursor.getDouble(cursor.getColumnIndex(columName)));
                                break;
                            case DataType.FLOAT:
                                field.set(object, cursor.getFloat(cursor.getColumnIndex(columName)));
                                break;
                            case DataType.LONG:
                                field.set(object, cursor.getLong(cursor.getColumnIndex(columName)));
                                break;
                            case DataType.BYTE:
                                field.set(object, cursor.getString(cursor.getColumnIndex(columName)));
                                break;
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
        if (name != null && name.length > 0) {
            for (int i = 0; i < name.length; i++) {
                wherearg += name[i] + "=?";
                if (i != name.length - 1)
                    wherearg += " and ";
            }
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

    /**
     * 通过对象Object 获取private获取public每个属性的类型
     *
     * @param object
     * @return
     */
    public static ArrayList<String> ObjectToArraryName(Object object) {
        ArrayList<String> list = new ArrayList<>();

        Class loadClass = object.getClass();

        Field[] fields = loadClass.getFields();
        Field[] declaredFields = loadClass.getDeclaredFields();

        ArrayList<Field> fieldList = ClassHandler.FieldsToOne(fields, declaredFields);

        for (Field field : fieldList) {
            field.setAccessible(true);

            //如果属性的修饰符是private 和 public
            if (DataType.PRIVATE.equals(Modifier.toString(field.getModifiers())) || DataType.PUBLIC.equals(Modifier.toString(field.getModifiers()))) {
                Log.e("Ruan", field.getType().toString());
                switch (field.getType().toString()) {
                    case DataType.STRING:
                        list.add("varchar(255)");
                        break;
                    case DataType.INTEGER:
                        list.add("integer");
                        break;
                    case DataType.INT:
                        list.add("integer");
                        break;
                    case DataType.ClassDOUBLE:
                        list.add("double");
                        break;
                    case DataType.ClassSHORT:
                        //sqlite不支持short类型使用int代替
                        list.add("integer");
                        break;
                    case DataType.ClassLONG:
                        //sqlite不支持long类型使用int代替
                        list.add("integer");
                        break;
                    case DataType.ClassBOOLEAN:
                        //sqlite不支持boolean类型使用int代替
                        list.add("integer");
                        break;
                    case DataType.ClassFLOAT:
                        list.add("float");
                        break;
                    case DataType.ClassBYTE:
                        //sqlite不支持byte类型使用varchar代替
                        list.add("varchar(255)");
                        break;
                    case DataType.BOOLEAN:
                        list.add("integer");
                        break;
                    case DataType.SHORT:
                        list.add("integer");
                        break;
                    case DataType.DOUBLE:
                        list.add("double");
                        break;
                    case DataType.FLOAT:
                        list.add("float");
                        break;
                    case DataType.LONG:
                        list.add("integer");
                        break;
                    case DataType.BYTE:
                        list.add("varchar(255)");
                        break;
                }
            }
        }
        return list;
    }

    /**
     * 通过对象Object 获取属性名称
     *
     * @param object
     * @return
     */
    public static ArrayList<String> ObjectToArraryContent(Object object) {
        ArrayList<String> list = new ArrayList<>();

        Class loadClass = object.getClass();

        Log.e("Ruan", loadClass.getName());

        Field[] fields = loadClass.getFields();
        Field[] declaredFields = loadClass.getDeclaredFields();

        ArrayList<Field> fieldList = ClassHandler.FieldsToOne(fields, declaredFields);

        for (Field field : fieldList) {
            field.setAccessible(true);

            //如果属性的修饰符是private 和 public
            if (DataType.PRIVATE.equals(Modifier.toString(field.getModifiers())) || DataType.PUBLIC.equals(Modifier.toString(field.getModifiers()))) {
                list.add(field.getName());
            }
        }
        return list;
    }
}
