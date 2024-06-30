package com.example.note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    AppCompatButton btnNoteCreate;
    FloatingActionButton Addbtn;
    RecyclerView recyclernotes;
    DatabaseHelper databaseHelper;
    LinearLayout noNotes;
    RecyclerNotesAdapter adapter;
    ArrayList<Note> arrNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVar();

        showNotes();

        Addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddNoteDialog();
            }
        });

        btnNoteCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Addbtn.performClick();
            }
        });
    }

    private void openAddNoteDialog() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.add_note);


        EditText TitleEdit = dialog.findViewById(R.id.TitleEdit);
        EditText Contentedit = dialog.findViewById(R.id.Contentedit);
        AppCompatButton Addbtnn = dialog.findViewById(R.id.Addbtnn);

        Addbtnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = TitleEdit.getText().toString();
                String content = Contentedit.getText().toString();

                if (!content.isEmpty()) {
                    databaseHelper.noteDao().addNote(new Note(title, content));
                    showNotes();
                    dialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Please enter something!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    public void showNotes() {
        arrNotes = new ArrayList<>(databaseHelper.noteDao().getNotes());

        if (arrNotes.size() > 0) {
            recyclernotes.setVisibility(View.VISIBLE);
            noNotes.setVisibility(View.GONE);

            adapter = new RecyclerNotesAdapter(this, arrNotes, databaseHelper);
            recyclernotes.setAdapter(adapter);
        } else {
            noNotes.setVisibility(View.VISIBLE);
            recyclernotes.setVisibility(View.GONE);
        }
    }

    private void initVar() {
        btnNoteCreate = findViewById(R.id.btnNoteCreate);
        Addbtn = findViewById(R.id.Addbtn);
        recyclernotes = findViewById(R.id.recyclernotes);
        noNotes = findViewById(R.id.noNotes);

        recyclernotes.setLayoutManager(new GridLayoutManager(this, 2));
        databaseHelper = DatabaseHelper.getInstance(this);
    }
}
