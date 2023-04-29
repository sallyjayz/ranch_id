package com.sallyjayz.ranchid.register.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.sallyjayz.ranchid.databinding.FragmentAddLivestockLocationUnsuccessfulBinding

class AddLivestockLocationUnsuccessfulFragment : Fragment() {

    private lateinit var binding: FragmentAddLivestockLocationUnsuccessfulBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action = AddLivestockLocationUnsuccessfulFragmentDirections
                    .actionAddLivestockLocationUnsuccessfulFragmentToAddLivestockLocationFragment()
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
        binding = FragmentAddLivestockLocationUnsuccessfulBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.locationUnsuccessfulFragment = this
    }

    fun retry() {
        val action = AddLivestockLocationUnsuccessfulFragmentDirections
            .actionAddLivestockLocationUnsuccessfulFragmentToAddLivestockLocationFragment()
        findNavController().navigate(action)
    }

}