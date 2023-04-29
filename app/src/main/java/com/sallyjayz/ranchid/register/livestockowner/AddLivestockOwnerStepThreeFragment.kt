package com.sallyjayz.ranchid.register.livestockowner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentAddLivestockOwnerStepThreeBinding
import com.sallyjayz.ranchid.model.offline.owner.OfflineOwner
import com.sallyjayz.ranchid.model.register.owner.Owner
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import com.sallyjayz.ranchid.viewmodel.TokenViewModel
import com.sallyjayz.ranchid.viewmodel.offline.OfflineOwnerViewModel
import com.sallyjayz.ranchid.viewmodel.register.LivestockOwnerViewModel
import com.sallyjayz.ranchid.viewmodel.register.response.OwnerResponseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddLivestockOwnerStepThreeFragment : Fragment() {

    private lateinit var binding: FragmentAddLivestockOwnerStepThreeBinding
    private val tokenViewModel: TokenViewModel by activityViewModels()
    private val sharedViewModel: LivestockOwnerViewModel by activityViewModels()
    private val ownerResponseViewModel: OwnerResponseViewModel by viewModels()
    private val offlineOwnerView: OfflineOwnerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddLivestockOwnerStepThreeBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            stepThreeModel = sharedViewModel
            //click buttons to be implemented
            stepThreeFragment = this@AddLivestockOwnerStepThreeFragment
        }

        Glide.with(requireContext())
            .load(sharedViewModel.ownerCameraPhoto.value.toString())
            .override(150, 150)
            .centerCrop()
            .into(binding.ownerImage)

    }


    fun String?.toBoolean() = equals("Yes")

    fun submitClicked() {

        ownerResponseViewModel.addOwnerResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {

                    /*var message = ""

                    for (ninError in it.errorValidation.nin) {
//                        message += "$ninError \n\n"
                        message += "NIN already exist \n\n"
                    }

                    for (phoneError in it.errorValidation.phone_number) {
//                        message += "$ninError \n\n"
                        message += "Phone Number already exist \n\n"
                    }

                    for (emailError in it.errorValidation.email_address) {
//                        message += "$emailError \n\n"
                        message += "Email Address already exist \n"
                    }

                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(resources.getString(R.string.error))
                        .setMessage(message)
                        *//*.setNeutralButton(resources.getString(R.string.cancel)) { dialog, which ->
                            // Respond to neutral button press
                        }*//*
                        *//*.setNegativeButton(resources.getString(R.string.close)) { dialog, _ ->
                            // Respond to negative button press
                            dialog.dismiss()
                        }*//*
                        .setPositiveButton(resources.getString(R.string.ok)) { dialog, which ->
                            // Respond to positive button press
                            dialog.dismiss()
                        }
                        .show()*/

                    binding.stepThreeProgress.isVisible = false

//                        "Code: ${it.code}, ${it.errorMessage}"

                    val action = AddLivestockOwnerStepThreeFragmentDirections
                        .actionAddLivestockOwnerStepThreeFragmentToAddLivestockOwnerUnsuccessfulFragment()
                    findNavController().navigate(action)
                }
                ApiResponse.Loading -> {
//                        binding.errorTv.text = "Loading"
                    binding.stepThreeProgress.isVisible = true
                }
                is ApiResponse.Success -> {
//                        binding.errorTv.text = "${it.data.data.id}"
                    val action = AddLivestockOwnerStepThreeFragmentDirections
                        .actionAddLivestockOwnerStepThreeFragmentToAddLivestockOwnerSuccessfulFragment()
                    findNavController().navigate(action)
                }
            }
        }

        ownerResponseViewModel.addOwner(
            Owner(
                sharedViewModel.ownerSurname.value.toString(),
                sharedViewModel.ownerOthername.value.toString(),
                sharedViewModel.ownerGender.value.toString(),
                sharedViewModel.ownerDateOfBirth.value.toString(),
                sharedViewModel.ownerPhoneNumber.value.toString(),
                sharedViewModel.ownerMaritalStatus.value.toString(),
                sharedViewModel.ownerNextOfKin.value.toString(),
                sharedViewModel.ownerKinPhoneNumber.value.toString(),
                sharedViewModel.ownerEmail.value.toString(),
                sharedViewModel.ownerNin.value.toString(),
                sharedViewModel.ownerStateId.value.toString(),
                sharedViewModel.ownerLgaId.value.toString(),
                "",
                sharedViewModel.ownerDocumentType.value.toString(),
                sharedViewModel.ownerDocumentNumber.value.toString(),
                sharedViewModel.ownerFarmLocationId.value.toString(),
                sharedViewModel.ownerAddress.value.toString(),
                "N/A",
                sharedViewModel.ownerCameraPhoto.value.toString(),
                sharedViewModel.ownerType.value.toString(),
                sharedViewModel.groupName.value.toString(),
                "",
                tokenViewModel.username.value.toString(),
//                sharedViewModel.ownerLivestockKeeper.value.toString().toBoolean()
                sharedViewModel.ownerLivestockKeeper.value?.toBoolean() ?: false
            ),
            object: CoroutinesErrorHandler {
                override fun onError(message: String) {
//                    binding.errorTv.text = "Error! $message"
                    binding.errorTv.text = getString(R.string.offline)

                    CoroutineScope(Dispatchers.IO).launch {
                        val offlineOwner = OfflineOwner(
                            0,
                            sharedViewModel.ownerSurname.value.toString(),
                            sharedViewModel.ownerOthername.value.toString(),
                            sharedViewModel.ownerGender.value.toString(),
                            sharedViewModel.ownerDateOfBirth.value.toString(),
                            sharedViewModel.ownerPhoneNumber.value.toString(),
                            sharedViewModel.ownerMaritalStatus.value.toString(),
                            sharedViewModel.ownerNextOfKin.value.toString(),
                            sharedViewModel.ownerKinPhoneNumber.value.toString(),
                            sharedViewModel.ownerEmail.value.toString(),
                            sharedViewModel.ownerNin.value.toString(),
                            sharedViewModel.ownerStateId.value.toString(),
                            sharedViewModel.ownerLgaId.value.toString(),
                            "",
                            sharedViewModel.ownerDocumentType.value.toString(),
                            sharedViewModel.ownerDocumentNumber.value.toString(),
                            sharedViewModel.ownerFarmLocationId.value.toString(),
                            sharedViewModel.ownerAddress.value.toString(),
                            "N/A",
                            sharedViewModel.ownerCameraPhoto.value.toString(),
                            sharedViewModel.ownerType.value.toString(),
                            sharedViewModel.groupName.value.toString(),
                            "",
                            tokenViewModel.username.value.toString(),
//                            sharedViewModel.ownerLivestockKeeper.value.toString().toBoolean(),
                            sharedViewModel.ownerLivestockKeeper.value?.toBoolean() ?: false,
                            "PENDING"
                        )
                        offlineOwnerView.insertOfflineOwner(offlineOwner)
                    }

                    val action = AddLivestockOwnerStepThreeFragmentDirections
                        .actionAddLivestockOwnerStepThreeFragmentToOfflineOwnerSuccess()
                    findNavController().navigate(action)

//                    binding.loginButton.isClickable = true
//                    binding.loginButton.alpha = 1.0F
                }
            }
        )

    }

}