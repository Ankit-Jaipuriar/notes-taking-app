package com.example.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.MutableLiveData

class NotesViewModel(
    private val repo:Repo
):ViewModel(){

    fun insert(note:Note){
        viewModelScope.launch(Dispatchers.IO) {
            repo.insert(note)
        }
    }
    fun getAllNotes() = repo.getAllNotes()

    fun delete(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.delete(note)
        }
    }
    fun update(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.update(note)
        }
    }
}