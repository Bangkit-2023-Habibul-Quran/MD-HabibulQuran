package com.everybodv.habibulquran.ui.quran

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.everybodv.habibulquran.databinding.FragmentQuranBinding
import com.everybodv.habibulquran.utils.showLoading

class QuranFragment : Fragment() {

    private var _binding: FragmentQuranBinding? = null
//    private lateinit var quranViewModel: QuranViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentQuranBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        quranViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())[QuranViewModel::class.java]
//        quranViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
//            showLoading(binding.loading, isLoading)
//        }
//
//        quranViewModel.listSurah.observe(viewLifecycleOwner) { listSurah ->
//            quranViewModel.getAllSurah()
//            binding.rvSurah.apply {
//                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//                adapter = QuranAdapter(listSurah)
//            }
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}