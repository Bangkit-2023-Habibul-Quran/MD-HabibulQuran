package com.everybodv.habibulquran.ui.utility

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.everybodv.habibulquran.databinding.FragmentResultTadarusDialogBinding
import com.everybodv.habibulquran.utils.setSafeOnClickListener

class ReciteIncorrectDialogFragment: DialogFragment() {

    private var _binding: FragmentResultTadarusDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultTadarusDialogBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.tvRetry.setSafeOnClickListener {
            dismiss()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}