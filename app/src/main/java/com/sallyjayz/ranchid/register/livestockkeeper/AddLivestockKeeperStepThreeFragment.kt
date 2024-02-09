package com.sallyjayz.ranchid.register.livestockkeeper

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentAddLivestockKeeperStepThreeBinding
import com.sallyjayz.ranchid.model.offline.keeper.OfflineKeeper
import com.sallyjayz.ranchid.model.register.keeper.Keeper
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import com.sallyjayz.ranchid.viewmodel.TokenViewModel
import com.sallyjayz.ranchid.viewmodel.offline.OfflineKeeperViewModel
import com.sallyjayz.ranchid.viewmodel.register.LivestockKeeperViewModel
import com.sallyjayz.ranchid.viewmodel.register.response.KeeperResponseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

@AndroidEntryPoint
class AddLivestockKeeperStepThreeFragment : Fragment() {

    private lateinit var binding: FragmentAddLivestockKeeperStepThreeBinding
    private val sharedViewModel: LivestockKeeperViewModel by activityViewModels()
    private val tokenViewModel: TokenViewModel by activityViewModels()
    private val keeperResponseViewModel: KeeperResponseViewModel by viewModels()
    private val offlineKeeperView: OfflineKeeperViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddLivestockKeeperStepThreeBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            stepThreeModel = sharedViewModel
            //click buttons to be implemented
            stepThreeFragment = this@AddLivestockKeeperStepThreeFragment
        }

        Glide.with(requireContext())
            .load(sharedViewModel.photo.value.toString())
            .override(150, 150)
            .centerCrop()
            .into(binding.keeperImage)

    }

    fun submitClicked() {
        keeperResponseViewModel.addKeeperResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {

                    /*when(it.errorMessage) {
                        it.errorValidation.nin[0] -> ninValidationMessage = "Nin already exist"
                        it.errorValidation.email_address[0] -> emailValidationMessage = "email already exist"
                        it.errorValidation.phone_number[0] -> phoneValidationMessage = "Phone number already exist"
                    }*/

                    /*for (ninError in it.errorValidation.nin) {
//                        message += "$ninError \n\n"
                        ninValidationMessage = "NIN already exist \n\n"
                    }

                    for (phoneError in it.errorValidation.phone_number) {
//                        message += "$ninError \n\n"
                        phoneValidationMessage = "Phone Number already exist \n\n"
                    }

                    for (emailError in it.errorValidation.email_address) {
//                        message += "$emailError \n\n"
                        emailValidationMessage = "Email Address already exist \n"

                    }*/


                    /*for (emailError in it.errorValidation.email_address) {
//                        message += "$emailError \n\n"
                        message += "Email Address already exist \n\n"
                    }

                    for (ninError in it.errorValidation.nin) {
//                        message += "$ninError \n\n"
                        message += "NIN already exist \n\n"
                    }

                    for (phoneError in it.errorValidation.phone_number) {
//                        message += "$ninError \n\n"
                        message += "Phone Number already exist \n"
                    }*/

                    /*if (it.errorValidation.phone_number[0] != null) {
                        phoneValidationMessage = "Phone number already exist \n\n"
                    }

                    if (it.errorValidation.nin[0] != null) {
                        ninValidationMessage = "Nin already exist \n\n"
                    }

                    if (it.errorValidation.email_address[0] != null) {
                        emailValidationMessage = "Email Address already exist \n\n"
                    }

                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(resources.getString(R.string.error))
//                        .setMessage("${it.errorValidation}")
                        .setMessage("$ninValidationMessage $phoneValidationMessage $emailValidationMessage")
                        *//*.setNeutralButton(resources.getString(R.string.cancel)) { dialog, which ->
                            // Respond to neutral button press
                        }
                        .setNegativeButton(resources.getString(R.string.close)) { dialog, _ ->
                            // Respond to negative button press
                            dialog.dismiss()
                        }*//*
                        .setPositiveButton(resources.getString(R.string.ok)) { dialog, _ ->
                            // Respond to positive button press
                            dialog.dismiss()
                        }
                        .show()*/

                    /*val keeperString = "SURNAME: ${sharedViewModel.surname.value.toString()}," +
                            "OTHERNAME: ${sharedViewModel.othername.value.toString()}," +
                            "GENDER: ${sharedViewModel.keeperGender.value.toString()}, " +
                            "DOB: ${sharedViewModel.keeperDateOfBirth.value.toString()}," +
                            "PHONE: ${sharedViewModel.phoneNumber.value.toString()}," +
                            "MARITAL STATUS: ${sharedViewModel.keeperMaritalStatus.value.toString()}, " +
                            "NEXT OF KIN: ${sharedViewModel.keeperNextOfKin.value.toString()} " +
                            "NEXT OF KIN PHONE: ${sharedViewModel.nextOfKinPhoneNumber.value.toString()}, " +
                            "EMAIL: ${sharedViewModel.email.value.toString()}, " +
                            "NIN: ${sharedViewModel.nin.value.toString()}, " +
                            "STATEID: ${sharedViewModel.keeperStateId.value.toString()}, " +
                            "LGAID: ${sharedViewModel.keeperLgaId.value.toString()}, " +
                            "WARD: ${""}," +
                            "DOCUMENT TYPE: ${sharedViewModel.documentType.value.toString()}, " +
                            "DOCUMENT NUMBER: ${sharedViewModel.documentNumber.value.toString()}, " +
                            "LOCATION: ${sharedViewModel.farmLocationId.value.toString()}, " +
                            "ADDRESS: ${sharedViewModel.address.value.toString()}, " +
                            "OTHER LOCATION: ${"N/A"}, " +
                            "PHOTO: ${sharedViewModel.photo.value.toString()}," +
                            "TIMESTAMP: ${ ""}, " +
                            "CAPTURED BY: ${tokenViewModel.username.value.toString()}"

                    val fileOutputStream: FileOutputStream

                    try {
                        fileOutputStream = activity?.openFileOutput("Keeper.txt",MODE_PRIVATE)!!
                        fileOutputStream.write(keeperString.toByteArray())
                    } catch (e: FileNotFoundException){
                        e.printStackTrace()
                    }catch (e: NumberFormatException){
                        e.printStackTrace()
                    }catch (e: IOException){
                        e.printStackTrace()
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                    Toast.makeText(requireContext(),"data save", Toast.LENGTH_LONG).show()*/

                    binding.stepThreeProgress.isVisible = false
                    val action = AddLivestockKeeperStepThreeFragmentDirections
                        .actionAddLivestockKeeperStepThreeFragmentToAddLivestockKeeperUnsuccessfulFragment()
                    findNavController().navigate(action)
                }
                ApiResponse.Loading -> {
//                        binding.errorTv.text = "Loading"
                    binding.stepThreeProgress.isVisible = true
                    binding.stepThreeKeeperSubmitButton.isClickable = false
                    binding.stepThreeKeeperSubmitButton.alpha = 0.5F
                }
                is ApiResponse.Success -> {
//                        binding.errorTv.text = "${it.data.data.id}"
                    val action = AddLivestockKeeperStepThreeFragmentDirections
                        .actionAddLivestockKeeperStepThreeFragmentToAddLivestockKeeperSuccessfulFragment()
                    findNavController().navigate(action)
                }
            }
        }

        keeperResponseViewModel.addKeeper(
            Keeper(
                sharedViewModel.surname.value.toString(),
                sharedViewModel.othername.value.toString(),
                sharedViewModel.keeperGender.value.toString(),
                sharedViewModel.keeperDateOfBirth.value.toString(),
                sharedViewModel.phoneNumber.value.toString(),
                sharedViewModel.keeperMaritalStatus.value.toString(),
                sharedViewModel.keeperNextOfKin.value.toString(),
                sharedViewModel.nextOfKinPhoneNumber.value.toString(),
                sharedViewModel.email.value.toString(),
                sharedViewModel.nin.value.toString(),
                sharedViewModel.keeperStateId.value.toString(),
                sharedViewModel.keeperLgaId.value.toString(),
                "",
                sharedViewModel.documentType.value.toString(),
                sharedViewModel.documentNumber.value.toString(),
                sharedViewModel.farmLocationId.value.toString(),
                sharedViewModel.address.value.toString(),
                "N/A",
                sharedViewModel.photo.value.toString(),
                "",
                tokenViewModel.username.value.toString()
            ),
            object: CoroutinesErrorHandler {
                override fun onError(message: String) {
//                    binding.errorTv.text = "Error! $message"

                    binding.errorTv.text = getString(R.string.offline)

                    CoroutineScope(Dispatchers.IO).launch {
                        val offlineKeeper = OfflineKeeper(
                            0,
                            sharedViewModel.surname.value.toString(),
                            sharedViewModel.othername.value.toString(),
                            sharedViewModel.keeperGender.value.toString(),
                            sharedViewModel.keeperDateOfBirth.value.toString(),
                            sharedViewModel.phoneNumber.value.toString(),
                            sharedViewModel.keeperMaritalStatus.value.toString(),
                            sharedViewModel.keeperNextOfKin.value.toString(),
                            sharedViewModel.nextOfKinPhoneNumber.value.toString(),
                            sharedViewModel.email.value.toString(),
                            sharedViewModel.nin.value.toString(),
                            sharedViewModel.keeperStateId.value.toString(),
                            sharedViewModel.keeperLgaId.value.toString(),
                            "",
                            sharedViewModel.documentType.value.toString(),
                            sharedViewModel.documentNumber.value.toString(),
                            sharedViewModel.farmLocationId.value.toString(),
                            sharedViewModel.address.value.toString(),
                            "N/A",
                            sharedViewModel.photo.value.toString(),
                            "",
                            tokenViewModel.username.value.toString(),
                            "PENDING"
                        )
                        offlineKeeperView.insertOfflineKeeper(offlineKeeper)
                    }
                    val action = AddLivestockKeeperStepThreeFragmentDirections
                        .actionAddLivestockKeeperStepThreeFragmentToOfflineKeeperSuccess()
                    findNavController().navigate(action)

//                    Log.d("Keeper save", "$message")

                    /*if (message == "Validation Failed") {

                        val action = AddLivestockKeeperStepThreeFragmentDirections
                            .actionAddLivestockKeeperStepThreeFragmentToAddLivestockKeeperUnsuccessfulFragment(
                                emailValidationMessage,
                                phoneValidationMessage,
                                ninValidationMessage
                            )
                        findNavController().navigate(action)

                    }*/

                }
            }
        )
    }
}