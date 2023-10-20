package com.teguh.notesappkotlin.util

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teguh.notesappkotlin.R
import com.teguh.notesappkotlin.databinding.FragmentNotesBottomSheetBinding


class NoteBottomSheetFragment : BottomSheetDialogFragment (){
    var selectedColor = "#363E50"
    private lateinit var binding : FragmentNotesBottomSheetBinding

    companion object {
        var noteId = -1
        fun newInstance(id:Int): NoteBottomSheetFragment{
            val args = Bundle()
            val fragment = NoteBottomSheetFragment()
            fragment.arguments = args
            noteId = id
            return fragment
        }
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        val view = LayoutInflater.from(context).inflate(R.layout.fragment_notes_bottom_sheet, null)
        dialog.setContentView(view)

        val param = (view.parent as View).layoutParams as CoordinatorLayout.LayoutParams
        val behavior = param.behavior

        if(behavior is BottomSheetBehavior<*>){
            behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                }

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    var state = ""
                    when (newState){
                        BottomSheetBehavior.STATE_DRAGGING -> {
                            state = "DRAGGING"
                        }
                        BottomSheetBehavior.STATE_SETTLING -> {
                            state = "SETTLING"
                        }
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            state = "EXPANDED"
                        }
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            state = "COLLAPSED"
                        }
                        BottomSheetBehavior.STATE_HIDDEN -> {
                            state = "HIDDEN"
                            dismiss()
                            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                        }

                        BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                            TODO()
                        }
                    }
                }
            })
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNotesBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(noteId != -1){
            binding.layoutDeleteNote.visibility = View.VISIBLE
        } else {
            binding.layoutDeleteNote.visibility = View.GONE
        }
        setListener()
    }

    private fun setListener(){
        binding.flNote1.setOnClickListener {
            binding.ivNote1.setImageResource(R.drawable.ic_tick)
            binding.ivNote2.setImageResource(0)
            binding.ivNote3.setImageResource(0)
            binding.ivNote4.setImageResource(0)
            binding.ivNote5.setImageResource(0)
            binding.ivNote7.setImageResource(0)
            selectedColor = "#118AE6"
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("actionNote", "Blue")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
//            dismiss()
        }
        binding.flNote2.setOnClickListener {
            binding.ivNote1.setImageResource(0)
            binding.ivNote2.setImageResource(R.drawable.ic_tick)
            binding.ivNote3.setImageResource(0)
            binding.ivNote4.setImageResource(0)
            binding.ivNote5.setImageResource(0)
            binding.ivNote7.setImageResource(0)
            selectedColor = "#EAD141"
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("actionNote", "Yellow")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
//            dismiss()
        }
        binding.flNote3.setOnClickListener {
            binding.ivNote1.setImageResource(0)
            binding.ivNote2.setImageResource(0)
            binding.ivNote3.setImageResource(R.drawable.ic_tick)
            binding.ivNote4.setImageResource(0)
            binding.ivNote5.setImageResource(0)
            binding.ivNote7.setImageResource(0)
            selectedColor = "#F18933"
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("actionNote", "Orange")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
//            dismiss()
        }
        binding.flNote4.setOnClickListener {
            binding.ivNote1.setImageResource(0)
            binding.ivNote2.setImageResource(0)
            binding.ivNote3.setImageResource(0)
            binding.ivNote4.setImageResource(R.drawable.ic_tick)
            binding.ivNote5.setImageResource(0)
            binding.ivNote7.setImageResource(0)
            selectedColor = "#3EBF13"
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("actionNote", "Green")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
//            dismiss()
        }
        binding.flNote5.setOnClickListener {
            binding.ivNote1.setImageResource(0)
            binding.ivNote2.setImageResource(0)
            binding.ivNote3.setImageResource(0)
            binding.ivNote4.setImageResource(0)
            binding.ivNote5.setImageResource(R.drawable.ic_tick)
            binding.ivNote7.setImageResource(0)
            selectedColor = "#9648F4"
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("actionNote", "Purple")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
//            dismiss()
        }
        binding.flNote7.setOnClickListener {
            binding.ivNote1.setImageResource(0)
            binding.ivNote2.setImageResource(0)
            binding.ivNote3.setImageResource(0)
            binding.ivNote4.setImageResource(0)
            binding.ivNote5.setImageResource(0)
            binding.ivNote7.setImageResource(R.drawable.ic_tick)
            selectedColor = "#202734"
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("actionNote", "Black")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
//            dismiss()
        }

        binding.layoutImageBar.setOnClickListener {
            dismiss()
        }

        binding.layoutAddImage.setOnClickListener {
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("actionNote", "Image")
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }

        binding.layoutWebUrl.setOnClickListener {
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("actionNote", "WebUrl")
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            dismiss()
        }

        binding.layoutDeleteNote.setOnClickListener {
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("actionNote", "DeleteNote")
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            dismiss()
        }

    }
}