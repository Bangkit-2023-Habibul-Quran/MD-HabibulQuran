package com.everybodv.habibulquran.ui.utility

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.everybodv.habibulquran.databinding.FragmentResultMakhrajDialogBinding
import com.everybodv.habibulquran.utils.setSafeOnClickListener

class ReciteCorrectDialogFragment: DialogFragment() {

    private var _binding: FragmentResultMakhrajDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultMakhrajDialogBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.tvRetry.setSafeOnClickListener {
            dismiss()
        }

        binding.btnContinue.setSafeOnClickListener {
            //
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}