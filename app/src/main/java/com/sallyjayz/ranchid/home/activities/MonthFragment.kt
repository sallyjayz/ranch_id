package com.sallyjayz.ranchid.home.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.sallyjayz.ranchid.databinding.FragmentMonthBinding
import com.sallyjayz.ranchid.viewmodel.TokenViewModel
import com.sallyjayz.ranchid.viewmodel.register.DashboardActivitiesViewModel
import com.sallyjayz.ranchid.viewmodel.vet.VetDashboardActivitiesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MonthFragment : Fragment() {

    private lateinit var binding: FragmentMonthBinding
    private val dashboardActivitiesViewModel: DashboardActivitiesViewModel by viewModels()
    private val vetDashboardActivitiesViewModel: VetDashboardActivitiesViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMonthBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (tokenViewModel.loginRole.value.equals("ENUMERATOR")) {
            binding.monthTaggedCardview.isVisible = true
            binding.monthOwnerCardview.isVisible = true
            binding.monthKeeperCardview.isVisible = true
            binding.monthExitedOwnerCardview.isVisible = true
            binding.monthAppointmentCardview.isVisible = false
            binding.monthVaccinationCardview.isVisible = false

        } else if (tokenViewModel.loginRole.value.equals("VET_DOCTOR")) {
            binding.monthTaggedCardview.isVisible = false
            binding.monthOwnerCardview.isVisible = false
            binding.monthKeeperCardview.isVisible = false
            binding.monthExitedOwnerCardview.isVisible = false
            binding.monthAppointmentCardview.isVisible = true
            binding.monthVaccinationCardview.isVisible = true
        }

        thisMonthEnumeratorActivities()
        thisMonthVetActivities()
    }

    /*private fun thisMonthActivities() {

        if (tokenViewModel.userRole.value == "ENUMERATOR") {

            dashboardActivitiesViewModel.readTagged.observe(viewLifecycleOwner) { tag ->
                if (tag != null) {
                    binding.monthTaggedLivestock.text = tag.thisMonth.toString()
                }
            }

            dashboardActivitiesViewModel.readOwner.observe(viewLifecycleOwner) { owner ->
                if (owner != null) {
                    binding.monthOwnerLivestock.text = owner.thisMonth.toString()
                }
            }

            dashboardActivitiesViewModel.readKeeper.observe(viewLifecycleOwner) { keeper ->
                if (keeper != null) {
                    binding.monthKeeperLivestock.text = keeper.thisMonth.toString()
                }
            }

            dashboardActivitiesViewModel.readExit.observe(viewLifecycleOwner) { exit ->
                if (exit != null) {
                    binding.monthExitedOwner.text = exit.thisMonth.toString()
                }
            }

        }
    }*/

    private fun thisMonthEnumeratorActivities() {
        dashboardActivitiesViewModel.readTagged.observe(viewLifecycleOwner) { tag ->
            if (tag != null) {
                binding.monthTaggedLivestock.text = tag.thisMonth.toString()
            }
        }

        dashboardActivitiesViewModel.readOwner.observe(viewLifecycleOwner) { owner ->
            if (owner != null) {
                binding.monthOwnerLivestock.text = owner.thisMonth.toString()
            }
        }

        dashboardActivitiesViewModel.readKeeper.observe(viewLifecycleOwner) { keeper ->
            if (keeper != null) {
                binding.monthKeeperLivestock.text = keeper.thisMonth.toString()
            }
        }

        dashboardActivitiesViewModel.readExit.observe(viewLifecycleOwner) { exit ->
            if (exit != null) {
                binding.monthExitedOwner.text = exit.thisMonth.toString()
            }
        }
    }

    private fun thisMonthVetActivities() {
        vetDashboardActivitiesViewModel.readAppointment.observe(viewLifecycleOwner) { appointment ->
            if (appointment != null) {
                binding.monthAppointment.text = appointment.thisMonth.toString()
            }
        }

        vetDashboardActivitiesViewModel.readVaccination.observe(viewLifecycleOwner) { vaccination ->
            if (vaccination != null) {
                binding.monthVaccination.text = vaccination.thisMonth.toString()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (tokenViewModel.userRole.value.equals("ENUMERATOR")) {
            binding.monthTaggedCardview.isVisible = true
            binding.monthOwnerCardview.isVisible = true
            binding.monthKeeperCardview.isVisible = true
            binding.monthExitedOwnerCardview.isVisible = true
            binding.monthAppointmentCardview.isVisible = false
            binding.monthVaccinationCardview.isVisible = false

        } else if (tokenViewModel.userRole.value.equals("VET_DOCTOR")) {
            binding.monthTaggedCardview.isVisible = false
            binding.monthOwnerCardview.isVisible = false
            binding.monthKeeperCardview.isVisible = false
            binding.monthExitedOwnerCardview.isVisible = false
            binding.monthAppointmentCardview.isVisible = true
            binding.monthVaccinationCardview.isVisible = true
        }

        thisMonthEnumeratorActivities()
        thisMonthVetActivities()

    }


}