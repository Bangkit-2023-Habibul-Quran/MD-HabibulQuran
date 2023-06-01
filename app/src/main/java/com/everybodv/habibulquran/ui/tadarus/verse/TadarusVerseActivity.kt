package com.everybodv.habibulquran.ui.tadarus.verse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.data.model.Quran
import com.everybodv.habibulquran.data.model.SurahAyat
import com.everybodv.habibulquran.data.model.SurahFakeDataSource
import com.everybodv.habibulquran.databinding.ActivityTadarusVerseBinding
import com.everybodv.habibulquran.utils.Const

class TadarusVerseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTadarusVerseBinding
    private lateinit var listAyat : List<SurahAyat>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTadarusVerseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.choose_ayat)

        val ayatDetail = intent.getParcelableExtra<Quran>(Const.EXTRA_VERSE) as Quran

        ayatDetail.surahAyat.map {
            listAyat = listOf(SurahAyat(it.numAyat, it.ayat, it.latin, it.translate))
            setRecycle()
        }

        binding.tvProgressVerse.text = "0/${ayatDetail.totalAyat}"
        binding.tvSurahVerse.text = ayatDetail.titleSurah
    }

    private fun setRecycle() {
        binding.rvVerse.apply {
            layoutManager = GridLayoutManager(this@TadarusVerseActivity, 4, LinearLayoutManager.HORIZONTAL, false)
            adapter = TadarusVerseAdapter(listAyat)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        @Suppress("DEPRECATION")
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}