package com.sallyjayz.ranchid.offline.livestockowner

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
import com.sallyjayz.ranchid.databinding.FragmentOfflineOwnerBinding
import com.sallyjayz.ranchid.model.offline.owner.OfflineOwner
import com.sallyjayz.ranchid.model.register.owner.Owner
import com.sallyjayz.ranchid.recyclerview.offline.OfflineOwnerRecyclerViewAdapter
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import com.sallyjayz.ranchid.viewmodel.EmptyData
import com.sallyjayz.ranchid.viewmodel.MyState
import com.sallyjayz.ranchid.viewmodel.NetworkStatusViewModel
import com.sallyjayz.ranchid.viewmodel.offline.OfflineOwnerViewModel
import com.sallyjayz.ranchid.viewmodel.register.response.OwnerResponseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OfflineOwnerFragment : Fragment() {

    private lateinit var binding: FragmentOfflineOwnerBinding
    private val offlineOwnerViewModel: OfflineOwnerViewModel by viewModels()
    private lateinit var recyclerViewAdapter: OfflineOwnerRecyclerViewAdapter
    private val ownerResponseViewModel: OwnerResponseViewModel by viewModels()
    private val networkStatusViewModel: NetworkStatusViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOfflineOwnerBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuSetup()

        initRecyclerView()
        initViewModel()
        failedUploadOwner()
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
                                    uploadOwner()
                                }
                                MyState.Error -> {
                                    binding.errorTv.isVisible = true
                                    binding.offlineOwnerRecyclerview.alpha = 1.0F
                                    binding.offlineOwnerProgress.isVisible = false
                                }
                            }
                        }
                        true
                    }
                    R.id.action_delete_completed_upload -> {
                        CoroutineScope(Dispatchers.IO).launch {
                            offlineOwnerViewModel
                                .deleteOfflineOwnerStatusCondition("COMPLETED")
                        }
                        true
                    }
                    R.id.action_delete_pending_upload -> {
                        CoroutineScope(Dispatchers.IO).launch {
                            offlineOwnerViewModel
                                .deleteOfflineOwnerStatusCondition("PENDING")
                        }
                        true
                    }
                    R.id.action_delete_failed_upload -> {
                        CoroutineScope(Dispatchers.IO).launch {
                            offlineOwnerViewModel
                                .deleteOfflineOwnerStatusCondition("FAILED")
                        }
                        true
                    }
                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun initRecyclerView() {
        binding.offlineOwnerRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            recyclerViewAdapter = OfflineOwnerRecyclerViewAdapter(requireContext())
            adapter = recyclerViewAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                recyclerViewAdapter.loadStateFlow.collect {
                    Log.d("recyclerview3", "${recyclerViewAdapter.itemCount}")
                    val emptyDataObserver = EmptyData(binding.offlineOwnerRecyclerview, binding.emptyDataPresent)
                    recyclerViewAdapter.registerAdapterDataObserver(emptyDataObserver)
                }
            }
        }
    }

    private fun initViewModel() {
        CoroutineScope(Dispatchers.IO).launch {
            offlineOwnerViewModel.readOfflineOwners().collectLatest {
                recyclerViewAdapter.submitData(it)
            }
        }
    }

    private fun uploadOwner() {

        offlineOwnerViewModel.selectAllOfflineOwners().observe(viewLifecycleOwner) { offlineOwnerList ->

            for (owner in offlineOwnerList) {
                if (owner.status == "PENDING") {
                    ownerResponseViewModel.addOwnerResponse.observe(viewLifecycleOwner) {
                        when(it) {
                            is ApiResponse.Failure -> {
//                                binding.errorTv.text = "Code: ${it.code}, ${it.errorMessage}"
//                                binding.errorTv.isVisible = true

                                CoroutineScope(Dispatchers.IO).launch {
                                    offlineOwnerViewModel.updateOfflineOwner(owner.id, "FAILED")
                                }
                                binding.offlineOwnerProgress.isVisible = false
                                binding.offlineOwnerRecyclerview.alpha = 1.0F
                            }
                            ApiResponse.Loading -> {
//                                binding.errorTv.text = "Loading"
                                binding.offlineOwnerProgress.isVisible = true
                                binding.offlineOwnerRecyclerview.alpha = 0.5F
                            }
                            is ApiResponse.Success -> {
//                        binding.errorTv.text = "${it.data.success}"

                                CoroutineScope(Dispatchers.IO).launch {
                                    offlineOwnerViewModel.updateOfflineOwner(owner.id, "COMPLETED")
                                    /*Log.d("status", "${offlineOwnerViewModel
                                        .updateOfflineOwner(owner.id, "COMPLETED")}")
                                    Log.d("id", "${owner.id}")

                                    Log.d("Response", "${it.data.success}")*/

                                }
                                binding.offlineOwnerProgress.isVisible = false
                                binding.offlineOwnerRecyclerview.alpha = 1.0F
                            }
                        }
                    }

                    ownerResponseViewModel.addOwner(
                        Owner(
                            owner.surname,
                            owner.otherNames,
                            owner.gender,
                            owner.dob,
                            owner.phoneNumber,
                            owner.marital_status,
                            owner.nextOfKin,
                            owner.nextOfKinPhoneNumber,
                            owner.emailAddress,
                            owner.nin,
                            owner.state,
                            owner.lga,
                            "",
                            owner.documentID,
                            owner.documentNumber,
                            owner.location,
                            owner.address,
                            "N/A",
                            owner.photo,
                            owner.ownershipType,
                            owner.group_name,
                            "",
                            owner.capturedBy,
                            owner.livestockKeeper
                        ),
                        object: CoroutinesErrorHandler {
                            override fun onError(message: String) {
//                    binding.errorTv.text = "Error! $message"
                                binding.errorTv.isVisible = true
                                binding.offlineOwnerProgress.isVisible = false
                                binding.offlineOwnerRecyclerview.alpha = 1.0F

                            }
                        }
                    )

                }
            }

        }

    }

    private fun failedUploadOwner() {
        recyclerViewAdapter.setOnClickListener(object :
            OfflineOwnerRecyclerViewAdapter.OnClickListener{
            override fun onClickOfflineOwner(position: Int, offlineOwner: OfflineOwner) {
                val ownerData = OfflineOwner(
                    offlineOwner.id,
                    offlineOwner.surname,
                    offlineOwner.otherNames,
                    offlineOwner.gender,
                    offlineOwner.dob,
                    offlineOwner.phoneNumber,
                    offlineOwner.marital_status,
                    offlineOwner.nextOfKin,
                    offlineOwner.nextOfKinPhoneNumber,
                    offlineOwner.emailAddress,
                    offlineOwner.nin,
                    offlineOwner.state,
                    offlineOwner.lga,
                    offlineOwner.ward,
                    offlineOwner.documentID,
                    offlineOwner.documentNumber,
                    offlineOwner.location,
                    offlineOwner.address,
                    offlineOwner.other_location,
                    offlineOwner.photo,
                    offlineOwner.ownershipType,
                    offlineOwner.group_name,
                    offlineOwner.timestamp,
                    offlineOwner.capturedBy,
                    offlineOwner.livestockKeeper,
                    offlineOwner.status
                )
                val action = OfflineOwnerFragmentDirections
                    .actionOfflineOwnerFragmentToOfflineOwnerDetailFragment(ownerData)
                findNavController().navigate(action)
            }

        })
    }

}