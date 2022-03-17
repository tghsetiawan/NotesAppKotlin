package com.teguh.notesappkotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.teguh.notesappkotlin.adapter.NotesAdapter
import com.teguh.notesappkotlin.database.NotesDatabase
import com.teguh.notesappkotlin.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.reyclerView.setHasFixedSize(true)
        binding.reyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        launch {
            context?.let {
                var notes = NotesDatabase.getDatabase(it).noteDao().getAllNotes()
                binding.reyclerView.adapter = NotesAdapter(notes)
            }
        }

        binding.fabCreateNote.setOnClickListener {
            replaceFragment(CreateNoteFragment.newInstance(), true)
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