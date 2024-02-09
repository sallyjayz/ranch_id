package com.sallyjayz.ranchid.register.taglivestock

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.utils.Util
import com.sallyjayz.ranchid.databinding.FragmentTagLivestockStepTwoBinding
import com.sallyjayz.ranchid.viewmodel.register.*
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class TagLivestockStepTwoFragment : Fragment() {

    private lateinit var binding: FragmentTagLivestockStepTwoBinding
    private val sharedViewModel: TagLivestockViewModel by activityViewModels()
    private val allOwnerViewModel: AllOwnerViewModel by viewModels()
    private val allKeeperViewModel: AllKeeperViewModel by viewModels()
    private val unusedPassportViewModel: UnusedPassportViewModel by viewModels()
    /*private var selectedType: String? = null
    private var selectedBreed: String? = null
    private var selectedGender: String? = null
    private var selectedHealthStatus: String? = null
    private var ownerSurname: String? = null
    private var ownerOthername: String? = null
    private var selectedOwner: String? = null
    private var keeperSurname: String? = null
    private var keeperOthername: String? = null
    private var selectedKeeper: String? = null*/
    private var selectedPassportId: String = ""
    private lateinit var selectedType: String
    private lateinit var selectedBreed: String
    private lateinit var selectedGender: String
    private lateinit var selectedHealthStatus: String
    private lateinit var ownerSurname: String
    private lateinit var ownerOthername: String
    private lateinit var selectedOwner: String
    private lateinit var keeperSurname: String
    private lateinit var keeperOthername: String
    private lateinit var selectedKeeper: String
    /*private lateinit var selectedPassportId: String*/
    private var ownerId: Int = 0
    private var keeperId: Int = 0
    private lateinit var livestockTypeAdapter: ArrayAdapter<String>
    private lateinit var livestockBreedAdapter: ArrayAdapter<String>
    private lateinit var allOwnerAdapter: ArrayAdapter<String>
    private lateinit var allKeeperAdapter: ArrayAdapter<String>
    private lateinit var passportIdAdapter: ArrayAdapter<String>
    private val animalTypeViewModel: AnimalTypeViewModel by viewModels()
    private val animalBreedViewModel: AnimalBreedViewModel by viewModels()
    private val args: TagLivestockStepTwoFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this
        binding = FragmentTagLivestockStepTwoBinding
            .inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            stepTwoViewModel = sharedViewModel
            //click buttons to be implemented
            stepTwoFragment = this@TagLivestockStepTwoFragment
        }

        genderDropdown()
        healthStatusDropdown()
        livestockTypeAndBreedDropdown()
        ownerDropdown()
        keeperDropdown()
        passportIdDropdown()
//        periodOfBirth()

        binding.scanId.setText(args.scannedTagID)

//        binding?.stepTwoFragment = this

//        binding.tagSaveButton.setOnClickListener { saveButtonClicked() }
//
//        binding.tagCancelButton.setOnClickListener { cancelButtonClicked() }
    }

    private fun passportIdDropdown() {
        unusedPassportViewModel.readAllUnusedPassport.observe(viewLifecycleOwner) {
            val passportIds = ArrayList<String>()
            for (passport in it) {
                passportIds.add(passport.passportId)
            }
            passportIdAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, passportIds)
            (binding.passportId.setAdapter(passportIdAdapter))
        }

        binding.passportId.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                selectedPassportId = passportIdAdapter.getItem(position).toString()

                /*Toast.makeText(requireContext(), "passportid: ${selectedPassportId}",
                    Toast.LENGTH_LONG).show()*/
            }
    }

    private fun ownerDropdown() {
        allOwnerViewModel.readAllOwnerList.observe(viewLifecycleOwner) {
            val owners = ArrayList<String>()
            for (owner in it) {
                ownerSurname = owner.surname
                ownerOthername = owner.other_names
                val ownerName = "$ownerSurname $ownerOthername"
                owners.add(ownerName)
            }
            allOwnerAdapter =
                ArrayAdapter(requireContext(), R.layout.dropdown_list_item, owners)
            (binding.livestockOwner.setAdapter(allOwnerAdapter))
        }

        binding.livestockOwner.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                selectedOwner = allOwnerAdapter.getItem(position).toString()

                allOwnerViewModel.getSelectedOwnerName(ownerSurname, ownerOthername).observe(viewLifecycleOwner) {
                    ownerId = it.id
                }
                /*Toast.makeText(requireContext(), "id: ${ownerId}, " +
                        "farm: ${selectedOwner}",
                    Toast.LENGTH_LONG).show()*/
            }
    }

    private fun keeperDropdown() {
        allKeeperViewModel.readAllKeeperList.observe(viewLifecycleOwner) {
            val keepers = ArrayList<String>()
            for (keeper in it) {
                keeperSurname = keeper.surname
                keeperOthername = keeper.other_names
                val keeperName = "$keeperSurname $keeperOthername"
                keepers.add(keeperName)
            }
            allKeeperAdapter =
                ArrayAdapter(requireContext(), R.layout.dropdown_list_item, keepers)
            (binding.livestockKeeper.setAdapter(allKeeperAdapter))
        }

        binding.livestockKeeper.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                selectedKeeper = allKeeperAdapter.getItem(position).toString()

                allKeeperViewModel.getSelectedKeeperName(keeperSurname, keeperOthername).observe(viewLifecycleOwner) {
                    keeperId = it.id
                }
                /*Toast.makeText(requireContext(), "id: ${keeperId}, " +
                        "farm: ${selectedKeeper}",
                    Toast.LENGTH_LONG).show()*/
            }
    }

    private fun genderDropdown() {
        val genderAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, Util.GENDER)
        (binding.gender.setAdapter(genderAdapter))
//        binding.gender.setText(genderAdapter.getItem(0).toString(), false)

        binding.gender.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            selectedGender = genderAdapter.getItem(position).toString()
        }
    }

    private fun healthStatusDropdown() {
        val healthAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, Util.HEALTH_STATUS)
        (binding.healthStatus.setAdapter(healthAdapter))
//        binding.healthStatus.setText(healthAdapter.getItem(0).toString(), false)

        binding.healthStatus.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            selectedHealthStatus = healthAdapter.getItem(position).toString()
        }
    }

    private fun livestockTypeAndBreedDropdown() {

        /*animalTypeViewModel.readAllAnimalType.observe(viewLifecycleOwner) {
            val animalTypes = ArrayList<String>()
            for (type in it) {
                animalTypes.add(type.name)
                livestockTypeAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, animalTypes)
                (binding.livestockType.setAdapter(livestockTypeAdapter))
            }
        }*/

        animalTypeViewModel.readAllAnimalType.observe(viewLifecycleOwner) {
            val animalTypes = ArrayList<String>()
            for (animal_type in it) {
                animalTypeViewModel.getAnimalTagType(args.tagType.toString()).observe(viewLifecycleOwner) {
                    if (animal_type.type.contains(args.tagType.toString())){
                        animalTypes.add(animal_type.name)
                        livestockTypeAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, animalTypes)
                        (binding.livestockType.setAdapter(livestockTypeAdapter))
                    }
                }
            }
        }

        binding.livestockType.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
               selectedType = livestockTypeAdapter.getItem(position).toString()
                binding.livestockBreed.setText("", false)

                animalTypeViewModel.getAnimalTypeName(selectedType).observe(viewLifecycleOwner){
                    if (selectedType.contains(it.name)) {
                        animalBreedViewModel.getAnimalTypeBreedId(it.id).observe(viewLifecycleOwner) { animalBreedList ->
                            val animalBreeds = ArrayList<String>()
                            for (breed in animalBreedList) {
                                animalBreeds.add(breed.name)
                                livestockBreedAdapter = ArrayAdapter(
                                    parent.context,
                                    R.layout.dropdown_list_item, animalBreeds)
                            }
                            (binding.livestockBreed.setAdapter(livestockBreedAdapter))
                        }
                    }
                }
                binding.livestockBreed.onItemClickListener = AdapterView.OnItemClickListener { _, _, pos, _ ->
                    selectedBreed = livestockBreedAdapter.getItem(pos).toString()
                }
            }




        /*val livestockTypeAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_list_item, Util.LIVESTOCK_TYPE)
        (binding.livestockType.setAdapter(livestockTypeAdapter))

        binding.livestockType.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                selectedType = livestockTypeAdapter.getItem(position).toString()
                binding.livestockBreed.setText("", false)

                Util.LIVESTOCK_TYPE.zip(Util.BREED).forEach { pair ->
                    when(selectedType) {
                        pair.component1() -> livestockBreedAdapter = ArrayAdapter(
                            parent.context,
                            R.layout.dropdown_list_item, pair.component2())
                    }
                }
                (binding.livestockBreed.setAdapter(livestockBreedAdapter))

                binding.livestockBreed.onItemClickListener = AdapterView.OnItemClickListener { _, _, pos, _ ->
                    selectedBreed = livestockBreedAdapter.getItem(pos).toString()
                }

            }*/
    }

    fun dateOfBirth() {
        val datePicker =
            MaterialDatePicker.Builder
                .datePicker()
//                    .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
                .setTitleText(getString(R.string.select_date_of_birth))
                .build()

        datePicker.show(requireActivity().supportFragmentManager, "DatePicker")
        datePicker.addOnPositiveButtonClickListener {
            val dateFormatter = SimpleDateFormat(getString(R.string.date_pattern), Locale.getDefault())
            val date = dateFormatter.format(Date(it))
            binding.dob.setText(date)
        }

        datePicker.addOnNegativeButtonClickListener {
            binding.dob.setText("")
        }

        datePicker.addOnCancelListener {
            binding.dob.setText("")
        }
    }

    /*fun periodOfBirth() {
        val periodPicker =
            MaterialDatePicker.Builder
                .datePicker()
//                    .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
                .setTitleText(getString(R.string.select_date_of_birth))
                .build()


        periodPicker.show(requireActivity().supportFragmentManager, "PeriodPicker")
        periodPicker.addOnPositiveButtonClickListener {
            val dateFormatter = SimpleDateFormat(getString(R.string.date_pattern), Locale.getDefault())
            val date = dateFormatter.format(Date(it))
            binding.birthPeriod.setText(date)
        }

        periodPicker.addOnNegativeButtonClickListener {
            binding.birthPeriod.setText("")
        }

        periodPicker.addOnCancelListener {
            binding.birthPeriod.setText("")
        }
    }*/

    fun cancelButtonClicked() {
        sharedViewModel.resetStepTwoTagLivestock()
        val action = TagLivestockStepTwoFragmentDirections
            .actionTagLivestockStepTwoFragmentToTagLivestockStepOneFragment()
        findNavController().navigate(action)
    }

    fun nextButtonClicked() {
        if (isStepOneEntryValid()) {
            sharedViewModel.setStepOne(
                binding.scanId.text.toString(),
                selectedPassportId,
                binding.livestockKeeper.text.toString(),
                binding.livestockOwner.text.toString(),
                keeperId.toString(),
                ownerId.toString(),
                selectedType,
                selectedBreed,
                selectedGender,
                selectedHealthStatus,
                binding.dob.text.toString(),
//                binding.birthPeriod.text.toString()
            )

            val action = TagLivestockStepTwoFragmentDirections
                .actionTagLivestockStepTwoFragmentToTagLivestockStepThreeFragment()
            findNavController().navigate(action)
        } else {
//            binding.errorTv.text = getString(R.string.all_fields_required)
            binding.errorTv.text = "All fields are required except PASSPORT ID"
        }


    }

    private fun isStepOneEntryValid(): Boolean {
        return sharedViewModel.isStepOneEntryValid(
            binding.scanId.text.toString(),
            /*binding.passportId.text.toString(),*/
            binding.livestockKeeper.text.toString(),
            binding.livestockOwner.text.toString(),
            binding.livestockType.text.toString(),
            binding.livestockBreed.text.toString(),
            binding.gender.text.toString(),
            binding.healthStatus.text.toString(),
            binding.dob.text.toString(),
//            binding.birthPeriod.text.toString()
        )
    }

}

