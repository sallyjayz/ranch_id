package com.sallyjayz.ranchid.report

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.sallyjayz.ranchid.databinding.FragmentReportBinding
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import com.sallyjayz.ranchid.viewmodel.report.ReportResponseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReportFragment : Fragment() {


    private lateinit var binding: FragmentReportBinding
    private val reportResponseViewModel: ReportResponseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReportBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.reportFragment = this
    }

    fun listLivestockOwner() {
        /*val action = ReportFragmentDirections
            .actionReportFragmentToListOwnerReportFragment()
        findNavController().navigate(action)*/

        reportResponseViewModel.allOwnerResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {
//                    binding.errorTv.text = "Failed to Download List of Owners"

                    Toast.makeText(requireContext(), "Failed to Download List of Owners",
                        Toast.LENGTH_LONG).show()
                }
                ApiResponse.Loading -> {
                    Toast.makeText(requireContext(), "Downloading List of Owners",
                        Toast.LENGTH_LONG).show()
//                    binding.homeFragmentProgress.isVisible = true
//                    binding.homeFragmentConstraintLayout.alpha = 0.5F
                }
                is ApiResponse.Success -> {

                    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
                        throwable.printStackTrace()
                    }

                    CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                        it.data.data.allOwnersList
                        Toast.makeText(requireContext(), "Downloading List of Owners Completed",
                            Toast.LENGTH_LONG).show()
                    }

//                    binding.homeFragmentProgress.isVisible = false
//                    binding.homeFragmentConstraintLayout.alpha = 1.0F
                }
            }
        }

        reportResponseViewModel.getAllOwner(object : CoroutinesErrorHandler {
            override fun onError(message: String) {
                Toast.makeText(requireContext(), message,
                    Toast.LENGTH_LONG).show()
//                binding.offlineTv.isVisible = true
//                binding.homeFragmentProgress.isVisible = false
//                binding.homeFragmentConstraintLayout.alpha = 1.0F
            }

        })
    }

    fun listLivestockKeeper() {
        /*val action = ReportFragmentDirections
            .actionReportFragmentToListKeeperReportFragment()
        findNavController().navigate(action)*/

        reportResponseViewModel.allKeeperResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {
//                    binding.errorTv.text = "Failed to Download List of Owners"

                    Toast.makeText(requireContext(), "Failed to Download List of Keepers",
                        Toast.LENGTH_LONG).show()
                }
                ApiResponse.Loading -> {
                    Toast.makeText(requireContext(), "Downloading List of Keepers",
                        Toast.LENGTH_LONG).show()
//                    binding.homeFragmentProgress.isVisible = true
//                    binding.homeFragmentConstraintLayout.alpha = 0.5F
                }
                is ApiResponse.Success -> {

                    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
                        throwable.printStackTrace()
                    }

                    CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                        it.data.allKeepersList
                        Toast.makeText(requireContext(), "Downloading List of Keepers Completed",
                            Toast.LENGTH_LONG).show()
                    }

//                    binding.homeFragmentProgress.isVisible = false
//                    binding.homeFragmentConstraintLayout.alpha = 1.0F
                }
            }
        }

        reportResponseViewModel.getAllKeeper(object : CoroutinesErrorHandler {
            override fun onError(message: String) {
                Toast.makeText(requireContext(), message,
                    Toast.LENGTH_LONG).show()
//                binding.offlineTv.isVisible = true
//                binding.homeFragmentProgress.isVisible = false
//                binding.homeFragmentConstraintLayout.alpha = 1.0F
            }

        })
    }

    fun listLivestockLocation() {
        /*val action = ReportFragmentDirections
            .actionReportFragmentToListLocationReportFragment()
        findNavController().navigate(action)*/
    }

    fun listTaggedLivestock() {
        /*val action = ReportFragmentDirections
            .actionReportFragmentToListTaggedLivestockReportFragment()
        findNavController().navigate(action)*/
    }

}