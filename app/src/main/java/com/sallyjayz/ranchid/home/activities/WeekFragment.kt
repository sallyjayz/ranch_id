package com.sallyjayz.ranchid.home.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.sallyjayz.ranchid.databinding.FragmentWeekBinding
import com.sallyjayz.ranchid.viewmodel.TokenViewModel
import com.sallyjayz.ranchid.viewmodel.register.DashboardActivitiesViewModel
import com.sallyjayz.ranchid.viewmodel.vet.VetDashboardActivitiesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeekFragment : Fragment() {

    private lateinit var binding: FragmentWeekBinding
    private val dashboardActivitiesViewModel: DashboardActivitiesViewModel by viewModels()
    private val vetDashboardActivitiesViewModel: VetDashboardActivitiesViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWeekBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (tokenViewModel.loginRole.value.equals("ENUMERATOR")) {
            binding.weekTaggedCardview.isVisible = true
            binding.weekOwnerCardview.isVisible = true
            binding.weekKeeperCardview.isVisible = true
            binding.weekExitedOwnerCardview.isVisible = true
            binding.weekAppointmentCardview.isVisible = false
            binding.weekVaccinationCardview.isVisible = false

        } else if (tokenViewModel.loginRole.value.equals("VET_DOCTOR")) {
            binding.weekTaggedCardview.isVisible = false
            binding.weekOwnerCardview.isVisible = false
            binding.weekKeeperCardview.isVisible = false
            binding.weekExitedOwnerCardview.isVisible = false
            binding.weekAppointmentCardview.isVisible = true
            binding.weekVaccinationCardview.isVisible = true
        }

        thisWeekEnumeratorActivities()
        thisWeekVetActivities()
    }

    /*private fun thisWeekActivities() {

        if (tokenViewModel.userRole.value == "ENUMERATOR") {
            dashboardActivitiesViewModel.readTagged.observe(viewLifecycleOwner) { tag ->
                if (tag != null) {
                    binding.weekTaggedLivestock.text = tag.thisWeek.toString()
                }
            }

            dashboardActivitiesViewModel.readOwner.observe(viewLifecycleOwner) { owner ->
                if (owner != null) {
                    binding.weekOwnerLivestock.text = owner.thisWeek.toString()
                }
            }

            dashboardActivitiesViewModel.readKeeper.observe(viewLifecycleOwner) { keeper ->
                if (keeper != null) {
                    binding.weekKeeperLivestock.text = keeper.thisWeek.toString()
                }
            }

            dashboardActivitiesViewModel.readExit.observe(viewLifecycleOwner) { exit ->
                if (exit != null) {
                    binding.weekExitedOwner.text = exit.thisWeek.toString()
                }
            }
        }
    }*/

    private fun thisWeekEnumeratorActivities() {
        dashboardActivitiesViewModel.readTagged.observe(viewLifecycleOwner) { tag ->
            if (tag != null) {
                binding.weekTaggedLivestock.text = tag.thisWeek.toString()
            }
        }

        dashboardActivitiesViewModel.readOwner.observe(viewLifecycleOwner) { owner ->
            if (owner != null) {
                binding.weekOwnerLivestock.text = owner.thisWeek.toString()
            }
        }

        dashboardActivitiesViewModel.readKeeper.observe(viewLifecycleOwner) { keeper ->
            if (keeper != null) {
                binding.weekKeeperLivestock.text = keeper.thisWeek.toString()
            }
        }

        dashboardActivitiesViewModel.readExit.observe(viewLifecycleOwner) { exit ->
            if (exit != null) {
                binding.weekExitedOwner.text = exit.thisWeek.toString()
            }
        }
    }

    private fun thisWeekVetActivities() {
        vetDashboardActivitiesViewModel.readAppointment.observe(viewLifecycleOwner) { appointment ->
            if (appointment != null) {
                binding.weekAppointment.text = appointment.thisWeek.toString()
            }
        }

        vetDashboardActivitiesViewModel.readVaccination.observe(viewLifecycleOwner) { vaccination ->
            if (vaccination != null) {
                binding.weekVaccination.text = vaccination.thisWeek.toString()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (tokenViewModel.userRole.value.equals("ENUMERATOR")) {
            binding.weekTaggedCardview.isVisible = true
            binding.weekOwnerCardview.isVisible = true
            binding.weekKeeperCardview.isVisible = true
            binding.weekExitedOwnerCardview.isVisible = true
            binding.weekAppointmentCardview.isVisible = false
            binding.weekVaccinationCardview.isVisible = false

        } else if (tokenViewModel.userRole.value.equals("VET_DOCTOR")) {
            binding.weekTaggedCardview.isVisible = false
            binding.weekOwnerCardview.isVisible = false
            binding.weekKeeperCardview.isVisible = false
            binding.weekExitedOwnerCardview.isVisible = false
            binding.weekAppointmentCardview.isVisible = true
            binding.weekVaccinationCardview.isVisible = true
        }

        thisWeekEnumeratorActivities()
        thisWeekVetActivities()

    }

}