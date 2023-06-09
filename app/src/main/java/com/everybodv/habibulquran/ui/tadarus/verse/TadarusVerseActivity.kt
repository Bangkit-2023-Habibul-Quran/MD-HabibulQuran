package com.everybodv.habibulquran.ui.tadarus.verse

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.data.remote.response.Data
import com.everybodv.habibulquran.data.remote.response.VersesItem
import com.everybodv.habibulquran.databinding.ActivityTadarusVerseBinding
import com.everybodv.habibulquran.utils.Const

class TadarusVerseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTadarusVerseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTadarusVerseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.choose_ayat)

        val ayatDetail = intent.getParcelableExtra<Data>(Const.EXTRA_VERSE) as Data

        val listAyat = ArrayList<VersesItem>()
        ayatDetail.verses!!.forEach {
            val ayat = VersesItem(it.number, it.translation, it.text, it.audio)
            listAyat.add(ayat)
        }
        val surahData = ayatDetail.number
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = prefs.edit()
        editor.putInt(Const.AYAT_NUMBER, surahData)
        editor.apply()

        binding.rvVerse.apply {
            layoutManager = GridLayoutManager(this@TadarusVerseActivity, 4, LinearLayoutManager.VERTICAL, false)
            adapter = TadarusVerseAdapter(listAyat)
        }

        binding.tvProgressVerse.text = "0/${ayatDetail.numberOfVerses}"
        binding.tvSurahVerse.text = ayatDetail.name.transliteration?.id
    }

    override fun onSupportNavigateUp(): Boolean {
        @Suppress("DEPRECATION")
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}