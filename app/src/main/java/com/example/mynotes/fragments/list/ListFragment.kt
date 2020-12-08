package com.example.mynotes.fragments.list

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mynotes.R
import com.example.mynotes.data.Note
import com.example.mynotes.data.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.play.core.internal.m


abstract class ListFragment : Fragment() {
    private  lateinit var mNoteViewModel: NoteViewModel
   lateinit var  note: Note

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //note= arguments!!["note"] as Note
        // var view= inflater.inflate(R.layout.fragment_list, container, false)

            val view = inflater.inflate(R.layout.activity_main, null)
            val adapter = ListAdapter()
            // Gild.displayImage(this, note.imageResId,addimage_view)

            val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_list)

            recyclerView.adapter = adapter

            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            //val imageView:ImageView=view.findViewById(R.id.addimage_view)
            // Gild.displayImage(this, note?.imageResId,addimage_view)
            mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
            mNoteViewModel.allNotes.observe(viewLifecycleOwner, Observer { note ->
                adapter.setdata(note)
            })
            view.findViewById<FloatingActionButton>(R.id.floatingActionButton2).setOnClickListener {
                findNavController().navigate(R.id.action_listFragment_to_addFragment)

            }
            setHasOptionsMenu(true)

            return view


    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_item) {
            deleteNotes()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteNotes() {
        val builder= AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_->}
        mNoteViewModel.clearbyId()

        builder.setNegativeButton("nO"){ _ ,_-> }
        builder.setTitle("Delete note number all notes")
        builder.setMessage("A you shure?")
        builder.create().show()
    }
    class Gild{
        companion object {
            fun displayImage(fragment: ListFragment, load: String?, into: ImageView) {
                Glide.with(fragment)
                    .load(load)

                    .into(into)

            }
        }
    }


}


