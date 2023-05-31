package com.everybodv.habibulquran.ui.makhraj.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.data.model.Hijaiyah
import com.everybodv.habibulquran.databinding.ActivityDetailMakhrajBinding
import com.everybodv.habibulquran.utils.Const

class DetailMakhrajActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMakhrajBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMakhrajBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.makhraj)
            setDisplayHomeAsUpEnabled(true)
        }

        val detail = intent.getParcelableExtra<Hijaiyah>(Const.MAKHRAJ) as Hijaiyah

        binding.tvHijaiyahLetter.text = detail.letter
        binding.tvPronounce.text = detail.pronounce
    }

    override fun onSupportNavigateUp(): Boolean {
        @Suppress("DEPRECATION")
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}