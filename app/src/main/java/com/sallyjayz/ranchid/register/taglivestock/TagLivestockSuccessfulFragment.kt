package com.sallyjayz.ranchid.register.taglivestock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentTagLivestockSuccessfulBinding
import com.sallyjayz.ranchid.viewmodel.register.TagLivestockViewModel

class TagLivestockSuccessfulFragment : Fragment() {

    private lateinit var binding: FragmentTagLivestockSuccessfulBinding
    private val sharedViewModel: TagLivestockViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                sharedViewModel.resetTagLivestock()
                val action = TagLivestockSuccessfulFragmentDirections
                    .actionTagLivestockSuccessfulFragmentToHomeFragment()
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
        binding = FragmentTagLivestockSuccessfulBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.successMessage.text = getString(R.string.tagged_successfully, sharedViewModel.scannedTagId.value)

        binding.tagLivestockSuccessFragment = this
    }

    fun goToDashboard() {
        sharedViewModel.resetTagLivestock()
        val action = TagLivestockSuccessfulFragmentDirections
            .actionTagLivestockSuccessfulFragmentToHomeFragment()
        findNavController().navigate(action)
    }

    fun addNewLivestock() {
        sharedViewModel.resetTagLivestock()
        val action = TagLivestockSuccessfulFragmentDirections
            .actionTagLivestockSuccessfulFragmentToTagLivestockStepOneFragment()
        findNavController().navigate(action)
    }
}