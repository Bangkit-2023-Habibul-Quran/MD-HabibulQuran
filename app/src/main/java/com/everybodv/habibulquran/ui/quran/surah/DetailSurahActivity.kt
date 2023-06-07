package com.everybodv.habibulquran.ui.quran.surah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.everybodv.habibulquran.databinding.ActivityDetailSurahBinding
import com.everybodv.habibulquran.utils.Const
import com.everybodv.habibulquran.data.remote.response.Data
import com.everybodv.habibulquran.data.remote.response.VersesItem
import com.everybodv.habibulquran.ui.quran.QuranViewModel
import com.everybodv.habibulquran.utils.ViewModelFactory
import com.everybodv.habibulquran.utils.showLoading

class DetailSurahActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailSurahBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSurahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val factory: ViewModelFactory = ViewModelFactory.getInstance(application)
        val quranViewModel : QuranViewModel by viewModels { factory }

        val detail = intent.getParcelableExtra<Data>(Const.EXTRA_SURAH) as Data

        val ayatList = ArrayList<VersesItem>()
//        detail.verses?.forEach {
//            val ayat = VersesItem(it.number, it.translation, it.text, it.audio)
//            ayatList.add(ayat)
//        }
        quranViewModel.isLoading.observe(this) { isLoading ->
            showLoading(binding.loading2, isLoading)
        }

        quranViewModel.getAyatBySurahId(detail.number)

        quranViewModel.listAyat.observe(this) { listAyat ->
            binding.rvAyat.apply {
                layoutManager = LinearLayoutManager(this@DetailSurahActivity, LinearLayoutManager.VERTICAL, false)
                adapter = DetailSurahAdapter(listAyat)
            }
        }



    }
}