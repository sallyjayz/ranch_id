package com.sallyjayz.ranchid.register.livestockowner

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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentAddLivestockOwnerStepTwoBinding
import com.sallyjayz.ranchid.utils.Util
import com.sallyjayz.ranchid.viewmodel.register.FarmLocationViewModel
import com.sallyjayz.ranchid.viewmodel.register.LivestockOwnerViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.File

@AndroidEntryPoint
class AddLivestockOwnerStepTwoFragment : Fragment() {
    private lateinit var binding: FragmentAddLivestockOwnerStepTwoBinding
    private val sharedViewModel: LivestockOwnerViewModel by activityViewModels()
    private var photoFile: File? = null
    private var photoURI: Uri? = null
    private lateinit var mCurrentPhotoPath: String
    private val farmLocationViewModel: FarmLocationViewModel by viewModels()
    private lateinit var farmLocationAdapter: ArrayAdapter<String>
    private lateinit var selectedFarm: String
    private var farmLocationId: Int = 0
    private lateinit var byteArrayOutputStream: ByteArrayOutputStream
    private var base64OwnerString: String? = null
    private var imageSize: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddLivestockOwnerStepTwoBinding
            .inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            stepTwoViewModel = sharedViewModel
            stepTwoFragment = this@AddLivestockOwnerStepTwoFragment
        }

        documentTypeDropdown()
        farmLocationDropdown()
        kinPhoneFocusListener()
        documentNumberFocusListener()

    }

    private fun documentTypeDropdown() {
        val documentTypeAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, Util.ID_DOCUMENT_TYPE)
//        (binding.ownerDocumenttype.editText as? AutoCompleteTextView)?.setAdapter(documentTypeAdapter)
        binding.idDocumentType.setAdapter(documentTypeAdapter)
//        binding.idDocumentType.setText(documentTypeAdapter.getItem(0).toString(), false)
    }

    private fun farmLocationDropdown() {

        /*farmLocationResponseViewModel.farmLocationResponse.observe(viewLifecycleOwner) {
            val farms = ArrayList<String>()
            when(it) {
                is ApiResponse.Failure -> "Code: ${it.code}, ${it.errorMessage}"
                ApiResponse.Loading -> {
                    binding.farmLocation.setText("Loading", false)
                }
                is ApiResponse.Success -> {
                    mFarmLocations = it.data.farmLocation
//                    Toast.makeText(requireContext(), "id: ${it.data.location}",
//                        Toast.LENGTH_LONG).show()
                    for (location in mFarmLocations) {
                        farms.add(location.location_name)
                    }

                    farmLocationAdapter =
                        ArrayAdapter(requireContext(), R.layout.dropdown_list_item, farms)
                    binding.farmLocation.setText("", false)
                    (binding.farmLocation.setAdapter(farmLocationAdapter))
                }
            }

        }

        binding.farmLocation.setOnClickListener {
            farmLocationResponseViewModel.getLocation(object: CoroutinesErrorHandler {
                override fun onError(message: String) {
                    binding.farmLocation.setText("Error! $message", false)
                }
            })
        }

        binding.farmLocation.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                selectedFarm = farmLocationAdapter.getItem(position).toString()

                for (location in mFarmLocations) {
                    if (selectedFarm.contains(location.location_name)) {
                        farmLocationId = location.id
                        Toast.makeText(requireContext(), "id: ${farmLocationId}",
                        Toast.LENGTH_LONG).show()
                    }
                }
            }*/



        farmLocationViewModel.readAllLocation.observe(viewLifecycleOwner) {
            val farms = ArrayList<String>()
            for (farm in it) {
                farms.add(farm.location_name)
            }
            farmLocationAdapter =
                ArrayAdapter(requireContext(), R.layout.dropdown_list_item, farms)
            (binding.farmLocation.setAdapter(farmLocationAdapter))
        }

        binding.farmLocation.onItemClickListener =
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

    fun capturedPhoto() {
        photoFile = createImageFile()
        try {
            photoURI = photoFile?.let {
                FileProvider.getUriForFile(
                    requireContext(),
                    "com.sallyjayz.ranchid.provider",
                    it
                )
            }
        } catch(e: Exception) {
            e.printStackTrace()
        }
        takePicture.launch(photoURI)
    }

    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccessful ->

        byteArrayOutputStream = ByteArrayOutputStream()

        if (isSuccessful) {
            val imageBitmap = BitmapFactory.decodeFile(photoFile?.absolutePath)
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream)
            val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
            base64OwnerString = Base64.encodeToString(imageBytes, Base64.DEFAULT)
//            val imageSize =  photoFile.length()/1024.0/1024.0
//            val imageSize =  photoFile.length()/1024.0
//            val roundOff = String.format("%.2f", imageSize)
            imageSize =  imageBytes.size/1024.0
            val roundOff = String.format("%.2f", imageSize)

            Glide.with(requireContext())
                .load(imageBitmap)
                .override(150, 150)
                .centerCrop()
                .into(binding.cameraImage)
//            binding.cameraImage.setImageBitmap(imageBitmap)
            binding.cameraImage.isVisible = true
            binding.imageName.isVisible = true
            binding.imageSize.isVisible = true
            binding.reCaptureBtn.isVisible = true
            binding.captureBtn.isVisible = false
            binding.cameraIcon.isVisible = false
            binding.imageName.text = photoFile?.name
            binding.imageSize.text = "$roundOff Kb"
        } else {
            binding.cameraImage.isVisible = false
            binding.imageName.isVisible = false
            binding.imageSize.isVisible = false
            binding.reCaptureBtn.isVisible = false
            binding.captureBtn.isVisible = true
            binding.cameraIcon.isVisible = true
            binding.cameraImage.setImageBitmap(null)
            binding.imageName.text = ""
            binding.imageSize.text = ""
        }
    }

    private fun createImageFile(): File? {
        // Create an image file name
//        val timeStamp = SimpleDateFormat("mmss").format(Date())
//        var count = 0
        val imageDirectory = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
//            "RanchId_${count++}",
            "RanchId_",
            ".jpeg",
            imageDirectory
        ).apply {
            mCurrentPhotoPath = absolutePath
        }
    }


    fun nextClicked() {

        binding.ownerNextofkinPhone.helperText = validKinPhone()
        binding.ownerDocumentnumber.helperText = validDocumentNumber()

        val validKinPhone = binding.ownerNextofkinPhone.helperText == null
        val validDocumentNumber = binding.ownerDocumentnumber.helperText == null

        if (photoFile == null) {
            Toast.makeText(requireContext(), "Please, take a photo", Toast.LENGTH_LONG).show()
        } else if(imageSize > 700.0) {
            Toast.makeText(requireContext(), "Photo size is too large, It should be less than 700.00 Kb", Toast.LENGTH_LONG).show()
        } else if (isStepTwoEntryValid() && validKinPhone && validDocumentNumber) {
            sharedViewModel.setStepTwo(
                binding.nextOfKin.text.toString(),
                binding.nextOfKinPhone.text.toString(),
                binding.idDocumentType.text.toString(),
                binding.documentNumber.text.toString(),
                binding.farmLocation.text.toString(),
                farmLocationId.toString(),
                "data:image/jpeg;base64,$base64OwnerString",
                binding.imageName.text.toString(),
                binding.imageSize.text.toString()
            )

            val action = AddLivestockOwnerStepTwoFragmentDirections
                .actionAddLivestockOwnerStepTwoFragmentToAddLivestockOwnerStepThreeFragment()
            findNavController().navigate(action)

        }else {
            binding.errorTv.text = getString(R.string.all_fields_required)
        }
    }


    fun cancelButtonClicked() {
        sharedViewModel.resetStepTwoLivestockOwner()
        val action = AddLivestockOwnerStepTwoFragmentDirections
            .actionAddLivestockOwnerStepTwoFragmentToAddLivestockOwnerStepOneFragment()
        findNavController().navigate(action)
    }

    private fun isStepTwoEntryValid(): Boolean {
        return sharedViewModel.isStepTwoEntryValid(
            binding.nextOfKin.text.toString(),
            binding.nextOfKinPhone.text.toString(),
            binding.idDocumentType.text.toString(),
            binding.documentNumber.text.toString(),
            binding.farmLocation.text.toString(),
            photoFile?.absolutePath.toString(),
            binding.imageName.text.toString(),
            binding.imageSize.text.toString()
        )
    }

    private fun kinPhoneFocusListener() {
        binding.nextOfKinPhone.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.ownerNextofkinPhone.helperText = validKinPhone()
            }
        }
    }

    private fun validKinPhone(): String? {
        val kinPhoneText = binding.nextOfKinPhone.text.toString()

        if (kinPhoneText.length != 11) {
            return "Must be 11 Digits"
        }

        if (!kinPhoneText.matches(".*[0-9].*".toRegex())) {
            return "Must be Digits"
        }
        return null
    }

    private fun documentNumberFocusListener() {
        binding.documentNumber.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.ownerDocumentnumber.helperText = validDocumentNumber()
            }
        }
    }

    private fun validDocumentNumber(): String? {
        val docNoText = binding.documentNumber.text.toString()

        if (docNoText.length < 11) {
            return "Minimum of 11 digits"
        }

        if (!docNoText.matches(".*[0-9].*".toRegex())) {
            return "Must be Digits"
        }
        return null
    }
}