package com.sallyjayz.ranchid.register.livestockowner

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.utils.Util
import com.sallyjayz.ranchid.databinding.FragmentAddLivestockOwnerStepOneBinding
import com.sallyjayz.ranchid.viewmodel.register.LgaViewModel
import com.sallyjayz.ranchid.viewmodel.register.LivestockOwnerViewModel
import com.sallyjayz.ranchid.viewmodel.register.StateViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddLivestockOwnerStepOneFragment : Fragment() {

    private lateinit var binding: FragmentAddLivestockOwnerStepOneBinding
    private lateinit var selectedState: String
    private lateinit var selectedLga: String
    private lateinit var selectedOwnershipType: String
    private lateinit var stateAdapter: ArrayAdapter<String>
    private lateinit var lgaAdapter: ArrayAdapter<String>
    private val sharedViewModel: LivestockOwnerViewModel by activityViewModels()
    private val stateViewModel: StateViewModel by viewModels()
    private val lgaViewModel: LgaViewModel by viewModels()
    private var stateId: Int = 0
    private var lgaId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                sharedViewModel.resetLivestockOwner()
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
        binding = FragmentAddLivestockOwnerStepOneBinding
            .inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            stepOneViewModel = sharedViewModel
            stepOneFragment = this@AddLivestockOwnerStepOneFragment
        }

        ownershipTypeDropdown()
        stateAndLgaDropDown()
        emailFocusListener()
        phoneFocusListener()
        ninFocusListener()

        /*stateLgaViewModel.readAllState.observe(viewLifecycleOwner) {
            Toast.makeText(requireActivity(), "$it", Toast.LENGTH_LONG).show()
//            Log.d("Owner", it.toString())
        }
        stateLgaViewModel.readAllLga.observe(viewLifecycleOwner) {
            Log.d("Owner", it.toString())
        }*/

    }

    private fun ownershipTypeDropdown() {
        val ownershipTypeAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, Util.OWNERSHIP_TYPE)
//        (binding.ownershipTypeDropdown.editText as? AutoCompleteTextView)?.setAdapter(ownershipTypeAdapter)
        binding.ownerType.setAdapter(ownershipTypeAdapter)
//        binding.ownerType.setText(ownershipTypeAdapter.getItem(0).toString(), false)
        binding.ownerType.onItemClickListener= AdapterView.OnItemClickListener { _, _, position, _ ->
            selectedOwnershipType = ownershipTypeAdapter.getItem(position).toString()

            if (selectedOwnershipType.contains("CO_OPERATIVE")) {
                binding.groupNameLabel.isVisible = true
                binding.groupNameInput.isVisible = true
            } else {
                binding.groupNameLabel.isVisible = false
                binding.groupNameInput.isVisible = false
            }
        }
    }

    private fun stateAndLgaDropDown() {

        stateViewModel.readAllState.observe(viewLifecycleOwner) {
            val states = ArrayList<String>()
            for (state in it) {
                states.add(state.name)
                stateAdapter =
                    ArrayAdapter(requireContext(), R.layout.dropdown_list_item, states)
                (binding.state.setAdapter(stateAdapter))
            }
        }

        binding.state.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                selectedState = stateAdapter.getItem(position).toString()
                binding.lga.setText("", false)

                stateViewModel.getStateName(selectedState).observe(viewLifecycleOwner){
                    stateId = it.id
                    Log.d("Owner", "Id: ${it.id}, name: ${it.name}")

                    if(selectedState.contains(it.name)) {
                        lgaViewModel.getLgaStateId(stateId).observe(viewLifecycleOwner){lgaList ->
                            val lgas = ArrayList<String>()
                            for(lga in lgaList) {
                                lgas.add(lga.name)
                                lgaAdapter = ArrayAdapter(
                                    parent.context,
                                    R.layout.dropdown_list_item, lgas)
                            }
                            (binding.lga.setAdapter(lgaAdapter))
                        }
                    }

                }
                binding.lga.onItemClickListener = AdapterView.OnItemClickListener { _, _, pos, _ ->
                    selectedLga = lgaAdapter.getItem(pos).toString()

                    lgaViewModel.getLgaName(selectedLga).observe(viewLifecycleOwner) {
                        lgaId = it.id
                    }
                }

            }
    }

    fun dateOfBirth() {
        val datePicker =
            MaterialDatePicker.Builder
                .datePicker()
//                .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
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

    fun nextButtonClicked() {

        binding.ownerEmail.helperText = validEmail()
        binding.ownerPhoneno.helperText = validPhone()
        binding.ownerNin.helperText = validNin()

        val validEmail = binding.ownerEmail.helperText == null
        val validPhone = binding.ownerPhoneno.helperText == null
        val validNin = binding.ownerNin.helperText == null

        if (binding.groupNameInput.isVisible) {
            if (isGroupNameValid()) {
                sharedViewModel.groupName(
                    binding.groupName.text.toString()
                )
            }
        } else {
//            binding.errorTv.text = getString(R.string.all_fields_required)
            binding.errorTv.text = "All fields are required except EMAIL"
        }

        if (isStepOneEntryValid() && validEmail && validPhone && validNin) {
            sharedViewModel.setStepOne(
                binding.ownerType.text.toString(),
                binding.surname.text.toString(),
                binding.othername.text.toString(),
                binding.dob.text.toString(),
                binding.phoneNumber.text.toString(),
                binding.ninNumber.text.toString(),
                binding.email.text.toString(),
                binding.address.text.toString(),
//                binding.addressTwo.text.toString(),
                binding.state.text.toString(),
                binding.lga.text.toString(),
                stateId.toString(),
                lgaId.toString()
            )
            val action = AddLivestockOwnerStepOneFragmentDirections
                .actionAddLivestockOwnerStepOneFragmentToAddLivestockOwnerStepTwoFragment()
            findNavController().navigate(action)
        } else {
//            binding.errorTv.text = getString(R.string.all_fields_required)
            binding.errorTv.text = "All fields are required except EMAIL"

            /*var message = ""
            if (binding.ownerEmail.helperText != null)
                message += "\n\nEmail: " + binding.ownerEmail.helperText

            if (binding.ownerPhoneno.helperText != null)
                message += "\n\nPhone Number: " + binding.ownerPhoneno.helperText

            if (binding.ownerNin.helperText != null)
                message += "\n\nNin Number: " + binding.ownerNin.helperText

            binding.errorTv.text = message*/
        }

    }

    fun cancelButtonClicked() {
//        sharedViewModel.resetStepOneLivestockOwner()
        sharedViewModel.resetLivestockOwner()
        val action = AddLivestockOwnerStepOneFragmentDirections
            .actionAddLivestockOwnerStepOneFragmentToRegisterFragment()
        findNavController().navigate(action)
    }

    private fun isStepOneEntryValid(): Boolean {
        return sharedViewModel.isStepOneEntryValid(
            binding.ownerType.text.toString(),
            binding.surname.text.toString(),
            binding.othername.text.toString(),
            binding.dob.text.toString(),
            binding.phoneNumber.text.toString(),
            binding.ninNumber.text.toString(),
            /*binding.email.text.toString(),*/
            binding.address.text.toString(),
//            binding.addressTwo.text.toString(),
            binding.state.text.toString(),
            binding.lga.text.toString()
        )
    }

    private fun isGroupNameValid(): Boolean {
        return sharedViewModel.isGroupNameEntryValid(
            binding.groupName.text.toString()
        )
    }

    private fun emailFocusListener() {
        binding.email.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.ownerEmail.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String? {
        val emailText = binding.email.text.toString()
        if (emailText.isBlank()) {
            return ""
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return "Invalid Email Address"
        }

        /*if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return "Invalid Email Address"
        }*/

        return null
    }

    private fun phoneFocusListener() {
        binding.phoneNumber.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.ownerPhoneno.helperText = validPhone()
            }
        }
    }

    private fun validPhone(): String? {
        val phoneText = binding.phoneNumber.text.toString()

        if (phoneText.length != 11) {
            return "Must be 11 Digits"
        }

        if (!phoneText.matches(".*[0-9].*".toRegex())) {
            return "Must be Digits"
        }
        return null
    }

    private fun ninFocusListener() {
        binding.ninNumber.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.ownerNin.helperText = validNin()
            }
        }
    }

    private fun validNin(): String? {
        val ninText = binding.ninNumber.text.toString()

        if (ninText.length < 11) {
            return "Minimum of 11 digits"
        }

        if (!ninText.matches(".*[0-9].*".toRegex())) {
            return "Must be Digits"
        }
        return null
    }

}



