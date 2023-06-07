package com.everybodv.habibulquran.ui.tadarus.verse.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.data.model.SurahAyat
import com.everybodv.habibulquran.data.model.SurahFakeDataSource
import com.everybodv.habibulquran.data.remote.response.VersesItem
import com.everybodv.habibulquran.databinding.ActivityDetailTadarusBinding
import com.everybodv.habibulquran.ui.utility.ReciteIncorrectDialogFragment
import com.everybodv.habibulquran.utils.Const
import com.everybodv.habibulquran.utils.setSafeOnClickListener

class DetailTadarusActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTadarusBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTadarusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.tes_tadarus)

        val detail = intent.getParcelableExtra<VersesItem>(Const.EXTRA_VERSE_DETAIL) as VersesItem

        binding.tvAyatSurah.text = detail.text.arab

        binding.btnRecordRecite.setSafeOnClickListener {
            val dialog = ReciteIncorrectDialogFragment()
            dialog.show(supportFragmentManager, Const.INCORRECT_DIALOG)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        @Suppress("DEPRECATION")
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}