package com.sallyjayz.ranchid.home.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentTodayBinding
import com.sallyjayz.ranchid.viewmodel.TokenViewModel
import com.sallyjayz.ranchid.viewmodel.register.DashboardActivitiesViewModel
import com.sallyjayz.ranchid.viewmodel.vet.VetDashboardActivitiesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TodayFragment : Fragment() {

    private lateinit var binding: FragmentTodayBinding
    private val dashboardActivitiesViewModel: DashboardActivitiesViewModel by viewModels()
    private val vetDashboardActivitiesViewModel: VetDashboardActivitiesViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by activityViewModels()

    /*init {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                if (tokenViewModel.userRole.value.equals("ENUMERATOR")) {
                    binding.todayTaggedCardview.isVisible = true
                    binding.todayOwnerCardview.isVisible = true
                    binding.todayKeeperCardview.isVisible = true
                    binding.todayExitedOwnerCardview.isVisible = true
                    binding.todayAppointmentCardview.isVisible = false
                    binding.todayVaccinationCardview.isVisible = false

                } else if (tokenViewModel.userRole.value.equals("vet_doctor")) {
                    binding.todayTaggedCardview.isVisible = false
                    binding.todayOwnerCardview.isVisible = false
                    binding.todayKeeperCardview.isVisible = false
                    binding.todayExitedOwnerCardview.isVisible = false
                    binding.todayAppointmentCardview.isVisible = true
                    binding.todayVaccinationCardview.isVisible = true
                }
             }
         }
    }*/


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTodayBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        todayAllActivities()
        if (tokenViewModel.loginRole.value.equals("ENUMERATOR")) {
            binding.todayTaggedCardview.isVisible = true
            binding.todayOwnerCardview.isVisible = true
            binding.todayKeeperCardview.isVisible = true
            binding.todayExitedOwnerCardview.isVisible = true
            binding.todayAppointmentCardview.isVisible = false
            binding.todayVaccinationCardview.isVisible = false

        } else if (tokenViewModel.loginRole.value.equals("VET_DOCTOR")) {
            binding.todayTaggedCardview.isVisible = false
            binding.todayOwnerCardview.isVisible = false
            binding.todayKeeperCardview.isVisible = false
            binding.todayExitedOwnerCardview.isVisible = false
            binding.todayAppointmentCardview.isVisible = true
            binding.todayVaccinationCardview.isVisible = true
        }

        todayEnumeratorActivities()
        todayVetActivities()
    }


    private fun todayEnumeratorActivities() {
        dashboardActivitiesViewModel.readTagged.observe(viewLifecycleOwner) { tag ->
            if (tag != null) {
                binding.todayTaggedLivestock.text = tag.today.toString()
            }
        }

        dashboardActivitiesViewModel.readOwner.observe(viewLifecycleOwner) { owner ->
            if (owner != null) {
                binding.todayOwnerLivestock.text = owner.today.toString()
            }
        }

        dashboardActivitiesViewModel.readKeeper.observe(viewLifecycleOwner) { keeper ->
            if (keeper != null) {
                binding.todayKeeperLivestock.text = keeper.today.toString()
            }
        }

        dashboardActivitiesViewModel.readExit.observe(viewLifecycleOwner) { exit ->
            if (exit != null) {
                binding.todayExitedOwner.text = exit.today.toString()
            }
        }
    }


    private fun todayVetActivities() {

        vetDashboardActivitiesViewModel.readAppointment.observe(viewLifecycleOwner) { appointment ->
            if (appointment != null) {
                binding.todayAppointment.text = appointment.today.toString()
            }
        }

        vetDashboardActivitiesViewModel.readVaccination.observe(viewLifecycleOwner) { vaccination ->
            if (vaccination != null) {
                binding.todayVaccination.text = vaccination.today.toString()
            }
        }
    }

    private fun todayAllActivities() {
        if (tokenViewModel.userRole.value == "ENUMERATOR") {
            dashboardActivitiesViewModel.readTagged.observe(viewLifecycleOwner) { tag ->
                if (tag != null) {
                    binding.todayTaggedLivestock.text = tag.today.toString()
//                            binding.todayTaggedCardview.isVisible = true
                }
            }

            dashboardActivitiesViewModel.readOwner.observe(viewLifecycleOwner) { owner ->
                if (owner != null) {
                    binding.todayOwnerLivestock.text = owner.today.toString()
//                            binding.todayOwnerCardview.isVisible = true
                }
            }

            dashboardActivitiesViewModel.readKeeper.observe(viewLifecycleOwner) { keeper ->
                if (keeper != null) {
                    binding.todayKeeperLivestock.text = keeper.today.toString()
//                            binding.todayKeeperCardview.isVisible = true
                }
            }

            dashboardActivitiesViewModel.readExit.observe(viewLifecycleOwner) { exit ->
                if (exit != null) {
                    binding.todayExitedOwner.text = exit.today.toString()
//                            binding.todayExitedOwnerCardview.isVisible = true
                }
            }
        } else if (tokenViewModel.userRole.value == "VET_DOCTOR") {
            vetDashboardActivitiesViewModel.readAppointment.observe(viewLifecycleOwner) { appointment ->
                if (appointment != null) {
                    binding.todayTaggedLivestock.text = appointment.today.toString()
                    binding.todayTaggedLivestockTxt.text = getString(R.string.upcoming_appointments)
//                            binding.todayAppointmentCardview.isVisible = true
//                    binding.todayTaggedLivestockTxt.text = getString(R.string.upcoming_appointments)
                }
            }

            vetDashboardActivitiesViewModel.readVaccination.observe(viewLifecycleOwner) { vaccination ->
                if (vaccination != null) {
                    binding.todayOwnerLivestock.text = vaccination.today.toString()
                    binding.todayOwnerLivestockTxt.text = getString(R.string.vaccinations)
//                            binding.todayVaccinationCardview.isVisible = true
//                    binding.todayOwnerLivestockTxt.text = getString(R.string.vaccinations)
                }
            }

            binding.todayKeeperCardview.visibility = View.INVISIBLE
            binding.todayExitedOwnerCardview.visibility = View.INVISIBLE
        }
    }

    override fun onResume() {
        super.onResume()

        if (tokenViewModel.userRole.value.equals("ENUMERATOR")) {
            binding.todayTaggedCardview.isVisible = true
            binding.todayOwnerCardview.isVisible = true
            binding.todayKeeperCardview.isVisible = true
            binding.todayExitedOwnerCardview.isVisible = true
            binding.todayAppointmentCardview.isVisible = false
            binding.todayVaccinationCardview.isVisible = false

        } else if (tokenViewModel.userRole.value.equals("VET_DOCTOR")) {
            binding.todayTaggedCardview.isVisible = false
            binding.todayOwnerCardview.isVisible = false
            binding.todayKeeperCardview.isVisible = false
            binding.todayExitedOwnerCardview.isVisible = false
            binding.todayAppointmentCardview.isVisible = true
            binding.todayVaccinationCardview.isVisible = true
        }

        todayEnumeratorActivities()
        todayVetActivities()
    }
}