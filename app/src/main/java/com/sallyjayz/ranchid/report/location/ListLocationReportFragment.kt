package com.sallyjayz.ranchid.report.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.sallyjayz.ranchid.databinding.FragmentListLocationReportBinding
import com.sallyjayz.ranchid.model.report.location.Location
import com.sallyjayz.ranchid.paging.PagingLoadStateAdapter
import com.sallyjayz.ranchid.recyclerview.report.LocationReportAdapter
import com.sallyjayz.ranchid.viewmodel.TokenViewModel
import com.sallyjayz.ranchid.viewmodel.report.ReportViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListLocationReportFragment : Fragment(), LocationReportAdapter.OnClickListener {

    private lateinit var binding: FragmentListLocationReportBinding
    private val locationReportAdapter = LocationReportAdapter(this)
    private val reportViewModel: ReportViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListLocationReportBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initViewModel()
        searchName()
    }

    private fun initRecyclerView() {
        binding.apply {
            locationSearchRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            locationSearchRecyclerview.adapter = locationReportAdapter.withLoadStateFooter(
                footer = PagingLoadStateAdapter {locationReportAdapter.retry()}
            )
            buttonRetry.setOnClickListener {
                locationReportAdapter.retry()
            }
        }

        locationReportAdapter.addLoadStateListener { loadStates ->
            binding.apply {
                locationReportProgress.isVisible = loadStates.source.refresh is LoadState.Loading
                locationSearchRecyclerview.isVisible = loadStates.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadStates.source.refresh is LoadState.Error
                textViewError.isVisible = loadStates.source.refresh is LoadState.Error

                if (loadStates.source.refresh is LoadState.NotLoading &&
                    loadStates.append.endOfPaginationReached &&
                    locationReportAdapter.itemCount < 1) {
                    locationSearchRecyclerview.isVisible = false
                    emptyText.isVisible = true
                }else {
                    emptyText.isVisible = false
                }
            }
        }
    }

    private fun initViewModel() {
        tokenViewModel.username.observe(viewLifecycleOwner) { username ->
            if (username != null) {
                reportViewModel.locationReport(username).observe(viewLifecycleOwner) {
                    locationReportAdapter.submitData(lifecycle, it)

                }
            }
        }

        lifecycleScope.launch {
            locationReportAdapter.loadStateFlow.distinctUntilChangedBy {
                it.refresh
            }.collect{
                renderLocationList(locationReportAdapter.snapshot().items as ArrayList<Location>)
            }
        }
    }

    private fun renderLocationList(locationList: ArrayList<Location>) {
        locationReportAdapter.addData(locationList)
        locationReportAdapter.notifyDataSetChanged()
    }

    private fun searchName(){
        binding.locationSearchview.setOnQueryTextListener(
            object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    locationReportAdapter.filter.filter(newText)
                    return false
                }

            }
        )
    }

    override fun onItemClick(location: Location) {
        val action = ListLocationReportFragmentDirections
            .actionListLocationReportFragmentToListLocationReportDetailFragment(location)
        findNavController().navigate(action)
    }

}