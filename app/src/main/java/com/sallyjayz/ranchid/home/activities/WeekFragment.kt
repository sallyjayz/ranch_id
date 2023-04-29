package com.sallyjayz.ranchid.home.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sallyjayz.ranchid.databinding.FragmentWeekBinding
import com.sallyjayz.ranchid.viewmodel.register.DashboardActivitiesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeekFragment : Fragment() {

    private lateinit var binding: FragmentWeekBinding
    private val dashboardActivitiesViewModel: DashboardActivitiesViewModel by viewModels()

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

        thisWeekActivities()
    }

    private fun thisWeekActivities() {
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

}