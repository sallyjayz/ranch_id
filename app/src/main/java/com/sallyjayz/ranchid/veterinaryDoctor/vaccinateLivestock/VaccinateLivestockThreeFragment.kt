package com.sallyjayz.ranchid.veterinaryDoctor.vaccinateLivestock

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentVaccinateLivestockThreeBinding
import com.sallyjayz.ranchid.model.vet.livestockwithvaccinationhistory.Livestock
import com.sallyjayz.ranchid.utils.Util
import com.sallyjayz.ranchid.viewmodel.TokenViewModel
import com.sallyjayz.ranchid.viewmodel.vet.AnimalTypeWithVaccineViewModel
import com.sallyjayz.ranchid.viewmodel.vet.LivestockVaccinationViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class VaccinateLivestockThreeFragment : Fragment() {

    private lateinit var binding: FragmentVaccinateLivestockThreeBinding
    private val sharedViewModel: LivestockVaccinationViewModel by activityViewModels()
    private val tokenViewModel: TokenViewModel by activityViewModels()
    private val animalTypeWithVaccineViewModel: AnimalTypeWithVaccineViewModel by viewModels()
    private val args: VaccinateLivestockThreeFragmentArgs by navArgs()
    private lateinit var councilNumber: String
    private var uploadPhotoFile: File? = null
    private var base64UploadString: String? = null
    private var uploadPhotoSize: String? = null
    private lateinit var mCurrentPhotoPath: String
    private var selectedFrequency: String? = null
    private lateinit var selectedAnimalType: String
    private lateinit var selectedVaccineName: String
    private lateinit var animalTypeAdapter: ArrayAdapter<String>
    private lateinit var vaccineAdapter: ArrayAdapter<String>
    private var drugId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                sharedViewModel.resetLivestockVaccination()
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
        binding = FragmentVaccinateLivestockThreeBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            stepThreeViewModel = sharedViewModel
            stepThreeFragment = this@VaccinateLivestockThreeFragment
        }

        binding.tagId.setText(args.scannedTagID)

        frequencyDropdown()
        animalTypeWithVaccineDropDown()

    }

    private fun frequencyDropdown() {
        val frequencyAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, Util.FREQUENCY)
        (binding.tagFrequency.setAdapter(frequencyAdapter))

        binding.tagFrequency.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            selectedFrequency = frequencyAdapter.getItem(position).toString()
        }
    }

    private fun animalTypeWithVaccineDropDown() {
        animalTypeWithVaccineViewModel.readAllAnimalTypeWithVaccine.observe(viewLifecycleOwner) {
            val animalTypes = ArrayList<String>()
            for (animalType in it) {
                animalTypes.add(animalType)
                animalTypeAdapter =
                    ArrayAdapter(requireContext(), R.layout.dropdown_list_item, animalTypes)
                (binding.tagLivestockType.setAdapter(animalTypeAdapter))
            }
        }

        binding.tagLivestockType.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                selectedAnimalType = animalTypeAdapter.getItem(position).toString()
                binding.tagDrugVaccine.setText("", false)

                animalTypeWithVaccineViewModel.getAnimalType(selectedAnimalType).observe(viewLifecycleOwner){
                    val vaccines = ArrayList<String>()
                    for(vaccine in it) {
                        vaccines.add(vaccine.vaccineName)
                        vaccineAdapter = ArrayAdapter(
                            parent.context,
                            R.layout.dropdown_list_item, vaccines)
                    }
                    (binding.tagDrugVaccine.setAdapter(vaccineAdapter))
                }
            }
        binding.tagDrugVaccine.onItemClickListener = AdapterView.OnItemClickListener { _, _, pos, _ ->
            selectedVaccineName = vaccineAdapter.getItem(pos).toString()
            animalTypeWithVaccineViewModel.getAnimalVaccineName(selectedVaccineName).observe(viewLifecycleOwner){
                drugId = it.id
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun timeOfVaccination() {
        val timePicker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText(getString(R.string.select_vaccination_time))
                .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                .build()
        timePicker.show(requireActivity().supportFragmentManager, "Time Picker")

        timePicker.addOnPositiveButtonClickListener {
            val newHour: Int = timePicker.hour
            val newMinute: Int = timePicker.minute
//            binding.time.setText("$newHour : $newMinute")

            /*val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
            val timeOfVaccination = LocalTime.parse("$newHour:$newMinute", timeFormatter)
            val formatted = timeOfVaccination.format(timeFormatter)*/

            val timeFormatter = DateTimeFormatter.ofPattern("H:m")
            val desiredTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
            val timeOfVaccination = LocalTime.parse("$newHour:$newMinute", timeFormatter)
            val formatted = timeOfVaccination.format(desiredTimeFormatter)

            binding.time.setText(formatted)
        }
        timePicker.addOnNegativeButtonClickListener {
            binding.time.setText("")
        }
        timePicker.addOnCancelListener {
            binding.time.setText("")
        }

    }

    fun dateOfVaccination() {
        val datePicker =
            MaterialDatePicker.Builder
                .datePicker()
//                .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
                .setTitleText(getString(R.string.select_vaccination_date))
                .build()

        datePicker.show(requireActivity().supportFragmentManager, "DatePicker")
        datePicker.addOnPositiveButtonClickListener {
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = dateFormatter.format(Date(it))
            binding.date.setText(date)
        }

        datePicker.addOnNegativeButtonClickListener {
            binding.date.setText("")
        }

        datePicker.addOnCancelListener {
            binding.date.setText("")
        }
    }

    fun nextAppointmentDate() {
        val datePicker =
            MaterialDatePicker.Builder
                .datePicker()
//                .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
                .setTitleText(getString(R.string.select_date_for_next_appointment))
                .build()

        datePicker.show(requireActivity().supportFragmentManager, "NextAppointmentPicker")
        datePicker.addOnPositiveButtonClickListener {
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = dateFormatter.format(Date(it))
            binding.nextAppointment.setText(date)
        }

        datePicker.addOnNegativeButtonClickListener {
            binding.nextAppointment.setText("")
        }

        datePicker.addOnCancelListener {
            binding.nextAppointment.setText("")
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


    fun cancelButtonClicked() {
        sharedViewModel.resetLivestockVaccination()
        val livestockData = Livestock(
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        )
        val  action = VaccinateLivestockThreeFragmentDirections
            .actionVaccinateLivestockThreeFragmentToVaccinateLivestockTwoFragment(livestockData)
        findNavController().navigate(action)
    }

    fun nextButtonClicked() {

        tokenViewModel.vetCouncilNumber.observe(viewLifecycleOwner) { vetCouncilNumber ->
            councilNumber = vetCouncilNumber.toString()
        }

        if (uploadPhotoFile == null) {
            Toast.makeText(requireContext(), "photo required", Toast.LENGTH_LONG).show()
        } else if (isStepThreeEntryValid()) {
            sharedViewModel.setStepThree(
                councilNumber,
                binding.tagId.text.toString(),
                selectedAnimalType.toString(),
                selectedVaccineName.toString(),
                drugId.toString(),
                binding.tagDrugDosage.text.toString(),
                selectedFrequency.toString(),
                binding.time.text.toString(),
                binding.date.text.toString(),
                binding.nextAppointment.text.toString(),
                binding.tagNotes.text.toString(),
                uploadPhotoFile?.absolutePath.toString(),
                "data:image/jpeg;base64,$base64UploadString",
                uploadPhotoFile?.name.toString(),
                "$uploadPhotoSize Kb"

            )
            val action = VaccinateLivestockThreeFragmentDirections
                .actionVaccinateLivestockThreeFragmentToVaccinateLivestockConfirmationFragment()
            findNavController().navigate(action)
        } else {
            binding.errorTv.text = getString(R.string.all_fields_required)
        }
    }

    private fun isStepThreeEntryValid(): Boolean {
        return sharedViewModel.isStepThreeEntryValid(
            binding.tagId.text.toString(),
            binding.tagLivestockType.text.toString(),
            binding.tagDrugVaccine.text.toString(),
            binding.tagDrugDosage.text.toString(),
            binding.tagFrequency.text.toString(),
            binding.time.text.toString(),
            binding.date.text.toString(),
            binding.nextAppointment.text.toString(),
            binding.tagNotes.text.toString(),
            uploadPhotoFile?.absolutePath.toString(),
            uploadPhotoFile?.name.toString(),
            uploadPhotoSize.toString()
        )

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

}