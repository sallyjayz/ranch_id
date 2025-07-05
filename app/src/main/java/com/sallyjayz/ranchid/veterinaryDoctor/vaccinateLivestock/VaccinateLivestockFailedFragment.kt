package com.sallyjayz.ranchid.veterinaryDoctor.vaccinateLivestock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sallyjayz.ranchid.databinding.FragmentVaccinateLivestockFailedBinding
import com.sallyjayz.ranchid.viewmodel.vet.LivestockVaccinationViewModel

class VaccinateLivestockFailedFragment : Fragment() {

    private lateinit var binding: FragmentVaccinateLivestockFailedBinding
    private val sharedViewModel: LivestockVaccinationViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                sharedViewModel.resetLivestockVaccination()
                val action = VaccinateLivestockFailedFragmentDirections
                    .actionVaccinateLivestockFailedFragmentToHomeFragment()
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
        binding = FragmentVaccinateLivestockFailedBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vaccinationFailedFragment = this
    }

    fun retry() {
        val action = VaccinateLivestockFailedFragmentDirections
            .actionVaccinateLivestockFailedFragmentToVaccinateLivestockConfirmationFragment()
        findNavController().navigate(action)
    }
}