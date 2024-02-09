package com.sallyjayz.ranchid.register.packinglist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentAddPackingListStepThreeBinding
import com.sallyjayz.ranchid.model.register.packinglist.postpackinglist.PackingListPost
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import com.sallyjayz.ranchid.viewmodel.MyState
import com.sallyjayz.ranchid.viewmodel.NetworkStatusViewModel
import com.sallyjayz.ranchid.viewmodel.TokenViewModel
import com.sallyjayz.ranchid.viewmodel.packinglist.LivestockDataViewModel
import com.sallyjayz.ranchid.viewmodel.packinglist.PackingListViewModel
import com.sallyjayz.ranchid.viewmodel.packinglist.response.ScanPackingListResponseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList

@AndroidEntryPoint
class AddPackingListStepThreeFragment : Fragment() {

    private lateinit var binding: FragmentAddPackingListStepThreeBinding
    private val sharedViewModel: PackingListViewModel by activityViewModels()
    private val livestockDataViewModel: LivestockDataViewModel by viewModels()
    private val scanPackingListResponseViewModel: ScanPackingListResponseViewModel by viewModels()
    private val networkStatusViewModel: NetworkStatusViewModel by activityViewModels()
    private val tokenViewModel: TokenViewModel by activityViewModels()
    private lateinit var livestockTagAdapter: ArrayAdapter<String>
    private lateinit var listView: ListView
    private lateinit var livestockTag: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddPackingListStepThreeBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            packingListViewModel = sharedViewModel
            //click buttons to be implemented
            packingListStepThree = this@AddPackingListStepThreeFragment
        }

        networkStatusViewModel.state.observe(viewLifecycleOwner) {
            when(it) {
                MyState.Fetched -> {
                    binding.offlineTv.isVisible = false
                    binding.submitButton.isEnabled = true
                    binding.submitButton.alpha = 1.0F
                }
                MyState.Error -> {
                    binding.offlineTv.isVisible = true
                    binding.submitButton.isEnabled = false
                    binding.submitButton.alpha = 0.5F
                }
            }
        }

        livestockTag()

    }

    private fun livestockTag() {
        livestockTag = ArrayList()
        livestockDataViewModel.selectAllLivestockData().observe(viewLifecycleOwner) {
            for (tag in it) {
                livestockTag.add(tag.tag_id)
            }
        }
    }

    fun livestockListClicked() {

        val rowList: View = layoutInflater.inflate(R.layout.dialog_listview, null)
        listView = rowList.findViewById(R.id.list_view)

//        livestockTagAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, livestockTag)
        livestockTagAdapter = ArrayAdapter(requireContext(), R.layout.packing_list_item, livestockTag)
        listView.adapter = livestockTagAdapter
        livestockTagAdapter.notifyDataSetChanged()

        /*livestockDataViewModel.selectAllLivestockData().observe(viewLifecycleOwner) {
            for (tag in it) {
                livestockTag.add(tag.tag_id)
                livestockTagAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, livestockTag)
                listView.adapter = livestockTagAdapter
                livestockTagAdapter.notifyDataSetChanged()
            }
        }*/

        MaterialAlertDialogBuilder(requireContext())
            .setView(rowList)
            .setPositiveButton(resources.getString(R.string.ok)) { dialog, _ ->
                // Respond to positive button press
                dialog.dismiss()
            }
            .show()

    }

    fun submitClicked() {
        scanPackingListResponseViewModel.packingListPostResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {
                    binding.progressBar.isVisible = false
                    val action = AddPackingListStepThreeFragmentDirections
                        .actionAddPackingListStepThreeFragmentToAddPackingListUnsuccessfulFragment()
                    findNavController().navigate(action)
                }
                ApiResponse.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is ApiResponse.Success -> {

                    binding.progressBar.isVisible = false

                    CoroutineScope(Dispatchers.IO).launch {
                        livestockDataViewModel.deleteLivestockData()
                    }

                    val action = AddPackingListStepThreeFragmentDirections
                        .actionAddPackingListStepThreeFragmentToAddPackingListSuccessfulFragment()
                    findNavController().navigate(action)
                }
            }
        }

        tokenViewModel.username.observe(viewLifecycleOwner) { username ->
            if (username != null) {
                scanPackingListResponseViewModel.parkingListPost(
                    username.lowercase(),
                    PackingListPost(
                        sharedViewModel.driverName.value.toString(),
                        sharedViewModel.vehicleName.value.toString(),
                        sharedViewModel.regNumber.value.toString(),
                        sharedViewModel.packingLivestockType.value.toString(),
                        sharedViewModel.packingStateId.value.toString(),
                        sharedViewModel.packingLgaId.value.toString(),
                        sharedViewModel.destination.value.toString(),
                        livestockTag
                    ),
                    object: CoroutinesErrorHandler {
                        override fun onError(message: String) {
                            binding.offlineTv.isVisible = true
                            binding.progressBar.isVisible = false
//                            binding.errorTv.text = message
                        }

                    }
                )
            }

        }
    }

}