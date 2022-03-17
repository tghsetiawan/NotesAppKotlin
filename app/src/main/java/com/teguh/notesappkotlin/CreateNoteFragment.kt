package com.teguh.notesappkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.teguh.notesappkotlin.database.NotesDatabase
import com.teguh.notesappkotlin.databinding.FragmentCreateNoteBinding
import com.teguh.notesappkotlin.entities.Notes
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CreateNoteFragment : BaseFragment() {

    private lateinit var binding : FragmentCreateNoteBinding
    private var currentDate:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_create_note, container, false)
        binding = FragmentCreateNoteBinding.inflate(inflater)
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
        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())

        binding.tvDatetime.text = currentDate

        binding.ivDone.setOnClickListener {
            //saveNote
            saveNote()
        }

        binding.ivBack.setOnClickListener {
            replaceFragment(HomeFragment.newInstance(), false)
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

            context?.let {
                NotesDatabase.getDatabase(it).noteDao().insertNotes(notes)
                binding.etNoteTitle.setText("")
                binding.etNoteSubTitle.setText("")
                binding.etNoteDescription.setText("")
            }
        }
    }

    fun replaceFragment(fragment: Fragment, istransition:Boolean){
        val fragmentTransition = activity?.supportFragmentManager?.beginTransaction()

        if(istransition){
            fragmentTransition!!.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.fade_out, android.R.anim.slide_in_left, android.R.anim.fade_in)
        }

//        fragmentTransition.replace(R.id.frame_layout, fragment).addToBackStack(fragment.javaClass.simpleName)

        fragmentTransition!!.replace(R.id.frame_layout, fragment).commitAllowingStateLoss()
    }
}