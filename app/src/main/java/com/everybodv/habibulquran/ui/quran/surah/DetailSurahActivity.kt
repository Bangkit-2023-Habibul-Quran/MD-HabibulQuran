package com.everybodv.habibulquran.ui.quran.surah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.databinding.ActivityDetailSurahBinding
import com.everybodv.habibulquran.utils.Const
import com.everybodv.habibulquran.data.remote.response.Data
import com.everybodv.habibulquran.data.remote.response.VersesItem

class DetailSurahActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailSurahBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSurahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val detail = intent.getParcelableExtra<Data>(Const.EXTRA_SURAH) as Data

        val ayatList = ArrayList<VersesItem>()
        detail.verses.forEach {
            val ayat = VersesItem(it.number, it.translation, it.text, it.audio)
            ayatList.add(ayat)
        }

        binding.rvAyat.apply {
            layoutManager = LinearLayoutManager(this@DetailSurahActivity, LinearLayoutManager.VERTICAL, false)
            adapter = DetailSurahAdapter(ayatList)
        }
    }
}