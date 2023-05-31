package com.everybodv.habibulquran.ui.makhraj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.data.model.HijaiyahDataSource
import com.everybodv.habibulquran.databinding.ActivityMakhrajMenuBinding

class MakhrajMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMakhrajMenuBinding
    private val listHijaiyah = HijaiyahDataSource.hijaiyahs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakhrajMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.belajar_makhraj)

        binding.rvMakhrajMenu.apply {
            layoutManager = GridLayoutManager(this@MakhrajMenuActivity, 4, LinearLayoutManager.VERTICAL, false)
            adapter = MakhrajMenuAdapter(listHijaiyah)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        @Suppress("DEPRECATION")
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}