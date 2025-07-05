package com.sallyjayz.ranchid.veterinaryDoctor.livestockTreatment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentLivestockTreatmentFailedBinding
import com.sallyjayz.ranchid.viewmodel.vet.LivestockTreatmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LivestockTreatmentFailedFragment : Fragment() {

    private lateinit var binding: FragmentLivestockTreatmentFailedBinding
    private val sharedViewModel: LivestockTreatmentViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                sharedViewModel.resetLivestockTreatment()
                val action = LivestockTreatmentFailedFragmentDirections
                    .actionLivestockTreatmentFailedFragmentToHomeFragment()
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
        binding = FragmentLivestockTreatmentFailedBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.treatmentFailedFragment = this
    }

    fun retry() {
        val action = LivestockTreatmentFailedFragmentDirections
            .actionLivestockTreatmentFailedFragmentToLivestockTreatmentConfirmationFragment()
        findNavController().navigate(action)
    }


}