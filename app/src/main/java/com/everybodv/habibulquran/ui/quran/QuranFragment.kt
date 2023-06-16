package com.everybodv.habibulquran.ui.quran

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.everybodv.habibulquran.databinding.FragmentQuranBinding
import com.everybodv.habibulquran.utils.ViewModelFactory

class QuranFragment : Fragment() {

    private var _binding: FragmentQuranBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuranBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity().application)
        val quranViewModel : QuranViewModel by viewModels { factory }

        quranViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.rvSurah.visibility = View.GONE
                binding.shimer2.apply {
                    visibility = View.VISIBLE
                    startShimmerAnimation()
                }
            } else {
                binding.rvSurah.visibility = View.VISIBLE
                binding.shimer2.apply {
                    stopShimmerAnimation()
                    visibility = View.GONE
                }
            }
        }

        quranViewModel.getListSurah()
        quranViewModel.surahList.observe(viewLifecycleOwner) { listSurah ->
            binding.rvSurah.apply {
                layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
                adapter = QuranAdapter(listSurah)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}