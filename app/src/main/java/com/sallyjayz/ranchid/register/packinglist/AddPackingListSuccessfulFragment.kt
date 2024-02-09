package com.sallyjayz.ranchid.register.packinglist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentAddLivestockLocationSuccessfulBinding
import com.sallyjayz.ranchid.databinding.FragmentAddPackingListSuccessfulBinding
import com.sallyjayz.ranchid.register.location.AddLivestockLocationSuccessfulFragmentDirections
import com.sallyjayz.ranchid.viewmodel.packinglist.PackingListViewModel

class AddPackingListSuccessfulFragment : Fragment() {

    private lateinit var binding: FragmentAddPackingListSuccessfulBinding
    private val packingListViewModel: PackingListViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                packingListViewModel.resetPackingList()
                val action = AddPackingListSuccessfulFragmentDirections
                    .actionAddPackingListSuccessfulFragmentToHomeFragment()
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
        binding = FragmentAddPackingListSuccessfulBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.packingListSuccess = this
    }

    fun goToDashboard() {
        packingListViewModel.resetPackingList()
        val action = AddPackingListSuccessfulFragmentDirections
            .actionAddPackingListSuccessfulFragmentToHomeFragment()
        findNavController().navigate(action)
    }

    fun addNewPackingList() {
        packingListViewModel.resetPackingList()
        val action = AddPackingListSuccessfulFragmentDirections
            .actionAddPackingListSuccessfulFragmentToAddPackingListStepOneFragment()
        findNavController().navigate(action)
    }

}