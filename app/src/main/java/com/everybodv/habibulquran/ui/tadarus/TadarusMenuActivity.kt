package com.everybodv.habibulquran.ui.tadarus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.data.model.SurahFakeDataSource
import com.everybodv.habibulquran.databinding.ActivityTadarusMenuBinding
import com.everybodv.habibulquran.utils.ViewModelFactory
import com.everybodv.habibulquran.utils.showLoading

class TadarusMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTadarusMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTadarusMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.tadarus)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(application)
        val tadarusViewModel : TadarusViewModel by viewModels { factory }

        tadarusViewModel.isLoading.observe(this) { isLoading ->
            showLoading(binding.loadingTadarus, isLoading)
        }

        tadarusViewModel.getTadarusTest()
        tadarusViewModel.listSurahTest.observe(this) { listSurah ->
            val surah = listSurah.sortedByDescending { it.number }
            binding.rvTadarusMenu.apply {
                layoutManager = LinearLayoutManager(this@TadarusMenuActivity, LinearLayoutManager.VERTICAL, false)
                adapter = TadarusMenuAdapter(surah)
            }
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        @Suppress("DEPRECATION")
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}