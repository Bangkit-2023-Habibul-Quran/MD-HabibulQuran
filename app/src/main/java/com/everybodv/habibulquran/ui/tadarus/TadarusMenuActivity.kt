package com.everybodv.habibulquran.ui.tadarus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.data.model.SurahFakeDataSource
import com.everybodv.habibulquran.databinding.ActivityTadarusMenuBinding

class TadarusMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTadarusMenuBinding

    private val surahList = SurahFakeDataSource.surah
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTadarusMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.tadarus)

        binding.rvTadarusMenu.apply {
            layoutManager = LinearLayoutManager(this@TadarusMenuActivity, LinearLayoutManager.VERTICAL, false)
            adapter = TadarusMenuAdapter(surahList)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        @Suppress("DEPRECATION")
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}