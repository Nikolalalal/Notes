package com.example.mynotes.fragments.add

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mynotes.R
import com.example.mynotes.data.Note
import com.example.mynotes.data.NoteViewModel
import com.example.mynotes.fragments.update.UpdateFragment
import com.yalantis.ucrop.UCrop
import kotlinx.android.synthetic.main.fragment_add.*
import java.lang.ref.WeakReference


class AddFragment : Fragment() {
     lateinit var mNoteViewModel: NoteViewModel

   lateinit  var imageUri: String
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        imageUri = ""
        // Inflate the layout for this fragmen
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        //var imageView: ImageView = view.findViewById(R.id.imageView_add)
        view.findViewById<Button>(R.id.button_add).setOnClickListener() {
            insertDataToDatabase()
//            Gild.displayImage(this, imageUri,imageView_add)

        }
        view.findViewById<Button>(R.id.button_gidle).setOnClickListener() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (requireContext().checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions,
                            PERMISSION_CODE
                    )
                } else {

                    pickphotofromGallery()
                }
            } else {
                pickphotofromGallery()
            }


        }
        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        return view
    }

    private fun pickphotofromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,
                IMAGE_PICK_CODE
        )
    }

    private fun insertDataToDatabase() {
        val title: String = view?.findViewById<EditText>(R.id.editText_title)?.text.toString()
        val text: String = view?.findViewById<EditText>(R.id.editext_context)?.text.toString()
        if (inputCheck(title, text)) {
            val note = Note(0, title, text)
            mNoteViewModel.insert(note)
            Toast.makeText(context, "Successfully", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Not Successfully", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(title: String, text: String): Boolean {
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(text))
    }

   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
         imageUri = data!!.data!!.toString()
           var name = System.currentTimeMillis().toString()
            mNoteViewModel.copyTempImageToInternalStorage(Uri.parse(imageUri), WeakReference(requireContext().applicationContext),name)
           Gild.displayImage(this, imageUri,imageView_add )
       }
        else if (requestCode== UCrop.REQUEST_CROP && resultCode== Activity.RESULT_OK){

           imageUri = UCrop.getOutput(data!!).toString()


        }
    }
    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;

        //Permission code
        private val PERMISSION_CODE = 1001;
    }
    class Gild{
        companion object {
            fun displayImage(fragment: Fragment, load: String, into:ImageView ) {
                Glide.with(fragment)
                        .load(load)

                        .into(into)

            }
        }
    }
}

