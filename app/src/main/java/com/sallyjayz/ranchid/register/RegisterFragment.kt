package com.sallyjayz.ranchid.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentRegisterBinding
import com.sallyjayz.ranchid.viewmodel.register.LivestockKeeperViewModel
import com.sallyjayz.ranchid.viewmodel.register.LivestockOwnerViewModel

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val keeperViewModel: LivestockKeeperViewModel by activityViewModels()
    private val ownerViewModel: LivestockOwnerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registerFragment = this
    }

    fun addLivestockOwner() {

        if (ownerViewModel.hasNoGenderSet()) {
            ownerViewModel.setGender(getString(R.string.male))
        }

        if (ownerViewModel.hasNoMaritalStatusSet()) {
            ownerViewModel.setMaritalStatus(getString(R.string.single))
        }

        if (ownerViewModel.hasNoLivestockKeeperSet()) {
            ownerViewModel.setLivestockKeeper(getString(R.string.yes))
        }

        val action = RegisterFragmentDirections
            .actionRegisterFragmentToAddLivestockOwnerStepOneFragment()
        findNavController().navigate(action)
    }

    fun addLivestockKeeper() {

        if (keeperViewModel.hasNoGenderSet()) {
            keeperViewModel.setGender(getString(R.string.male))
        }

        if (keeperViewModel.hasNoMaritalStatusSet()) {
            keeperViewModel.setMaritalStatus(getString(R.string.single))
        }
        val action = RegisterFragmentDirections
            .actionRegisterFragmentToAddLivestockKeeperStepOneFragment()
        findNavController().navigate(action)
    }

    fun addLivestockLocation() {
        val action = RegisterFragmentDirections
            .actionRegisterFragmentToAddLivestockLocationFragment()
        findNavController().navigate(action)
    }

    fun addLivestockTag() {
        val action = RegisterFragmentDirections
            .actionRegisterFragmentToTagLivestockStepOneFragment()
        findNavController().navigate(action)
    }

    fun addPackingList() {
        val action = RegisterFragmentDirections
            .actionRegisterFragmentToAddPackingListStepOneFragment()
        findNavController().navigate(action)
    }

}