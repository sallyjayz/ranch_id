package com.sallyjayz.ranchid.home.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sallyjayz.ranchid.databinding.FragmentMonthBinding
import com.sallyjayz.ranchid.viewmodel.register.DashboardActivitiesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MonthFragment : Fragment() {

    private lateinit var binding: FragmentMonthBinding
    private val dashboardActivitiesViewModel: DashboardActivitiesViewModel by viewModels()

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

        thisMonthActivities()
    }

    private fun thisMonthActivities() {
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

}