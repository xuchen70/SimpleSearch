package com.mark.ss;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/1/7.
 */

public class SearchHistoryHelper extends SQLiteOpenHelper {
    private static final String name = "SearchHistory.db";
    private static int version = 1;

    private String sql = "create table "+
            SearchHistoryDao.TABLE_NAME+"("+
            SearchHistoryDao.NUMBER+" varchar primary key,"+
            SearchHistoryDao.TYPE_NAME+" varchar,"+
            SearchHistoryDao.TYPE_CODE+" varchar);";

    public SearchHistoryHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS " + SearchHistoryDao.TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
}
