package com.andrewtorr.skywriter;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;


public class ScriptEditActivity extends ActionBarActivity {
    @Bind(R.id.script_title)
    TextView script_title;
    @Bind(R.id.script_title_edit)
    EditText script_title_edit;
    @Bind(R.id.line_list)
    ListView line_list;
    @Bind(R.id.save_button)
    FloatingActionButton save_button;
    @Bind(R.id.add_line_button)
    FloatingActionButton add_line_button;
    @Bind(R.id.character_button)
    FloatingActionButton character_button;

    @Bind(R.id.line_select_layout)
    RelativeLayout line_select_layout;

    @Bind(R.id.action_line_layout)
    RelativeLayout action_line_layout;
    @Bind(R.id.dialogue_line_layout)
    RelativeLayout dialogue_line_layout;
    @Bind(R.id.lyric_line_layout)
    RelativeLayout lyric_line_layout;
    @Bind(R.id.transition_line_layout)
    RelativeLayout transition_line_layout;


    @Bind(R.id.action_edit)
    EditText action_edit;
    @Bind(R.id.dialogue_speaker)
    TextView dialogue_speaker;
    @Bind(R.id.dialogue_edit)
    EditText dialogue_edit;

    @Bind(R.id.pick_action_button)
    Button pick_action_button;
    @Bind(R.id.pick_dialogue_button)
    Button pick_dialogue_button;
    @Bind(R.id.pick_lyric_button)
    Button pick_lyric_button;
    @Bind(R.id.pick_transition_button)
    Button pick_transition_button;

    @Bind(R.id.save_action_button)
    FloatingActionButton save_action_button;
    @Bind(R.id.save_dialogue_button)
    FloatingActionButton save_dialogue_button;

    @Bind(R.id.cancel_title)
    FloatingActionButton cancel_title;
    @Bind(R.id.save_title)
    FloatingActionButton save_title;

    private String TAG = "Script Edit View: ";

    private String speaker;
    private Integer lineType = 0;
    private boolean set_line = false;
    private boolean changes = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_script);
        ButterKnife.bind(this);

        Intent intent = getIntent();
    }

    @OnClick(R.id.save_button)
    public void saveScript() {
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @OnLongClick(R.id.add_line_button)
    public boolean setLineType() {
        set_line = true;
        line_select_layout.setVisibility(View.VISIBLE);
        add_line_button.setVisibility(View.INVISIBLE);
        character_button.setVisibility(View.INVISIBLE);
        save_button.setVisibility(View.INVISIBLE);
        return false;
    }

    @OnClick(R.id.add_line_button)
    public void addLine() {
        if (!set_line) {
            switch (lineType) {
                case 0:
                    Log.d(TAG, "adding action line");
                    action_line_layout.setVisibility(View.VISIBLE);

                    break;
                case 1:
                    Log.d(TAG, "adding dialogue line");
                    dialogue_line_layout.setVisibility(View.VISIBLE);

                    break;
                case 2:
                    Log.d(TAG, "adding transition line");
                    break;
                case 3:
                    Log.d(TAG, "adding lyric line");
                default:
                    break;
            }
        } else {
            Log.d(TAG, "ignoring regular click");
            set_line = false;
        }
    }

    @OnClick(R.id.pick_action_button)
    public void pickAction() {
        line_select_layout.setVisibility(View.INVISIBLE);
        action_line_layout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.pick_dialogue_button)
    public void pickDialogue() {
        line_select_layout.setVisibility(View.INVISIBLE);
        dialogue_line_layout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.pick_lyric_button)
    public void pickLyric() {
        line_select_layout.setVisibility(View.INVISIBLE);
        lyric_line_layout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.pick_transition_button)
    public void pickTransition() {
        line_select_layout.setVisibility(View.INVISIBLE);
        transition_line_layout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.save_action_button)
    public void saveAction() {
        action_line_layout.setVisibility(View.INVISIBLE);
        if (!action_edit.getText().toString().isEmpty()) {

        }
        add_line_button.setVisibility(View.VISIBLE);
        character_button.setVisibility(View.VISIBLE);
        save_button.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.cancel_action_button)
    public void cancelAction() {
        action_line_layout.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.save_dialogue_button)
    public void saveDialogue() {
        dialogue_line_layout.setVisibility(View.INVISIBLE);
        if (!dialogue_edit.getText().toString().isEmpty()) {

        }
        add_line_button.setVisibility(View.VISIBLE);
        character_button.setVisibility(View.VISIBLE);
        save_button.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.script_title)
    public void editTitle() {
        script_title.setVisibility(View.INVISIBLE);
        script_title_edit.setVisibility(View.VISIBLE);

        script_title_edit.setText(script_title.getText());

        save_title.setVisibility(View.VISIBLE);
        cancel_title.setVisibility(View.VISIBLE);

        save_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                script_title.setText(script_title_edit.getText());

                script_title.setVisibility(View.VISIBLE);
                script_title_edit.setVisibility(View.INVISIBLE);
                save_title.setVisibility(View.INVISIBLE);
                cancel_title.setVisibility(View.INVISIBLE);
            }
        });

        cancel_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                script_title.setVisibility(View.VISIBLE);
                script_title_edit.setVisibility(View.INVISIBLE);
                save_title.setVisibility(View.INVISIBLE);
                cancel_title.setVisibility(View.INVISIBLE);
            }
        });

        script_title_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    script_title.setVisibility(View.VISIBLE);
                    script_title_edit.setVisibility(View.INVISIBLE);
                    save_title.setVisibility(View.INVISIBLE);
                    cancel_title.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @OnClick(R.id.character_button)
    public void characters() {
        final Dialog dialog = new Dialog(this, R.style.Dialog_No_Border);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater dialog_inflater = LayoutInflater.from(this);
        View dialog_view = dialog_inflater.inflate(R.layout.character_list, null);

        FloatingActionButton add_character_button = (FloatingActionButton) dialog_view.findViewById(R.id.add_character_button);
        FloatingActionButton cancel_button = (FloatingActionButton) dialog_view.findViewById(R.id.cancel_button);

        ListView char_list = (ListView) dialog_view.findViewById(R.id.char_list);

        //TODO: Populate list view with characters

        add_character_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Add Character");

            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Cancel character list");
                dialog.dismiss();
            }
        });

        char_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "Select Character: " + i);
            }
        });

        dialog.setContentView(dialog_view);
        dialog.show();
    }


    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.andrewtorr.skywriter.R.menu.menu_edit_script, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.andrewtorr.skywriter.R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}