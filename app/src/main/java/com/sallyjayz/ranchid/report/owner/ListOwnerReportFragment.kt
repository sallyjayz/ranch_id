/*
package com.sallyjayz.ranchid.report.owner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.sallyjayz.ranchid.databinding.FragmentListOwnerReportBinding
import com.sallyjayz.ranchid.model.report.owner.Owner
import com.sallyjayz.ranchid.paging.PagingLoadStateAdapter
import com.sallyjayz.ranchid.recyclerview.report.OwnerReportAdapter
import com.sallyjayz.ranchid.viewmodel.report.ReportViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListOwnerReportFragment : Fragment(), OwnerReportAdapter.OnClickListener {

    private lateinit var binding: FragmentListOwnerReportBinding
    private val ownerReportAdapter = OwnerReportAdapter(this)
    private val reportViewModel: ReportViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListOwnerReportBinding
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
            ownerSearchRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            ownerSearchRecyclerview.adapter = ownerReportAdapter.withLoadStateFooter(
                footer = PagingLoadStateAdapter {ownerReportAdapter.retry()}
            )

            buttonRetry.setOnClickListener {
                ownerReportAdapter.retry()
            }
        }

        ownerReportAdapter.addLoadStateListener { loadStates ->
            binding.apply {
                ownerReportProgress.isVisible = loadStates.source.refresh is LoadState.Loading
                ownerSearchRecyclerview.isVisible = loadStates.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadStates.source.refresh is LoadState.Error
                textViewError.isVisible = loadStates.source.refresh is LoadState.Error

                if (loadStates.source.refresh is LoadState.NotLoading &&
                    loadStates.append.endOfPaginationReached &&
                    ownerReportAdapter.itemCount < 1) {
                    ownerSearchRecyclerview.isVisible = false
                    emptyText.isVisible = true
                }else {
                    emptyText.isVisible = false
                }
            }
        }

    }

    private fun initViewModel() {
        reportViewModel.listOwnerReport.observe(viewLifecycleOwner) {
            ownerReportAdapter.submitData(lifecycle, it)
        }

        lifecycleScope.launch {
            ownerReportAdapter.loadStateFlow.distinctUntilChangedBy {
                it.refresh
            }.collect{
                renderOwnerList(ownerReportAdapter.snapshot().items as ArrayList<Owner>)
            }
        }
    }

    private fun renderOwnerList(ownerList: ArrayList<Owner>) {
        ownerReportAdapter.addData(ownerList)
        ownerReportAdapter.notifyDataSetChanged()
    }

    private fun searchName(){
        binding.ownerSearchview.setOnQueryTextListener(
            object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    ownerReportAdapter.filter.filter(newText)
                    return false
                }

            }
        )
    }

    override fun onItemClick(owner: Owner) {
        val action = ListOwnerReportFragmentDirections
            .actionListOwnerReportFragmentToListOwnerReportDetailFragment(owner)
        findNavController().navigate(action)
    }

}*/
