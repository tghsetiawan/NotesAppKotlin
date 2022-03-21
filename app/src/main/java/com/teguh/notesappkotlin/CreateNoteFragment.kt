package com.teguh.notesappkotlin

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.text.SimpleDateFormat
import java.util.*

class CreateNoteFragment : BaseFragment(),EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {

    private lateinit var binding : FragmentCreateNoteBinding
    private var currentDate:String? = null
    private var READ_STORAGE_PERM = 123
    private var REQUEST_CODE_IMAGE = 456
    private var selectedImagePath = ""
    var selectedColor = "#363E50"

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
        else if (binding.etNoteSubTitle.text.isNullOrEmpty()) {
            Toast.makeText(activity, "Note Sub Title is Required", Toast.LENGTH_SHORT).show()
        }
        else if (binding.etNoteDescription.text.isNullOrEmpty()) {
            Toast.makeText(activity, "Note Description must not be null", Toast.LENGTH_SHORT).show()
        }
        else {
            launch {
                var notes = Notes()
                notes.title = binding.etNoteTitle.text.toString()
                notes.subTitle = binding.etNoteSubTitle.text.toString()
                notes.noteText = binding.etNoteDescription.text.toString()
                notes.dateTime = currentDate
                notes.color = selectedColor
                notes.imgPath = selectedImagePath
                context?.let {
                    NotesDatabase.getDatabase(it).noteDao().insertNotes(notes)
                    binding.etNoteTitle.setText("")
                    binding.etNoteSubTitle.setText("")
                    binding.etNoteDescription.setText("")
                    binding.ivNote.visibility = View.GONE
                    requireActivity().supportFragmentManager.popBackStack()
                }
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
            val actionNote = p1!!.getStringExtra("actionNote")

            when(actionNote!!){
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

                "Image" -> {
                    readStorageTask()
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

    private fun hasReadStoragePerm():Boolean{
        return EasyPermissions.hasPermissions(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
    }

//    private fun hasWriteStoragePerm():Boolean{
//        return EasyPermissions.hasPermissions(requireContext(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//    }

    private fun readStorageTask(){
        if(hasReadStoragePerm()){
            pickImageFromGallery()
        } else {
            EasyPermissions.requestPermissions(requireActivity(), getString(R.string.storage_permission_text), READ_STORAGE_PERM, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun pickImageFromGallery(){
        var intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if(intent.resolveActivity(requireActivity().packageManager) != null){
            startActivityForResult(intent, REQUEST_CODE_IMAGE)
        }
    }

    private fun getPathFromUri(contentUri: Uri): String?{
        var filePath:String? = null
        var cursor = requireActivity().contentResolver.query(contentUri, null, null, null,null)
        if(cursor == null){
            filePath = contentUri.path
        } else {
            cursor.moveToFirst()
            var index = cursor.getColumnIndex("_data")
            filePath = cursor.getString(index)
            cursor.close()
        }
        return filePath
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK){
            if(data != null){
                var selectedImageUrl = data.data
                if(selectedImageUrl != null){
                    try{
                        var inputStream = requireActivity().contentResolver.openInputStream(selectedImageUrl)
                        var bitmap = BitmapFactory.decodeStream(inputStream)
                        binding.ivNote.setImageBitmap(bitmap)
                        binding.ivNote.visibility = View.VISIBLE

                        selectedImagePath = getPathFromUri(selectedImageUrl)!!
                    } catch (e:Exception){
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, requireActivity())
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        TODO("Not yet implemented")
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(requireActivity(),perms)){
            AppSettingsDialog.Builder(requireActivity()).build().show()
        }
    }

    override fun onRationaleAccepted(requestCode: Int) {
        TODO("Not yet implemented")
    }

    override fun onRationaleDenied(requestCode: Int) {
        TODO("Not yet implemented")
    }
}