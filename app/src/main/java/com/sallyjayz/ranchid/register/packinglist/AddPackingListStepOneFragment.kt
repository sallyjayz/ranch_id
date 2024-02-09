package com.sallyjayz.ranchid.register.packinglist

import android.os.Bundle
import android.util.Log
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
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentAddLivestockKeeperStepOneBinding
import com.sallyjayz.ranchid.databinding.FragmentAddPackingListStepOneBinding
import com.sallyjayz.ranchid.register.livestockkeeper.AddLivestockKeeperStepOneFragmentDirections
import com.sallyjayz.ranchid.utils.Util
import com.sallyjayz.ranchid.viewmodel.MyState
import com.sallyjayz.ranchid.viewmodel.NetworkStatusViewModel
import com.sallyjayz.ranchid.viewmodel.packinglist.PackingListViewModel
import com.sallyjayz.ranchid.viewmodel.register.AnimalTypeViewModel
import com.sallyjayz.ranchid.viewmodel.register.LgaViewModel
import com.sallyjayz.ranchid.viewmodel.register.StateViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class AddPackingListStepOneFragment : Fragment() {

    private lateinit var binding: FragmentAddPackingListStepOneBinding
    private lateinit var selectedState: String
    private lateinit var selectedLga: String
    private lateinit var selectedType: String
    private lateinit var stateAdapter: ArrayAdapter<String>
    private lateinit var lgaAdapter: ArrayAdapter<String>
    private lateinit var livestockTypeAdapter: ArrayAdapter<String>
    private val sharedViewModel: PackingListViewModel by activityViewModels()
    private val networkStatusViewModel: NetworkStatusViewModel by activityViewModels()
//    private val animalTypeViewModel: AnimalTypeViewModel by viewModels()
    private val stateViewModel: StateViewModel by viewModels()
    private val lgaViewModel: LgaViewModel by viewModels()
    private var stateId: Int = 0
    private var lgaId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                sharedViewModel.resetPackingList()
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
        binding = FragmentAddPackingListStepOneBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            stepOneViewModel = sharedViewModel
            stepOneFragment = this@AddPackingListStepOneFragment
        }

        networkStatusViewModel.state.observe(viewLifecycleOwner) {
            when(it) {
                MyState.Fetched -> {
                    binding.offlineTv.isVisible = false
                    binding.nextButton.isEnabled = true
                    binding.nextButton.alpha = 1.0F
                }
                MyState.Error -> {
                    binding.offlineTv.isVisible = true
                    binding.nextButton.isEnabled = false
                    binding.nextButton.alpha = 0.5F
                }
            }
        }

        livestockTypeDropdown()
        stateAndLgaDropDown()

    }

    private fun livestockTypeDropdown() {

        val livestockTypeAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, Util.PACKING_LIST_LIVESTOCK_TYPE)
        (binding.livestockType.setAdapter(livestockTypeAdapter))

        binding.livestockType.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            selectedType = livestockTypeAdapter.getItem(position).toString()
        }

        /*animalTypeViewModel.readAllAnimalType.observe(viewLifecycleOwner) {
            val animalTypes = ArrayList<String>()
            for (animal_type in it) {
                animalTypes.add(animal_type.name)
                livestockTypeAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, animalTypes)
                (binding.livestockType.setAdapter(livestockTypeAdapter))
            }
        }

        binding.livestockType.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            selectedType = livestockTypeAdapter.getItem(position).toString()
        }*/
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

    fun cancelButtonClicked() {
        sharedViewModel.resetPackingList()
        val action = AddPackingListStepOneFragmentDirections
            .actionAddPackingListStepOneFragmentToRegisterFragment()
        findNavController().navigate(action)
    }

    fun nextButtonClicked() {

        if (isStepOneEntryValid()) {
            sharedViewModel.setStepOne(
                binding.driverName.text.toString(),
                binding.vehicleName.text.toString(),
                binding.regNo.text.toString(),
                binding.livestockType.text.toString(),
//                binding.livestockQty.text.toString(),
                binding.destination.text.toString(),
                binding.state.text.toString(),
                binding.lga.text.toString(),
                stateId.toString(),
                lgaId.toString()
            )
            val action = AddPackingListStepOneFragmentDirections
                .actionAddPackingListStepOneFragmentToAddPackingListStepTwoFragment()
            findNavController().navigate(action)
        } else {
            binding.errorTv.text = getString(R.string.all_fields_required)
        }

    }

    private fun isStepOneEntryValid(): Boolean {
        return sharedViewModel.isStepOneEntryValid(
            binding.driverName.text.toString(),
            binding.vehicleName.text.toString(),
            binding.regNo.text.toString(),
            binding.livestockType.text.toString(),
//            binding.livestockQty.text.toString(),
            binding.destination.text.toString(),
            binding.state.text.toString(),
            binding.lga.text.toString()
        )
    }
}