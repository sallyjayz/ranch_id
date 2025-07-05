package com.sallyjayz.ranchid.veterinaryDoctor.livestockTreatment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentLivestockTreatmentTwoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LivestockTreatmentTwoFragment : Fragment() {

    private lateinit var binding: FragmentLivestockTreatmentTwoBinding
    private val args: LivestockTreatmentTwoFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //        No need by default, doing this to clear content before going back to register screen
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
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
        binding = FragmentLivestockTreatmentTwoBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.treatmentStepTwo = this@LivestockTreatmentTwoFragment
        livestockDetails()
    }

    private fun livestockDetails() {
        binding.tagScan.text = args.livestockTreatmentWithHistory.tag_id
        binding.tagPassport.text = args.livestockTreatmentWithHistory.passport_id
        binding.tagKeeper.text = "${args.livestockTreatmentWithHistory.keeper_surname} " +
                "${args.livestockTreatmentWithHistory.keeper_other_names}"
        binding.tagOwner.text = "${args.livestockTreatmentWithHistory.owner_surname} " +
                "${args.livestockTreatmentWithHistory.owner_other_names}"
        binding.tagType.text = args.livestockTreatmentWithHistory.livestock_type
        binding.tagBreed.text = args.livestockTreatmentWithHistory.livestock_breed
        binding.tagGender.text = args.livestockTreatmentWithHistory.gender
        binding.tagDob.text = args.livestockTreatmentWithHistory.gestation_date

    }

    fun cancelButtonClicked() {
        val action = LivestockTreatmentTwoFragmentDirections
            .actionLivestockTreatmentTwoFragmentToLivestockTreatmentOneFragment()
        findNavController().navigate(action)
    }

    fun nextButtonClicked() {
        val action = LivestockTreatmentTwoFragmentDirections
            .actionLivestockTreatmentTwoFragmentToLivestockTreatmentThreeFragment(
                args.livestockTreatmentWithHistory.tag_id.toString()
            )
        findNavController().navigate(action)
    }

}