package com.example.anubhav.todo_list;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Anubhav on 03-07-2017.
 */

public class ToDoOpenHelper extends SQLiteOpenHelper {

    public final String title = "title";
    public final String id = "id";
    public final String date = "date";
    public final String time = "time";
    public final String detail = "detail";
    public final String tablename = "To_Do_table";
    public static ToDoOpenHelper toDoOpenHelper;

    public ToDoOpenHelper(Context context) {

        super(context, "Todo.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + tablename + "(" +
                id + " integer primary key autoincrement," +
                title + " text," +
                date + " text," +
                time + " text," +
                detail + " text );";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public static ToDoOpenHelper getTodoOpenHelperInstance(Context context){
        if(toDoOpenHelper==null){
            return new ToDoOpenHelper(context);
        }
        else{
            return toDoOpenHelper;
        }
    }
}
