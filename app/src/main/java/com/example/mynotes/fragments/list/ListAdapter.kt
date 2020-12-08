package com.example.mynotes.fragments.list

import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.ListFragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.ListFragmentDirections
import com.example.mynotes.R
import com.example.mynotes.R.id.fill
import com.example.mynotes.R.id.id
import com.example.mynotes.data.Note
import com.example.mynotes.fragments.add.AddFragment
import kotlinx.android.synthetic.main.customrow.view.*
import org.w3c.dom.Text
import android.widget.TextView as TextView

class ListAdapter :RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    lateinit var note: Note
    private var noteList= emptyList<Note>()
    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.customrow,parent,false))

    }

    override fun getItemCount(): Int {
     return noteList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem=noteList[position]
       holder.itemView.findViewById<TextView>(R.id.title_txt).text=currentItem.title
        holder.itemView.findViewById<TextView>(R.id.text_txt).text=currentItem.text
        holder.itemView.findViewById<TextView>(R.id.id).text=currentItem.noteId.toString()
        holder.itemView.findViewById<TextView>(R.id.date).text=currentItem.noteTime.toString()

        holder.itemView.rowLayout.setOnClickListener {
val action= ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }
    fun setdata(note:List<Note>){
        this.noteList=note
        notifyDataSetChanged()
    }
}