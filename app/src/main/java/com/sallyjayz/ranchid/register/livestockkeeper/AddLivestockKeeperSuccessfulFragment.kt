package com.sallyjayz.ranchid.register.livestockkeeper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentAddLivestockKeeperSuccessfulBinding
import com.sallyjayz.ranchid.viewmodel.register.LivestockKeeperViewModel

class AddLivestockKeeperSuccessfulFragment : Fragment() {

    private lateinit var binding: FragmentAddLivestockKeeperSuccessfulBinding
//    private val sharedViewModel: LivestockKeeperViewModel by activityViewModels()
    private val keeperViewModel: LivestockKeeperViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                keeperViewModel.resetLivestockKeeper()
                val action = AddLivestockKeeperSuccessfulFragmentDirections
                    .actionAddLivestockKeeperSuccessfulFragmentToHomeFragment()
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
        binding = FragmentAddLivestockKeeperSuccessfulBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*binding.apply {
            lifecycleOwner = viewLifecycleOwner
//            viewModel = sharedViewModel
        }*/

        binding.keeperSuccessFragment = this

    }

    fun goToDashboard() {
        keeperViewModel.resetLivestockKeeper()
        val action = AddLivestockKeeperSuccessfulFragmentDirections
            .actionAddLivestockKeeperSuccessfulFragmentToHomeFragment()
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

        val action = AddLivestockKeeperSuccessfulFragmentDirections
            .actionAddLivestockKeeperSuccessfulFragmentToAddLivestockKeeperStepOneFragment()
        findNavController().navigate(action)
    }

}