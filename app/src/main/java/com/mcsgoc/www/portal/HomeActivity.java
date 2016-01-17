package com.mcsgoc.www.portal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    Button login,dir,map,about,news,admi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        login= (Button) findViewById(R.id.loginbtn);
        login.setOnClickListener(this);
        dir= (Button) findViewById(R.id.dirbtn);
        dir.setOnClickListener(this);
        map= (Button) findViewById(R.id.mapbtn);
        map.setOnClickListener(this);
        about= (Button) findViewById(R.id.aboutbtn);
        about.setOnClickListener(this);
        news= (Button) findViewById(R.id.newsbtn);
        news.setOnClickListener(this);
        admi= (Button) findViewById(R.id.admibtn);
        admi.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_login) {
            Intent i=new Intent(this,LoginActivity.class);
            startActivity(i);
            return true;

        }

        if (id==R.id.action_dir)
        {
            Intent i=new Intent(this,DirectoryActivity.class);
            startActivity(i);
            return true;
        }

        if (id==R.id.action_map)
        {
            Intent i=new Intent(this,MapActivity.class);
            startActivity(i);
            return true;
        }
        if (id==R.id.action_about)
        {
            Intent i=new Intent(this,AboutActivity.class);
            startActivity(i);
            return true;
        }
        if (id==R.id.action_news)
        {
            Intent i=new Intent(this,NewsActivity.class);
            startActivity(i);
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();

        switch (id)
        {
            case R.id.loginbtn:
                Intent i=new Intent(this,LoginActivity.class);
                startActivity(i);
                break;
            case R.id.dirbtn:
                Intent j=new Intent(this,DirectoryActivity.class);
                startActivity(j);
                break;
            case R.id.mapbtn:
                Intent k=new Intent(this,MapActivity.class);
                startActivity(k);
                break;
            case R.id.aboutbtn:
                Intent l=new Intent(this,AboutActivity.class);
                startActivity(l);
                break;
            case R.id.newsbtn:
                Intent m=new Intent(this,NewsActivity.class);
                startActivity(m);
                break;
            case R.id.admibtn:
                Intent n=new Intent(this,AdmissionActivity.class);
                startActivity(n);


        }

    }
}
