package com.sallyjayz.ranchid.veterinaryDoctor.livestockTreatment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.MaterialDatePicker
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentLivestockTreatmentFourBinding
import com.sallyjayz.ranchid.viewmodel.TokenViewModel
import com.sallyjayz.ranchid.viewmodel.vet.LivestockTreatmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@AndroidEntryPoint
class LivestockTreatmentFourFragment : Fragment() {

    private lateinit var binding: FragmentLivestockTreatmentFourBinding
    private val sharedViewModel: LivestockTreatmentViewModel by activityViewModels()
    private val tokenViewModel: TokenViewModel by activityViewModels()
    private var uploadPhotoFile: File? = null
    private var base64UploadString: String? = null
    private var uploadPhotoSize: String? = null
    private lateinit var mCurrentPhotoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                sharedViewModel.resetLivestockTreatmentTwo()
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLivestockTreatmentFourBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            stepFourViewModel = sharedViewModel
            stepFourFragment = this@LivestockTreatmentFourFragment
        }
    }

    private val uploadMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        val byteArrayOutputStream = ByteArrayOutputStream()
        if (uri != null) {
            uploadPhotoFile = uriToFile(uri)
            val imageBitmap = BitmapFactory.decodeFile(uploadPhotoFile?.absolutePath)
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream)
            val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
            base64UploadString = Base64.encodeToString(imageBytes, Base64.DEFAULT)
            val imageSize =  imageBytes.size/1024.0
            uploadPhotoSize = String.format("%.2f", imageSize)

            Glide.with(requireContext())
                .load(uri)
                .override(150, 150)
                .centerCrop()
                .into(binding.cameraImage)

            binding.cameraImage.isVisible = true
            binding.imageName.isVisible = true
            binding.imageSize.isVisible = true
            binding.reCaptureBtn.isVisible = true
            binding.captureBtn.isVisible = false
            binding.cameraIcon.isVisible = false
            binding.imageName.text = uploadPhotoFile?.name
            binding.imageSize.text = "$uploadPhotoSize Kb"
        } else {
            binding.cameraImage.isVisible = false
            binding.imageName.isVisible = false
            binding.imageSize.isVisible = false
            binding.reCaptureBtn.isVisible = false
            binding.captureBtn.isVisible = true
            binding.cameraIcon.isVisible = true
            binding.cameraImage.setImageURI(null)
            binding.imageName.text = ""
            binding.imageSize.text = ""
        }
    }

    fun uploadImage() {
        val mimeType = "image/*"
        uploadMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.SingleMimeType(mimeType)))
    }

    private fun createImageFile(): File? {

        val imageDirectory = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "RanchId_",
            ".jpg",
            imageDirectory
        ).apply {
            mCurrentPhotoPath = absolutePath
        }
    }

    private fun uriToFile(uri: Uri): File? {
        val tempFile = createImageFile()
        requireContext().contentResolver.openInputStream(uri)?.let { inputStream ->
            val fileOutputStream = FileOutputStream(tempFile)
            inputStream.copyTo(fileOutputStream)
            inputStream.close()
        }
        return tempFile
    }

    fun followUpDate() {
        val datePicker =
            MaterialDatePicker.Builder
                .datePicker()
//                .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
                .setTitleText(getString(R.string.select_follow_up_date))
                .build()

        datePicker.show(requireActivity().supportFragmentManager, "followUpPicker")
        datePicker.addOnPositiveButtonClickListener {
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = dateFormatter.format(Date(it))
            binding.followUpDate.setText(date)
        }

        datePicker.addOnNegativeButtonClickListener {
            binding.followUpDate.setText("")
        }

        datePicker.addOnCancelListener {
            binding.followUpDate.setText("")
        }
    }

    fun cancelButtonClicked() {
        val action = LivestockTreatmentFourFragmentDirections
            .actionLivestockTreatmentFourFragmentToLivestockTreatmentThreeFragment("")
        findNavController().navigate(action)
    }

    fun nextButtonClicked() {

        if (uploadPhotoFile == null) {
            Toast.makeText(requireContext(), "photo required", Toast.LENGTH_LONG).show()
        } else if (isStepFourEntryValid()) {
            sharedViewModel.setStepFour(
                uploadPhotoFile?.absolutePath.toString(),
                "data:image/jpeg;base64,$base64UploadString",
                uploadPhotoFile?.name.toString(),
                "$uploadPhotoSize Kb",
                binding.followUpDate.text.toString(),
                binding.tagNotes.text.toString()

            )
            val action = LivestockTreatmentFourFragmentDirections
                .actionLivestockTreatmentFourFragmentToLivestockTreatmentConfirmationFragment()
            findNavController().navigate(action)
        } else {
            binding.errorTv.text = getString(R.string.all_fields_required)
        }
    }

    private fun isStepFourEntryValid(): Boolean {
        return sharedViewModel.isStepFourEntryValid(
            uploadPhotoFile?.absolutePath.toString(),
            uploadPhotoFile?.name.toString(),
            uploadPhotoSize.toString(),
            binding.followUpDate.text.toString(),
            binding.tagNotes.text.toString()
        )
    }


}