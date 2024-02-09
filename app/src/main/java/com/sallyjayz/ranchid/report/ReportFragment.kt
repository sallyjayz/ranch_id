package com.sallyjayz.ranchid.report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sallyjayz.ranchid.databinding.FragmentReportBinding

class ReportFragment : Fragment() {


    private lateinit var binding: FragmentReportBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReportBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.reportFragment = this
    }

    fun listLivestockOwner() {
        /*val action = ReportFragmentDirections
            .actionReportFragmentToListOwnerReportFragment()
        findNavController().navigate(action)*/
    }

    fun listLivestockKeeper() {
        /*val action = ReportFragmentDirections
            .actionReportFragmentToListKeeperReportFragment()
        findNavController().navigate(action)*/
    }

    fun listLivestockLocation() {
        /*val action = ReportFragmentDirections
            .actionReportFragmentToListLocationReportFragment()
        findNavController().navigate(action)*/
    }

    fun listTaggedLivestock() {
        /*val action = ReportFragmentDirections
            .actionReportFragmentToListTaggedLivestockReportFragment()
        findNavController().navigate(action)*/
    }

}