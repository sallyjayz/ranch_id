package com.sallyjayz.ranchid.offline.livestockowner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentOfflineOwnerSuccessBinding
import com.sallyjayz.ranchid.viewmodel.register.LivestockOwnerViewModel

class OfflineOwnerSuccess : Fragment() {

    private lateinit var binding: FragmentOfflineOwnerSuccessBinding
    private val sharedViewModel: LivestockOwnerViewModel by activityViewModels()
    private val ownerViewModel: LivestockOwnerViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                sharedViewModel.resetLivestockOwner()
                val action = OfflineOwnerSuccessDirections.actionOfflineOwnerSuccessToHomeFragment()
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
        binding = FragmentOfflineOwnerSuccessBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.offlineOwnerSuccess = this
    }

    fun goToDashboard() {
        sharedViewModel.resetLivestockOwner()
        val action = OfflineOwnerSuccessDirections.actionOfflineOwnerSuccessToHomeFragment()
        findNavController().navigate(action)

    }

    fun addNewOwner() {
        sharedViewModel.resetLivestockOwner()

        if (ownerViewModel.hasNoGenderSet()) {
            ownerViewModel.setGender(getString(R.string.male))
        }

        if (ownerViewModel.hasNoMaritalStatusSet()) {
            ownerViewModel.setMaritalStatus(getString(R.string.single))
        }

        if (ownerViewModel.hasNoLivestockKeeperSet()) {
            ownerViewModel.setLivestockKeeper(getString(R.string.yes))
        }

        val action = OfflineOwnerSuccessDirections
            .actionOfflineOwnerSuccessToAddLivestockOwnerStepOneFragment()
        findNavController().navigate(action)
    }
}