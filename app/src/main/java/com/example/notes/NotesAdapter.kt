package com.example.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(
    private val listOfNotes : List<com.example.notes.Note>,
    private val clicklistner: Clicklistner
):RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    class NotesViewHolder(
        itemView: View
    ): RecyclerView.ViewHolder(itemView) {
        val textname: TextView =itemView.findViewById(R.id.name_of_note)
        val textcontent: TextView = itemView.findViewById(R.id.content)
        val deleteimg : ImageView = itemView.findViewById(R.id.delete_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_rv,parent,false)
        return NotesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfNotes.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentNote = listOfNotes[position]
        holder.textname.text = currentNote.noteName
        holder.textcontent.text = currentNote.noteContent

        holder.itemView.setOnClickListener {
            clicklistner.updateNote(currentNote)
        }

        holder.deleteimg.setOnClickListener {
            clicklistner.deleteNote(currentNote)
        }
    }
    interface Clicklistner{
        fun updateNote(note:Note)
        fun deleteNote(note: Note)
    }
}