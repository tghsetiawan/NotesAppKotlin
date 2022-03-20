package com.teguh.notesappkotlin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.teguh.notesappkotlin.database.NotesDatabase
import com.teguh.notesappkotlin.databinding.FragmentCreateNoteBinding
import com.teguh.notesappkotlin.entities.Notes
import com.teguh.notesappkotlin.util.NoteBottomSheetFragment
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CreateNoteFragment : BaseFragment() {

    private lateinit var binding : FragmentCreateNoteBinding
    private var currentDate:String? = null
    var selectedColor = "#FF171c26"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CreateNoteFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            BroadcastReceiver, IntentFilter("bottom_sheet_action")
        )

        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")

        currentDate = sdf.format(Date())

        binding.viewColor.setBackgroundColor(Color.parseColor(selectedColor))

        binding.tvDatetime.text = currentDate

        binding.ivDone.setOnClickListener {
            //saveNote
            saveNote()
        }

        binding.ivBack.setOnClickListener {
//            replaceFragment(HomeFragment.newInstance(), false)
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.ivMore.setOnClickListener {
            var noteBottomSheetFragment = NoteBottomSheetFragment.newInstance()
            noteBottomSheetFragment.show(requireActivity().supportFragmentManager, "Note Bottom Sheet Fragment")
        }
    }

    private fun saveNote() {
        if (binding.etNoteTitle.text.isNullOrEmpty()) {
            Toast.makeText(activity, "Note Title is Required", Toast.LENGTH_SHORT).show()
        }
        if (binding.etNoteSubTitle.text.isNullOrEmpty()) {
            Toast.makeText(activity, "Note Sub Title is Required", Toast.LENGTH_SHORT).show()
        }
        if (binding.etNoteDescription.text.isNullOrEmpty()) {
            Toast.makeText(activity, "Note Description must not be null", Toast.LENGTH_SHORT).show()
        }
        launch {
            var notes = Notes()
            notes.title = binding.etNoteTitle.text.toString()
            notes.subTitle = binding.etNoteSubTitle.text.toString()
            notes.noteText = binding.etNoteDescription.text.toString()
            notes.dateTime = currentDate
            notes.color = selectedColor

            context?.let {
                NotesDatabase.getDatabase(it).noteDao().insertNotes(notes)
                binding.etNoteTitle.setText("")
                binding.etNoteSubTitle.setText("")
                binding.etNoteDescription.setText("")
            }
        }
    }

//    fun replaceFragment(fragment: Fragment, istransition:Boolean){
//        val fragmentTransition = activity?.supportFragmentManager?.beginTransaction()
//
//        if(istransition){
//            fragmentTransition!!.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.fade_out, android.R.anim.slide_in_left, android.R.anim.fade_in)
//        }
//
////        fragmentTransition.replace(R.id.frame_layout, fragment).addToBackStack(fragment.javaClass.simpleName)
//
//        fragmentTransition!!.replace(R.id.frame_layout, fragment).commitAllowingStateLoss()
//    }

    private val BroadcastReceiver : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            val actionColor = p1!!.getStringExtra("actionColor")

            when(actionColor!!){
                "Blue" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    binding.viewColor.setBackgroundColor(Color.parseColor(selectedColor))
                }

                "Yellow" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    binding.viewColor.setBackgroundColor(Color.parseColor(selectedColor))
                }

                "Orange" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    binding.viewColor.setBackgroundColor(Color.parseColor(selectedColor))
                }

                "Green" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    binding.viewColor.setBackgroundColor(Color.parseColor(selectedColor))
                }

                "Purple" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    binding.viewColor.setBackgroundColor(Color.parseColor(selectedColor))
                }

                "Black" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    binding.viewColor.setBackgroundColor(Color.parseColor(selectedColor))
                }

                else -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    binding.viewColor.setBackgroundColor(Color.parseColor(selectedColor))
                }
            }
        }
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(BroadcastReceiver)
        super.onDestroy()
    }
}