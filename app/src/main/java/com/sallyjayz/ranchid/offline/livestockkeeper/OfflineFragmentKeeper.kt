package com.sallyjayz.ranchid.offline.livestockkeeper

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
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
import com.sallyjayz.ranchid.databinding.FragmentOfflineKeeperBinding
import com.sallyjayz.ranchid.model.offline.keeper.OfflineKeeper
import com.sallyjayz.ranchid.model.register.keeper.Keeper
import com.sallyjayz.ranchid.recyclerview.offline.OfflineKeeperRecyclerViewAdapter
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import com.sallyjayz.ranchid.viewmodel.EmptyData
import com.sallyjayz.ranchid.viewmodel.MyState
import com.sallyjayz.ranchid.viewmodel.NetworkStatusViewModel
import com.sallyjayz.ranchid.viewmodel.offline.OfflineKeeperViewModel
import com.sallyjayz.ranchid.viewmodel.register.response.KeeperResponseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
    throwable.printStackTrace()
}

@AndroidEntryPoint
class OfflineFragmentKeeper : Fragment() {

    private lateinit var binding: FragmentOfflineKeeperBinding
    private val offlineKeeperViewModel: OfflineKeeperViewModel by viewModels()
    private lateinit var recyclerViewAdapter: OfflineKeeperRecyclerViewAdapter
    private val keeperResponseViewModel: KeeperResponseViewModel by viewModels()
    private val networkStatusViewModel: NetworkStatusViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOfflineKeeperBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuSetup()
        initRecyclerView()
        initViewModel()
        failedUploadKeeper()
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
                                    uploadKeeper()
                                }
                                MyState.Error -> {
                                    binding.errorTv.isVisible = true
                                    binding.offlineKeeperRecyclerview.alpha = 1.0F
                                    binding.offlineKeeperProgress.isVisible = false
                                }
                            }
                        }
                        true
                    }
                    R.id.action_delete_completed_upload -> {
                        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                            offlineKeeperViewModel
                                .deleteOfflineKeeperStatusCondition("COMPLETED")
                        }
                        true
                    }
                    R.id.action_delete_pending_upload -> {
                        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                            offlineKeeperViewModel
                                .deleteOfflineKeeperStatusCondition("PENDING")
                        }
                        true
                    }
                    R.id.action_delete_failed_upload -> {
                        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                            offlineKeeperViewModel
                                .deleteOfflineKeeperStatusCondition("FAILED")
                        }
                        true
                    }
                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun initRecyclerView() {
        binding.offlineKeeperRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            recyclerViewAdapter = OfflineKeeperRecyclerViewAdapter(requireContext())
            adapter = recyclerViewAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                recyclerViewAdapter.loadStateFlow.collect {
                    Log.d("recyclerview3", "${recyclerViewAdapter.itemCount}")
                    val emptyDataObserver = EmptyData(binding.offlineKeeperRecyclerview, binding.emptyDataPresent)
                    recyclerViewAdapter.registerAdapterDataObserver(emptyDataObserver)
                }
            }
        }
    }

    private fun initViewModel() {
        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
            offlineKeeperViewModel.readOfflineKeepers().collectLatest {
                recyclerViewAdapter.submitData(it)
            }
        }
    }



    private fun uploadKeeper() {
        offlineKeeperViewModel.selectAllOfflineKeepers().observe(viewLifecycleOwner) {offlineKeeperList ->
//            Log.d("Offline Keeper", "$offlineKeeperList")

            for(keeper in offlineKeeperList) {
//                Log.d("Offline Keeper", "$keeper")
                if (keeper.status == "PENDING") {

//                    keeperId = keeper.id

                    keeperResponseViewModel.addKeeperResponse.observe(viewLifecycleOwner) {
                        when(it) {
                            is ApiResponse.Failure -> {
//                                binding.errorTv.text = "Code: ${it.code}, ${it.errorMessage}"

                                CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                                    offlineKeeperViewModel.updateOfflineKeeper("FAILED", keeper.id)
                                }
                                binding.offlineKeeperProgress.isVisible = false
                                binding.offlineKeeperRecyclerview.alpha = 1.0F
                            }
                            ApiResponse.Loading -> {
//                                binding.errorTv.text = "Loading"
                                binding.offlineKeeperProgress.isVisible = true
                                binding.offlineKeeperRecyclerview.alpha = 0.5F
                            }
                            is ApiResponse.Success -> {
//                        binding.errorTv.text = "${it.data.success}"

                                CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                                    offlineKeeperViewModel.updateOfflineKeeper("COMPLETED", keeper.id)
                                   /* Log.d("status", "${offlineKeeperViewModel
                                        .updateOfflineKeeper("COMPLETED", keeper.id)}")
                                    Log.d("id", "${keeper.id}")

                                    Log.d("Response", "${it.data.success}")*/

                                }
                                binding.offlineKeeperProgress.isVisible = false
                                binding.offlineKeeperRecyclerview.alpha = 1.0F
//                                binding.errorTv.text = "inserted"
                            }
                        }
                    }

                    keeperResponseViewModel.addKeeper(
                        Keeper(
                            keeper.surname,
                            keeper.otherNames,
                            keeper.gender,
                            keeper.dob,
                            keeper.phoneNumber,
                            keeper.marital_status,
                            keeper.nextOfKin,
                            keeper.nextOfKinPhoneNumber,
                            keeper.emailAddress,
                            keeper.nin,
                            keeper.state,
                            keeper.lga,
                            "",
                            keeper.documentID,
                            keeper.documentNumber,
                            keeper.location,
                            keeper.address,
                            "N/A",
                            keeper.photo,
                            "",
                            keeper.capturedBy
                        ),
                        object: CoroutinesErrorHandler {
                            override fun onError(message: String) {
//                    binding.errorTv.text = "Error! $message"
                                binding.errorTv.isVisible = true
                                binding.offlineKeeperProgress.isVisible = false
                                binding.offlineKeeperRecyclerview.alpha = 1.0F

                            }
                        }
                    )
                }
            }
        }
    }

    private fun failedUploadKeeper() {
        recyclerViewAdapter.setOnClickListener(object :
            OfflineKeeperRecyclerViewAdapter.OnClickListener{
                override fun onClickOfflineKeeper(position: Int, offlineKeeper: OfflineKeeper) {
                    val keeperData = OfflineKeeper(
                        offlineKeeper.id,
                        offlineKeeper.surname,
                        offlineKeeper.otherNames,
                        offlineKeeper.gender,
                        offlineKeeper.dob,
                        offlineKeeper.phoneNumber,
                        offlineKeeper.marital_status,
                        offlineKeeper.nextOfKin,
                        offlineKeeper.nextOfKinPhoneNumber,
                        offlineKeeper.emailAddress,
                        offlineKeeper.nin,
                        offlineKeeper.status,
                        offlineKeeper.lga,
                        offlineKeeper.ward,
                        offlineKeeper.documentID,
                        offlineKeeper.documentNumber,
                        offlineKeeper.location,
                        offlineKeeper.address,
                        offlineKeeper.other_location,
                        offlineKeeper.photo,
                        offlineKeeper.timestamp,
                        offlineKeeper.capturedBy,
                        offlineKeeper.status
                    )

                    val action = OfflineFragmentKeeperDirections
                        .actionOfflineFragmentKeeperToOfflineKeeperDetailFragment(keeperData)
                    findNavController().navigate(action)
                }
            })
    }
}