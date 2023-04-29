package com.sallyjayz.ranchid.register.livestockkeeper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.sallyjayz.ranchid.databinding.FragmentAddLivestockKeeperUnsuccessfulBinding

class AddLivestockKeeperUnsuccessfulFragment : Fragment() {

    private lateinit var binding: FragmentAddLivestockKeeperUnsuccessfulBinding
//    private val args: AddLivestockKeeperUnsuccessfulFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action = AddLivestockKeeperUnsuccessfulFragmentDirections
                    .actionAddLivestockKeeperUnsuccessfulFragmentToAddLivestockKeeperStepOneFragment()
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
        binding = FragmentAddLivestockKeeperUnsuccessfulBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.keeperUnsuccessfulFragment = this
//        validations()
    }

    /*private fun validations() {
        if (args.email != null) {
           binding.errorMessage.text = "Email Address already exists\n\n"
        } else if (args.phoneNumber != null) {
            binding.errorMessage.text = "Phone Number already exists\n\n"
        } else if (args.nin != null) {
            binding.errorMessage.text = "NIN already exists\n\n"
        }
    }*/

    fun retry() {
        val action = AddLivestockKeeperUnsuccessfulFragmentDirections
            .actionAddLivestockKeeperUnsuccessfulFragmentToAddLivestockKeeperStepOneFragment()
        findNavController().navigate(action)
    }
}