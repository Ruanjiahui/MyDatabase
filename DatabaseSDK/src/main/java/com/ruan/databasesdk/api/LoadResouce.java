package com.ruan.databasesdk.api;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.ruan.databasesdk.api.LoadClass;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/2.
 */
public class LoadResouce extends LoadClass {

    private Context context = null;

    /**
     * 将数据库Cursor游标封装成对象
     *
     * @param cursor    数据库的游标（保存数据库的数据）
     * @param loadclass 封装的类的对象
     * @return
     */
    @Override
    protected ArrayList<Object> AnalysisCursors(Cursor cursor, Class loadclass) {
        ArrayList<Object> list = new ArrayList<>();
        if (cursor != null) {
            //新建链表对象存在数据
            try {
                //获取该类的所有属性
                //包括父类的所有属性
                Field[] fields = loadclass.getFields();
                //将数据库返回的数据
                String[] colum = cursor.getColumnNames();
                Log.d("Ruan", Arrays.toString(colum));
                //将cursor用游标的方式取出来
                while (cursor.moveToNext()) {
                    Object object = loadclass.newInstance();
                    //循环的方法把数据库的列名称给取出来
                    for (String columName : colum) {
                        for (int i = 0; i < fields.length; i++) {
                            //获取类里面的所有属性
                            Field field = fields[i];
                            //设置可以访问
                            field.setAccessible(true);
                            //如果数据库的字段名和类的属性名称一样就说明是同一个变量
                            if (field.getName().equals(columName)) {
                                //将数据库的数据设置给类的属性
                                field.set(object, cursor.getString(cursor.getColumnIndex(columName)));
                                break;
                            }
                        }
                    }
                    list.add(object);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        return list;
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
        ArrayList<Object> list = new ArrayList<>();
        if (cursor != null) {
            //新建链表对象存在数据
            try {
                //获取该类的所有属性
                //包括父类的所有属性
                Field[] fields = loadclass.getDeclaredFields();
                //将数据库返回的数据
                String[] colum = cursor.getColumnNames();

                //将cursor用游标的方式取出来
                while (cursor.moveToNext()) {
                    Object object = loadclass.newInstance();
                    //循环的方法把数据库的列名称给取出来
                    for (String columName : colum) {
                        for (int i = 0; i < fields.length; i++) {
                            //获取类里面的所有属性
                            Field field = fields[i];
                            //设置可以访问
                            field.setAccessible(true);
                            //如果数据库的字段名和类的属性名称一样就说明是同一个变量
                            if (field.getName().equals(columName)) {
                                //将数据库的数据设置给类的属性
                                field.set(object, cursor.getString(cursor.getColumnIndex(columName)));
                                break;
                            }
                        }
                    }
                    list.add(object);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        return list;
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
        return null;
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
        ContentValues contentValues = new ContentValues();

        Field[] fields = loadClass.getFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if (!field.getName().startsWith("&") && !field.getName().startsWith("CREATOR") && !field.getName().startsWith("CONTENTS_FILE_DESCRIPTOR") && !field.getName().startsWith("PARCELABLE_WRITE_RETURN_VALUE")) {
                    contentValues.put(field.getName(), "" + field.get(objects));
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return contentValues;
    }

    /**
     * 将对象的数据封装成可以插进数据库的数据
     *
     * @param loadClass 封装的类对象
     * @param objects   类对象实体类
     * @return
     */
    @Override
    protected ContentValues getContentValues(Class loadClass, Object objects, String[] colums) {
        ContentValues contentValues = new ContentValues();

        Field[] fields = loadClass.getFields();
        try {
            for (String colum : colums) {
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.getName().equals(colum)) {
                        contentValues.put(field.getName(), (String) field.get(objects));
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
     * Class对象转ContentValues
     *
     * @param loadClass
     * @param objects
     * @return
     */
    @Override
    protected ContentValues ClassToContentValue(Class loadClass, Object objects) {
        return null;
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
        ContentValues contentValues = new ContentValues();

        Field[] fields = loadClass.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if ((String) field.get(objects) != null && "".equals((String) field.get(objects))) {
                    contentValues.put(field.getName(), (String) field.get(objects));
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return contentValues;
    }

    /**
     * 将Map集合解析封装成对象
     *
     * @param list
     * @param loadClass
     * @return
     */
    @Override
    protected ArrayList<Object> AnalysisMap(ArrayList<Map<Object, Object>> list, Class loadClass) {
        ArrayList<Object> obj = new ArrayList<>();
        if (list != null && list.size() != 0) {
            // 方法 一 ：
            // 转化成  Set>>，然后在转化为迭代器
            Iterator iterator = list.get(0).entrySet().iterator();
            ArrayList<String> colum = new ArrayList<>();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                colum.add((String) entry.getKey());
            }

            if (list != null) {
                //新建链表对象存在数据
                try {
                    //获取该类的所有属性
                    Field[] fields = loadClass.getDeclaredFields();
                    //将cursor用游标的方式取出来
                    for (Map<Object, Object> map : list) {
                        Object object = loadClass.newInstance();
                        //循环的方法把数据库的列名称给取出来
                        for (String columName : colum) {
                            for (int i = 0; i < fields.length; i++) {
                                //获取类里面的所有属性
                                Field field = fields[i];
                                //设置可以访问
                                field.setAccessible(true);
                                //如果数据库的字段名和类的属性名称一样就说明是同一个变量
                                if (field.getName().equals(columName)) {
                                    //将数据库的数据设置给类的属性
                                    field.set(object, map.get(columName));
                                    break;
                                }
                            }
                        }
                        obj.add(object);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }

    /**
     * 对外面开放的接口（包括父类）
     *
     * @param cursor
     * @param loadclass
     * @return
     */
    public ArrayList<Object> CursorToObjects(Context context, Cursor cursor, Class loadclass) {
        this.context = context;
        return AnalysisCursors(cursor, loadclass);
    }

    /**
     * 对外面开放的接口（不包括父类）
     *
     * @param cursor
     * @param loadclass
     * @return
     */
    public ArrayList<Object> CursorToObject(Context context, Cursor cursor, Class loadclass) {
        this.context = context;
        return AnalysisCursor(cursor, loadclass);
    }

    /**
     * 对外面开放的接口(包括父类)
     *
     * @param loadClass
     * @param objects
     * @param colums
     * @return
     */
    public ContentValues ObjectToContentValues(Context context, Class loadClass, Object objects, String[] colums) {
        this.context = context;
        return getContentValues(loadClass, objects, colums);
    }


    /**
     * 对外面开放的接口(包括父类)
     *
     * @param loadClass
     * @param objects
     * @return
     */
    public ContentValues ObjectToContentValues(Context context, Class loadClass, Object objects) {
        this.context = context;
        return getContentValues(loadClass, objects);
    }

    /**
     * 对外面开放的接口(不包括父类)
     *
     * @param loadClass
     * @param objects
     * @param
     * @return
     */
    public ContentValues ObjectToContentValue(Context context, Class loadClass, Object objects) {
        this.context = context;
        return getContentValue(loadClass, objects);
    }


    /**
     * 对外面开放的接口
     *
     * @param map
     * @param loadclass
     * @return
     */
    public ArrayList<Object> MapToObjects(ArrayList<Map<Object, Object>> map, Class loadclass) {
        return AnalysisMap(map, loadclass);
    }
}
