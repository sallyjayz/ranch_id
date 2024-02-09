package com.sallyjayz.ranchid.report.keeper

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.sallyjayz.ranchid.databinding.FragmentListKeeperReportBinding
import com.sallyjayz.ranchid.model.report.keeper.Keeper
import com.sallyjayz.ranchid.paging.PagingLoadStateAdapter
import com.sallyjayz.ranchid.recyclerview.report.KeeperReportAdapter
import com.sallyjayz.ranchid.viewmodel.register.AllKeeperViewModel
import com.sallyjayz.ranchid.viewmodel.report.ReportViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListKeeperReportFragment : Fragment(), KeeperReportAdapter.OnClickListener {

    private lateinit var binding: FragmentListKeeperReportBinding
    private val keeperReportAdapter = KeeperReportAdapter(this)
    private val reportViewModel: ReportViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListKeeperReportBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initViewModel()
        searchName()
//        keeperDetail()
    }

    private fun initRecyclerView() {
        binding.apply {
            keeperSearchRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            keeperSearchRecyclerview.adapter = keeperReportAdapter.withLoadStateFooter(
                footer = PagingLoadStateAdapter {keeperReportAdapter.retry()}
            )
            buttonRetry.setOnClickListener {
                keeperReportAdapter.retry()
            }
        }


        /*val emptyDataObserver = EmptyData(binding.keeperSearchRecyclerview, empty_data_parent)
        keeperReportAdapter.registerAdapterDataObserver(emptyDataObserver)*/

        /*keeperReportAdapter.addLoadStateListener { state ->
            when(state.refresh){
                is LoadState.NotLoading -> {
                    binding.keeperReportProgress.isVisible = false
                    binding.textViewError.isVisible = false
                    binding.buttonRetry.isVisible = false
                }
                LoadState.Loading -> {
                    binding.keeperReportProgress.isVisible = true
                    binding.textViewError.isVisible = false
                    binding.buttonRetry.isVisible = false
                }
                is LoadState.Error -> {
                    binding.keeperReportProgress.isVisible = false
                    binding.textViewError.isVisible = true
                    binding.buttonRetry.isVisible = true
                }
            }
        }*/

        keeperReportAdapter.addLoadStateListener { loadStates ->
            binding.apply {
                keeperReportProgress.isVisible = loadStates.source.refresh is LoadState.Loading
                keeperSearchRecyclerview.isVisible = loadStates.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadStates.source.refresh is LoadState.Error
                textViewError.isVisible = loadStates.source.refresh is LoadState.Error

                if (loadStates.source.refresh is LoadState.NotLoading &&
                    loadStates.append.endOfPaginationReached &&
                    keeperReportAdapter.itemCount < 1) {
                    keeperSearchRecyclerview.isVisible = false
                    emptyDataPresent.isVisible = true
                }else {
                    emptyDataPresent.isVisible = false
                }
            }
        }
    }

    private fun initViewModel() {
        reportViewModel.listKeeperReport.observe(viewLifecycleOwner) {
            keeperReportAdapter.submitData(lifecycle, it)

        }

        lifecycleScope.launch {
            keeperReportAdapter.loadStateFlow.distinctUntilChangedBy {
                it.refresh
            }.collect{
                renderKeeperList(keeperReportAdapter.snapshot().items as ArrayList<Keeper>)
            }
        }
    }

    private fun renderKeeperList(keeperList: ArrayList<Keeper>) {
        Log.e("TAGS", "=====${keeperList}")
        keeperReportAdapter.addData(keeperList)
        keeperReportAdapter.notifyDataSetChanged()
    }

   private fun searchName(){
//        binding.keeperSearchview.imeOptions = EditorInfo.IME_ACTION_DONE
        binding.keeperSearchview.setOnQueryTextListener(
            object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    /*keeperReportAdapter.filter.filter(query)
                    return false*/
                    /*if (query != null) {
                        binding.keeperSearchRecyclerview.scrollToPosition(0)
                        keeperReportAdapter.filter.filter(query)
//                        binding.keeperSearchview.clearFocus()
                    }
                    return true*/
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    keeperReportAdapter.filter.filter(newText)
                    return false

                    /*if (newText != null) {
                        binding.keeperSearchRecyclerview.scrollToPosition(0)
                        keeperReportAdapter.filter.filter(newText)
                    }
                    return false*/
                }

            }
        )
    }

    /*private fun keeperDetail() {
        keeperReportAdapter.setOnClickListener(object: KeeperReportAdapter.OnClickListener{
            override fun onClickKeeper(position: Int, Keeper: Keepers) {
                val keeperDetail = Keepers(
                    Keeper.id,
                    Keeper.surname,
                    Keeper.other_names,
                    Keeper.gender,
                    Keeper.dob,
                    Keeper.phone_number,
                    Keeper.marital_status,
                    Keeper.next_of_kin,
                    Keeper.next_of_kin_number,
                    Keeper.email_address,
                    Keeper.nin,
                    Keeper.state,
                    Keeper.lga,
                    Keeper.passport_photo,
                    Keeper.id_doc,
                    Keeper.prof_id_doc,
                    Keeper.location,
                    Keeper.address
                )
                val action = ListKeeperReportFragmentDirections
                    .actionListKeeperReportFragmentToListKeeperReportDetailFragment(keeperDetail)
                findNavController().navigate(action)
            }

        })
    }*/

    override fun onItemClick(keeper: Keeper) {
        /*val keeperDetail = Keepers(
            keeper.id,
            keeper.surname,
            keeper.other_names,
            keeper.gender,
            keeper.dob,
            keeper.phone_number,
            keeper.marital_status,
            keeper.next_of_kin,
            keeper.next_of_kin_number,
            keeper.email_address,
            keeper.nin,
            keeper.state,
            keeper.lga,
            keeper.passport_photo,
            keeper.id_doc,
            keeper.prof_id_doc,
            keeper.location,
            keeper.address
        )*/
        val action = ListKeeperReportFragmentDirections
            .actionListKeeperReportFragmentToListKeeperReportDetailFragment(keeper)
        findNavController().navigate(action)
    }
}