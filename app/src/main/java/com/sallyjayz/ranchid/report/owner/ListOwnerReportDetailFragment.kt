/*
package com.sallyjayz.ranchid.report.owner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentListOwnerReportDetailBinding
import com.sallyjayz.ranchid.viewmodel.register.FarmLocationViewModel
import com.sallyjayz.ranchid.viewmodel.register.LgaViewModel
import com.sallyjayz.ranchid.viewmodel.register.StateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListOwnerReportDetailFragment : Fragment() {

    private lateinit var binding: FragmentListOwnerReportDetailBinding
    private val args: ListOwnerReportDetailFragmentArgs by navArgs()
    private val stateViewModel: StateViewModel by viewModels()
    private val lgaViewModel: LgaViewModel by viewModels()
    private val farmLocationViewModel: FarmLocationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListOwnerReportDetailBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ownerReport = this@ListOwnerReportDetailFragment

        ownerDetails()
    }

    private fun ownerDetails() {
        binding.stepThreeType.text = args.ownerDetails.ownership_type
        binding.stepThreeSurname.text = args.ownerDetails.surname
        binding.stepThreeOthername.text = args.ownerDetails.other_names
        binding.stepThreeGender.text = args.ownerDetails.gender
        binding.stepThreeMaritalStatus.text = args.ownerDetails.marital_status
        binding.stepThreeDob.text = args.ownerDetails.dob
        binding.stepThreePhoneno.text = args.ownerDetails.phone_number
        binding.stepThreeNin.text = args.ownerDetails.nin
        binding.stepThreeEmail.text = args.ownerDetails.email_address
//        binding.stepThreeState.text = args.ownerDetails.state
        stateViewModel.getStateId(args.ownerDetails.state.toInt()).observe(viewLifecycleOwner) {
            binding.stepThreeState.text = it.name
        }
//        binding.stepThreeLga.text = args.ownerDetails.lga
        lgaViewModel.getLgaAndStateId(args.ownerDetails.lga.toInt(), args.ownerDetails.state.toInt()).observe(viewLifecycleOwner) {
            binding.stepThreeLga.text = it.name
        }
        binding.stepThreeKin.text = args.ownerDetails.next_of_kin
        binding.stepThreeKinPhone.text = args.ownerDetails.next_of_kin_number
        binding.stepThreeDocumentType.text = args.ownerDetails.id_doc
        binding.stepThreeDocumentNumber.text = args.ownerDetails.prof_id_doc
//        binding.stepThreeFarmLocation.text = args.ownerDetails.location
        farmLocationViewModel.getLocationId(args.ownerDetails.location.toInt()).observe(viewLifecycleOwner) {
            binding.stepThreeFarmLocation.text = it.location_name
        }

        Glide.with(requireContext())
            .load(args.ownerDetails.passport_photo)
            .override(150, 150)
            .centerCrop()
            .placeholder(R.drawable.no_data_available)
            .into(binding.ownerImage)

    }
}*/
