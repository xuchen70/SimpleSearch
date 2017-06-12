package com.mark.ss;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class SearchHistoryDao {

    public static String TABLE_NAME = "history";
    public static String NUMBER = "number";
    public static String TYPE_NAME = "type_name";
    public static String TYPE_CODE = "type_code";

    private SearchHistoryHelper helper;
    private Context context;

    public SearchHistoryDao(Context context) {
        this.context = context;
        helper = new SearchHistoryHelper(context);
    }

    public void insert(History history){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.replace(TABLE_NAME,null,getValues(history));
        db.close();

    }

    public void delete(String number){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_NAME,NUMBER+"=?",new String[]{number});
        db.close();

    }

    public void deleteAll(){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.close();
    }

    public List<History> query(){
        List<History> list = new ArrayList<>();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()){
            History history = new History();
            history.setNumber(cursor.getString(cursor.getColumnIndex(NUMBER)));
            history.setTypeCode(cursor.getString(cursor.getColumnIndex(TYPE_CODE)));
            history.setTypeName(cursor.getString(cursor.getColumnIndex(TYPE_NAME)));
            list.add(history);
        }

        return list;
    }

    private ContentValues getValues(History history) {
        ContentValues values = new ContentValues();
        values.put(NUMBER,history.getNumber());
        values.put(TYPE_NAME,history.getTypeName());
        values.put(TYPE_CODE,history.getTypeCode());
        return values;
    }
}
