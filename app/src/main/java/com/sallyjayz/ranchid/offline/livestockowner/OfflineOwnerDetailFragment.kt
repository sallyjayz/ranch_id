package com.sallyjayz.ranchid.offline.livestockowner

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentOfflineOwnerDetailBinding
import com.sallyjayz.ranchid.viewmodel.offline.OfflineOwnerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OfflineOwnerDetailFragment : Fragment() {

    private lateinit var binding: FragmentOfflineOwnerDetailBinding
    private val args: OfflineOwnerDetailFragmentArgs by navArgs()
    private val offlineOwnerView: OfflineOwnerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOfflineOwnerDetailBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.offlineOwnerDetail = this@OfflineOwnerDetailFragment

        ownerDetails()
        emailFocusListener()
        phoneFocusListener()
        ninFocusListener()
    }

    private fun ownerDetails() {
        binding.offlineOwnerType.text = args.offlineOwner.ownershipType
        binding.offlineOwnerSurname.text = args.offlineOwner.surname
        binding.offlineOwnerOthername.text = args.offlineOwner.otherNames
        binding.offlineOwnerGender.text = args.offlineOwner.gender
        binding.offlineOwnerMarital.text = args.offlineOwner.marital_status
        binding.offlineOwnerDob.text = args.offlineOwner.dob
        binding.phoneNumber.setText(args.offlineOwner.phoneNumber)
        binding.ninNumber.setText(args.offlineOwner.nin)
        binding.email.setText(args.offlineOwner.emailAddress)
        binding.offlineOwnerAddress.text = args.offlineOwner.address
        binding.offlineOwnerKin.text = args.offlineOwner.nextOfKin
        binding.kinPhoneNumber.setText(args.offlineOwner.nextOfKinPhoneNumber)
    }

    fun updateClicked() {
        binding.offlineOwnerEmail.helperText = validEmail()
        binding.offlineOwnerPhoneno.helperText = validPhone()
        binding.offlineOwnerNin.helperText = validNin()

        val validEmail = binding.offlineOwnerEmail.helperText == null
        val validPhone = binding.offlineOwnerPhoneno.helperText == null
        val validNin = binding.offlineOwnerNin.helperText == null

        if (validEmail && validPhone && validNin) {
            CoroutineScope(Dispatchers.IO).launch {
                offlineOwnerView.updateOfflineOwnerData(
                    args.offlineOwner.id,
                    binding.phoneNumber.text.toString(),
                    binding.ninNumber.text.toString(),
                    binding.email.text.toString(),
                    binding.kinPhoneNumber.text.toString(),
                    "PENDING"
                )
            }

            val action = OfflineOwnerDetailFragmentDirections
                .actionOfflineOwnerDetailFragmentToOfflineOwnerFragment()
            findNavController().navigate(action)
        } else {
            binding.errorTv.text = getString(R.string.phone_nin_required)
        }
    }

    private fun emailFocusListener() {
        binding.email.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.offlineOwnerEmail.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String? {
        val emailText = binding.email.text.toString()
        /*if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return "Invalid Email Address"
        }*/

        if (emailText.isBlank()) {
            return ""
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return "Invalid Email Address"
        }
        return null
    }

    private fun phoneFocusListener() {
        binding.phoneNumber.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.offlineOwnerPhoneno.helperText = validPhone()
            }
        }
    }

    private fun validPhone(): String? {
        val phoneText = binding.phoneNumber.text.toString()

        if (phoneText.length != 11) {
            return "Must be 11 Digits"
        }

        if (!phoneText.matches(".*[0-9].*".toRegex())) {
            return "Must be Digits"
        }
        return null
    }

    private fun ninFocusListener() {
        binding.ninNumber.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.offlineOwnerNin.helperText = validNin()
            }
        }
    }

    private fun validNin(): String? {
        val ninText = binding.ninNumber.text.toString()

        if (ninText.length < 11) {
            return "Minimum of 11 digits"
        }

        if (!ninText.matches(".*[0-9].*".toRegex())) {
            return "Must be Digits"
        }
        return null
    }

}