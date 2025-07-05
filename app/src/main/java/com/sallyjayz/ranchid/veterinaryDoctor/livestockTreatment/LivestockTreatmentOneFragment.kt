package com.sallyjayz.ranchid.veterinaryDoctor.livestockTreatment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentLivestockTreatmentOneBinding
import com.sallyjayz.ranchid.model.vet.livestockwithtreatmenthistory.Livestock
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.veterinaryDoctor.vaccinateLivestock.VaccinateLivestockOneFragmentDirections
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import com.sallyjayz.ranchid.viewmodel.PermissionViewModel
import com.sallyjayz.ranchid.viewmodel.vet.LivestockTreatmentViewModel
import com.sallyjayz.ranchid.viewmodel.vet.response.LivestockWithTreatmentHistoryResponseViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class LivestockTreatmentOneFragment : Fragment() {

    private lateinit var cameraExecutor: ExecutorService
    private lateinit var binding: FragmentLivestockTreatmentOneBinding
    private lateinit var sharedViewModel: PermissionViewModel
    private val livestockTreatmentSharedViewModel: LivestockTreatmentViewModel by activityViewModels()
    private val livestockWithTreatmentHistoryResponseViewModel: LivestockWithTreatmentHistoryResponseViewModel by viewModels()
    private var tagId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let {
            sharedViewModel = ViewModelProvider(it)[PermissionViewModel::class.java]
        }

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                livestockTreatmentSharedViewModel.resetLivestockTreatment()
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLivestockTreatmentOneBinding
            .inflate(layoutInflater, container, false)
        cameraExecutor = Executors.newSingleThreadExecutor()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.isPermissionGranted.observe(viewLifecycleOwner) {
            if (it) startCamera()
        }

        binding?.treatmentStepOne = this
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireActivity())
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.tagLivestockScanimage.surfaceProvider)
                }


            val imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(
                        cameraExecutor,
                        BarcodeAnalyzer()
                    )
                }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try{
                cameraProvider.unbindAll()
                activity?.let {
                    cameraProvider.bindToLifecycle(
                        it, cameraSelector, preview, imageAnalyzer
                    )
                }
            } catch (exc: Exception) {
                exc.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(requireActivity()))

    }

    fun nextButtonClicked(){

        if(tagId != null) {

            livestockWithTreatmentHistoryResponseViewModel.livestockWithTreatmentHistoryResponse.observe(viewLifecycleOwner) {
                when(it) {
                    is ApiResponse.Failure -> {
                        binding.tagError.text = it.errorMessage
                    }
                    ApiResponse.Loading -> {
                        binding.tagError.text = "Loading"
                    }
                    is ApiResponse.Success -> {

                        val livestockData = Livestock(
                            it.data.data.livestock.gender,
                            it.data.data.livestock.gestation_date,
                            it.data.data.livestock.keeper_other_names,
                            it.data.data.livestock.keeper_surname,
                            it.data.data.livestock.livestock_breed,
                            it.data.data.livestock.livestock_type,
                            it.data.data.livestock.owner_other_names,
                            it.data.data.livestock.owner_surname,
                            it.data.data.livestock.passport_id,
                            it.data.data.livestock.tag_id
                        )

                        val action = LivestockTreatmentOneFragmentDirections
                            .actionLivestockTreatmentOneFragmentToLivestockTreatmentTwoFragment(livestockData)
                        findNavController().navigate(action)
                    }
                }
            }

            livestockWithTreatmentHistoryResponseViewModel.getLivestockWithTreatmentHistory(tagId!!, object:
                CoroutinesErrorHandler {
                override fun onError(message: String) {
                    binding.tagError.text = getString(R.string.something_went_wrong)
                }

            })

        } else {
            binding.tagError.text = "SCAN A TAG"
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        cameraExecutor.shutdown()
    }

    inner class BarcodeAnalyzer : ImageAnalysis.Analyzer {
        @SuppressLint("UnsafeOptInUsageError")
        override fun analyze(image: ImageProxy) {
            val mediaImage = image.image
            if (mediaImage != null) {

                val inputImage = InputImage.fromMediaImage(mediaImage, image.imageInfo.rotationDegrees)

                val scanner = BarcodeScanning.getClient()
                scanner.process(inputImage).addOnSuccessListener { barcodes ->
                    if (barcodes.isNotEmpty()) {
                        for (barcode in barcodes) {
                            binding.tagLivestockTag.text = barcode.rawValue
                            binding.barcodeBoundary.setImageResource(R.drawable.border)
                            tagId = barcode.rawValue.toString()
                        }
                    }
                }.addOnFailureListener {
                    binding.tagLivestockTag.text = getString(R.string.try_again)
                }.addOnCompleteListener{
                    image.close()
                }
            }
        }
    }
}