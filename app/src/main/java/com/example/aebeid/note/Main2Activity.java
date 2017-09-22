package com.example.aebeid.note;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    EditText editText;
    String data;
    int position;
    ArrayList<String> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        editText = (EditText) findViewById(R.id.editText);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        data = i.getStringExtra("data");
        position = i.getIntExtra("position", -1);
        Log.i("position", String.valueOf(position));
        arrayList = i.getStringArrayListExtra("array");
        if(position != -1){
            editText.setText(data);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.finish();
            return true;
        }
        if(item.getItemId() == R.id.Done){
            Done();
        }
        return super.onOptionsItemSelected(item);
    }

    public void Done(){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        data = editText.getText().toString();
        i.putExtra("data", data).putExtra("position", position).putStringArrayListExtra("array", arrayList);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_second, menu);
        return true;
    }

}
