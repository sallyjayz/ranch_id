package com.sallyjayz.ranchid.register.location


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentAddLivestockLocationBinding
import com.sallyjayz.ranchid.utils.Util
import com.sallyjayz.ranchid.viewmodel.PermissionViewModel
import com.sallyjayz.ranchid.viewmodel.register.LgaViewModel
import com.sallyjayz.ranchid.viewmodel.register.LocationViewModel
import com.sallyjayz.ranchid.viewmodel.register.StateViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.ArrayList

@AndroidEntryPoint
class AddLivestockLocationFragment : Fragment() {

    private lateinit var binding: FragmentAddLivestockLocationBinding
    /*private var selectedState: String? = null
    private var selectedLga: String? = null*/
    private lateinit var selectedState: String
    private lateinit var selectedLga: String
    private lateinit var stateAdapter: ArrayAdapter<String>
    private lateinit var lgaAdapter: ArrayAdapter<String>
    private val sharedViewModel: LocationViewModel by activityViewModels()
    private val stateViewModel: StateViewModel by viewModels()
    private val lgaViewModel: LgaViewModel by viewModels()
    private lateinit var permissionViewModel: PermissionViewModel
    private var stateId: Int = 0
    private var lgaId: Int = 0
//    private var priority: Int = 0

//    private var latitude: Double? = 0.0
//    private var longitude: Double? = 0.0
//    private var settingsClient: SettingsClient? = null
//    private var locationSettingsRequest: LocationSettingsRequest? = null
//    private var requestingLocationUpdates: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let {
            permissionViewModel = ViewModelProvider(it)[PermissionViewModel::class.java]
        }

//        No need by default, doing this to clear content before going back to register screen
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                sharedViewModel.resetLocation()
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

        binding = FragmentAddLivestockLocationBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationTypeDropdown()
        stateAndLgaDropDown()
//        setLocation()

        Log.d("LivestockLocation", "latitude: ${permissionViewModel.latitude.value}, longitude: ${permissionViewModel.longitude.value}")

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            locationViewModel = sharedViewModel
            locationFragment = this@AddLivestockLocationFragment
        }
    }


    private fun locationTypeDropdown() {
        val locationTypeAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, Util.LOCATION_TYPE)
        (binding.type.setAdapter(locationTypeAdapter))

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

    fun nextButtonClicked() {

        if (permissionViewModel.longitude.value == null && permissionViewModel.latitude.value == null) {
            binding.errorTv.text = getString(R.string.location_lat_lon_warning)
        }else if (isEntryValid()) {
            val certificateId: Int = binding.certificate.text.toString().toInt()
            sharedViewModel.setLocation(
                binding.ownerName.text.toString(),
                binding.name.text.toString(),
                binding.address.text.toString(),
                binding.type.text.toString(),
                binding.state.text.toString(),
                binding.lga.text.toString(),
                stateId,
                lgaId,
                binding.certificate.text.toString(),
                certificateId,

            )
            val action = AddLivestockLocationFragmentDirections
                .actionAddLivestockLocationFragmentToAddLivestockLocationConfirmationFragment()
            findNavController().navigate(action)
        } else {
            binding.errorTv.text = getString(R.string.all_fields_required)
        }
    }

    fun cancelButtonClicked() {
        sharedViewModel.resetLocation()
        val action = AddLivestockLocationFragmentDirections
            .actionAddLivestockLocationFragmentToRegisterFragment()
        findNavController().navigate(action)
    }

    private fun isEntryValid(): Boolean {
        return sharedViewModel.isEntryValid(
            binding.ownerName.text.toString(),
            binding.name.text.toString(),
            binding.address.text.toString(),
            binding.type.text.toString(),
            binding.state.text.toString(),
            binding.lga.text.toString(),
            binding.certificate.text.toString()
        )
    }
}