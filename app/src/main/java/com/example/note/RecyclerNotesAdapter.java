package com.example.note;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerNotesAdapter extends RecyclerView.Adapter<RecyclerNotesAdapter.ViewHolder> {
    Context context;
    ArrayList<Note> arrNotes;
    DatabaseHelper databaseHelper;

    RecyclerNotesAdapter(Context context, ArrayList<Note> arrNotes, DatabaseHelper databaseHelper) {
        this.context = context;
        this.arrNotes = arrNotes;
        this.databaseHelper = databaseHelper;  // Correctly initialize databaseHelper
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Note note = arrNotes.get(position);
        holder.txtHeading.setText(note.getTitle());
        holder.txtContent.setText(note.getContent());

        holder.llrow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteItem(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtHeading, txtContent;
        LinearLayout llrow;

        public ViewHolder(View itemView) {
            super(itemView);
            txtHeading = itemView.findViewById(R.id.txtHeading);
            txtContent = itemView.findViewById(R.id.txtContent);
            llrow = itemView.findViewById(R.id.llrow);
        }
    }

    public void deleteItem(int pos) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Note noteToDelete = arrNotes.get(pos);
                        databaseHelper.noteDao().deleteNotes(noteToDelete);
                        arrNotes.remove(pos);  // Remove item from the list
                        notifyItemRemoved(pos);  // Notify the adapter of item removal
                        ((MainActivity) context).showNotes();  // Refresh the notes in MainActivity
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}

