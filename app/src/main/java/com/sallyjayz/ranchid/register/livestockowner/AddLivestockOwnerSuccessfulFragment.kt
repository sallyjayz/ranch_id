package com.sallyjayz.ranchid.register.livestockowner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentAddLivestockOwnerSuccessfulBinding
import com.sallyjayz.ranchid.viewmodel.register.LivestockOwnerViewModel

class AddLivestockOwnerSuccessfulFragment : Fragment() {

    private lateinit var binding: FragmentAddLivestockOwnerSuccessfulBinding
//    private val sharedViewModel: LivestockOwnerViewModel by activityViewModels()
    private val ownerViewModel: LivestockOwnerViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                ownerViewModel.resetLivestockOwner()
                val action = AddLivestockOwnerSuccessfulFragmentDirections
                    .actionAddLivestockOwnerSuccessfulFragmentToHomeFragment()
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
        binding = FragmentAddLivestockOwnerSuccessfulBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*binding?.apply {
            lifecycleOwner = viewLifecycleOwner
//            viewModel = sharedViewModel
        }*/

        binding.ownerSuccessFragment = this
    }

    fun goToDashboard() {
        ownerViewModel.resetLivestockOwner()
        val action = AddLivestockOwnerSuccessfulFragmentDirections
            .actionAddLivestockOwnerSuccessfulFragmentToHomeFragment()
        findNavController().navigate(action)
    }

    fun addNewOwner() {
        ownerViewModel.resetLivestockOwner()

        if (ownerViewModel.hasNoGenderSet()) {
            ownerViewModel.setGender(getString(R.string.male))
        }

        if (ownerViewModel.hasNoMaritalStatusSet()) {
            ownerViewModel.setMaritalStatus(getString(R.string.single))
        }

        if (ownerViewModel.hasNoLivestockKeeperSet()) {
            ownerViewModel.setLivestockKeeper(getString(R.string.yes))
        }

        val action = AddLivestockOwnerSuccessfulFragmentDirections
            .actionAddLivestockOwnerSuccessfulFragmentToAddLivestockOwnerStepOneFragment()
        findNavController().navigate(action)
    }

}