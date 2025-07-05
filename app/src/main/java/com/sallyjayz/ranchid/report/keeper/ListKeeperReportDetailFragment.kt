/*
package com.sallyjayz.ranchid.report.keeper

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentListKeeperReportDetailBinding
import com.sallyjayz.ranchid.viewmodel.register.FarmLocationViewModel
import com.sallyjayz.ranchid.viewmodel.register.LgaViewModel
import com.sallyjayz.ranchid.viewmodel.register.StateViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListKeeperReportDetailFragment : Fragment() {

    private lateinit var binding: FragmentListKeeperReportDetailBinding
    private val args: ListKeeperReportDetailFragmentArgs by navArgs()
    private val stateViewModel: StateViewModel by viewModels()
    private val lgaViewModel: LgaViewModel by viewModels()
    private val farmLocationViewModel: FarmLocationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListKeeperReportDetailBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.keeperReport = this@ListKeeperReportDetailFragment

        keeperDetails()
    }

    private fun keeperDetails() {
        binding.stepThreeSurname.text = args.keeperDetails.surname
        binding.stepThreeOthername.text = args.keeperDetails.other_names
        binding.stepThreeGender.text = args.keeperDetails.gender
        binding.stepThreeMaritalStatus.text = args.keeperDetails.marital_status
        binding.stepThreeDob.text = args.keeperDetails.dob
        binding.stepThreePhoneno.text = args.keeperDetails.phone_number
        binding.stepThreeNin.text = args.keeperDetails.nin
        binding.stepThreeEmail.text = args.keeperDetails.email_address
        binding.stepThreeAddress.text = args.keeperDetails.address
//        binding.stepThreeState.text = args.keeperDetails.state
        stateViewModel.getStateId(args.keeperDetails.state.toInt()).observe(viewLifecycleOwner) {
            binding.stepThreeState.text = it.name
        }
//        binding.stepThreeLga.text = args.keeperDetails.lga
        lgaViewModel.getLgaAndStateId(args.keeperDetails.lga.toInt(), args.keeperDetails.state.toInt()).observe(viewLifecycleOwner) {
            binding.stepThreeLga.text = it.name
        }
        binding.stepThreeKin.text = args.keeperDetails.next_of_kin
        binding.stepThreeKinPhone.text = args.keeperDetails.next_of_kin_number
        binding.stepThreeDocumentType.text = args.keeperDetails.id_doc
        binding.stepThreeDocumentNumber.text = args.keeperDetails.prof_id_doc
//        binding.stepThreeFarmLocation.text = args.keeperDetails.location
        farmLocationViewModel.getLocationId(args.keeperDetails.location.toInt()).observe(viewLifecycleOwner) {
            binding.stepThreeFarmLocation.text = it.location_name
        }
        Glide.with(requireContext())
            .load(args.keeperDetails.passport_photo)
            .override(150, 150)
            .centerCrop()
            .placeholder(R.drawable.no_data_available)
            .into(binding.keeperImage)
    }
}*/
