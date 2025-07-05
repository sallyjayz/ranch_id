package com.sallyjayz.ranchid.veterinaryDoctor.vaccinateLivestock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentVaccinateLivestockSuccessfulBinding
import com.sallyjayz.ranchid.viewmodel.vet.LivestockVaccinationViewModel


class VaccinateLivestockSuccessfulFragment : Fragment() {

    private lateinit var binding: FragmentVaccinateLivestockSuccessfulBinding
    private val sharedViewModel: LivestockVaccinationViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                sharedViewModel.resetLivestockVaccination()
                val action = VaccinateLivestockSuccessfulFragmentDirections
                    .actionVaccinateLivestockSuccessfulFragmentToHomeFragment()
                findNavController().navigate(action)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVaccinateLivestockSuccessfulBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.successMessage.text = getString(R.string.vaccinated_successfully, sharedViewModel.scannedId.value)

        binding.vaccinatedSuccessFragment = this@VaccinateLivestockSuccessfulFragment
    }

    fun addNewVaccination() {
        sharedViewModel.resetLivestockVaccination()
        val action = VaccinateLivestockSuccessfulFragmentDirections
            .actionVaccinateLivestockSuccessfulFragmentToVaccinateLivestockOneFragment()
        findNavController().navigate(action)
    }

    fun goToDashboard() {
        sharedViewModel.resetLivestockVaccination()
        val action = VaccinateLivestockSuccessfulFragmentDirections
            .actionVaccinateLivestockSuccessfulFragmentToHomeFragment()
        findNavController().navigate(action)

    }

}