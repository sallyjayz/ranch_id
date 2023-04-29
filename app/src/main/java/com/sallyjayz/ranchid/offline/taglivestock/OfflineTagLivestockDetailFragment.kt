package com.sallyjayz.ranchid.offline.taglivestock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sallyjayz.ranchid.databinding.FragmentOfflineTagLivestockDetailBinding
import com.sallyjayz.ranchid.viewmodel.offline.OfflineTagLivestockViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OfflineTagLivestockDetailFragment : Fragment() {

    private lateinit var binding: FragmentOfflineTagLivestockDetailBinding
    private val args: OfflineTagLivestockDetailFragmentArgs by navArgs()
    private val offlineTagLivestockView: OfflineTagLivestockViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOfflineTagLivestockDetailBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.offlineTagLivestockDetail = this

        tagLivestockDetails()
    }

    private fun tagLivestockDetails() {
        binding.tagid.setText(args.offlineTagLivestock.tagId)
        binding.offlineTaglivestockPassport.text = args.offlineTagLivestock.passportId
        binding.offlineTaglivestockType.text = args.offlineTagLivestock.livestockType
        binding.offlineTaglivestockBreed.text = args.offlineTagLivestock.livestockBreed
        binding.offlineTaglivestockGender.text = args.offlineTagLivestock.gender
        binding.offlineTaglivestockHealth.text = args.offlineTagLivestock.healthStatus
        binding.offlineTaglivestockDob.text = args.offlineTagLivestock.gestationDate
        binding.offlineTaglivestockProdType.text = args.offlineTagLivestock.productionType
    }

    fun updateClicked() {
        CoroutineScope(Dispatchers.IO).launch {
            offlineTagLivestockView.updateOfflineTagLivestockTag(
                args.offlineTagLivestock.id,
                binding.tagid.text.toString(),
                "PENDING"
            )
        }

        val action = OfflineTagLivestockDetailFragmentDirections
            .actionOfflineTagLivestockDetailFragmentToOfflineFragmentTagLivestock()
        findNavController().navigate(action)
    }

}