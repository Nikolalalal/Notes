package com.example.mynotes.data

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions

import com.example.mynotes.data.NoteDatabase.Companion.getInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.lang.ref.WeakReference

class NoteViewModel (application: Application):AndroidViewModel(application) {
    val allNotes: LiveData<List<Note>>
    private val repository: NoteRepository
    val tempImageUri: MutableLiveData<String> by lazy {
        MutableLiveData<String>()

    }

    init {
        val noteDatabaseDao = getInstance(application).noteDatabaseDao
        repository = NoteRepository(noteDatabaseDao)
        allNotes = repository.allNotes
        tempImageUri.value = ""
    }

    fun insert(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(note)
            tempImageUri.value = ""}
    }

    fun clearbyId() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearbyId()
        }
    }

    fun update(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(note)
            tempImageUri.value = ""
        }
    }

    fun deletebyid(note: Note) {
        viewModelScope.launch(Dispatchers.IO) { repository.deletebyid(note) }
    }

    fun copyTempImageToInternalStorage(uri: Uri, mContext: WeakReference<Context>, fileName: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val uri = uri
            val requestOptions = RequestOptions()
                    .downsample(DownsampleStrategy.CENTER_INSIDE)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)

            mContext.get()?.let {
                val bitmap = Glide.with(it)
                        .asBitmap()
                        .load(uri)
                        .apply(requestOptions)
                        .submit()
                        .get()
                try {

                    var file = File(it.filesDir, "TempImages")
                    if (!file.exists()) {
                        file.mkdir()
                    }
                    file = File(file, "$fileName.jpg")
                    val out = FileOutputStream(file)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                    out.flush()
                    out.close()

                    tempImageUri.postValue(file.toURI().toString())
                    Log.i("MainActivityViewModel", "Image saved)")
                } catch (e: Exception) {
                    Log.i("MainActivityViewModel", "Failed to save image. ")
                    e.printStackTrace()
                }
            }
        }

    }
}



