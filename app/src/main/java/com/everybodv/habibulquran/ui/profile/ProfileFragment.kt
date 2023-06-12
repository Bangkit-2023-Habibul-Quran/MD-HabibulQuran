package com.everybodv.habibulquran.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.data.Token
import com.everybodv.habibulquran.data.local.AuthPreferences
import com.everybodv.habibulquran.databinding.FragmentProfileBinding
import com.everybodv.habibulquran.ui.auth.LoginActivity
import com.everybodv.habibulquran.ui.profile.edit.EditProfileActivity
import com.everybodv.habibulquran.utils.setSafeOnClickListener

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

        val profileViewModel =
            ViewModelProvider(this)[ProfileViewModel::class.java]

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        profileViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val authPreferences = AuthPreferences(requireActivity())
        val token = Token(authPreferences)

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

        binding.btnEdit.setSafeOnClickListener {
            val toEditIntent = Intent(requireContext(), EditProfileActivity::class.java)
            startActivity(toEditIntent)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}