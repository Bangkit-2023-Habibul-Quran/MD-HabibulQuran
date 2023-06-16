package com.everybodv.habibulquran.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.data.UserID
import com.everybodv.habibulquran.data.local.UserIdPreferences
import com.everybodv.habibulquran.databinding.FragmentHomeBinding
import com.everybodv.habibulquran.ui.makhraj.MakhrajMenuActivity
import com.everybodv.habibulquran.ui.makhraj.MakhrajViewModel
import com.everybodv.habibulquran.ui.profile.ProfileViewModel
import com.everybodv.habibulquran.ui.quran.QuranViewModel
import com.everybodv.habibulquran.ui.tadarus.TadarusMenuActivity
import com.everybodv.habibulquran.utils.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity().application)
        val makhrajViewModel : MakhrajViewModel by viewModels { factory }
        val quranViewModel: QuranViewModel by viewModels { factory }
        val profileViewModel: ProfileViewModel by viewModels { factory }

        val userIdPreferences = UserIdPreferences(requireContext())
        val userID = UserID(userIdPreferences)

        val layout = GridLayoutManager(requireActivity(), 2, LinearLayoutManager.HORIZONTAL, false)

        makhrajViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.rvMakhraj.visibility = View.GONE
                binding.shimmer.apply {
                    visibility = View.VISIBLE
                    startShimmerAnimation()
                }
            } else {
                binding.rvMakhraj.visibility = View.VISIBLE
                binding.shimmer.apply {
                    stopShimmerAnimation()
                    visibility = View.GONE
                }
            }
        }

        profileViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) hideContent(binding.tvWelcomeUser) else showContent(binding.tvWelcomeUser)
        }

        userID.getId().observe(viewLifecycleOwner) { id ->
            profileViewModel.getDetailUser(id)
        }

        profileViewModel.userData.observe(viewLifecycleOwner) { user ->
            binding.tvWelcomeUser.text = "Ahlan, ${user?.name}"
        }

        makhrajViewModel.getAllHijaiyah()
        makhrajViewModel.listHijaiyah.observe(viewLifecycleOwner) { listHijaiyah ->
            val hijaiyahShuffled = listHijaiyah.shuffled()
            binding.rvMakhraj.apply {
                layoutManager = layout
                adapter = HomeMakhrajAdapter(hijaiyahShuffled)
            }
        }

        quranViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.rvTadarus.visibility = View.GONE
                binding.shimmerTadarus.apply {
                    visibility = View.VISIBLE
                    startShimmerAnimation()
                }
            } else {
                binding.rvTadarus.visibility = View.VISIBLE
                binding.shimmerTadarus.apply {
                    stopShimmerAnimation()
                    visibility = View.GONE
                }
            }
        }

        quranViewModel.getTadarusTest()
        quranViewModel.listSurahTest.observe(viewLifecycleOwner) { listSurah ->
            val surahShuffled = listSurah.shuffled()
            binding.rvTadarus.apply {
                layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
                adapter = HomeTadarusAdapter(surahShuffled)
            }
        }

        binding.ivMakhraj.setSafeOnClickListener {
            val intent = Intent(activity, MakhrajMenuActivity::class.java)
            startActivity(intent)
        }

        binding.ivTadarus.setSafeOnClickListener {
            val intent = Intent(activity, TadarusMenuActivity::class.java)
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