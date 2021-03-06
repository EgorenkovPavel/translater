package com.epipasha.translater.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.epipasha.translater.objects.Language;

/**
 * Created by Pavel on 20.03.2017.
 */

public class DbManager {

    private static DbManager manager;

    private SQLiteDatabase db;
    private final Context context;
    private DbHelper dbHelper;

    public static DbManager getInstance(Context context){
        if (manager == null){
            manager = new DbManager(context);
        }
        return manager;
    }

    private DbManager(Context context){
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public void open(){
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        db.close();
    }


    public int addHistory(String inputText, Language langIn, String outputText, Language langOut){
        open();
        ContentValues values = new ContentValues(4);

        values.put(DbHelper.INPUT_TEXT, inputText);
        values.put(DbHelper.INPUT_CODE, langIn.getCode());
        values.put(DbHelper.OUTPUT_TEXT, outputText);
        values.put(DbHelper.OUTPUT_CODE, langOut.getCode());

        int res = (int)db.insertOrThrow(DbHelper.TABLE_HISTORY, null, values);
        close();
        return res;
    }

    public int addFavorites(String inputText, String inputCode, String outputText, String outputCode){
        open();
        ContentValues values = new ContentValues(4);

        values.put(DbHelper.INPUT_TEXT, inputText);
        values.put(DbHelper.INPUT_CODE, inputCode);
        values.put(DbHelper.OUTPUT_TEXT, outputText);
        values.put(DbHelper.OUTPUT_CODE, outputCode);

        int res = (int)db.insertOrThrow(DbHelper.TABLE_FAVORITES, null, values);
        close();
        return res;
    }

    public int deleteHistory(){
        open();
        int res = (int)db.delete(DbHelper.TABLE_HISTORY, null, null);
        close();
        return res;
    }

    public int deleteFavorites(){
        open();
        int res = (int)db.delete(DbHelper.TABLE_FAVORITES, null, null);
        close();
        return res;
    }
}
