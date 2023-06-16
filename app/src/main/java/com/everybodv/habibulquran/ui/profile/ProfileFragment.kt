package com.everybodv.habibulquran.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.data.Token
import com.everybodv.habibulquran.data.UserID
import com.everybodv.habibulquran.data.local.AuthPreferences
import com.everybodv.habibulquran.data.local.UserIdPreferences
import com.everybodv.habibulquran.databinding.FragmentProfileBinding
import com.everybodv.habibulquran.ui.auth.LoginActivity
import com.everybodv.habibulquran.ui.profile.edit.EditProfileActivity
import com.everybodv.habibulquran.utils.*

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val authPreferences = AuthPreferences(requireActivity())
        val token = Token(authPreferences)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity().application)
        val profileViewModel: ProfileViewModel by viewModels { factory }

        val userIdPreferences = UserIdPreferences(requireContext())
        val userID = UserID(userIdPreferences)

        profileViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(binding.loading2, isLoading)
            if (!isLoading) {
                hideContent(binding.shimmerProfile)
            }
        }

        userID.getId().observe(viewLifecycleOwner) { id ->
            profileViewModel.getDetailUser(id)
        }

        profileViewModel.userData.observe(viewLifecycleOwner) { data ->
            binding.apply {
                if (data != null) {
                    showContent(btnEdit)
                    tvUserName.text = data.name
                    tvEmail.text = data.email
                    tvUserGender.text = data.jenisKelamin
                    tvUserBirthdate.text = data.birthdate?.let { convertDateFormat(it) }
                    Glide.with(this@ProfileFragment)
                        .load(data.image)
                        .into(imageView2)
                    btnEdit.setSafeOnClickListener {
                        val toEditIntent = Intent(requireActivity(), EditProfileActivity::class.java)
                        toEditIntent.putExtra(Const.EXTRA_PROFILE, data)
                        startActivity(toEditIntent)
                    }
                }

            }
        }

        binding.topAppBar.inflateMenu(R.menu.menu)

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.logout_menu -> {
                    val builder = AlertDialog.Builder(requireActivity())
                    builder.setMessage(getString(R.string.logout_ask))
                    builder.setNegativeButton(getString(R.string.no)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    builder.setPositiveButton(getString(R.string.yes)) { _, _, ->
                        requireActivity().getSharedPreferences("data", 0)
                            .edit().clear().apply()
                        val toLoginIntent = Intent(activity, LoginActivity::class.java)
                        toLoginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            .also {
                                token.deleteToken()
                                userID.deleteId()
                                profileViewModel.clearDetailUserData()
                                startActivity(it)
                            }
                    }
                    val alert = builder.create()
                    alert.show()
                    true
                } else -> { false}
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}