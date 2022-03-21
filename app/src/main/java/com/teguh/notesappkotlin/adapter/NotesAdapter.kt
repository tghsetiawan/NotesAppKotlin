package com.teguh.notesappkotlin.adapter

import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teguh.notesappkotlin.R
import com.teguh.notesappkotlin.databinding.ItemRvNotesBinding
import com.teguh.notesappkotlin.entities.Notes

class NotesAdapter(private val arrList: List<Notes>) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(ItemRvNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.noteTitle.text = arrList[position].title
        holder.dateTime.text = arrList[position].dateTime
        holder.noteDesc.text = arrList[position].noteText

//        if(arrList[position].color != null){
//            holder.colorNote.setBackgroundColor(Color.parseColor(arrList[position].color))
////            holder.colorNote.setBackgroundColor(Color.parseColor("#EAEDED"))
//        } else if (arrList[position].color.isNullOrEmpty()) {
//            holder.colorNote.setBackgroundColor(Color.parseColor("#363E50"))
//        }
//        else {
//            holder.colorNote.setBackgroundColor(Color.parseColor("#363E50"))
//        }

        if (arrList[position].color.isNullOrEmpty()) {
            holder.colorNote.getBackground().setTint(Color.parseColor("#363E50"))
        } else {
            holder.colorNote.getBackground().setTint(Color.parseColor(arrList[position].color))
        }

        if(arrList[position].imgPath != null){
            holder.imgNote.setImageBitmap(BitmapFactory.decodeFile(arrList[position].imgPath))
            holder.imgNote.visibility = View.VISIBLE
        } else {
            holder.imgNote.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return arrList.size
    }

    class NotesViewHolder(private val binding:ItemRvNotesBinding) : RecyclerView.ViewHolder(binding.root){
        val noteTitle = binding.tvTitle
        val dateTime = binding.tvDatetime
        val noteDesc = binding.tvDesc
        val colorNote = binding.cvNote
        val imgNote = binding.rivImgNote
    }
}
