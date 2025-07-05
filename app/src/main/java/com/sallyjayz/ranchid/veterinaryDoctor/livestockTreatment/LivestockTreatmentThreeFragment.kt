package com.sallyjayz.ranchid.veterinaryDoctor.livestockTreatment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentLivestockTreatmentThreeBinding
import com.sallyjayz.ranchid.model.vet.livestockwithtreatmenthistory.Livestock
import com.sallyjayz.ranchid.utils.Util
import com.sallyjayz.ranchid.viewmodel.TokenViewModel
import com.sallyjayz.ranchid.viewmodel.vet.LivestockTreatmentViewModel
import com.sallyjayz.ranchid.viewmodel.vet.TreatmentTypeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class LivestockTreatmentThreeFragment : Fragment() {

    private lateinit var binding: FragmentLivestockTreatmentThreeBinding
    private val sharedViewModel: LivestockTreatmentViewModel by activityViewModels()
    private val treatmentTypeViewModel: TreatmentTypeViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by activityViewModels()
    private val args: LivestockTreatmentThreeFragmentArgs by navArgs()
    private var selectedFrequency: String? = null
    private lateinit var selectedAdminRoute: String
    private lateinit var selectedInjection: String
    private lateinit var selectedTreatmentType: String
    private lateinit var selectedTreatmentCategory: String
    private lateinit var treatmentTypeAdapter: ArrayAdapter<String>
    private lateinit var treatmentCategoryAdapter: ArrayAdapter<String>
    private lateinit var councilNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                sharedViewModel.resetLivestockTreatment()
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
        binding = FragmentLivestockTreatmentThreeBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            stepThreeViewModel = sharedViewModel
            stepThreeFragment = this@LivestockTreatmentThreeFragment
        }

        binding.tagId.setText(args.scannedTagID)

        frequencyDropdown()
        treatmentType()
        adminRouteDropdown()
    }

    private fun treatmentType() {
        treatmentTypeViewModel.readAllTreatmentType.observe(viewLifecycleOwner) {
            val treatmentCategories = ArrayList<String>()
            for(treatmentCategory in it) {
                treatmentCategories.add(treatmentCategory)
                treatmentCategoryAdapter =
                    ArrayAdapter(requireContext(), R.layout.dropdown_list_item, treatmentCategories)
                (binding.tagTreatmentCategory.setAdapter(treatmentCategoryAdapter))
            }
        }

        binding.tagTreatmentCategory.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                selectedTreatmentCategory = treatmentCategoryAdapter.getItem(position).toString()
                binding.tagTreatmentType.setText("", false)

                treatmentTypeViewModel.getTreatmentCategory(selectedTreatmentCategory).observe(viewLifecycleOwner){
                    val treatmentTypes = ArrayList<String>()
                    for(type in it) {
                        treatmentTypes.add(type.variants)
                        treatmentTypeAdapter = ArrayAdapter(
                            parent.context,
                            R.layout.dropdown_list_item, treatmentTypes)
                    }
                    (binding.tagTreatmentType.setAdapter(treatmentTypeAdapter))
                }
            }

        binding.tagTreatmentType.onItemClickListener = AdapterView.OnItemClickListener { _, _, pos, _ ->
            selectedTreatmentType = treatmentTypeAdapter.getItem(pos).toString()
        }
    }

    private fun frequencyDropdown() {
        val frequencyAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, Util.FREQUENCY)
        (binding.tagFrequency.setAdapter(frequencyAdapter))

        binding.tagFrequency.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            selectedFrequency = frequencyAdapter.getItem(position).toString()
        }
    }

    private fun adminRouteDropdown() {
        val adminRouteAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, Util.ADMINISTRATION_ROUTE)
        binding.administrationRoute.setAdapter(adminRouteAdapter)
        binding.administrationRoute.onItemClickListener= AdapterView.OnItemClickListener { _, _, position, _ ->
            selectedAdminRoute = adminRouteAdapter.getItem(position).toString()

            if (selectedAdminRoute.contains("injection")) {
                binding.tagInjectionLabel.isVisible = true
                binding.tagInjectionInput.isVisible = true

                val injectionAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, Util.INJECTION)
                binding.injection.setAdapter(injectionAdapter)
                binding.injection.onItemClickListener= AdapterView.OnItemClickListener { _, _, position, _ ->
                    selectedInjection = adminRouteAdapter.getItem(position).toString()
                }

            } else {
                binding.tagInjectionLabel.isVisible = false
                binding.tagInjectionInput.isVisible = false
            }
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun timeOfTreatment() {
        val timePicker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText(getString(R.string.select_treatment_time))
                .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                .build()
        timePicker.show(requireActivity().supportFragmentManager, "Time Picker")

        timePicker.addOnPositiveButtonClickListener {
            val newHour: Int = timePicker.hour
            val newMinute: Int = timePicker.minute

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

    fun dateOfTreatment() {
        val datePicker =
            MaterialDatePicker.Builder
                .datePicker()
//                .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
                .setTitleText(getString(R.string.select_treatment_date))
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

    private fun isStepThreeEntryValid(): Boolean {
        return sharedViewModel.isStepThreeEntryValid(
            binding.tagId.text.toString(),
            binding.time.text.toString(),
            binding.date.text.toString(),
            selectedTreatmentCategory,
            selectedTreatmentType,
            binding.tagDrugVaccine.text.toString(),
            binding.tagDrugDosage.text.toString(),
            selectedFrequency.toString(),
            binding.administrationRoute.text.toString(),
//            binding.injection.text.toString(),
            binding.tagDiagnosis.text.toString()

        )
    }

    fun cancelButtonClicked() {
        sharedViewModel.resetLivestockTreatment()
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
            "",
        )
        val action = LivestockTreatmentThreeFragmentDirections
            .actionLivestockTreatmentThreeFragmentToLivestockTreatmentTwoFragment(livestockData)
        findNavController().navigate(action)
    }

    fun nextButtonClicked() {

        tokenViewModel.vetCouncilNumber.observe(viewLifecycleOwner) { vetCouncilNumber ->
            councilNumber = vetCouncilNumber.toString()
        }

        if (isStepThreeEntryValid()) {
            sharedViewModel.setStepThree(
                councilNumber,
                binding.tagId.text.toString(),
                binding.time.text.toString(),
                binding.date.text.toString(),
                binding.tagTreatmentCategory.text.toString(),
                binding.tagTreatmentType.text.toString(),
                binding.tagDrugVaccine.text.toString(),
                binding.tagDrugDosage.text.toString(),
                binding.tagFrequency.text.toString(),
                binding.administrationRoute.text.toString(),
                binding.injection.text.toString(),
                binding.tagDiagnosis.text.toString()

            )
            val action = LivestockTreatmentThreeFragmentDirections
                .actionLivestockTreatmentThreeFragmentToLivestockTreatmentFourFragment()
            findNavController().navigate(action)
        } else {
            binding.errorTv.text = getString(R.string.all_fields_required)
        }

    }

}