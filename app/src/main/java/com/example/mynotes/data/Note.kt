package com.example.mynotes.data


import android.os.Parcelable
import android.widget.ImageView
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Note_table")
data class Note(
        @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="noteId")
        var noteId: Long = 0L,
        @ColumnInfo(name = "title")
        val title:String=" ",
        @ColumnInfo(name = "context")
        val text:String=" ",
       @ColumnInfo(name="note_time")
        var noteTime:Long=System.currentTimeMillis(),
        @ColumnInfo(name="image_view")
        val imageid:String?=null,
        @ColumnInfo(name = "item_image")
        var imageResId:String?=null

):Parcelable