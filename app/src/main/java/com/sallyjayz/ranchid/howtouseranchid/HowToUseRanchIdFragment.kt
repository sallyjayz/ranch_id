package com.sallyjayz.ranchid.howtouseranchid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sallyjayz.ranchid.databinding.FragmentHowToUseRanchIdBinding


class HowToUseRanchIdFragment : Fragment() {

    private lateinit var binding: FragmentHowToUseRanchIdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHowToUseRanchIdBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.howToMenuFragment = this
    }

    fun gettingStarted() {
        val action = HowToUseRanchIdFragmentDirections
            .actionHowToUseRanchIdFragmentToGettingStartedFragment()
        findNavController().navigate(action)
        Thread.dumpStack()
    }

    fun dashboard() {
        val action = HowToUseRanchIdFragmentDirections
            .actionHowToUseRanchIdFragmentToDashboardFragment()
        findNavController().navigate(action)
    }

    fun enumeratorMenu() {
        val action = HowToUseRanchIdFragmentDirections
            .actionHowToUseRanchIdFragmentToEnumeratorMenuFragment()
        findNavController().navigate(action)
    }

    fun registerMenu() {
        val action = HowToUseRanchIdFragmentDirections
            .actionHowToUseRanchIdFragmentToRegisterMenuFragment()
        findNavController().navigate(action)
    }

    fun addLivestockOwner() {
        val action = HowToUseRanchIdFragmentDirections
            .actionHowToUseRanchIdFragmentToLivestockOwnerFragment()
        findNavController().navigate(action)
    }

    fun addLivestockKeeper() {
        val action = HowToUseRanchIdFragmentDirections
            .actionHowToUseRanchIdFragmentToLivestockKeeperFragment()
        findNavController().navigate(action)
    }

    fun addLivestockLocation() {
        val action = HowToUseRanchIdFragmentDirections
            .actionHowToUseRanchIdFragmentToLivestockLocationFragment()
        findNavController().navigate(action)
    }

    fun tagLivestock() {
        val action = HowToUseRanchIdFragmentDirections
            .actionHowToUseRanchIdFragmentToTagLivestockFragment()
        findNavController().navigate(action)
    }

    fun scanForExit() {
        val action = HowToUseRanchIdFragmentDirections
            .actionHowToUseRanchIdFragmentToTaggedLivestockOperationFragment()
        findNavController().navigate(action)
    }

    fun packingList() {
        val action = HowToUseRanchIdFragmentDirections
            .actionHowToUseRanchIdFragmentToPackingListFragment()
        findNavController().navigate(action)
    }

}

