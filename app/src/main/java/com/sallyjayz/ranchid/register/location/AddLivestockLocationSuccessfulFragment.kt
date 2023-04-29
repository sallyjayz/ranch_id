package com.sallyjayz.ranchid.register.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sallyjayz.ranchid.databinding.FragmentAddLivestockLocationSuccessfulBinding
import com.sallyjayz.ranchid.viewmodel.register.LocationViewModel

class AddLivestockLocationSuccessfulFragment : Fragment() {

    private lateinit var binding: FragmentAddLivestockLocationSuccessfulBinding
    private val viewModel: LocationViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.resetLocation()
                val action = AddLivestockLocationSuccessfulFragmentDirections
                    .actionAddLivestockLocationSuccessfulFragmentToHomeFragment()
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
        binding = FragmentAddLivestockLocationSuccessfulBinding
            .inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.locationSuccessFragment = this
    }

    fun goToDashboard() {
        viewModel.resetLocation()
        val action = AddLivestockLocationSuccessfulFragmentDirections
            .actionAddLivestockLocationSuccessfulFragmentToHomeFragment()
        findNavController().navigate(action)
    }

    fun addNewLocation() {
        viewModel.resetLocation()
        val action = AddLivestockLocationSuccessfulFragmentDirections
            .actionAddLivestockLocationSuccessfulFragmentToAddLivestockLocationFragment()
        findNavController().navigate(action)
    }
}