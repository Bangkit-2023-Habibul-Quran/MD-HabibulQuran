package com.everybodv.habibulquran.ui.quran.surah

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.everybodv.habibulquran.data.remote.response.VersesItem
import com.everybodv.habibulquran.databinding.ItemQuranPerAyatRowBinding
import com.everybodv.habibulquran.utils.setSafeOnClickListener

class DetailSurahAdapter(private val listAyat: List<VersesItem>): RecyclerView.Adapter<DetailSurahAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemQuranPerAyatRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VersesItem) {
            with(binding) {
                tvNumAyat.text = item.number.toString()
                tvAyatQuran.text = item.text.arab
                tvArtiQuran.text = item.translation.id
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailSurahAdapter.ViewHolder {
        val binding = ItemQuranPerAyatRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailSurahAdapter.ViewHolder, position: Int) {
        val ayat = listAyat[position]
        holder.bind(ayat)
    }

    override fun getItemCount(): Int = listAyat.size
}