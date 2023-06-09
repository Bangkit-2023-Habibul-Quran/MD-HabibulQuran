package com.everybodv.habibulquran.ui.tadarus

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.everybodv.habibulquran.data.remote.response.Data
import com.everybodv.habibulquran.databinding.ItemTadarusMenuCardBinding
import com.everybodv.habibulquran.ui.tadarus.verse.TadarusVerseActivity
import com.everybodv.habibulquran.utils.Const
import com.everybodv.habibulquran.utils.setSafeOnClickListener

class TadarusMenuAdapter(private val listSurah: List<Data>) : RecyclerView.Adapter<TadarusMenuAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemTadarusMenuCardBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(item: Data) {
            with(binding) {
                tvSurahTadarus.text = item.name.transliteration?.id
                tvAyatFinished.text = " 0/${item.numberOfVerses} ayat selesai"
            }
            itemView.setSafeOnClickListener {
                with(it.context) {
                    val toTadarusVerseIntent = Intent(this, TadarusVerseActivity::class.java)
                    toTadarusVerseIntent.putExtra(Const.EXTRA_VERSE, item)
                    startActivity(toTadarusVerseIntent)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TadarusMenuAdapter.ViewHolder {
        val binding = ItemTadarusMenuCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TadarusMenuAdapter.ViewHolder, position: Int) {
        val surah = listSurah[position]
        holder.bind(surah)
    }

    override fun getItemCount(): Int = listSurah.size
}