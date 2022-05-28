package com.robotech.todo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.robotech.todo.model.Note
import com.robotech.todo.model.NoteViewModel
import com.robotech.todo.databinding.ActivityAddEditNoteBinding
import com.robotech.todo.R

class AddEditNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditNoteBinding

    lateinit var viewModal: NoteViewModel
    var noteID = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModal = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(
            NoteViewModel::class.java)


        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")) {
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDescription = intent.getStringExtra("noteDescription")
            noteID = intent.getIntExtra("noteId", -1)
            binding.idBtn.setText("Update Note")
            binding.idEdtNoteName.editText?.setText(noteTitle)
            binding.idEdtNoteDesc.editText?.setText(noteDescription)
        } else {
            binding.idBtn.setText("Save Note")
        }

        binding.idBtn.setOnClickListener {
            val noteTitle = binding.idEdtNoteName.editText?.text.toString()
            val noteDescription = binding.idEdtNoteDesc.editText?.text.toString()

            if (noteType.equals("Edit")) {
                if (noteTitle.isNotEmpty()) {
                    val updatedNote = Note(noteTitle, noteDescription)
                    updatedNote.id = noteID
                    viewModal.updateNote(updatedNote)
                    Toast.makeText(this, "Note Updated..", Toast.LENGTH_LONG).show()
                }
            } else {
                if (noteTitle.isNotEmpty()) {
                    viewModal.addNote(Note(noteTitle, noteDescription))
                    Toast.makeText(this, "$noteTitle Added", Toast.LENGTH_LONG).show()
                }
            }
            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()
        }
    }
}