package com.sallyjayz.ranchid.offline.livestockkeeper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentOfflineKeeperSuccessBinding
import com.sallyjayz.ranchid.viewmodel.register.LivestockKeeperViewModel

class OfflineKeeperSuccess : Fragment() {

    private lateinit var binding: FragmentOfflineKeeperSuccessBinding
//    private val sharedViewModel: LivestockKeeperViewModel by activityViewModels()
    private val keeperViewModel: LivestockKeeperViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOfflineKeeperSuccessBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.offlineKeeperSuccess = this

    }

    fun goToDashboard() {
        keeperViewModel.resetLivestockKeeper()
        val action = OfflineKeeperSuccessDirections.actionOfflineKeeperSuccessToHomeFragment()
        findNavController().navigate(action)

    }

    fun addNewKeeper() {
        keeperViewModel.resetLivestockKeeper()

        if (keeperViewModel.hasNoGenderSet()) {
            keeperViewModel.setGender(getString(R.string.male))
        }

        if (keeperViewModel.hasNoMaritalStatusSet()) {
            keeperViewModel.setMaritalStatus(getString(R.string.single))
        }

        val action = OfflineKeeperSuccessDirections
            .actionOfflineKeeperSuccessToAddLivestockKeeperStepOneFragment()
        findNavController().navigate(action)

    }

}