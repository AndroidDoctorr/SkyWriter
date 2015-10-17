package com.andrewtorr.skywriter;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.andrewtorr.skywriter.Adapters.ScriptArrayAdapter;
import com.andrewtorr.skywriter.Models.Script;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ScriptListActivity extends ActionBarActivity {
    public static final String NOTE_ID = "com.eandrewtorr.www.skywriter.NOTE_ID";
    public static final String NOTE_TITLE = "com.eandrewtorr.www.skywriter.NOTE_TITLE";
    public static final String NOTE_TEXT = "com.eandrewtorr.www.skywriter.NOTE_TEXT";

    public static final ArrayList<Script> scripts = new ArrayList<>();
    private ScriptArrayAdapter scriptArrayAdapter;

    private SharedPreferences scriptPrefs;
    private ListView scriptsList;

    ParseQuery<Script> query;

    private String scriptID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scriptlist);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        scriptPrefs = getPreferences(Context.MODE_PRIVATE);
        scriptsList = (ListView)findViewById(R.id.list);

        //This puts the items from Parse associated with the user into the scripts list/list view
        setupScripts();

        scriptsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Script script = scripts.get(position);
                Intent intent = new Intent(ScriptListActivity.this, ScriptEditActivity.class);
                intent.putExtra(NOTE_ID, script.getObjectId());
                intent.putExtra(NOTE_TITLE, script.getTitle());
                intent.putExtra(NOTE_TEXT, script.getText());

                startActivityForResult(intent, 1);
            }
        });

        scriptsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ScriptListActivity.this);
                alertBuilder.setTitle("Delete Script...");
                alertBuilder.setMessage("Are you sure you want to delete this script?");
                alertBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Script note = scripts.get(position);
                        scripts.remove(position);
                        scriptArrayAdapter.updateAdapter(scripts);
                        note.deleteInBackground();
                    }
                });
                alertBuilder.setNegativeButton("Cancel", null);
                alertBuilder.create().show();
                return true;
            }
        });
    }

    private void setupScripts() {
        scripts.clear();
        final ParseQuery<Script> query = ParseQuery.getQuery(Script.class);
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<Script>() {
            @Override
            public void done(List<Script> list, ParseException e) {
                for (Script script : list) {
                    Script newScript = new Script();
                    newScript.setText(script.getText());
                    newScript.setDate(script.getDate());
                    newScript.setTitle(script.getTitle());
                    newScript.setObjectId(script.getObjectId());
                    scripts.add(newScript);
                }
                scriptArrayAdapter = new ScriptArrayAdapter(getApplicationContext(), R.layout.scripts_list_item, scripts);
                scriptsList.setAdapter(scriptArrayAdapter);
                scriptArrayAdapter.updateAdapter(scripts);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            scriptID = data.getStringExtra(NOTE_ID);
            if (scriptID.equals("")) {
                Script note = new Script();
                note.setDate(new Date());
                note.setTitle(data.getStringExtra(NOTE_TITLE));
                note.setText(data.getStringExtra(NOTE_TEXT));
                note.setBoth_lowercase(data.getStringExtra(NOTE_TITLE).toLowerCase()+data.getStringExtra(NOTE_TEXT).toLowerCase());
                note.setUser(ParseUser.getCurrentUser());
                note.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Toast.makeText(getApplicationContext(),
                                "Script saved!", Toast.LENGTH_SHORT)
                                .show();
                        setupScripts();
                    }
                });
            } else {
                ParseQuery<Script> query = ParseQuery.getQuery(Script.class);
                query.getInBackground(scriptID, new GetCallback<Script>() {
                    public void done(Script script, ParseException e) {
                        if (e == null) {
                            script.setDate(new Date());
                            script.put("title", data.getStringExtra(NOTE_TITLE));
                            script.put("text", data.getStringExtra(NOTE_TEXT));
                            script.put("both_lowercase", data.getStringExtra(NOTE_TITLE).toLowerCase() + data.getStringExtra(NOTE_TEXT).toLowerCase());
                            script.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    Toast.makeText(getApplicationContext(),
                                            "Script saved!", Toast.LENGTH_SHORT)
                                            .show();
                                    setupScripts();
                                }
                            });
                        }
                    }
                });
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scriptlist, menu);
        MenuItem searchItem = menu.findItem(R.id.search);

        SearchManager searchManager = (SearchManager) ScriptListActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);

            SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener()
            {
                public boolean onQueryTextChange(String newText)
                {
                    final ArrayList<Script> searchList = new ArrayList<>();
                    Log.d("TEST","on query text change");
                    query = ParseQuery.getQuery(Script.class);
                    query.whereEqualTo("user", ParseUser.getCurrentUser());
                    query.whereContains("both_lowercase", newText);
                    query.findInBackground(new FindCallback<Script>() {
                        @Override
                        public void done(List<Script> list, ParseException e) {
                            for (Script script : list) {
                                searchList.add(script);
                                Log.d("TEST", "adding a note");
                            }
                            Log.d("TEST", "populating list");
                            scriptArrayAdapter = new ScriptArrayAdapter(getApplicationContext(), R.layout.scripts_list_item, searchList);
                            scriptArrayAdapter.updateAdapter(searchList);
                            scriptsList.setAdapter(scriptArrayAdapter);
                        }
                    });
                    return true;
                }

                public boolean onQueryTextSubmit(String query)
                {
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.new_script) {
            Intent intent = new Intent(ScriptListActivity.this, ScriptEditActivity.class);
            intent.putExtra(NOTE_TITLE, "");
            intent.putExtra(NOTE_TEXT, "");
            intent.putExtra(NOTE_ID, "");
            startActivityForResult(intent, 1);
            return true;
        } else if (id == R.id.logout) {
            ParseUser.logOut();
            Intent intent = new Intent(ScriptListActivity.this, LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.settings) {
            Intent intent = new Intent(ScriptListActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}