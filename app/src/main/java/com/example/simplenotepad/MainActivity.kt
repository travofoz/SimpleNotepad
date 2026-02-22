package com.example.simplenotepad

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var noteEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var loadButton: Button
    private lateinit var clearButton: Button

    private val notesFile: File by lazy {
        File(filesDir, "notes.txt")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupClickListeners()
        loadNotesOnStart()
    }

    private fun initViews() {
        noteEditText = findViewById(R.id.noteEditText)
        saveButton = findViewById(R.id.saveButton)
        loadButton = findViewById(R.id.loadButton)
        clearButton = findViewById(R.id.clearButton)
    }

    private fun setupClickListeners() {
        saveButton.setOnClickListener {
            saveNotes()
        }

        loadButton.setOnClickListener {
            loadNotes()
        }

        clearButton.setOnClickListener {
            clearNotes()
        }
    }

    private fun saveNotes() {
        try {
            notesFile.writeText(noteEditText.text.toString())
            Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error saving: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadNotes() {
        try {
            if (notesFile.exists()) {
                noteEditText.setText(notesFile.readText())
                Toast.makeText(this, getString(R.string.loaded), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No saved notes found", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error loading: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadNotesOnStart() {
        try {
            if (notesFile.exists()) {
                noteEditText.setText(notesFile.readText())
            }
        } catch (e: Exception) {
            // Silently fail on auto-load
        }
    }

    private fun clearNotes() {
        noteEditText.text?.clear()
        Toast.makeText(this, getString(R.string.cleared), Toast.LENGTH_SHORT).show()
    }
}
