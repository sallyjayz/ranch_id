package com.sallyjayz.ranchid.register.taglivestock

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
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentTagLivestockStepFourBinding
import com.sallyjayz.ranchid.model.offline.taglivestock.OfflineTagLivestock
import com.sallyjayz.ranchid.model.register.taglivestock.TagLivestock
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import com.sallyjayz.ranchid.viewmodel.PermissionViewModel
import com.sallyjayz.ranchid.viewmodel.TokenViewModel
import com.sallyjayz.ranchid.viewmodel.offline.OfflineTagLivestockViewModel
import com.sallyjayz.ranchid.viewmodel.register.TagLivestockViewModel
import com.sallyjayz.ranchid.viewmodel.register.response.TagLivestockResponseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TagLivestockStepFourFragment : Fragment() {

    private lateinit var binding: FragmentTagLivestockStepFourBinding
    private val tokenViewModel: TokenViewModel by activityViewModels()
    private val sharedViewModel: TagLivestockViewModel by activityViewModels()
    private val tagLivestockResponseViewModel: TagLivestockResponseViewModel by viewModels()
    private val offlineTagLivestockView: OfflineTagLivestockViewModel by viewModels()
    private lateinit var permissionViewModel: PermissionViewModel

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
        binding = FragmentTagLivestockStepFourBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            stepFourModel = sharedViewModel
            //click buttons to be implemented
            stepFourFragment = this@TagLivestockStepFourFragment
        }

        Glide.with(requireContext())
            .load(sharedViewModel.verificationPhoto.value.toString())
            .override(150, 150)
            .centerCrop()
            .into(binding.verificationImage)

        Glide.with(requireContext())
            .load(sharedViewModel.muzzlePhoto.value.toString())
            .override(150, 150)
            .centerCrop()
            .into(binding.muzzleCameraImage)

    }

    fun submitClicked() {
        tagLivestockResponseViewModel.tagLivestockResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {

//                    MaterialAlertDialogBuilder(requireContext(), R.style.ThemeOverlay_RanchID_Button_TextButton_Dialog)
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(resources.getString(R.string.error))
                        .setMessage(it.errorMessage)
                        /*.setNeutralButton(resources.getString(R.string.cancel)) { dialog, which ->
                            // Respond to neutral button press
                        }*/
                        /*.setNegativeButton(resources.getString(R.string.close)) { dialog, _ ->
                            // Respond to negative button press
                            dialog.dismiss()
                        }*/
                        .setPositiveButton(resources.getString(R.string.ok)) { dialog, _ ->
                            // Respond to positive button press
                            dialog.dismiss()
                        }
                        .show()

                    binding.tagLivestockProgress.isVisible = false

//                    binding.errorTv.text = "Code: ${it.code}, ${it.errorMessage}"
                    /*val action = TagLivestockStepFourFragmentDirections
                        .actionTagLivestockStepFourFragmentToTagLivestockUnsuccessfulFragment()
                    findNavController().navigate(action)*/
                }
                ApiResponse.Loading -> {
//                    binding.errorTv.text = "Loading"
                    binding.tagLivestockProgress.isVisible = true
                }
                is ApiResponse.Success -> {
//                    binding.errorTv.text = "${it.data.data.id}"
                    val action = TagLivestockStepFourFragmentDirections
                        .actionTagLivestockStepFourFragmentToTagLivestockSuccessfulFragment()
                    findNavController().navigate(action)
                }
            }
        }

        tagLivestockResponseViewModel.addTagLivestock(
            TagLivestock(
                permissionViewModel.latitude.value.toString(),
                permissionViewModel.longitude.value.toString(),
                sharedViewModel.scannedTagId.value.toString(),
                sharedViewModel.passportId.value.toString(),
                sharedViewModel.livestockKeeperId.value.toString(),
                sharedViewModel.livestockOwnerId.value.toString(),
                sharedViewModel.livestockType.value.toString(),
                sharedViewModel.livestockBreed.value.toString(),
                sharedViewModel.gender.value.toString(),
                sharedViewModel.healthStatus.value.toString(),
                sharedViewModel.dateOfBirth.value.toString(),
                sharedViewModel.description.value.toString(),
                sharedViewModel.taggingLocationId.value.toString(),
                "",
                sharedViewModel.productionType.value.toString(),
                tokenViewModel.username.value.toString(),
                sharedViewModel.locationComment.value.toString(),
                "TAGGING",
                sharedViewModel.verificationPhotoBase64String.value.toString(),
                sharedViewModel.muzzlePhotoBase64String.value.toString()
            ),
            object: CoroutinesErrorHandler {
                override fun onError(message: String) {
//                    binding.errorTv.text = "Error! $message"
                    binding.errorTv.text = getString(R.string.offline)

                    CoroutineScope(Dispatchers.IO).launch {
                        val offlineTagLivestock = OfflineTagLivestock(
                            0,
                            permissionViewModel.latitude.value.toString(),
                            permissionViewModel.longitude.value.toString(),
                            sharedViewModel.scannedTagId.value.toString(),
                            sharedViewModel.passportId.value.toString(),
                            sharedViewModel.livestockKeeperId.value.toString(),
                            sharedViewModel.livestockOwnerId.value.toString(),
                            sharedViewModel.livestockType.value.toString(),
                            sharedViewModel.livestockBreed.value.toString(),
                            sharedViewModel.gender.value.toString(),
                            sharedViewModel.healthStatus.value.toString(),
                            sharedViewModel.dateOfBirth.value.toString(),
                            sharedViewModel.description.value.toString(),
                            sharedViewModel.taggingLocationId.value.toString(),
                            "",
                            sharedViewModel.productionType.value.toString(),
                            tokenViewModel.username.value.toString(),
                            sharedViewModel.locationComment.value.toString(),
                            "TAGGING",
                            sharedViewModel.verificationPhotoBase64String.value.toString(),
                            sharedViewModel.muzzlePhotoBase64String.value.toString(),
                            "PENDING"
                        )
                        offlineTagLivestockView.insertOfflineTagLivestock(offlineTagLivestock)
                    }
                    val action = TagLivestockStepFourFragmentDirections
                        .actionTagLivestockStepFourFragmentToOfflineTagLivestockSuccess()
                    findNavController().navigate(action)
                }
            }
        )

    }
}