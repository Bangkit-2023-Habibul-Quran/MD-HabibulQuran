package com.everybodv.habibulquran.ui.quran.surah

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.everybodv.habibulquran.data.remote.response.VersesItem
import com.everybodv.habibulquran.databinding.ItemQuranPerAyatRowBinding

class DetailSurahAdapter(private val listAyat: List<VersesItem>): RecyclerView.Adapter<DetailSurahAdapter.ViewHolder>() {
    private lateinit var onButtonClickCallback: OnButtonClickCallback

    fun setOnButtonClickCallback(onButtonClickCallback: OnButtonClickCallback) {
        this.onButtonClickCallback = onButtonClickCallback
    }

    inner class ViewHolder(val binding: ItemQuranPerAyatRowBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: VersesItem) {
            with(binding) {
                tvNumAyat.text = item.number.inSurah.toString()
                tvAyatQuran.text = item.text.arab
                tvArtiQuran.text = item.translation?.id

                btnPlayAyat.setOnClickListener { btn ->
                    onButtonClickCallback.onButtonPlayClicked(listAyat[adapterPosition])
                }

                btnStopAyat.setOnClickListener {
                    onButtonClickCallback.onButtonStopClicked(listAyat[adapterPosition])
                }
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

    interface OnButtonClickCallback {
        fun onButtonPlayClicked(versesItem: VersesItem)
        fun onButtonStopClicked(versesItem: VersesItem)
    }
}