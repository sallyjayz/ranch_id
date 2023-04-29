package com.sallyjayz.ranchid.register.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentAddLivestockLocationConfirmationBinding
import com.sallyjayz.ranchid.model.register.registrationlocation.RegistrationLocation
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import com.sallyjayz.ranchid.viewmodel.PermissionViewModel
import com.sallyjayz.ranchid.viewmodel.register.LocationViewModel
import com.sallyjayz.ranchid.viewmodel.register.response.RegistrationLocationViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddLivestockLocationConfirmationFragment : Fragment() {

    private lateinit var binding: FragmentAddLivestockLocationConfirmationBinding
    private val sharedViewModel: LocationViewModel by activityViewModels()
    private lateinit var permissionViewModel: PermissionViewModel
    private val regLocationResponseViewModel: RegistrationLocationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let {
            permissionViewModel = ViewModelProvider(it)[PermissionViewModel::class.java]
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddLivestockLocationConfirmationBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            locationConfirmModel = sharedViewModel
            //click buttons to be implemented
            locationConfirmationFragment = this@AddLivestockLocationConfirmationFragment
        }

    }

    fun submitClicked() {

        val dateFormatter = SimpleDateFormat("MMMM d, yyyy hh:mm aaa", Locale.getDefault())
        val date = dateFormatter.format(Date())

        regLocationResponseViewModel.addRegistrationLocationResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {
//                        "Code: ${it.code}, ${it.errorMessage}"
                    val action = AddLivestockLocationConfirmationFragmentDirections
                        .actionAddLivestockLocationConfirmationFragmentToAddLivestockLocationUnsuccessfulFragment()
                    findNavController().navigate(action)
                }
                ApiResponse.Loading -> {
//                        binding.errorTv.text = "Loading"
                    binding.locationProgress.isVisible = true
                }
                is ApiResponse.Success -> {
//                        binding.errorTv.text = "${it.data.data.id}"
                    val action = AddLivestockLocationConfirmationFragmentDirections
                        .actionAddLivestockLocationConfirmationFragmentToAddLivestockLocationSuccessfulFragment()
                    findNavController().navigate(action)
                }
            }
        }

        regLocationResponseViewModel.addRegistrationLocation(

            RegistrationLocation(
                sharedViewModel.locationName.value.toString(),
                sharedViewModel.locationAddress.value.toString(),
                sharedViewModel.locationStateId.value.toString().toInt(),
                sharedViewModel.locationLgaId.value.toString().toInt(),
                "",
                sharedViewModel.certificateId.value.toString().toInt(),
                permissionViewModel.longitude.value,
                permissionViewModel.longitude.value,
                sharedViewModel.locationType.value.toString(),
                sharedViewModel.ownerName.value.toString(),
                date
            ),
            object: CoroutinesErrorHandler {
                override fun onError(message: String) {
//                    binding.errorTv.text = "Error! $message"

                    binding.locationProgress.isVisible = false
                    binding.errorTv.isVisible = true
                    binding.locationSubmitButton.isClickable = false
                    binding.locationSubmitButton.alpha = 0.5F

                }
            }
        )

    }
}