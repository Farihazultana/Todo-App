package com.robotech.todo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.robotech.todo.adapter.NoteAdapter
import com.robotech.todo.adapter.NoteClickDeleteInterface
import com.robotech.todo.adapter.NoteClickInterface
import com.robotech.todo.model.Note
import com.robotech.todo.model.NoteViewModel
import com.robotech.todo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NoteClickInterface, NoteClickDeleteInterface {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModal: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.notesRV.layoutManager = LinearLayoutManager(this)
        binding.notesRV.setHasFixedSize(true)

        val noteAdapter = NoteAdapter(this, this, this)

        binding.notesRV.adapter = noteAdapter

        viewModal = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(
            NoteViewModel::class.java)

        viewModal.allNotes.observe(this, Observer { list -> list?.let { noteAdapter.updateList(it) } })

        binding.idFAB.setOnClickListener { startActivity(Intent(applicationContext, AddEditNoteActivity::class.java)) }
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.noteTitle)
        intent.putExtra("noteDescription", note.noteDescription)
        intent.putExtra("noteId", note.id)
        startActivity(intent)
    }

    override fun onDeleteIconClick(note: Note) {
        viewModal.deleteNote(note)
        Toast.makeText(this, "${note.noteTitle} Deleted", Toast.LENGTH_LONG).show()
    }
}