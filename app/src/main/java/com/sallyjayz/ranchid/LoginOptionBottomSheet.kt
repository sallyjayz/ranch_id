package com.sallyjayz.ranchid

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sallyjayz.ranchid.databinding.FragmentLoginOptionBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint


class LoginOptionBottomSheet : BottomSheetDialogFragment() {

    interface OnInputSelectListener {
        fun sendInput(data: String)
    }

    private lateinit var binding: FragmentLoginOptionBottomSheetBinding
    private lateinit var callback: OnInputSelectListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginOptionBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginOptionBottomSheet = this
    }

    fun showEnumeratorDashboard() {
        callback.sendInput("ENUMERATOR")
        dismiss()
    }

    fun showVetDocDashboard() {
        callback.sendInput("VET_DOCTOR")
        dismiss()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.callback = activity as OnInputSelectListener
    }

    companion object {
        const val TAG = "LoginOptionBottomSheet"
    }

}