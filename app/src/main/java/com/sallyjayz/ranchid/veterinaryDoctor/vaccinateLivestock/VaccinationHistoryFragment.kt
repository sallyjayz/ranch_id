package com.sallyjayz.ranchid.veterinaryDoctor.vaccinateLivestock

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.sallyjayz.ranchid.databinding.FragmentVaccinationHistoryBinding
import com.sallyjayz.ranchid.model.vet.livestockwithvaccinationhistory.VaccinationHistory
import com.sallyjayz.ranchid.recyclerview.vet.VaccinationHistoryRecyclerView
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import com.sallyjayz.ranchid.viewmodel.EmptyData
import com.sallyjayz.ranchid.viewmodel.vet.response.LivestockWithVaccinationHistoryResponseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VaccinationHistoryFragment : Fragment() {

    private lateinit var binding: FragmentVaccinationHistoryBinding
    private lateinit var vaccinationHistoryList: ArrayList<VaccinationHistory>
    private lateinit var recyclerViewAdapter: VaccinationHistoryRecyclerView
    private val mLivestockWithVaccinationHistoryResponseViewModel: LivestockWithVaccinationHistoryResponseViewModel by viewModels()
    private val args: VaccinationHistoryFragmentArgs by navArgs()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVaccinationHistoryBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vaccinationHistoryList = ArrayList()
        recyclerViewAdapter = VaccinationHistoryRecyclerView(vaccinationHistoryList)
        initViewModel()
        initRecyclerView()
//        recyclerViewAdapter.notifyDataSetChanged()

    }

    private fun initViewModel() {

        mLivestockWithVaccinationHistoryResponseViewModel.livestockWithVaccinationHistoryResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {
                    binding.errorTv.text = it.errorMessage
                    binding.vetHistoryProgress.isVisible = false
                }
                ApiResponse.Loading -> {
                    binding.vetHistoryProgress.isVisible = true
                }
                is ApiResponse.Success -> {
                    binding.vetHistoryProgress.isVisible = false
                    Log.d("recyclerviewData", "${it.data.data.vaccination_history}")
                    vaccinationHistoryList.addAll(it.data.data.vaccination_history)
                    recyclerViewAdapter.notifyDataSetChanged()
                }
            }
        }

        mLivestockWithVaccinationHistoryResponseViewModel.getLivestockWithVaccinationHistory(args.scannedTagID.toString(), object:
            CoroutinesErrorHandler {
            override fun onError(message: String) {
                binding.errorTv.text = message
//                    binding.tagError.text = getString(R.string.you_are_offline)
//                binding.offlineTv.isVisible = true
            }

        })
    }

    private fun initRecyclerView() {

        binding.vetHistoryRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = recyclerViewAdapter
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                Log.d("recyclerview3", "${recyclerViewAdapter.itemCount}")
                val emptyDataObserver = EmptyData(binding.vetHistoryRecyclerview, binding.emptyDataPresent)
                recyclerViewAdapter.registerAdapterDataObserver(emptyDataObserver)
            }
        }
    }


}