package com.sallyjayz.ranchid.veterinaryDoctor.livestockTreatment

import android.os.Bundle
import android.util.Log
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
import com.sallyjayz.ranchid.databinding.FragmentLivestockTreatmentConfirmationBinding
import com.sallyjayz.ranchid.model.vet.treatment.Treatment
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import com.sallyjayz.ranchid.viewmodel.vet.LivestockTreatmentViewModel
import com.sallyjayz.ranchid.viewmodel.vet.response.TreatmentResponseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LivestockTreatmentConfirmationFragment : Fragment() {

    private lateinit var binding: FragmentLivestockTreatmentConfirmationBinding
    private val sharedViewModel: LivestockTreatmentViewModel by activityViewModels()
    private val treatmentResponseViewModel: TreatmentResponseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLivestockTreatmentConfirmationBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            stepFiveViewModel = sharedViewModel
            //click buttons to be implemented
            stepFiveFragment = this@LivestockTreatmentConfirmationFragment
        }

        Glide.with(requireContext())
            .load(sharedViewModel.photo.value.toString())
            .override(150, 150)
            .centerCrop()
            .into(binding.tagImage)

    }

    fun submitClicked() {
        treatmentResponseViewModel.treatmentResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {
                    binding.treatmentProgress.isVisible = false

                    Log.d("TreatmentCon", "Id: ${it.errorMessage}")

                    val action = LivestockTreatmentConfirmationFragmentDirections
                        .actionLivestockTreatmentConfirmationFragmentToLivestockTreatmentFailedFragment()
                    findNavController().navigate(action)
                }
                ApiResponse.Loading -> {
                    binding.treatmentProgress.isVisible = true
                }
                is ApiResponse.Success -> {
                    binding.treatmentProgress.isVisible = false

                    val action = LivestockTreatmentConfirmationFragmentDirections
                        .actionLivestockTreatmentConfirmationFragmentToLivestockTreatmentSuccessFragment()
                    findNavController().navigate(action)
                }
            }
        }

        treatmentResponseViewModel.addTreatment(
            Treatment(
                sharedViewModel.administrationRoute.value.toString(),
                sharedViewModel.dateOfTreatment.value.toString(),
                sharedViewModel.diagnosis.value.toString(),
                sharedViewModel.dosage.value.toString(),
                sharedViewModel.vaccine.value.toString(),
                sharedViewModel.followUpDate.value.toString(),
                sharedViewModel.frequency.value.toString(),
                sharedViewModel.injection.value.toString(),
                sharedViewModel.notes.value.toString(),
                sharedViewModel.scannedId.value.toString(),
                sharedViewModel.timeOfTreatment.value.toString(),
                sharedViewModel.treatmentCategory.value.toString(),
                sharedViewModel.photoBase64String.value.toString(),
                sharedViewModel.treatmentType.value.toString(),
                sharedViewModel.vetCouncilNumber.value.toString(),
            ),
            object: CoroutinesErrorHandler {
                override fun onError(message: String) {
                    binding.errorTv.text = getString(R.string.offline)
                }

            }
        )
    }
}