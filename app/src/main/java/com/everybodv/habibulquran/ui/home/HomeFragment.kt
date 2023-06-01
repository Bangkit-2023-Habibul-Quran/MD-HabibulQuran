package com.everybodv.habibulquran.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.data.model.HijaiyahDataSource
import com.everybodv.habibulquran.data.model.SurahFakeDataSource
import com.everybodv.habibulquran.data.model.TadarusFakeDataSource
import com.everybodv.habibulquran.databinding.FragmentHomeBinding
import com.everybodv.habibulquran.ui.makhraj.MakhrajMenuActivity
import com.everybodv.habibulquran.ui.tadarus.TadarusMenuActivity
import com.everybodv.habibulquran.utils.addOnBackPressedCallbackWithInterval
import com.everybodv.habibulquran.utils.setSafeOnClickListener
import com.everybodv.habibulquran.utils.showToast

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val hijaiyahList = HijaiyahDataSource.hijaiyahs
    private val tadarusList = SurahFakeDataSource.surah

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layout = GridLayoutManager(requireActivity(), 2, LinearLayoutManager.HORIZONTAL, false)

        binding.rvMakhraj.apply {
            layoutManager = layout
            adapter = HomeMakhrajAdapter(hijaiyahList)
        }

        binding.rvTadarus.apply {
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            adapter = HomeTadarusAdapter(tadarusList)
        }

        binding.ivMakhraj.setSafeOnClickListener {
            val intent = Intent(activity, MakhrajMenuActivity::class.java)
            startActivity(intent)
        }

        binding.btnShowMakhraj.setSafeOnClickListener {
            val intent = Intent(activity, MakhrajMenuActivity::class.java)
            startActivity(intent)
        }

        binding.btnShowTadarus.setSafeOnClickListener {
            val intent = Intent(activity, TadarusMenuActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        configureBackPress()
    }

    private fun configureBackPress() {
        requireActivity().addOnBackPressedCallbackWithInterval(2000) {
            context?.let { showToast(it, getString(R.string.press_again)) }
        }
    }
}