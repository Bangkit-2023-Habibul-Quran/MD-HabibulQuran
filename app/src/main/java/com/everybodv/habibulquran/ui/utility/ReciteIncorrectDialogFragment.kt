package com.everybodv.habibulquran.ui.utility

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.everybodv.habibulquran.databinding.FragmentCorrectDialogBinding
import com.everybodv.habibulquran.databinding.FragmentIncorrectDialogBinding
import com.everybodv.habibulquran.utils.setSafeOnClickListener

class ReciteIncorrectDialogFragment: DialogFragment() {

    private var _binding: FragmentIncorrectDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIncorrectDialogBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnRetry.setSafeOnClickListener {
            dismiss()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}