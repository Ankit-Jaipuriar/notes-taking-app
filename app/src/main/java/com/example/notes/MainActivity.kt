package com.example.notes

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), NotesAdapter.Clicklistner {
    private lateinit var repo: Repo
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var notesViewModelFactory: NotesViewModelFactory
    private lateinit var noteDatabase: NoteDatabase
    private lateinit var notesadapter: NotesAdapter
    private lateinit var rv:RecyclerView
    private lateinit var notes : Note
    private lateinit var fab : FloatingActionButton
    private lateinit var dialog :Dialog
    private lateinit var edttitle : EditText
    private lateinit var edtcontent :EditText
    private lateinit var savebtn  :  Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        init()


        notesViewModel.getAllNotes().observe(this) {
            notesadapter = NotesAdapter(it,this)
            rv.adapter = notesadapter
            rv.layoutManager = LinearLayoutManager(this)

        }

        fab.setOnClickListener {
            openDialog(comingFromFAB = true)
        }
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
    private fun init(){
        noteDatabase = NoteDatabase(this)
        repo = Repo(noteDatabase.getNoteDao())
        notesViewModelFactory = NotesViewModelFactory(repo)
        rv = findViewById(R.id.rv)
        notesViewModel = ViewModelProvider(this, notesViewModelFactory).get(NotesViewModel::class.java)
        fab = findViewById(R.id.fab)

    }

    private fun openDialog(comingFromFAB:Boolean){
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_dialog)

        edtcontent = dialog.findViewById(R.id.content)
        edttitle = dialog.findViewById(R.id.title)
        savebtn = dialog.findViewById(R.id.save_btn)

        savebtn.setOnClickListener {
            val note  = Note(
                noteName = edttitle.text.toString(),
                noteContent = edtcontent.text.toString()
            )
            if(comingFromFAB) {
                notesViewModel.insert(note)
            }
            else{
                notesViewModel.update(note)
            }
            dialog.dismiss()
        }
        dialog.show()

    }

    override fun updateNote(note: Note) {
        openDialog(comingFromFAB = false)
    }

    override fun deleteNote(note: Note) {
        notesViewModel.delete(note)
    }
}