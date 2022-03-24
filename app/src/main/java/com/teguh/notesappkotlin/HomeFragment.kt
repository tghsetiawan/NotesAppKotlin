package com.teguh.notesappkotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.teguh.notesappkotlin.adapter.NotesAdapter
import com.teguh.notesappkotlin.database.NotesDatabase
import com.teguh.notesappkotlin.databinding.FragmentHomeBinding
import com.teguh.notesappkotlin.entities.Notes
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    var arrNotes = ArrayList<Notes>()
    var notesAdapter: NotesAdapter = NotesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
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
                notesAdapter.setData(notes)
                arrNotes = notes as ArrayList<Notes>
                binding.reyclerView.adapter = notesAdapter
            }
        }

        notesAdapter.setOnClickListener(onClicked)

        binding.fabCreateNote.setOnClickListener {
            //edit karena kalo sesuai tutorial nilai noteId berubah menjadi 0
            var fragment: Fragment
            var bundle = Bundle()
            bundle.putInt("noteId", -1)
            fragment = CreateNoteFragment.newInstance()
            fragment.arguments = bundle
            replaceFragment(fragment, false)

            //sesuai tutorial
//            replaceFragment(CreateNoteFragment.newInstance(), false)
        }

        binding.searchView.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                var tempArr = ArrayList<Notes>()

                for (arr in arrNotes){
                    if(arr.title!!.toLowerCase(Locale.getDefault()).contains(p0.toString())){
                        tempArr.add(arr)
                    }
                }

                notesAdapter.setData(tempArr)
                notesAdapter.notifyDataSetChanged()
                return true
            }
        })
    }

    private val onClicked = object : NotesAdapter.OnItemClickListener{
        override fun onClicked(noteId: Int) {
            var fragment: Fragment
            var bundle = Bundle()
            bundle.putInt("noteId", noteId)
            fragment = CreateNoteFragment.newInstance()
            fragment.arguments = bundle

            replaceFragment(fragment, false)
        }

    }

    fun replaceFragment(fragment: Fragment, istransition:Boolean){
        val fragmentTransition = activity?.supportFragmentManager?.beginTransaction()

        if(istransition){
            fragmentTransition!!.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.fade_out, android.R.anim.slide_in_left, android.R.anim.fade_in)
        }

//        fragmentTransition.replace(R.id.frame_layout, fragment).addToBackStack(fragment.javaClass.simpleName)

        fragmentTransition!!.replace(R.id.frame_layout, fragment).addToBackStack(fragment.javaClass.simpleName).commitAllowingStateLoss()
    }
}