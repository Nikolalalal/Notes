package com.example.mynotes.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDatabaseDao {
    @Insert
    suspend fun insert(note: Note)
    @Update
    suspend fun update(note: Note)
    @Query("delete from note_table ")
    suspend fun clear()
    @Delete
    suspend fun deletebyid(note:Note)
    @Query("SELECT * FROM note_table ORDER BY note_time")
     fun getAllNotes(): LiveData<List<Note>>

}