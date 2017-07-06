package com.example.anubhav.todo_list;

/**
 * Created by Anubhav on 03-07-2017.
 */

public class ToDoItem {
    String title,date,time,details;
    int id;

    public ToDoItem(int id,String title, String date, String time, String details) {
        this.id=id;
        this.title = title;
        this.date = date;
        this.time = time;
        this.details = details;
    }
}
