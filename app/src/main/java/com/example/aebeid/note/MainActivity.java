package com.example.aebeid.note;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.DialogPreference;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    ArrayList<String> arrayList;
    ListView listView;
    String data;
    SharedPreferences shared;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shared = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        listView = (ListView) findViewById(R.id.listview);
        updatingArrayList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                oldForm(position);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteForm(position);
                return true;
            }
        });
    }

    public void newForm(){
        Intent i = new Intent(getApplicationContext(), Main2Activity.class);
        i.putExtra("data", "").putStringArrayListExtra("array", arrayList);
        startActivity(i);
    }

    public void oldForm(int position){
        Intent i = new Intent(getApplicationContext(), Main2Activity.class);
        i.putExtra("position", position).putExtra("data", arrayList.get(position)).putStringArrayListExtra("array", arrayList);
        startActivity(i);
    }

    public void updatingArrayList(){
        Intent i = getIntent();
        int position = i.getIntExtra("position", -2);

        if(position == -1){
            arrayList = i.getStringArrayListExtra("array");
            if(!i.getStringExtra("data").isEmpty()){
            arrayList.add(0, i.getStringExtra("data"));
            }
            Set<String> tasksSet = new HashSet<String>(arrayList);
            shared.edit().putStringSet("array_list", tasksSet).apply();
            updatingListView();
        }
        else if(position > -1){
            arrayList = i.getStringArrayListExtra("array");
            if(i.getStringExtra("data").isEmpty()){
                arrayList.remove(position);
                Set<String> tasksSet = new HashSet<String>(arrayList);
                shared.edit().putStringSet("array_list", tasksSet).apply();
                updatingListView();
            }
            else{
                arrayList.add(position, i.getStringExtra("data"));
                arrayList.remove(position + 1);
                Set<String> tasksSet = new HashSet<String>(arrayList);
                shared.edit().putStringSet("array_list", tasksSet).apply();
                updatingListView();
            }
        }
        else if(position == -2){
            Set<String> tasksSet = shared.getStringSet("array_list", new HashSet<String>());
            arrayList = new ArrayList<String>(tasksSet);
            updatingListView();
        }

    }

    public void updatingListView(){
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.parseColor("#ff000000"));

                return textView;
            }
        });
    }

    public void deleteForm(final int position){

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Sure")
                .setMessage("Are you want to delete this note.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        arrayList.remove(position);
                        updatingListView();
                    }
                })
                .setNegativeButton("No", null).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.Add:
                newForm();
            default:
                return super.onOptionsItemSelected(item);

        }
    }






}
