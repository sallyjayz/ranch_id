package com.sallyjayz.ranchid.register.taglivestock

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.sallyjayz.ranchid.viewmodel.PermissionViewModel
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentTagLivestockStepOneBinding
import com.sallyjayz.ranchid.viewmodel.register.TagLivestockViewModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

//@AndroidEntryPoint
class TagLivestockStepOneFragment : Fragment() {

    private lateinit var cameraExecutor: ExecutorService
    private lateinit var binding: FragmentTagLivestockStepOneBinding
    private lateinit var sharedViewModel: PermissionViewModel
    private val tagSharedViewModel: TagLivestockViewModel by activityViewModels()
//    private lateinit var captureSound: MediaPlayer
//    private val tokenViewModel: TokenViewModel by activityViewModels()
//    private val unusedEnumeratorTagResponseViewModel: UnusedEnumeratorTagResponseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            sharedViewModel = ViewModelProvider(it)[PermissionViewModel::class.java]
        }

        //        No need by default, doing this to clear content before going back to register screen
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                tagSharedViewModel.resetTagLivestock()
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

//        captureSound = MediaPlayer.create(requireContext(), R.raw.camera_shutter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTagLivestockStepOneBinding
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
        binding?.stepOneFragment = this
    }

    private fun startCamera() {
//        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext() as Activity)
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext() as Activity)
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
        }, ContextCompat.getMainExecutor(requireContext() as Activity))

    }

    fun nextButtonClicked() {
        val scannedTagId = binding.tagLivestockTag.text.toString()
        val action = TagLivestockStepOneFragmentDirections
            .actionTagLivestockStepOneFragmentToTagLivestockStepTwoFragment(scannedTagId)
        findNavController().navigate(action)
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

//                val options = BarcodeScannerOptions.Builder()
//                    .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
//                    .build()

                val scanner = BarcodeScanning.getClient()
                scanner.process(inputImage).addOnSuccessListener { barcodes ->
                    if (barcodes.isNotEmpty()) {
                        for (barcode in barcodes) {
//                            val captureSound: MediaPlayer = MediaPlayer.create(requireContext(), R.raw.camera_shutter)
                            binding.tagLivestockTag.text = barcode.rawValue
                            binding.barcodeBoundary.setImageResource(R.drawable.border)
//                            captureSound.start()
//                            unusedEnumeratorTag(barcode.rawValue)


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

    /*private fun unusedEnumeratorTag(tagId: String?) {
        unusedEnumeratorTagResponseViewModel.unusedEnumeratorTagResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {
                    binding.tagAvailability.text = "Code: ${it.code}, ${it.errorMessage}"
                }
                ApiResponse.Loading -> {
                    binding.tagAvailability.text = "Loading"
                }
                is ApiResponse.Success -> {
                    CoroutineScope(Dispatchers.IO).launch {

                        Log.d("Available Tagid", "${it.data.data.id}")
                        Log.d("Available Tagstatus", "${it.data.status}")
//                        Log.d("Available Tagmessage", "${it.data.message}")

                        *//*if (it.data.data == tagId) {
                            binding.tagAvailability.text = "Tag Available, click next to continue"
                            Log.d("Available Tag", "${it.data.data.tag_id}")
                        } else {
                            binding.tagAvailability.text = "Tag Unavailable, Scan another Tag"
                            Log.d("UnAvailable Tag", "${it.data.data.tag_id}")
                        }*//*

                        *//*for (data in it.data.data) {
                            if (tagid.equals(tagId)) {
                                binding.tagAvailability.text = "Tag Available, click next to continue"
                                Log.d("Available Tag", "${it.data.data.tag_id}")
                            } else {
                                binding.tagAvailability.text = "Tag Unavailable, Scan another Tag"
                                Log.d("UnAvailable Tag", "${it.data.data.tag_id}")
                            }
                        }*//*
                    }
                }
            }
        }

        tokenViewModel.username.observe(viewLifecycleOwner) { username ->
            if (username != null && tagId != null) {
                unusedEnumeratorTagResponseViewModel.getUnusedEnumeratorTag(username.lowercase(),
                    tagId,
                    object: CoroutinesErrorHandler {
                        override fun onError(message: String) {
                            binding.tagAvailability.text = "Error! $message"
//                            binding.tagAvailability.text = getString(R.string.offline)
                        }
                    })
            }
        }

    }*/
}