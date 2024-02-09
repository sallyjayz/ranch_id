package com.sallyjayz.ranchid.register.packinglist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.sallyjayz.ranchid.databinding.FragmentAddPackListScanDetailBinding

class AddPackListScanDetailFragment : Fragment() {

    private lateinit var binding: FragmentAddPackListScanDetailBinding
    private val args: AddPackListScanDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddPackListScanDetailBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.packingListDetail = this@AddPackListScanDetailFragment

        livestockDetails()
    }

    private fun livestockDetails() {
        binding.scanTagDetail.text = "${args.scanLivestock.tag_id} Details"
        binding.packingDetailScan.text = args.scanLivestock.tag_id
        binding.packingDetailType.text = args.scanLivestock.livestock_type
        binding.packingDetailBreed.text = args.scanLivestock.livestock_breed
        binding.packingDetailGender.text = args.scanLivestock.gender
        binding.packingDetailDob.text = args.scanLivestock.gestation_date
    }

}