package com.everybodv.habibulquran.ui.utility

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.databinding.FragmentCorrectDialogBinding
import com.everybodv.habibulquran.utils.setSafeOnClickListener

class ReciteCorrectDialogFragment: DialogFragment() {

    private var _binding: FragmentCorrectDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCorrectDialogBinding.inflate(inflater, container, false)
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