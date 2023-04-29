package com.sallyjayz.ranchid.register.taglivestock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.sallyjayz.ranchid.databinding.FragmentTagLivestockUnsuccessfulBinding

class TagLivestockUnsuccessfulFragment : Fragment() {

    private lateinit var binding: FragmentTagLivestockUnsuccessfulBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action = TagLivestockUnsuccessfulFragmentDirections
                    .actionTagLivestockUnsuccessfulFragmentToTagLivestockStepOneFragment()
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
        binding = FragmentTagLivestockUnsuccessfulBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tagLivestockUnsuccessfulFragment = this
    }

    fun retry() {
        val action = TagLivestockUnsuccessfulFragmentDirections
            .actionTagLivestockUnsuccessfulFragmentToTagLivestockStepOneFragment()
        findNavController().navigate(action)
    }

}