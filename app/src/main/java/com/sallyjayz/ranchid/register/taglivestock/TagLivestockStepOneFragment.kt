package com.sallyjayz.ranchid.register.taglivestock

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.sallyjayz.ranchid.viewmodel.PermissionViewModel
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentTagLivestockStepOneBinding
import com.sallyjayz.ranchid.viewmodel.register.TagLivestockViewModel
import com.sallyjayz.ranchid.viewmodel.register.UnusedEnumeratorTagViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class TagLivestockStepOneFragment : Fragment() {

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private val cameraExecutor = Executors.newSingleThreadExecutor()
    private lateinit var binding: FragmentTagLivestockStepOneBinding
    private lateinit var sharedViewModel: PermissionViewModel
    private val tagSharedViewModel: TagLivestockViewModel by activityViewModels()
    private val unusedEnumeratorTagViewModel: UnusedEnumeratorTagViewModel by viewModels()


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
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
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

            cameraProvider?.bindToLifecycle(
                requireActivity() as LifecycleOwner,
                cameraSelector,
                preview,
                imageAnalyzer
            )
        }, ContextCompat.getMainExecutor(requireContext()))
//        }, ContextCompat.getMainExecutor(requireContext() as Activity))

    }

    fun nextButtonClicked() {
        val scannedTagId = binding.tagLivestockTag.text.toString()
        val tagType = binding.tagType.text.toString()
        val action = TagLivestockStepOneFragmentDirections
            .actionTagLivestockStepOneFragmentToTagLivestockStepTwoFragment(scannedTagId, tagType)
        findNavController().navigate(action)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        if (::cameraProviderFuture.isInitialized) {
            cameraProviderFuture.get().unbindAll()
        }
        cameraExecutor.shutdown()
    }



    inner class BarcodeAnalyzer : ImageAnalysis.Analyzer {

        @SuppressLint("UnsafeOptInUsageError")
        override fun analyze(imageProxy: ImageProxy) {

            imageProxy.image?.let { image ->

                val inputImage = InputImage.fromMediaImage(
                    image,
                    imageProxy.imageInfo.rotationDegrees
                )



//                val options = BarcodeScannerOptions.Builder()
//                    .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
//                    .build()

                val scanner = BarcodeScanning.getClient()
                scanner.process(inputImage).addOnCompleteListener{ task ->

                    imageProxy.close()

                    if (task.isSuccessful) {
                        val barcode = task.result.getOrNull(0)
                        barcode?.rawValue?.let { barcodeValue ->
                            binding.tagLivestockTag.text = barcodeValue
                            binding.barcodeBoundary.setImageResource(R.drawable.border)
                            unusedEnumeratorTag(barcodeValue)
//                            unusedEnumeratorTag("NGFC123456779")
                        }
                    } else {
                        binding.tagLivestockTag.text = getString(R.string.try_again)
                    }
                }
            }
        }
    }

    private fun unusedEnumeratorTag(tagId: String) {
        Log.d("Available Tag1", "$tagId")

        unusedEnumeratorTagViewModel.getSelectedUnusedEnumeratorTag(tagId).observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.tag_id == tagId && it.status == "Available") {
                    tagSharedViewModel.databaseId = it.id
                    binding.tagAvailability.text = "Available Tag, click next to continue"
                    binding.tagType.text = "${it.tag_type}"
                    binding.tagLivestockNextButton.isEnabled = true
                    binding.tagLivestockNextButton.alpha = 1.0F
                    Log.d("Available Tag", "${tagSharedViewModel.databaseId}")
                }
            } else {
                binding.tagAvailability.text = "Tag already USED/UNASSIGNED/INVALID, Scan another Tag"
                binding.tagLivestockNextButton.isEnabled = false
                binding.tagLivestockNextButton.alpha = 0.5F
            }
        }

        /*if (unusedEnumeratorTagViewModel.getSelectedUnusedEnumeratorTag(tagId).value == null){
            binding.tagAvailability.text = "Tag Unavailable, Scan another Tag"
            binding.tagLivestockNextButton.isClickable = false
            binding.tagLivestockNextButton.alpha = 0.5F
            Log.d("Available Tag2", "${unusedEnumeratorTagViewModel.getSelectedUnusedEnumeratorTag(tagId).value}")
        } else if(unusedEnumeratorTagViewModel.getSelectedUnusedEnumeratorTag(tagId).value != null){
            unusedEnumeratorTagViewModel.getSelectedUnusedEnumeratorTag(tagId).observe(viewLifecycleOwner) {
                if (it.tag_id == tagId) {
                    binding.tagAvailability.text = "Tag Available, click next to continue"
                    binding.tagLivestockNextButton.isClickable = true
                    binding.tagLivestockNextButton.alpha = 1.0F
                    Log.d("Available Tag", "${it.tag_id}")
                }
            }
        }*/

        /*CoroutineScope(Dispatchers.IO).launch {
            taggingId = unusedEnumeratorTagViewModel.getTagRowIfExist(tagId)
            Log.d("Available Tag2", "${unusedEnumeratorTagViewModel.getTagRowIfExist(tagId)}")
        }

        if (!taggingId && unusedEnumeratorTagViewModel.getSelectedUnusedEnumeratorTag(tagId).value == null){
            binding.tagAvailability.text = "Unavailable Tag, Scan another Tag"
            binding.tagLivestockNextButton.isEnabled= false
            binding.tagLivestockNextButton.alpha = 0.5F
        }

        if (taggingId) {
            unusedEnumeratorTagViewModel.getSelectedUnusedEnumeratorTag(tagId).observe(viewLifecycleOwner) {
                if (it != null) {
                    if (it.tag_id == tagId && it.status == "Available") {
                        tagSharedViewModel.databaseId = it.id
                        binding.tagAvailability.text = "Available Tag, click next to continue"
                        binding.tagLivestockNextButton.isEnabled = true
                        binding.tagLivestockNextButton.alpha = 1.0F
                        Log.d("Available Tag", "${tagSharedViewModel.databaseId}")
                    }
                }
            }
        }*/

            /*unusedEnumeratorTagViewModel.getSelectedUnusedEnumeratorTag(tagId).observe(viewLifecycleOwner) {
//                taggingId = it.tag_id
                if (it.tag_id == tagId && it.status == "Available") {
                    binding.tagAvailability.text = "Tag Available, click next to continue"
                    binding.tagLivestockNextButton.isClickable = true
                    binding.tagLivestockNextButton.alpha = 1.0F
                    Log.d("Available Tag", "${it.tag_id}, ${it.status}")
                }

                if (it.tag_id != tagId || it.tag_id == tagId && it.status == "Unavailable") {
                    binding.tagAvailability.text = "Tag Unavailable, Scan another Tag"
                    binding.tagLivestockNextButton.isClickable = false
                    binding.tagLivestockNextButton.alpha = 0.5F
                    Log.d("UnAvailable Tag", "${it.tag_id}, ${tagId}, ${it.status}")
                }
            }*/

        /*if (taggingId == tagId) {
            binding.tagAvailability.text = "Tag Available, click next to continue"
            binding.tagLivestockNextButton.isClickable = true
            binding.tagLivestockNextButton.alpha = 1.0F
            Log.d("Available Tag", "${taggingId}")
        } else {
            binding.tagAvailability.text = "Tag Unavailable, Scan another Tag"
            binding.tagLivestockNextButton.isClickable = false
            binding.tagLivestockNextButton.alpha = 0.5F
            Log.d("UnAvailable Tag", "${taggingId}")
        }*/
    }

    /*private fun unusedEnumeratorTag(tagId: String?) {
        unusedEnumeratorTagResponseViewModel.unusedEnumeratorTagResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {
                    binding.tagAvailability.text = "Code: ${it.code}, ${it.errorMessage}"
                    Log.d("Failed Tagstatus", "${it.code}, ${it.errorMessage}")

                }
                ApiResponse.Loading -> {
                    binding.tagAvailability.text = "Loading"
                }
                is ApiResponse.Success -> {
                    CoroutineScope(Dispatchers.IO).launch {

                        Log.d("Available Tagid", "${it.data.data.tag_id}")
                        Log.d("Available Tagstatus", "${it.data.status}")
//                        Log.d("Available Tagmessage", "${it.data.message}")

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
                    if (it.data.data.tag_id == tagId) {
                        binding.tagAvailability.text = "Tag Available, click next to continue"
                        binding.tagLivestockNextButton.isClickable = true
                        binding.tagLivestockNextButton.alpha = 1.0F
                        Log.d("Available Tag", "${it.data.data.tag_id}")
                    } else {
                        binding.tagAvailability.text = "Tag Unavailable, Scan another Tag"
                        binding.tagLivestockNextButton.isClickable = false
                        binding.tagLivestockNextButton.alpha = 0.5F
                        Log.d("UnAvailable Tag", "${it.data.data.tag_id}")
                    }
                }
            }
        }

        tokenViewModel.username.observe(viewLifecycleOwner) { username ->
            if (username != null && tagId != null) {
                unusedEnumeratorTagResponseViewModel.getUnusedEnumeratorTagById(username.lowercase(),
                    tagId,
                    object: CoroutinesErrorHandler {
                        override fun onError(message: String) {
//                            binding.tagAvailability.text = "Error! $message"
//                            binding.tagAvailability.text = getString(R.string.offline)
                            binding.tagAvailability.text = "Unassigned Tag"
                            binding.tagLivestockNextButton.isClickable = false
                            binding.tagLivestockNextButton.alpha = 0.5F
                        }
                    })
            }
        }

    }*/
}