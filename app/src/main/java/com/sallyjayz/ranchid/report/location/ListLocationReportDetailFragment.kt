/*
package com.sallyjayz.ranchid.report.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentListLocationReportDetailBinding
import com.sallyjayz.ranchid.databinding.FragmentListOwnerReportDetailBinding
import com.sallyjayz.ranchid.viewmodel.register.LgaViewModel
import com.sallyjayz.ranchid.viewmodel.register.StateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListLocationReportDetailFragment : Fragment() {

    private lateinit var binding: FragmentListLocationReportDetailBinding
    private val args: ListLocationReportDetailFragmentArgs by navArgs()
    private val stateViewModel: StateViewModel by viewModels()
    private val lgaViewModel: LgaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListLocationReportDetailBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.locationReport = this@ListLocationReportDetailFragment

        locationDetails()
    }

    private fun locationDetails() {
        binding.locationName.text = args.locationDetails.location_name
        binding.locationType.text = args.locationDetails.location_type
//        binding.locationState.text = args.locationDetails.state.toString()

        stateViewModel.getStateId(args.locationDetails.state).observe(viewLifecycleOwner) {
            binding.locationState.text = it.name
        }
//        binding.locationLga.text = args.locationDetails.lga.toString()

        lgaViewModel.getLgaAndStateId(args.locationDetails.lga, args.locationDetails.state)
            .observe(viewLifecycleOwner) {
            binding.locationLga.text = it.name
        }
    }

}*/
