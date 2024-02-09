package com.sallyjayz.ranchid.register.taglivestock

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.utils.Util
import com.sallyjayz.ranchid.databinding.FragmentTagLivestockStepThreeBinding
import com.sallyjayz.ranchid.viewmodel.register.FarmLocationViewModel
import com.sallyjayz.ranchid.viewmodel.register.TagLivestockViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class TagLivestockStepThreeFragment : Fragment() {

    private lateinit var binding: FragmentTagLivestockStepThreeBinding
    private var selectedProductionType: String? = null
//    private lateinit var selectedProductionType: String
    private val sharedViewModel: TagLivestockViewModel by activityViewModels()
//    private lateinit var photoFile: File
    private var verificationPhotoFile: File? = null
    private var muzzlePhotoFile: File? = null
    private var verificationPhotoURI: Uri? = null
    private var muzzlePhotoURI: Uri? = null
    private lateinit var mCurrentPhotoPath: String
    private var base64VerificationString: String? = null
    private var base64MuzzleString: String? = null
    private var verificationPhotoSize: String? = null
    private var muzzlePhotoSize: String? = null
    private val farmLocationViewModel: FarmLocationViewModel by viewModels()
    private lateinit var farmLocationAdapter: ArrayAdapter<String>
    private lateinit var selectedFarm: String
    private var farmLocationId: Int = 0
    private var verificationImageSize: Double = 0.0
    private var muzzleImageSize: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTagLivestockStepThreeBinding
            .inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            stepThreeViewModel = sharedViewModel
            //click buttons to be implemented
            stepThreeFragment = this@TagLivestockStepThreeFragment
        }

        productionTypeDropdown()
        tagLocationDropdown()

//        binding?.stepThreeFragment = this
    }


    private fun productionTypeDropdown() {
        val productionTypeAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, Util.PRODUCTION_TYPE)
        (binding.productionType.setAdapter(productionTypeAdapter))
//        binding.productionType.setText(productionTypeAdapter.getItem(0).toString(), false)

        binding.productionType.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            selectedProductionType = productionTypeAdapter.getItem(position).toString()
        }
    }

    private fun tagLocationDropdown() {

        farmLocationViewModel.readAllLocation.observe(viewLifecycleOwner) {
            val farms = ArrayList<String>()
            for (farm in it) {
                farms.add(farm.location_name)
            }
            farmLocationAdapter =
                ArrayAdapter(requireContext(), R.layout.dropdown_list_item, farms)
            (binding.tagLocation.setAdapter(farmLocationAdapter))
        }

        binding.tagLocation.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                selectedFarm = farmLocationAdapter.getItem(position).toString()

                farmLocationViewModel.getLocationName(selectedFarm).observe(viewLifecycleOwner) {
                    farmLocationId = it.id
                }
                /*Toast.makeText(requireContext(), "id: ${farmLocationId}, " +
                        "farm: ${selectedFarm}",
                    Toast.LENGTH_LONG).show()*/
            }

    }

    fun verificationCapturedPhoto() {
        verificationPhotoFile = createImageFile()
        try {
            verificationPhotoURI = verificationPhotoFile?.let {
                FileProvider.getUriForFile(
                    requireContext(),
                    "com.sallyjayz.ranchid.provider",
                    it
                )
            }
        } catch(e: Exception) {
            e.printStackTrace()
        }
        takePicture.launch(verificationPhotoURI)
    }

    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccessful ->
        val byteArrayOutputStream = ByteArrayOutputStream()
        if (isSuccessful) {
            val imageBitmap = BitmapFactory.decodeFile(verificationPhotoFile?.absolutePath)
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream)
            val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
            base64VerificationString = Base64.encodeToString(imageBytes, Base64.DEFAULT)
            verificationImageSize =  imageBytes.size/1024.0
            verificationPhotoSize = String.format("%.2f", verificationImageSize)

            Glide.with(requireContext())
                .load(imageBitmap)
                .override(150, 150)
                .centerCrop()
                .into(binding.verificationCameraImage)
//            binding.verificationCameraImage.setImageBitmap(imageBitmap)
            binding.verificationCameraImage.isVisible = true
            binding.verificationImageName.isVisible = true
            binding.verificationImageSize.isVisible = true
            binding.verificationReCaptureBtn.isVisible = true
            binding.verificationRePickBtn.isVisible = true
            binding.verificationPickImage.isVisible = false
            binding.verificationCaptureBtn.isVisible = false
            binding.verificationCameraIcon.isVisible = false
            binding.verificationImageName.text = verificationPhotoFile?.name
            binding.verificationImageSize.text = "$verificationPhotoSize Kb"
        } else {
            binding.verificationCameraImage.isVisible = false
            binding.verificationImageName.isVisible = false
            binding.verificationImageSize.isVisible = false
            binding.verificationReCaptureBtn.isVisible = false
            binding.verificationRePickBtn.isVisible = false
            binding.verificationPickImage.isVisible = true
            binding.verificationCaptureBtn.isVisible = true
            binding.verificationCameraIcon.isVisible = true
            binding.verificationCameraImage.setImageBitmap(null)
            binding.verificationImageName.text = ""
            binding.verificationImageSize.text = ""
        }
    }

    private val verificationPickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        val byteArrayOutputStream = ByteArrayOutputStream()
        if (uri != null) {
            verificationPhotoFile = uriToFile(uri)
            val imageBitmap = BitmapFactory.decodeFile(verificationPhotoFile?.absolutePath)
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream)
            val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
            base64VerificationString = Base64.encodeToString(imageBytes, Base64.DEFAULT)
            muzzleImageSize =  imageBytes.size/1024.0
            verificationPhotoSize = String.format("%.2f", muzzleImageSize)

//            val imageSize = (querySize(uri)?.div(1024.0)?.div(1024.0))
            /*val imageSize = (querySize(uri)?.div(1024.0))
            val roundOff = String.format("%.2f", imageSize)*/

            Glide.with(requireContext())
                .load(uri)
                .override(150, 150)
                .centerCrop()
                .into(binding.verificationCameraImage)
//            binding.verificationCameraImage.setImageURI(uri)
            binding.verificationCameraImage.isVisible = true
            binding.verificationImageName.isVisible = true
            binding.verificationImageSize.isVisible = true
            binding.verificationReCaptureBtn.isVisible = true
            binding.verificationRePickBtn.isVisible = true
            binding.verificationPickImage.isVisible = false
            binding.verificationCaptureBtn.isVisible = false
            binding.verificationCameraIcon.isVisible = false
            binding.verificationImageName.text = verificationPhotoFile?.name
            binding.verificationImageSize.text = "$verificationPhotoSize Kb"
            /*binding.muzzleImageName.text = queryName(uri)
            binding.muzzleImageSize.text = "${roundOff} Mb"*/
        } else {
            binding.verificationCameraImage.isVisible = false
            binding.verificationImageName.isVisible = false
            binding.verificationImageSize.isVisible = false
            binding.verificationReCaptureBtn.isVisible = false
            binding.verificationRePickBtn.isVisible = false
            binding.verificationPickImage.isVisible = true
            binding.verificationCaptureBtn.isVisible = true
            binding.verificationCameraIcon.isVisible = true
            binding.verificationCameraImage.setImageURI(null)
            binding.verificationImageName.text = ""
            binding.verificationImageSize.text = ""
        }
    }

    fun verificationPickedPhoto() {
        val mimeType = "image/*"
        verificationPickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.SingleMimeType(mimeType)))
    }

    fun muzzleCapturedPhoto() {
        muzzlePhotoFile = createImageFile()
        try {
            muzzlePhotoURI = muzzlePhotoFile?.let {
                FileProvider.getUriForFile(
                    requireContext(),
                    "com.sallyjayz.ranchid.provider",
                    it
                )
            }
        } catch(e: Exception) {
            e.printStackTrace()
        }
        muzzleTakePicture.launch(muzzlePhotoURI)
    }

    private val muzzleTakePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccessful ->
        val byteArrayOutputStream = ByteArrayOutputStream()
        if (isSuccessful) {
            val imageBitmap = BitmapFactory.decodeFile(muzzlePhotoFile?.absolutePath)
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream)
            val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
            base64MuzzleString = Base64.encodeToString(imageBytes, Base64.DEFAULT)
            val imageSize =  imageBytes.size/1024.0
            muzzlePhotoSize = String.format("%.2f", imageSize)

            Glide.with(requireContext())
                .load(imageBitmap)
                .override(150, 150)
                .centerCrop()
                .into(binding.muzzleCameraImage)
//            binding.muzzleCameraImage.setImageBitmap(imageBitmap)
            binding.muzzleCameraImage.isVisible = true
            binding.muzzleImageName.isVisible = true
            binding.muzzleImageSize.isVisible = true
            binding.muzzleReCaptureBtn.isVisible = true
            binding.muzzleRePickBtn.isVisible = true
            binding.muzzlePickImage.isVisible = false
            binding.muzzleCaptureBtn.isVisible = false
            binding.muzzleCameraIcon.isVisible = false
            binding.muzzleImageName.text = muzzlePhotoFile?.name
            binding.muzzleImageSize.text = "$muzzlePhotoSize Kb"
        } else {
            binding.muzzleCameraImage.isVisible = false
            binding.muzzleImageName.isVisible = false
            binding.muzzleImageSize.isVisible = false
            binding.muzzleReCaptureBtn.isVisible = false
            binding.muzzleRePickBtn.isVisible = false
            binding.muzzlePickImage.isVisible = true
            binding.muzzleCaptureBtn.isVisible = true
            binding.muzzleCameraIcon.isVisible = true
            binding.muzzleCameraImage.setImageBitmap(null)
            binding.muzzleImageName.text = ""
            binding.muzzleImageSize.text = ""
        }
    }

    private val muzzlePickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        val byteArrayOutputStream = ByteArrayOutputStream()
        if (uri != null) {
            /*val imageSize = (querySize(uri)?.div(1024.0)?.div(1024.0))
            val roundOff = String.format("%.2f", imageSize)*/

            muzzlePhotoFile = uriToFile(uri)
            val imageBitmap = BitmapFactory.decodeFile(muzzlePhotoFile?.absolutePath)
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream)
            val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
            base64MuzzleString = Base64.encodeToString(imageBytes, Base64.DEFAULT)
            val imageSize =  imageBytes.size/1024.0
            muzzlePhotoSize = String.format("%.2f", imageSize)

            Glide.with(requireContext())
                .load(uri)
                .override(150, 150)
                .centerCrop()
                .into(binding.muzzleCameraImage)
//            binding.muzzleCameraImage.setImageURI(uri)
            binding.muzzleCameraImage.isVisible = true
            binding.muzzleImageName.isVisible = true
            binding.muzzleImageSize.isVisible = true
            binding.muzzleReCaptureBtn.isVisible = true
            binding.muzzleRePickBtn.isVisible = true
            binding.muzzlePickImage.isVisible = false
            binding.muzzleCaptureBtn.isVisible = false
            binding.muzzleCameraIcon.isVisible = false
            binding.muzzleImageName.text = muzzlePhotoFile?.name
            binding.muzzleImageSize.text = "$muzzlePhotoSize Kb"
            /*binding.muzzleImageName.text = queryName(uri)
            binding.muzzleImageSize.text = "${roundOff} Mb"*/
        } else {
            binding.muzzleCameraImage.isVisible = false
            binding.muzzleImageName.isVisible = false
            binding.muzzleImageSize.isVisible = false
            binding.muzzleReCaptureBtn.isVisible = false
            binding.muzzleRePickBtn.isVisible = false
            binding.muzzlePickImage.isVisible = true
            binding.muzzleCaptureBtn.isVisible = true
            binding.muzzleCameraIcon.isVisible = true
            binding.muzzleCameraImage.setImageURI(null)
            binding.muzzleImageName.text = ""
            binding.muzzleImageSize.text = ""
        }
    }

    fun muzzlePickedPhoto() {
        val mimeType = "image/*"
        muzzlePickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.SingleMimeType(mimeType)))
    }

    private fun createImageFile(): File? {
        // Create an image file name
//        val timeStamp = SimpleDateFormat("mmss").format(Date())

        val imageDirectory = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "RanchId_",
            ".jpg",
            imageDirectory
        ).apply {
            mCurrentPhotoPath = absolutePath
        }
    }

    fun nextButtonClicked() {

        if (verificationPhotoFile == null || muzzlePhotoFile == null) {
            Toast.makeText(requireContext(), "photo required", Toast.LENGTH_LONG).show()
        } /*else if(verificationImageSize > 700.0 || muzzleImageSize > 700.0) {
            Toast.makeText(requireContext(), "Photo size is too large, It should be less than 700.00 Kb", Toast.LENGTH_LONG).show()
        }*/else if (isStepTwoEntryValid()) {
            sharedViewModel.setStepTwo(
                binding.description.text.toString(),
                binding.tagLocation.text.toString(),
                farmLocationId.toString(),
                binding.comment.text.toString(),
                selectedProductionType.toString(),
                verificationPhotoFile?.absolutePath.toString(),
                "data:image/jpeg;base64,$base64VerificationString",
                verificationPhotoFile?.name.toString(),
                "$verificationPhotoSize Kb",
                muzzlePhotoFile?.absolutePath.toString(),
                "data:image/jpeg;base64,$base64MuzzleString",
                muzzlePhotoFile?.name.toString(),
                "$muzzlePhotoSize Kb"
            )
            val action = TagLivestockStepThreeFragmentDirections
                .actionTagLivestockStepThreeFragmentToTagLivestockStepFourFragment()
            findNavController().navigate(action)
        } else {
//            binding.errorTv.text = getString(R.string.all_fields_required)
            binding.errorTv.text = "All fields are required except COMMENTS FOR OTHER LOCATION"
        }
    }

    fun cancelButtonClicked() {
        sharedViewModel.resetStepThreeTagLivestock()
        val action = TagLivestockStepThreeFragmentDirections
            .actionTagLivestockStepThreeFragmentToTagLivestockStepTwoFragment("", "")
        findNavController().navigate(action)
    }

    /*private fun queryName(uri: Uri): String? {
        val returnCursor: Cursor = activity?.contentResolver?.query(uri, null, null, null, null)!!
        val nameIndex: Int = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        val name: String = returnCursor.getString(nameIndex)
        returnCursor.close()
        return name
    }

    private fun querySize(uri: Uri): Int? {
        val returnCursor: Cursor = activity?.contentResolver?.query(uri, null, null, null, null)!!
        val sizeIndex: Int = returnCursor.getColumnIndex(OpenableColumns.SIZE)
        returnCursor.moveToFirst()
        val size: Int = returnCursor.getInt(sizeIndex)
        returnCursor.close()
        return size
    }*/

    private fun isStepTwoEntryValid(): Boolean {
        return sharedViewModel.isStepTwoEntryValid(
            binding.description.text.toString(),
            binding.tagLocation.text.toString(),
            /*binding.comment.text.toString(),*/
            selectedProductionType.toString(),
            verificationPhotoFile?.absolutePath.toString(),
            verificationPhotoFile?.name.toString(),
            verificationPhotoSize.toString(),
            muzzlePhotoFile?.absolutePath.toString(),
            muzzlePhotoFile?.name.toString(),
            muzzlePhotoSize.toString()
        )
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
}