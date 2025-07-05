package com.sallyjayz.ranchid.veterinaryDoctor.vaccinateLivestock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sallyjayz.ranchid.databinding.FragmentVaccinateLivestockTwoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VaccinateLivestockTwoFragment : Fragment() {

    private lateinit var binding: FragmentVaccinateLivestockTwoBinding
    private val args: VaccinateLivestockTwoFragmentArgs by navArgs()

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
        binding = FragmentVaccinateLivestockTwoBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vaccinationStepTwo = this@VaccinateLivestockTwoFragment

        livestockDetails()
    }

    private fun livestockDetails() {
        binding.tagScan.text = args.vaccinateLivestockWithHistory.tag_id
        binding.tagPassport.text = args.vaccinateLivestockWithHistory.passport_id
        binding.tagKeeper.text = "${args.vaccinateLivestockWithHistory.keeper_surname} " +
                "${args.vaccinateLivestockWithHistory.keeper_other_names}"
        binding.tagOwner.text = "${args.vaccinateLivestockWithHistory.owner_surname} " +
                "${args.vaccinateLivestockWithHistory.owner_other_names}"
        binding.tagType.text = args.vaccinateLivestockWithHistory.livestock_type
        binding.tagBreed.text = args.vaccinateLivestockWithHistory.livestock_breed
        binding.tagGender.text = args.vaccinateLivestockWithHistory.gender
        binding.tagDob.text = args.vaccinateLivestockWithHistory.gestation_date

    }

    /*fun vaccinationHistoryClicked() {
        val action = VaccinateLivestockTwoFragmentDirections
            .actionVaccinateLivestockTwoFragmentToVaccinationHistoryFragment(args.vaccinateLivestockWithHistory.tag_id.toString())
        findNavController().navigate(action)
    }*/

    fun cancelButtonClicked() {
        val action = VaccinateLivestockTwoFragmentDirections
            .actionVaccinateLivestockTwoFragmentToVaccinateLivestockOneFragment()
        findNavController().navigate(action)
    }

    fun nextButtonClicked() {
        val action = VaccinateLivestockTwoFragmentDirections
            .actionVaccinateLivestockTwoFragmentToVaccinateLivestockThreeFragment(
                args.vaccinateLivestockWithHistory.tag_id.toString()
            )
        findNavController().navigate(action)
    }
}