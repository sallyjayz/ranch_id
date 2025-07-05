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
import com.sallyjayz.ranchid.databinding.FragmentLivestockTreatmentSuccessBinding
import com.sallyjayz.ranchid.viewmodel.vet.LivestockTreatmentViewModel
import dagger.hilt.android.AndroidEntryPoint

class LivestockTreatmentSuccessFragment : Fragment() {

    private lateinit var binding: FragmentLivestockTreatmentSuccessBinding
    private val sharedViewModel: LivestockTreatmentViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                sharedViewModel.resetLivestockTreatment()
                val action = LivestockTreatmentSuccessFragmentDirections
                    .actionLivestockTreatmentSuccessFragmentToHomeFragment()
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
        binding = FragmentLivestockTreatmentSuccessBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.successMessage.text = getString(R.string.treated_successfully, sharedViewModel.scannedId.value)

        binding.treatmentSuccessFragment = this@LivestockTreatmentSuccessFragment
    }

    fun addNewTreatment(){
        sharedViewModel.resetLivestockTreatment()
        val action = LivestockTreatmentSuccessFragmentDirections
            .actionLivestockTreatmentSuccessFragmentToLivestockTreatmentOneFragment()
        findNavController().navigate(action)
    }

    fun goToDashboard() {
        sharedViewModel.resetLivestockTreatment()
        val action = LivestockTreatmentSuccessFragmentDirections
            .actionLivestockTreatmentSuccessFragmentToHomeFragment()
        findNavController().navigate(action)
    }

}