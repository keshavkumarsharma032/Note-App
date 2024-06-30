package com.example.note;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("select * from note")
    List<Note> getNotes();

    @Insert
    void addNote(Note note);

    @Delete
    void deleteNotes(Note note);
}
