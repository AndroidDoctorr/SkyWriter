package com.andrewtorr.skywriter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.RadioButton;

/**
 * Created by Andrew on 8/3/2015.
 *
 */
public class SettingsActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final RadioButton rb1 = (RadioButton) findViewById(R.id.rb1);
        final RadioButton rb2 = (RadioButton) findViewById(R.id.rb2);
        final RadioButton rb3 = (RadioButton) findViewById(R.id.rb3);

        rb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rb2.setChecked(false);
                    rb3.setChecked(false);
                }
            }
        });
        rb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rb1.setChecked(false);
                    rb3.setChecked(false);
                }
            }
        });
        rb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rb1.setChecked(false);
                    rb2.setChecked(false);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save) {
            Intent intent = new Intent(SettingsActivity.this, ScriptListActivity.class);
            startActivity(intent);
        } else if (id == R.id.cancel) {
            Intent intent = new Intent(SettingsActivity.this, ScriptListActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
