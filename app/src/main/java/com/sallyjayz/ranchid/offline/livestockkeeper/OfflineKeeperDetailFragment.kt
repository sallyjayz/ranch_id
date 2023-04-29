package com.sallyjayz.ranchid.offline.livestockkeeper

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
import com.sallyjayz.ranchid.databinding.FragmentOfflineKeeperDetailBinding
import com.sallyjayz.ranchid.viewmodel.offline.OfflineKeeperViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OfflineKeeperDetailFragment : Fragment() {

    private lateinit var binding: FragmentOfflineKeeperDetailBinding
    private val args: OfflineKeeperDetailFragmentArgs by navArgs()
    private val offlineKeeperView: OfflineKeeperViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOfflineKeeperDetailBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.offlineKeeperDetail = this@OfflineKeeperDetailFragment

        keeperDetails()
        emailFocusListener()
        phoneFocusListener()
        ninFocusListener()
    }

    private fun keeperDetails() {
        binding.offlineKeeperSurname.text = args.offlineKeeperDetails.surname
        binding.offlineKeeperOthername.text = args.offlineKeeperDetails.otherNames
        binding.offlineKeeperGender.text = args.offlineKeeperDetails.gender
        binding.offlineKeeperMarital.text = args.offlineKeeperDetails.marital_status
        binding.offlineKeeperDob.text = args.offlineKeeperDetails.dob
        binding.phoneNumber.setText(args.offlineKeeperDetails.phoneNumber)
        binding.ninNumber.setText(args.offlineKeeperDetails.nin)
        binding.email.setText(args.offlineKeeperDetails.emailAddress)
        binding.offlineKeeperAddress.text = args.offlineKeeperDetails.address
        /*binding.offlineKeeperState.text = args.offlineKeeperDetails.state
        binding.offlineKeeperLga.text = args.offlineKeeperDetails.lga*/
        binding.offlineKeeperKin.text = args.offlineKeeperDetails.nextOfKin
        binding.kinPhoneNumber.setText(args.offlineKeeperDetails.nextOfKinPhoneNumber)
        /*binding.offlineKeeperDocType.text = args.offlineKeeperDetails.documentID
        binding.offlineKeeperDocNo.text = args.offlineKeeperDetails.documentNumber
        binding.offlineKeeperLocation.text = args.offlineKeeperDetails.location*/
    }

    fun updateClicked() {

        binding.offlineKeeperEmail.helperText = validEmail()
        binding.offlineKeeperPhoneno.helperText = validPhone()
        binding.offlineKeeperNin.helperText = validNin()

        val validEmail = binding.offlineKeeperEmail.helperText == null
        val validPhone = binding.offlineKeeperPhoneno.helperText == null
        val validNin = binding.offlineKeeperNin.helperText == null

        if (validEmail && validPhone && validNin) {
            CoroutineScope(Dispatchers.IO).launch {
                offlineKeeperView.updateOfflineKeeperData(
                    args.offlineKeeperDetails.id,
                    binding.phoneNumber.text.toString(),
                    binding.ninNumber.text.toString(),
                    binding.email.text.toString(),
                    binding.kinPhoneNumber.text.toString(),
                    "PENDING"
                )
            }
            val action = OfflineKeeperDetailFragmentDirections
                .actionOfflineKeeperDetailFragmentToOfflineFragmentKeeper()
            findNavController().navigate(action)
        } else {
            binding.errorTv.text = getString(R.string.phone_nin_required)
        }
    }

    private fun emailFocusListener() {
        binding.email.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.offlineKeeperEmail.helperText = validEmail()
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
            return getString(R.string.invalid_email)
        }
        return null
    }

    private fun phoneFocusListener() {
        binding.phoneNumber.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.offlineKeeperPhoneno.helperText = validPhone()
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
                binding.offlineKeeperNin.helperText = validNin()
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