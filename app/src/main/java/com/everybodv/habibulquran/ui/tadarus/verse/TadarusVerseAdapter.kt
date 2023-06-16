package com.everybodv.habibulquran.ui.tadarus.verse

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.everybodv.habibulquran.data.remote.response.VersesItem
import com.everybodv.habibulquran.databinding.ItemTadarusAyatCardBinding
import com.everybodv.habibulquran.ui.tadarus.verse.detail.DetailTadarusActivity
import com.everybodv.habibulquran.utils.Const
import com.everybodv.habibulquran.utils.setSafeOnClickListener

class TadarusVerseAdapter(private val listAyat: List<VersesItem>) : RecyclerView.Adapter<TadarusVerseAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemTadarusAyatCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VersesItem) {
            binding.tvAyat.text = item.number.inSurah.toString()

            itemView.setSafeOnClickListener {
                with(it.context) {
                    val toDetailTadarusIntent = Intent(this, DetailTadarusActivity::class.java)
                    toDetailTadarusIntent.putExtra(Const.EXTRA_VERSE_DETAIL, item)
                    startActivity(toDetailTadarusIntent)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TadarusVerseAdapter.ViewHolder {
        val binding = ItemTadarusAyatCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TadarusVerseAdapter.ViewHolder, position: Int) {
        val verse = listAyat[position]
        holder.bind(verse)
    }

    override fun getItemCount(): Int {
        return listAyat.size
    }
}