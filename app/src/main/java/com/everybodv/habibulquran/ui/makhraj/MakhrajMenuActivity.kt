package com.everybodv.habibulquran.ui.makhraj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.databinding.ActivityMakhrajMenuBinding
import com.everybodv.habibulquran.utils.ViewModelFactory
import com.everybodv.habibulquran.utils.showLoading

class MakhrajMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMakhrajMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakhrajMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.belajar_makhraj)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(application)
        val makhrajViewModel : MakhrajViewModel by viewModels { factory }

        makhrajViewModel.isLoading.observe(this) { isLoading ->
            showLoading(binding.loadingMakhraj, isLoading)
        }
        makhrajViewModel.getAllHijaiyah()
        makhrajViewModel.listHijaiyah.observe(this) { listHijaiyah ->
            binding.rvMakhrajMenu.apply {
                layoutManager = GridLayoutManager(this@MakhrajMenuActivity, 4, LinearLayoutManager.VERTICAL, false)
                adapter = MakhrajMenuAdapter(listHijaiyah)
            }
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        @Suppress("DEPRECATION")
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}