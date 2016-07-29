package com.example.asu.noticeboard;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class AdminActivity extends AppCompatActivity {

    private Button button;
    private Button button2;
    private EditText edtxt;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private ParseObject NoticeText;
    private String theText;
    private String pinText;
    private EditText pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        button = (Button) findViewById(R.id.button);

        edtxt = (EditText) findViewById(R.id.edtxt);
        pin = (EditText) findViewById(R.id.pin);
        UpdateNotice();

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Parse.enableLocalDatastore(this);

        Parse.initialize(this, "mEFS8BapHk2hkvfOUVo6SvwW42qzjg9JHkdczbha",
                "MwKr7iPyWAZNYi6ng8aCXu5ardAqXNOVvZzHZq6W");


       NoticeText = new ParseObject("TestObject");

    }


    public void UpdateNotice(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                theText = edtxt.getText().toString();

                pinText = pin.getText().toString();

                // Add this to parse db

                ParseQuery<ParseObject> query = ParseQuery.getQuery("TestObject");

// pKjvOUKvo3 is the object ID of the row object where you want to update the value.

                query.getInBackground("pKjvOUKvo3", new GetCallback<ParseObject>() {
                    public void done(ParseObject notc, ParseException e) {
                        if (e == null) {
                            
                            if (pinText.equals("1010")) {
                                notc.put("TheNoticeRead", theText);
                                notc.saveInBackground();
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                            }

                            else {
                                Toast.makeText(getApplicationContext(), "Wrong pincode entered" ,
                                        Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                });



            }
        });
    }

    private void addDrawerItems() {
        String[] osArray = { "NoticeBoard", "ADMIN" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int pos = position;

                if (pos == 0){

                    Intent j = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(j);
                }

                if (pos == 1){
                    Toast.makeText(AdminActivity.this, "For Admin Use Only", Toast.LENGTH_SHORT).show();
                    Intent j = new Intent(getApplicationContext(),AdminActivity.class);
                    startActivity(j);
                }

            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("NoticeBoard - MENU");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
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
        if (id == R.id.action_settings) {
            return true;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

