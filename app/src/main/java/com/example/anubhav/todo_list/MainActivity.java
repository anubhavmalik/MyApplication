package com.example.anubhav.todo_list;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<ToDoItem> arrayList;
    ListView listView;
    ToDoAdapter toDoAdapter;
    int ADD_REQUEST = 10, EDIT_REQUEST = 10;
    int exit_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.list_item);
        arrayList = new ArrayList<>();
        toDoAdapter = new ToDoAdapter(this, arrayList);
        listView.setAdapter(toDoAdapter);
        updateDatabase();
        toDoAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, ToDoDetails.class);
                i.putExtra(arrayList.get(position).title, IntentConstants.to_do_title);
                i.putExtra(arrayList.get(position).date, IntentConstants.to_do_date);
                i.putExtra(arrayList.get(position).details, IntentConstants.to_do_details);
                i.putExtra(arrayList.get(position).time, IntentConstants.to_do_time);
                startActivityForResult(i, EDIT_REQUEST);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ToDoDetails.class);
                startActivityForResult(i, ADD_REQUEST);
            }
        });
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Snackbar.make(v, "Adds a new task.", Snackbar.LENGTH_SHORT).show();
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.feedback) {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_SEND);
            Uri uri = Uri.parse("mailto:anubhavmalikdeveloper@gmail.com");
            i.putExtra(Intent.EXTRA_SUBJECT, "Feedback for Todo app");
            startActivity(i);
        }
        if (id == R.id.about) {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_SCREEN_OFF);
            i.getAction();
        }
        if (id == R.id.app_bar_switch) {
            Toast.makeText(this, "Still have to change the theme", Toast.LENGTH_LONG).show();
        }
        return true;
    }

    public void updateDatabase() {
        ToDoOpenHelper toDoOpenHelper = ToDoOpenHelper.getTodoOpenHelperInstance(MainActivity.this);
        SQLiteDatabase database = toDoOpenHelper.getReadableDatabase();
        Cursor cursor = database.query(toDoOpenHelper.tablename, null, null, null, null, null, null);
        arrayList.clear();
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(toDoOpenHelper.title));
            String detail = cursor.getString(cursor.getColumnIndex(toDoOpenHelper.detail));
            String date = cursor.getString(cursor.getColumnIndex(toDoOpenHelper.date));
            String time = cursor.getString(cursor.getColumnIndex(toDoOpenHelper.time));
            int id = cursor.getInt(cursor.getColumnIndex(toDoOpenHelper.id));
            ToDoItem toDoItem = new ToDoItem(id, title, date, time, detail);
            arrayList.add(toDoItem);
        }
        toDoAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_REQUEST) {
            if (resultCode == RESULT_OK) {
                updateDatabase();
            }
            if(resultCode==RESULT_CANCELED){
                Toast.makeText(this, "Details not saved", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (exit_count < 1) {
            exit_count++;
            Toast.makeText(this, "Press back " + exit_count + " more time to exit.", Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }
}