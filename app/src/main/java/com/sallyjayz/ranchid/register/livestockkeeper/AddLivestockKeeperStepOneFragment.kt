package com.sallyjayz.ranchid.register.livestockkeeper

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentAddLivestockKeeperStepOneBinding
import com.sallyjayz.ranchid.viewmodel.register.LgaViewModel
import com.sallyjayz.ranchid.viewmodel.register.LivestockKeeperViewModel
import com.sallyjayz.ranchid.viewmodel.register.StateViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddLivestockKeeperStepOneFragment : Fragment() {

    private lateinit var binding: FragmentAddLivestockKeeperStepOneBinding
    private lateinit var selectedState: String
    private lateinit var selectedLga: String
    private lateinit var stateAdapter: ArrayAdapter<String>
    private lateinit var lgaAdapter: ArrayAdapter<String>
    private val sharedViewModel: LivestockKeeperViewModel by activityViewModels()
    private val stateViewModel: StateViewModel by viewModels()
    private val lgaViewModel: LgaViewModel by viewModels()
    private var stateId: Int = 0
    private var lgaId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                sharedViewModel.resetLivestockKeeper()
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        /*activity?.let {
            mStateViewModel = ViewModelProvider(it)[StateViewModel::class.java]
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddLivestockKeeperStepOneBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            stepOneViewModel = sharedViewModel
            stepOneFragment = this@AddLivestockKeeperStepOneFragment
        }

        stateAndLgaDropDown()
        emailFocusListener()
        phoneFocusListener()
        ninFocusListener()
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
                        lgaViewModel.getLgaStateId(stateId).observe(viewLifecycleOwner){ lgaList ->
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

        binding.keeperEmail.helperText = validEmail()
        binding.keeperPhoneno.helperText = validPhone()
        binding.keeperNin.helperText = validNin()

        val validEmail = binding.keeperEmail.helperText == null
        val validPhone = binding.keeperPhoneno.helperText == null
        val validNin = binding.keeperNin.helperText == null

        if (isStepOneEntryValid() && validEmail && validPhone && validNin) {
            sharedViewModel.setStepOne(
                binding.surname.text.toString(),
                binding.othername.text.toString(),
                binding.dob.text.toString(),
                binding.phoneNumber.text.toString(),
                binding.ninNumber.text.toString(),
                binding.email.text.toString(),
                binding.address.text.toString(),
                /*binding.addressTwo.text.toString(),*/
                binding.state.text.toString(),
                binding.lga.text.toString(),
                stateId.toString(),
                lgaId.toString()
            )
            val action = AddLivestockKeeperStepOneFragmentDirections
                .actionAddLivestockKeeperStepOneFragmentToAddLivestockKeeperStepTwoFragment()
            findNavController().navigate(action)
        } else {
            binding.errorTv.text = getString(R.string.all_fields_required)
        }
    }

    fun cancelButtonClicked() {
//        sharedViewModel.resetStepOneLivestockKeeper()
        sharedViewModel.resetLivestockKeeper()
        val action = AddLivestockKeeperStepOneFragmentDirections
            .actionAddLivestockKeeperStepOneFragmentToRegisterFragment()
        findNavController().navigate(action)
    }

    private fun isStepOneEntryValid(): Boolean {
        return sharedViewModel.isStepOneEntryValid(
            binding.surname.text.toString(),
            binding.othername.text.toString(),
            binding.dob.text.toString(),
            binding.phoneNumber.text.toString(),
            binding.ninNumber.text.toString(),
            /*binding.email.text.toString(),*/
            binding.address.text.toString(),
            /*binding.addressTwo.text.toString(),*/
            binding.state.text.toString(),
            binding.lga.text.toString()
        )
    }

    private fun emailFocusListener() {
        binding.email.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.keeperEmail.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String? {
        val emailText = binding.email.text.toString()
        /*if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return "Invalid Email Address"
        }*/

        if (emailText.isBlank()) {
            return ""
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return "Invalid Email Address"
        }
        return null
    }

    private fun phoneFocusListener() {
        binding.phoneNumber.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.keeperPhoneno.helperText = validPhone()
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
                binding.keeperNin.helperText = validNin()
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