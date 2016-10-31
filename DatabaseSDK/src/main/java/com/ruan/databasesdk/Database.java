package com.ruan.databasesdk;

import com.ruan.databasesdk.api.Establish;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface Database {

    public interface Check{

        /**
         * 自动创建表的接口
         * @param database      数据库的名称
         * @param table         数据库的表名称
         * @param state         数据库的表是否存在
         * @return              返回一个创建数据的对象
         */
        public Establish CreateTable(String database, String table, boolean state);


    }

}
