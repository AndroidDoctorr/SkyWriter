package com.andrewtorr.skywriter.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.andrewtorr.skywriter.Models.Script;
import com.andrewtorr.skywriter.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Andrew on 7/12/2015.
 *
 */
public class ScriptArrayAdapter extends ArrayAdapter<Script> {
    private int resource;
    private ArrayList<Script> scripts;
    private LayoutInflater inflater;
    private SimpleDateFormat formatter;

    public ScriptArrayAdapter(Context context, int resource, ArrayList<Script> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.scripts = objects;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View notesRow = inflater.inflate(resource, parent, false);

        TextView noteTitle = (TextView)notesRow.findViewById(R.id.note_title);
        TextView noteText = (TextView)notesRow.findViewById(R.id.note_text);
        TextView noteDate = (TextView)notesRow.findViewById(R.id.note_date);

        Script script = scripts.get(position);

        noteTitle.setText(script.getTitle());
        noteText.setText(script.getText());
        noteDate.setText(formatter.format(script.getDate()));

        return notesRow;
    }

    public void updateAdapter(ArrayList<Script> scripts) {
        this.scripts = scripts;
        super.notifyDataSetChanged();
    }
}
