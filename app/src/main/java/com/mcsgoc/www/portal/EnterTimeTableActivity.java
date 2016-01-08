package com.mcsgoc.www.portal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EnterTimeTableActivity extends AppCompatActivity {
    SQLiteDatabase sampleDB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_time_table);
        try {
            //Instantiate sampleDB object
            sampleDB = this.openOrCreateDatabase("iuuniversity", MODE_PRIVATE, null);
            //Create table using execSQL
            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS addtimetable1 (day VARCHAR,branch VARCHAR,semister VARCHAR,section VARCHAR,subject VARCHAR,time VARCHAR,place VARCHAR, period_No VARCHAR );");

        }catch(Exception e){}
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void getAdd(View v)
    {

        String day=((EditText)findViewById(R.id.editText1)).getText().toString();
        String branch=((EditText)findViewById(R.id.editText2)).getText().toString();
        String semester=((EditText)findViewById(R.id.editText4)).getText().toString();
        String section=((EditText)findViewById(R.id.editText5)).getText().toString();
        String subject=((EditText)findViewById(R.id.editText6)).getText().toString();
        String time=((EditText)findViewById(R.id.editText7)).getText().toString();
        String Period_No=((EditText)findViewById(R.id.editText8)).getText().toString();
        String place=((EditText)findViewById(R.id.editText9)).getText().toString();


        try {


            sampleDB.execSQL("INSERT INTO addtimetable1  Values ('"+day+"','"+branch+"','"+semester+"','"+section+"','"+subject+"','"+time+"','"+place+"','"+Period_No+"');");
            Toast.makeText(getApplicationContext(), "Data is store :" + day, Toast.LENGTH_LONG).show();

            ((EditText)findViewById(R.id.editText6)).setText("");
            ((EditText)findViewById(R.id.editText7)).setText("");
            ((EditText)findViewById(R.id.editText8)).setText("");


        }
        catch(Exception ee){}




        //	 Toast.makeText(getApplicationContext(),"Data is store"+day, Toast.LENGTH_LONG).show();

    }

    public void showData(View v)
    {

        try
        {
            Cursor c = sampleDB.rawQuery("SELECT * FROM addtimetable1 ", null);
            String s="";

            while(c.moveToNext())
            {
                s=s+c.getString(0)+" "+c.getString(1)+"\n";

            }

            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

        }catch(Exception ee){}

    }

    public void getDelete(View v)
    {


        try {


            sampleDB.execSQL("Delete from addtimetable");
            Toast.makeText(getApplicationContext(),"Records are deleted", Toast.LENGTH_LONG).show();




        }
        catch(Exception ee){}


    }

}
