package com.sallyjayz.ranchid

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sallyjayz.ranchid.databinding.HomeMenuBinding
import com.sallyjayz.ranchid.viewmodel.TokenViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.intercom.android.sdk.Intercom

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

        /*tokenViewModel.vetCouncilNumber.observe(viewLifecycleOwner) { vetCouncilNumber ->
            binding.vetCouncilNumber.text = vetCouncilNumber
        }*/


        /*tokenViewModel.userPhoto.observe(viewLifecycleOwner) { photo ->
            Glide.with(requireContext())
                .load(photo)
                .override(70, 70)
                .centerCrop()
                .into(binding.headerImage)
        }*/

        if (tokenViewModel.userRole.value == "ENUMERATOR") {
            binding.menuRegister.isVisible = true
            binding.registerDivider.isVisible = true
            binding.menuTags.isVisible = true
            binding.tagDivider.isVisible = true
            binding.menuOffline.isVisible = true
            binding.offlineDivider.isVisible = true
            binding.menuReports.isVisible = true
            binding.reportsDivider.isVisible = true
            binding.menuGeneralInfo.isVisible = true
            binding.generalInfoDivider.isVisible = true
            binding.menuSupport.isVisible = true
            binding.supportDivider.isVisible = true
            binding.menuHelp.isVisible = true
            binding.helpDivider.isVisible = true
            binding.menuVaccinateLivestock.isVisible = false
            binding.vaccinateDivider.isVisible = false
            binding.menuTreatLivestock.isVisible = false
            binding.treatDivider.isVisible = false
            binding.menuVetOffline.isVisible = false
            binding.vetOfflineDivider.isVisible = false
            binding.menuVetReports.isVisible = false
            binding.vetReportsDivider.isVisible = false
        } else if (tokenViewModel.userRole.value == "VET_DOCTOR") {
            binding.menuRegister.isVisible = false
            binding.registerDivider.isVisible = false
            binding.menuTags.isVisible = false
            binding.tagDivider.isVisible = false
            binding.menuOffline.isVisible = false
            binding.offlineDivider.isVisible = false
            binding.menuReports.isVisible = false
            binding.reportsDivider.isVisible = false
            binding.menuGeneralInfo.isVisible = false
            binding.generalInfoDivider.isVisible = false
            binding.menuSupport.isVisible = false
            binding.supportDivider.isVisible = false
            binding.menuHelp.isVisible = false
            binding.helpDivider.isVisible = false
            binding.menuVaccinateLivestock.isVisible = true
            binding.vaccinateDivider.isVisible = true
            binding.menuTreatLivestock.isVisible = true
            binding.treatDivider.isVisible = true
            binding.menuVetOffline.isVisible = false
            binding.vetOfflineDivider.isVisible = false
            /*binding.menuVetOffline.isVisible = true
            binding.vetOfflineDivider.isVisible = true*/
            binding.menuVetReports.isVisible = true
            binding.vetReportsDivider.isVisible = true
        }
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
        val action = HomeMenuBottomSheetDirections
            .actionHomeMenuBottomSheetToGeneralInformationSearchFragment()
        findNavController().navigate(action)
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

    fun showVaccinateLivestockFragment() {
        val action = HomeMenuBottomSheetDirections
            .actionHomeMenuBottomSheetToVaccinateLivestockOneFragment()
        findNavController().navigate(action)
    }

    fun showLivestockTreatmentFragment() {
        val action = HomeMenuBottomSheetDirections
            .actionHomeMenuBottomSheetToLivestockTreatmentOneFragment()
        findNavController().navigate(action)
    }

    fun showVetOfflineFragment() {

    }

    fun showVetReportFragment() {

    }
}