package com.sallyjayz.ranchid.offline.taglivestock

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentOfflineTagLivestockBinding
import com.sallyjayz.ranchid.model.offline.taglivestock.OfflineTagLivestock
import com.sallyjayz.ranchid.model.register.taglivestock.TagLivestock
import com.sallyjayz.ranchid.recyclerview.offline.OfflineTagLivestockRecyclerViewAdapter
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import com.sallyjayz.ranchid.viewmodel.EmptyData
import com.sallyjayz.ranchid.viewmodel.MyState
import com.sallyjayz.ranchid.viewmodel.NetworkStatusViewModel
import com.sallyjayz.ranchid.viewmodel.offline.OfflineTagLivestockViewModel
import com.sallyjayz.ranchid.viewmodel.register.response.TagLivestockResponseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OfflineFragmentTagLivestock : Fragment() {

    private lateinit var binding: FragmentOfflineTagLivestockBinding
    private val offlineTagLivestockViewModel: OfflineTagLivestockViewModel by viewModels()
    private lateinit var recyclerViewAdapter: OfflineTagLivestockRecyclerViewAdapter
    private val tagLivestockResponseViewModel: TagLivestockResponseViewModel by viewModels()
    private val networkStatusViewModel: NetworkStatusViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOfflineTagLivestockBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuSetup()

        initRecyclerView()
        initViewModel()
        failedUploadTagLivestock()
    }

    private fun menuSetup() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.offline_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_sync -> {
                        networkStatusViewModel.state.observe(viewLifecycleOwner) {
                            when (it) {
                                MyState.Fetched -> {
                                    binding.errorTv.isVisible = false
                                    uploadTagLivestock()
                                }
                                MyState.Error -> {
                                    binding.errorTv.isVisible = true
                                    binding.offlineTagLivestockRecyclerview.alpha = 1.0F
                                    binding.offlineTagLivestockProgress.isVisible = false
                                }
                            }
                        }
                        true
                    }
                    R.id.action_delete_completed_upload -> {
                        CoroutineScope(Dispatchers.IO).launch {
                            offlineTagLivestockViewModel
                                .deleteOfflineTagLivestockStatusCondition("COMPLETED")
                        }
                        true
                    }
                    R.id.action_delete_pending_upload -> {
                        CoroutineScope(Dispatchers.IO).launch {
                            offlineTagLivestockViewModel
                                .deleteOfflineTagLivestockStatusCondition("PENDING")
                        }
                        true
                    }
                    R.id.action_delete_failed_upload -> {
                        CoroutineScope(Dispatchers.IO).launch {
                            offlineTagLivestockViewModel
                                .deleteOfflineTagLivestockStatusCondition("FAILED")
                        }
                        true
                    }
                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun initRecyclerView() {
        binding.offlineTagLivestockRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            recyclerViewAdapter = OfflineTagLivestockRecyclerViewAdapter(requireContext())
            adapter = recyclerViewAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                recyclerViewAdapter.loadStateFlow.collect {
                    Log.d("recyclerview3", "${recyclerViewAdapter.itemCount}")
                    val emptyDataObserver = EmptyData(binding.offlineTagLivestockRecyclerview, binding.emptyDataPresent)
                    recyclerViewAdapter.registerAdapterDataObserver(emptyDataObserver)
                }
            }
        }
    }

    private fun initViewModel() {
        CoroutineScope(Dispatchers.IO).launch {
            offlineTagLivestockViewModel.readOfflineTagLivestock().collectLatest {
                recyclerViewAdapter.submitData(it)
            }
        }
    }

    private fun uploadTagLivestock() {
        offlineTagLivestockViewModel.selectAllOfflineTagLivestock().observe(viewLifecycleOwner) { offlineTagLivestockList ->
            for(tagLivestock in offlineTagLivestockList) {
                if (tagLivestock.status == "PENDING") {

                    tagLivestockResponseViewModel.tagLivestockResponse.observe(viewLifecycleOwner) {
                        when(it) {
                            is ApiResponse.Failure -> {
//                                binding.errorTv.text = "Code: ${it.code}, ${it.errorMessage}"

                                CoroutineScope(Dispatchers.IO).launch {
                                    offlineTagLivestockViewModel.updateOfflineTagLivestock(tagLivestock.id,"FAILED")
                                }
                                binding.offlineTagLivestockProgress.isVisible = false
                                binding.offlineTagLivestockRecyclerview.alpha = 1.0F
                            }
                            ApiResponse.Loading -> {
//                                binding.errorTv.text = "Loading"
                                binding.offlineTagLivestockProgress.isVisible = true
                                binding.offlineTagLivestockRecyclerview.alpha = 0.5F
                            }
                            is ApiResponse.Success -> {
//                        binding.errorTv.text = "${it.data.success}"

                                CoroutineScope(Dispatchers.IO).launch {
                                    offlineTagLivestockViewModel
                                        .updateOfflineTagLivestock(tagLivestock.id,"COMPLETED")
                                    Log.d("status", "${offlineTagLivestockViewModel
                                        .updateOfflineTagLivestock(tagLivestock.id,"COMPLETED")}")
                                    Log.d("id", "${tagLivestock.id}")

                                    Log.d("Response", "${it.data.success}")

                                }
                                binding.offlineTagLivestockProgress.isVisible = false
                                binding.offlineTagLivestockRecyclerview.alpha = 1.0F
//                                binding.errorTv.text = "inserted"
                            }
                        }
                    }

                    tagLivestockResponseViewModel.addTagLivestock(
                        TagLivestock(
                            tagLivestock.latitude,
                            tagLivestock.longitude,
                            tagLivestock.tagId,
                            tagLivestock.passportId,
                            tagLivestock.keeperId,
                            tagLivestock.ownerId,
                            tagLivestock.livestockType,
                            tagLivestock.livestockBreed,
                            tagLivestock.gender,
                            tagLivestock.healthStatus,
                            tagLivestock.gestationDate,
                            tagLivestock.description,
                            tagLivestock.taggingLocId,
                            "",
                            tagLivestock.productionType,
                            tagLivestock.enumerator,
                            tagLivestock.otherComments,
                            "TAGGING",
                            tagLivestock.verificationPhoto,
                            tagLivestock.muzzlePhoto
                        ),
                        object: CoroutinesErrorHandler {
                            override fun onError(message: String) {
//                    binding.errorTv.text = "Error! $message"
                                binding.errorTv.isVisible = true
                                binding.offlineTagLivestockProgress.isVisible = false
                                binding.offlineTagLivestockRecyclerview.alpha = 1.0F

                            }
                        }
                    )

                }
            }
        }
    }

    private fun failedUploadTagLivestock() {
        recyclerViewAdapter.setOnClickListener(object :
            OfflineTagLivestockRecyclerViewAdapter.OnClickListener{
            override fun onClickOfflineTagLivestock(position: Int, offlineTagLivestock: OfflineTagLivestock
            ) {
                val tagLivestockData = OfflineTagLivestock(
                    offlineTagLivestock.id,
                    offlineTagLivestock.latitude,
                    offlineTagLivestock.longitude,
                    offlineTagLivestock.tagId,
                    offlineTagLivestock.passportId,
                    offlineTagLivestock.keeperId,
                    offlineTagLivestock.ownerId,
                    offlineTagLivestock.livestockType,
                    offlineTagLivestock.livestockBreed,
                    offlineTagLivestock.gender,
                    offlineTagLivestock.healthStatus,
                    offlineTagLivestock.gestationDate,
                    offlineTagLivestock.description,
                    offlineTagLivestock.taggingLocId,
                    offlineTagLivestock.weight,
                    offlineTagLivestock.productionType,
                    offlineTagLivestock.enumerator,
                    offlineTagLivestock.otherComments,
                    offlineTagLivestock.scanPurpose,
                    offlineTagLivestock.verificationPhoto,
                    offlineTagLivestock.muzzlePhoto,
                    offlineTagLivestock.status

                )

                val action = OfflineFragmentTagLivestockDirections
                    .actionOfflineFragmentTagLivestockToOfflineTagLivestockDetailFragment(tagLivestockData)
                findNavController().navigate(action)
            }

        })
    }

}