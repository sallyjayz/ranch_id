package com.sallyjayz.ranchid.veterinaryDoctor.vaccinateLivestock

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
import com.sallyjayz.ranchid.databinding.FragmentVaccinateLivestockConfirmationBinding
import com.sallyjayz.ranchid.model.vet.vaccination.Vaccination
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import com.sallyjayz.ranchid.viewmodel.vet.response.VaccinationResponseViewModel
import com.sallyjayz.ranchid.viewmodel.vet.LivestockVaccinationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VaccinateLivestockConfirmationFragment : Fragment() {

    private lateinit var binding: FragmentVaccinateLivestockConfirmationBinding
    private val sharedViewModel: LivestockVaccinationViewModel by activityViewModels()
    private val vaccinationResponseViewModel: VaccinationResponseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVaccinateLivestockConfirmationBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            stepFourViewModel = sharedViewModel
            //click buttons to be implemented
            stepFourFragment = this@VaccinateLivestockConfirmationFragment
        }

        Glide.with(requireContext())
            .load(sharedViewModel.photo.value.toString())
            .override(150, 150)
            .centerCrop()
            .into(binding.tagImage)

    }

    fun submitClicked() {
        vaccinationResponseViewModel.vaccinationResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {

                    Log.d("VaccinateLivestockCon", "Id: ${it.errorMessage}")

                    /*Log.d("VaccinateLivestockCon",
                        "date: ${sharedViewModel.dateOfVaccination.value.toString()}," +
                                "dosage: ${sharedViewModel.dosage.value.toString()}," +
                                "drug_id: ${sharedViewModel.drugId.value.toString()}," +
                                "frequency: ${sharedViewModel.frequency.value.toString()}," +
                                "nextAppointment: ${sharedViewModel.dateOfNextAppointment.value.toString()}," +
                                "notes: ${sharedViewModel.notes.value.toString()}," +
                                "tag_id: ${sharedViewModel.scannedId.value.toString()}," +
                                "time: ${sharedViewModel.timeOfVaccination.value.toString()}," +
                                "council number: ${sharedViewModel.vetCouncilNumber.value.toString()}" +
                                "photo: ${sharedViewModel.photoBase64String.value.toString()},")*/

                    binding.vaccinationProgress.isVisible = false

                    val action = VaccinateLivestockConfirmationFragmentDirections
                        .actionVaccinateLivestockConfirmationFragmentToVaccinateLivestockFailedFragment()
                    findNavController().navigate(action)
                }
                ApiResponse.Loading -> {
                    binding.vaccinationProgress.isVisible = true
                }
                is ApiResponse.Success -> {
                    binding.vaccinationProgress.isVisible = false
                    val action = VaccinateLivestockConfirmationFragmentDirections
                        .actionVaccinateLivestockConfirmationFragmentToVaccinateLivestockSuccessfulFragment()
                    findNavController().navigate(action)
                }
            }
        }

        vaccinationResponseViewModel.addVaccination(
            Vaccination(
                sharedViewModel.dateOfVaccination.value.toString(),
                sharedViewModel.dosage.value.toString(),
                sharedViewModel.drugId.value.toString(),
                sharedViewModel.frequency.value.toString(),
                sharedViewModel.dateOfNextAppointment.value.toString(),
                sharedViewModel.notes.value.toString(),
                sharedViewModel.scannedId.value.toString(),
                sharedViewModel.timeOfVaccination.value.toString(),
                sharedViewModel.photoBase64String.value.toString(),
                sharedViewModel.vetCouncilNumber.value.toString()

            ),
            object: CoroutinesErrorHandler {
                override fun onError(message: String) {
                    binding.errorTv.text = getString(R.string.offline)
                }

            }
        )

    }
}