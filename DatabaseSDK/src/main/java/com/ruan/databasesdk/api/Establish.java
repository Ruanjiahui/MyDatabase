package com.ruan.databasesdk.api;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/2/29.
 * <p/>
 * 这个对象用于封装储存创建数据表的数据的对象
 */
public class Establish {


    private HashMap<String, Object> map = null;

    public Establish() {
        map = new HashMap<>();
    }

    public void put(String key, String value) {
        map.put(key, value);
    }


    public String get(String key) {
        return (String) map.get(key);
    }

    public ArrayList<String> getKey() {
        return GetMapKey.getMapKey(map);
    }
}
