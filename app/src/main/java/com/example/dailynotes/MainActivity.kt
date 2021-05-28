package com.example.dailynotes

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailynotes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), INotesRVAdapter {


    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = NotesRVAdapter(this,this)
        binding.recyclerView.adapter = adapter

       viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
       viewModel.allNotes.observe(this, Observer {list->
           list?.let{
               adapter.updateList(it)
           }


       })


    }

    override fun onItemClicked(note: Note) {

        viewModel.deleteNote(note)
        Toast.makeText(this,"${note.text} deleted",Toast.LENGTH_LONG).show()
    }

    fun submitData(view: View) {
        val noteText = binding.input.text.toString()
        if (noteText.isNotEmpty()){
            viewModel.insertNote(Note(noteText))
            Toast.makeText(this,"$noteText inserted",Toast.LENGTH_LONG).show()
            binding.input.setText("")
        }
    }

}


