package com.everybodv.habibulquran.ui.quran

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.everybodv.habibulquran.data.remote.response.Data
import com.everybodv.habibulquran.databinding.ItemSurahCardBinding
import com.everybodv.habibulquran.ui.quran.surah.DetailSurahActivity
import com.everybodv.habibulquran.utils.Const
import com.everybodv.habibulquran.utils.setSafeOnClickListener

class QuranAdapter(private val listSurah: List<Data>): RecyclerView.Adapter<QuranAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemSurahCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Data) {
            with(binding) {
                tvSurahNum.text = item.number.toString()
                tvSurah.text = item.name.transliteration.id
                tvAyat.text = "${item.numberOfVerses} ayat"
                tvSurahInArab.text = item.name.jsonMemberShort
            }
            itemView.setSafeOnClickListener {
                with(it.context) {
                    val toDetailSurahIntent = Intent(this, DetailSurahActivity::class.java)
                    toDetailSurahIntent.putExtra(Const.EXTRA_SURAH, item)
                    startActivity(toDetailSurahIntent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuranAdapter.ViewHolder {
        val binding = ItemSurahCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuranAdapter.ViewHolder, position: Int) {
        val surah = listSurah[position]
        holder.bind(surah)
    }

    override fun getItemCount(): Int = listSurah.size
}