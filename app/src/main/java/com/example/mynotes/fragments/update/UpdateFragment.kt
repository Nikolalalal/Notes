package com.example.mynotes.fragments.update

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mynotes.R
import com.example.mynotes.R.id.delete_item
import com.example.mynotes.UpdateFragmentArgs
import com.example.mynotes.data.Note
import com.example.mynotes.data.NoteViewModel
import kotlinx.android.synthetic.main.customrow.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*


class UpdateFragment : Fragment() {
    private lateinit var mNoteViewModel: NoteViewModel
    private val args by navArgs<UpdateFragmentArgs>()
    lateinit var imageView: ImageView
    lateinit var imageUri:String
    lateinit var note: Note


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        note= arguments!!["note"] as Note
        imageUri=""
        //imageView=imageViewup
        // Inflate the layout for this fragment
        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_update, container, false)
        view.upeditText_title.setText(args.currentNote.title)
        view.upeditext_context.setText(args.currentNote.text)


        view.button_update.setOnClickListener {
            updatenote()
        }
        setHasOptionsMenu(true)
        return view
    }

    class Gild{
        companion object {
            fun displayImage(fragment:UpdateFragment, load: String?, into:ImageView ) {
                Glide.with(fragment)
                        .load(load)
                        .into(into)

            }
        }
    }

    private fun updatenote() {
        val title = upeditText_title.text.toString()
        val text = upeditext_context.text.toString()
        if (inputCheck(title, text)) {
            val updatedNote = Note(args.currentNote.noteId, title, text)
            mNoteViewModel.update(updatedNote)
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
    }

    private fun inputCheck(title: String, text: String): Boolean {
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(text))
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == delete_item) {
            deleteNote()
        }
        return super.onOptionsItemSelected(item)
    }



    private fun deleteNote() {
        val builder=AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_->}
        mNoteViewModel.deletebyid(args.currentNote)

        builder.setNegativeButton("nO"){ _ ,_-> }
        builder.setTitle("Delete note number ${args.currentNote.noteId}")
        builder.setMessage("A you shure?")
        builder.create().show()
        findNavController().navigate(R.id.action_updateFragment_to_listFragment)
    }

}