package com.sallyjayz.ranchid.register.packinglist

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textview.MaterialTextView
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentAddPackingListStepTwoBinding
import com.sallyjayz.ranchid.model.register.packinglist.scannedlivestock.ScanLivestock
import com.sallyjayz.ranchid.recyclerview.packinglist.PackingListRecyclerviewAdapter
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.*
import com.sallyjayz.ranchid.viewmodel.packinglist.LivestockDataViewModel
import com.sallyjayz.ranchid.viewmodel.packinglist.response.ScanPackingListResponseViewModel
import com.sallyjayz.ranchid.viewmodel.register.UsedEnumeratorTagViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class AddPackingListStepTwoFragment : Fragment() {

    private lateinit var binding: FragmentAddPackingListStepTwoBinding
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var sharedViewModel: PermissionViewModel
    private val scanPackingListResponseViewModel: ScanPackingListResponseViewModel by viewModels()
    private val networkStatusViewModel: NetworkStatusViewModel by activityViewModels()
    private var scannedTag: String? = null
    private lateinit var packingListRecyclerviewAdapter: PackingListRecyclerviewAdapter
    private val usedEnumeratorTagViewModel: UsedEnumeratorTagViewModel by viewModels()
    private val livestockDataViewModel:LivestockDataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            sharedViewModel = ViewModelProvider(it)[PermissionViewModel::class.java]
        }

        //        No need by default, doing this to clear content before going back to register screen
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
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
        binding = FragmentAddPackingListStepTwoBinding
            .inflate(layoutInflater, container, false)
        cameraExecutor = Executors.newSingleThreadExecutor()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.isPermissionGranted.observe(viewLifecycleOwner) {
            if (it) startCamera()
        }
        binding?.stepTwoFragment = this

        networkStatusViewModel.state.observe(viewLifecycleOwner) {
            when(it) {
                MyState.Fetched -> {
                    binding.offlineTv.isVisible = false
                    binding.packingNewscanBtn.isEnabled = true
                    binding.packingNewscanBtn.alpha = 1.0F
                    binding.packingDoneBtn.isEnabled = true
                    binding.packingDoneBtn.alpha = 1.0F
                }
                MyState.Error -> {
                    binding.offlineTv.isVisible = true
                    binding.packingNewscanBtn.isEnabled = false
                    binding.packingNewscanBtn.alpha = 0.5F
                    binding.packingDoneBtn.isEnabled = false
                    binding.packingDoneBtn.alpha = 0.5F
                }
            }
        }


        initRecyclerView()
        initViewModel()
//        swipeToDeleteLivestock()
        livestockDetail()
    }

    private fun initRecyclerView() {

        binding.packingRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            packingListRecyclerviewAdapter = PackingListRecyclerviewAdapter(requireContext())
            adapter = packingListRecyclerviewAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                packingListRecyclerviewAdapter.loadStateFlow.collect {
                    val emptyDataObserver = EmptyData(binding.packingRecyclerview, binding.emptyDataPresent)
                    packingListRecyclerviewAdapter.registerAdapterDataObserver(emptyDataObserver)
                }
            }
        }

    }

    private fun initViewModel() {
        CoroutineScope(Dispatchers.IO).launch {
            livestockDataViewModel.readLivestockData().collectLatest {
                packingListRecyclerviewAdapter.submitData(it)
            }
        }

    }

    /*private fun swipeToDeleteLivestock() {
        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deleteItem: ScanLivestock =
                val position = viewHolder.bindingAdapterPosition
                packingListRecyclerviewAdapter.deleteItem(position)
            }
        }).attachToRecyclerView(binding.packingRecyclerview)
    }*/


    /*private fun getUsedEnumeratorTag(){
        usedEnumeratorTagResponse.allUsedEnumeratorTagResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {

                }
                ApiResponse.Loading -> {

                }
                is ApiResponse.Success -> {
                    for (data in it.data.usedEnumeratorTag){
//                        Log.d("Fragment UsedTags", "UsedTag: ${data.tag_id}")
                        if (data.tag_id.contains(scannedTag.toString())) {
                            availableUsedTag = scannedTag.toString()
                        } else {
//                          Tag not found
                            Log.d("Fragment getusedtag", "Tag not found ${data.tag_id}, ${scannedTag}")
                        }
                    }
                }
            }
        }

        tokenViewModel.username.observe(viewLifecycleOwner) { username ->
            if (username != null) {
                usedEnumeratorTagResponse.getAllUsedEnumeratorTag(username, object:
                    CoroutinesErrorHandler {
                    override fun onError(message: String) {
                        Log.d("UsedTags Error", message)
                    }

                })
            }
        }
    }*/

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getUsedEnumeratorTag() {
        usedEnumeratorTagViewModel.getSelectedUsedEnumeratorTag(scannedTag.toString()).observe(viewLifecycleOwner) { usedEnumeratorTag ->

                if (usedEnumeratorTag?.tag_id == scannedTag.toString()) {
                    livestockDataViewModel.getSelectedPackingList(usedEnumeratorTag.tag_id).observe(viewLifecycleOwner) { packingList ->
                        if (packingList?.tags?.contains(scannedTag.toString()) == true){
                            val snackBarOne = Snackbar.make(binding.coordinator,
                                packingList.status, Snackbar.LENGTH_SHORT)
                            val textViewOne:MaterialTextView = snackBarOne.view
                                .findViewById(com.google.android.material.R.id.snackbar_text)
                            textViewOne.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_info, 0, 0, 0)
                            textViewOne.compoundDrawablePadding = (resources.getDimensionPixelOffset(R.dimen.eight_dp))
                            snackBarOne.anchorView = binding.packingLivestockScanimage
                            snackBarOne.setTextColor(resources.getColor(R.color.snackbar_text, null))
                            snackBarOne.setBackgroundTint(resources.getColor(R.color.snackbar_background, null))
                            snackBarOne.show()
                        } else {
                            scanPackingListResponseViewModel.scanTagDetailResponse.observe(viewLifecycleOwner) { livestock ->
                                when(livestock) {
                                    is ApiResponse.Failure -> {
                                        binding.errorTv.text = "Retry or Use a Different Tag"
                                        binding.errorTv.isVisible = true
                                        binding.offlineTv.isVisible = false
                                        binding.packingProgress.isIndeterminate = false
                                    }
                                    ApiResponse.Loading -> {
                                        binding.packingProgress.isIndeterminate = true
                                        binding.errorTv.isVisible = false
                                        binding.offlineTv.isVisible = false
                                    }
                                    is ApiResponse.Success -> {
                                        binding.packingProgress.isIndeterminate = false
                                        binding.errorTv.isVisible = false
                                        binding.offlineTv.isVisible = false
                                        val snackBarTwo = Snackbar.make(binding.coordinator,
                                            "Livestock Added Successfully", Snackbar.LENGTH_SHORT)
                                        val textViewTwo: MaterialTextView = snackBarTwo.view
                                            .findViewById(com.google.android.material.R.id.snackbar_text)
                                        textViewTwo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_info, 0, 0, 0)
                                        textViewTwo.compoundDrawablePadding = (resources.getDimensionPixelOffset(R.dimen.eight_dp))
                                        snackBarTwo.anchorView = binding.packingLivestockScanimage
                                        snackBarTwo.setTextColor(resources.getColor(R.color.snackbar_text, null))
                                        snackBarTwo.setBackgroundTint(resources.getColor(R.color.snackbar_background, null))
                                        snackBarTwo.show()

                                        CoroutineScope(Dispatchers.IO).launch {
                                            livestockDataViewModel.insertLivestockData(ScanLivestock(
                                                livestock.data.scanLivestock.tag_id,
                                                livestock.data.scanLivestock.livestock_type,
                                                livestock.data.scanLivestock.livestock_breed,
                                                livestock.data.scanLivestock.gender,
                                                livestock.data.scanLivestock.gestation_date
                                            ))
                                        }
//                                                        Toast.makeText(requireContext(), "Livestock Added Successfully", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }

                            scanPackingListResponseViewModel.scanTagDetail(scannedTag.toString(), object:
                                CoroutinesErrorHandler {
                                override fun onError(message: String) {
                                    binding.offlineTv.isVisible = true
                                    binding.errorTv.isVisible = false
                                }

                            })
                        }
                    }
                }else {
//                    Toast.makeText(requireContext(), "Tag Not Found", Toast.LENGTH_LONG).show()
                    val snackBarThree = Snackbar.make(binding.coordinator,
                        "Tag not USED by Enumerator", Snackbar.LENGTH_SHORT)
                    val textViewThree:MaterialTextView = snackBarThree.view
                        .findViewById(com.google.android.material.R.id.snackbar_text)
                    textViewThree.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_info, 0, 0, 0)
                    textViewThree.compoundDrawablePadding = (resources.getDimensionPixelOffset(R.dimen.eight_dp))
                    snackBarThree.anchorView = binding.packingLivestockScanimage
                    snackBarThree.setTextColor(resources.getColor(R.color.snackbar_text, null))
                    snackBarThree.setBackgroundTint(resources.getColor(R.color.snackbar_background, null))
                    snackBarThree.show()
                }
        }


    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun addNewLivestockClicked() {
        getUsedEnumeratorTag()
    }

    private fun livestockDetail() {
        packingListRecyclerviewAdapter.setOnClickListener(object :
            PackingListRecyclerviewAdapter.OnClickListener{
            override fun onClickLivestock(position: Int, scanLivestock: ScanLivestock) {
                val livestockData = ScanLivestock(
                    scanLivestock.tag_id,
                    scanLivestock.livestock_type,
                    scanLivestock.livestock_breed,
                    scanLivestock.gender,
                    scanLivestock.gestation_date
                )
                val action = AddPackingListStepTwoFragmentDirections
                    .actionAddPackingListStepTwoFragmentToAddPackListScanDetailFragment(livestockData)
                findNavController().navigate(action)
            }

        })
    }

    fun doneButtonClicked() {

        if(packingListRecyclerviewAdapter.itemCount == 0) {
            Toast.makeText(requireContext(), "Add New Livestock", Toast.LENGTH_LONG).show()
        } else {
            val action = AddPackingListStepTwoFragmentDirections
                .actionAddPackingListStepTwoFragmentToAddPackingListStepThreeFragment()
            findNavController().navigate(action)
        }


    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireActivity())
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.packingLivestockScanimage.surfaceProvider)
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
                            binding.packingLivestockTag.text = barcode.rawValue
                            binding.barcodeBoundary.setImageResource(R.drawable.border)
                            scannedTag = barcode.rawValue.toString()
                        }
                    }
                }.addOnFailureListener {
                    binding.packingLivestockTag.text = getString(R.string.try_again)
                }.addOnCompleteListener{
                    image.close()
                }
            }
        }
    }
}