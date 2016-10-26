package com.ruan.databasesdk.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/15.
 */
public class GetMapKey {

    /**
     * 获取Map集合的key
     * @param map
     * @return
     */
    public static ArrayList<String> getMapKey(Map<String, Object> map) {
        ArrayList<String> key = new ArrayList<>();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            key.add(entry.getKey() + "");
            // entry.getKey() 返回与此项对应的键
            // entry.getValue() 返回与此项对应的值
        }
        return key;
    }
}
