package com.sallyjayz.ranchid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sallyjayz.ranchid.databinding.HomeMenuBinding
import com.sallyjayz.ranchid.viewmodel.TokenViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.intercom.android.sdk.Intercom
import io.intercom.android.sdk.IntercomError
import io.intercom.android.sdk.IntercomStatusCallback
import io.intercom.android.sdk.identity.Registration

@AndroidEntryPoint
class HomeMenuBottomSheet: BottomSheetDialogFragment() {

    private lateinit var binding: HomeMenuBinding
    private val tokenViewModel: TokenViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = HomeMenuBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeMenuBottomSheet = this

        tokenViewModel.name.observe(viewLifecycleOwner) { name ->
            binding.headerName.text = name
        }

        tokenViewModel.userEmail.observe(viewLifecycleOwner) { email ->
            binding.headerEmail.text = email
        }

        tokenViewModel.userRole.observe(viewLifecycleOwner) { role ->
            binding.headerRole.text = role
        }

        /*tokenViewModel.userPhoto.observe(viewLifecycleOwner) { photo ->
            Glide.with(requireContext())
                .load(photo)
                .override(70, 70)
                .centerCrop()
                .into(binding.headerImage)
        }*/
    }

    fun closeMenu() {
        dismiss()
    }

    fun showRegisterFragment() {
        val action = HomeMenuBottomSheetDirections.actionHomeMenuBottomSheetToRegisterFragment()
        findNavController().navigate(action)
    }

    fun showTagFragment() {
        val action = HomeMenuBottomSheetDirections.actionHomeMenuBottomSheetToTagFragment()
        findNavController().navigate(action)
    }

    fun showOfflineFragment() {
        val action = HomeMenuBottomSheetDirections.actionHomeMenuBottomSheetToOfflineFragment()
        findNavController().navigate(action)

    }

    fun showReportFragment() {
        val action = HomeMenuBottomSheetDirections.actionHomeMenuBottomSheetToReportFragment()
        findNavController().navigate(action)
    }

    fun showGeneralInfoFragment() {
        /*val action = HomeMenuBottomSheetDirections
            .actionHomeMenuBottomSheetToGeneralInformationSearchFragment()
        findNavController().navigate(action)*/
    }

    fun showSupportFragment() {
        Intercom.client().present()
    }

    fun showRanchIdHowFragment() {
        val action = HomeMenuBottomSheetDirections
            .actionHomeMenuBottomSheetToHowToUseRanchIdFragment()
        findNavController().navigate(action)
    }

    fun showLogoutFragment() {
        tokenViewModel.deleteToken()
        Intercom.client().logout()
        activity?.finish()
    }
}