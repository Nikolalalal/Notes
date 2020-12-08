package com.example.mynotes.data

import androidx.lifecycle.LiveData

class NoteRepository(private val noteDatabaseDao: NoteDatabaseDao){
    val allNotes: LiveData<List<Note>> = noteDatabaseDao.getAllNotes()
    suspend fun insert(note: Note){
        noteDatabaseDao.insert(note)
    }
    suspend fun clearbyId(){
        noteDatabaseDao.clear()}
    suspend fun deletebyid(note:Note){
        noteDatabaseDao.deletebyid(note)
    }
    suspend fun update(note: Note){
        noteDatabaseDao.update(note)
    }

}
