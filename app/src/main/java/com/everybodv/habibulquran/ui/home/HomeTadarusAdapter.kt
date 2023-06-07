package com.everybodv.habibulquran.ui.home

import android.content.Intent
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.data.model.Quran
import com.everybodv.habibulquran.data.model.Tadarus
import com.everybodv.habibulquran.data.remote.response.Data
import com.everybodv.habibulquran.databinding.ItemHijaiyahCardBinding
import com.everybodv.habibulquran.databinding.ItemTadarusHomeCardBinding
import com.everybodv.habibulquran.ui.tadarus.TadarusMenuActivity
import com.everybodv.habibulquran.ui.tadarus.verse.TadarusVerseActivity
import com.everybodv.habibulquran.utils.Const
import com.everybodv.habibulquran.utils.setSafeOnClickListener

class HomeTadarusAdapter(private val listTadarus: List<Data>): RecyclerView.Adapter<HomeTadarusAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemTadarusHomeCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Data) {
            with(binding) {
                tvSurahName.text = item.name.transliteration?.id
                tvTotalAyat.text = "Total ayat: ${item.numberOfVerses}"
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
    ): HomeTadarusAdapter.ViewHolder {
        val binding = ItemTadarusHomeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeTadarusAdapter.ViewHolder, position: Int) {
        val tadarus = listTadarus[position]
        holder.bind(tadarus)
    }

    override fun getItemCount(): Int = 3
}