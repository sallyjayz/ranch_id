package com.sallyjayz.ranchid.generalinformation

import android.content.*
import android.content.Context.CLIPBOARD_SERVICE
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textview.MaterialTextView
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentGeneralInformationDetailBinding
import com.sallyjayz.ranchid.glide.GlideApp
import com.sallyjayz.ranchid.viewmodel.register.FarmLocationViewModel
import com.sallyjayz.ranchid.viewmodel.register.LgaViewModel
import com.sallyjayz.ranchid.viewmodel.register.StateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GeneralInformationDetailFragment : Fragment() {

    private lateinit var binding: FragmentGeneralInformationDetailBinding
    private val stateViewModel: StateViewModel by viewModels()
    private val lgaViewModel: LgaViewModel by viewModels()
    private val farmLocationViewModel: FarmLocationViewModel by viewModels()
    private val args: GeneralInformationDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGeneralInformationDetailBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.generalInfoDetailFragment = this
        generateInformation()
    }

    private fun generateInformation() {
        binding.ownerKeeperLabel.text = args.custodianType.uppercase()
        binding.infoFirstName.text = args.custodian.other_names
        binding.infoLastName.text = args.custodian.surname
        binding.infoGender.text = args.custodian.gender
        binding.infoDob.text = args.custodian.dob
        binding.infoPhoneno.text = args.custodian.phone_number
        binding.infoAddress.text = args.custodian.address
        binding.infoMaritalStatus.text = args.custodian.marital_status
        binding.infoKin.text = args.custodian.next_of_kin
        binding.infoKinPhoneno.text = args.custodian.next_of_kin_number
        binding.infoEmail.text = args.custodian.email_address
        binding.infoNin.text = args.custodian.nin
//        binding.infoState.text = args.custodian.state
        stateViewModel.getStateId(args.custodian.state.toInt()).observe(viewLifecycleOwner) {
            binding.infoState.text = it.name
        }
//        binding.infoLga.text = args.custodian.lga
        lgaViewModel.getLgaAndStateId(args.custodian.lga.toInt(), args.custodian.state.toInt()).observe(viewLifecycleOwner) {
            binding.infoLga.text = it.name
        }
        binding.infoWard.text = args.custodian.ward
        binding.infoDoctype.text = args.custodian.id_doc
        binding.infoDocno.text = args.custodian.prof_id_doc
//        binding.infoLocation.text = args.custodian.location
        farmLocationViewModel.getLocationId(args.custodian.location.toInt()).observe(viewLifecycleOwner) {
            binding.infoLocation.text = it.location_name
        }
        binding.infoOtherLocation.text = args.custodian.other_location
        GlideApp.with(requireContext())
            .load(args.custodian.passport_photo)
            .override(150, 150)
            .centerCrop()
            .into(binding.passportImage)
//        binding.passportImage = args.custodian.passport_photo
//        work on image, state and lga

    }

    fun generateFormClicked() {
        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
        val customAlertDialogView:View = LayoutInflater.from(requireContext())
            .inflate(R.layout.generate_form_custom_dialog, null, false)

        val linkText:MaterialTextView = customAlertDialogView.findViewById(R.id.general_form_link)
        val copyUrlBtn: MaterialButton = customAlertDialogView.findViewById(R.id.copy_url_button)
        val downloadBtn: MaterialButton = customAlertDialogView.findViewById(R.id.download_form_button)

        val urlLink = "https://ranch-admin.netlify.app/admin/download/${args.refID.lowercase()} has been generated"

        linkText.text = urlLink

        copyUrlBtn.setOnClickListener {
            val clipboard = requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("url", urlLink)
            clipboard.setPrimaryClip(clip)
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2)
                Toast.makeText(requireContext(), "Copied", Toast.LENGTH_SHORT).show()

        }

        downloadBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlLink))
            startActivity(intent)
        }


        materialAlertDialogBuilder.setView(customAlertDialogView)
            .show()
    }

}