package com.sallyjayz.ranchid.offline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sallyjayz.ranchid.databinding.FragmentOfflineFunctionBinding

class OfflineFunctionFragment : Fragment() {

    private lateinit var binding: FragmentOfflineFunctionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOfflineFunctionBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.offlineFragment = this
    }

    fun livestockOwner() {
        val action = OfflineFunctionFragmentDirections
            .actionOfflineFragmentToOfflineOwnerFragment()
        findNavController().navigate(action)
    }

    fun livestockKeeper() {
        val action = OfflineFunctionFragmentDirections
            .actionOfflineFragmentToOfflineFragmentKeeper()
        findNavController().navigate(action)
    }

    fun livestockTag() {
        val action = OfflineFunctionFragmentDirections
            .actionOfflineFragmentToOfflineFragmentTagLivestock()
        findNavController().navigate(action)
    }

}