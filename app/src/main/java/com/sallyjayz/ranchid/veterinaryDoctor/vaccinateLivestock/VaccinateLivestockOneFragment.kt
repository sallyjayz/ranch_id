package com.sallyjayz.ranchid.veterinaryDoctor.vaccinateLivestock

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
import com.sallyjayz.ranchid.databinding.FragmentVaccinateLivestockOneBinding
import com.sallyjayz.ranchid.model.vet.livestockwithvaccinationhistory.Livestock
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import com.sallyjayz.ranchid.viewmodel.PermissionViewModel
import com.sallyjayz.ranchid.viewmodel.vet.LivestockVaccinationViewModel
import com.sallyjayz.ranchid.viewmodel.vet.response.LivestockWithVaccinationHistoryResponseViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class VaccinateLivestockOneFragment : Fragment() {

    private lateinit var cameraExecutor: ExecutorService
    private lateinit var binding: FragmentVaccinateLivestockOneBinding
    private lateinit var sharedViewModel: PermissionViewModel
    private val livestockVaccinationSharedViewModel: LivestockVaccinationViewModel by activityViewModels()
    private val livestockWithVaccinationHistoryResponseViewModel: LivestockWithVaccinationHistoryResponseViewModel by viewModels()
    private var tagId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            sharedViewModel = ViewModelProvider(it)[PermissionViewModel::class.java]
        }

        //        No need by default, doing this to clear content before going back to register screen
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                livestockVaccinationSharedViewModel.resetLivestockVaccination()
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
        binding = FragmentVaccinateLivestockOneBinding
            .inflate(layoutInflater, container, false)

        cameraExecutor = Executors.newSingleThreadExecutor()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.isPermissionGranted.observe(viewLifecycleOwner) {
            if (it) startCamera()
//            else Toast.makeText(activity, "Go to app settings to accept camera permission",
//                Toast.LENGTH_SHORT).show()
        }
        binding?.vaccinationStepOne = this
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

            livestockWithVaccinationHistoryResponseViewModel.livestockWithVaccinationHistoryResponse.observe(viewLifecycleOwner) {
                when(it) {
                    is ApiResponse.Failure -> {
                        binding.tagError.text = it.errorMessage
//                    binding.errorTv.text = getString(R.string.dashboard_activity_download_failed)
                    }
                    ApiResponse.Loading -> {
                        binding.tagError.text = "Loading"
//                    binding.homeFragmentProgress.isVisible = true
//                    binding.homeFragmentConstraintLayout.alpha = 0.5F
                    }
                    is ApiResponse.Success -> {

//                        binding.tagError.text = "success"

                        val livestockData = Livestock(
                            /* it.data.data.livestock.assigned_flg,
                             it.data.data.livestock.capture_location,
                             it.data.data.livestock.capture_state,
                             it.data.data.livestock.date_tagged,
                             it.data.data.livestock.enumerator,
                             it.data.data.livestock.exit_date,
                             it.data.data.livestock.exit_type,*/
                            it.data.data.livestock.gender,
                            it.data.data.livestock.gestation_date,
//                                it.data.data.livestock.keeper_id,
                            it.data.data.livestock.keeper_other_names,
//                                it.data.data.livestock.keeper_phone_number,
                            it.data.data.livestock.keeper_surname,
                            it.data.data.livestock.livestock_breed,
                            it.data.data.livestock.livestock_type,
                            /*it.data.data.livestock.missing_flg,
                            it.data.data.livestock.muzzle_photo,
                            it.data.data.livestock.owner_id,*/
                            it.data.data.livestock.owner_other_names,
//                                it.data.data.livestock.owner_phone_number,
                            it.data.data.livestock.owner_surname,
                            it.data.data.livestock.passport_id,
                            /*it.data.data.livestock.published_flg,
                            it.data.data.livestock.sold_flg,*/
                            it.data.data.livestock.tag_id,
                            /*it.data.data.livestock.tag_state,
                            it.data.data.livestock.verification_photo*/
                        )

                        val action = VaccinateLivestockOneFragmentDirections
                            .actionVaccinateLivestockOneFragmentToVaccinateLivestockTwoFragment(livestockData)
                        findNavController().navigate(action)
                    }
                }
            }

            livestockWithVaccinationHistoryResponseViewModel.getLivestockWithVaccinationHistory(tagId!!, object:
                CoroutinesErrorHandler{
                override fun onError(message: String) {
                    binding.tagError.text = getString(R.string.something_went_wrong)
//                    binding.tagError.text = getString(R.string.you_are_offline)
//                binding.offlineTv.isVisible = true
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
//                            tagId = "NGKW00001RN0009"
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