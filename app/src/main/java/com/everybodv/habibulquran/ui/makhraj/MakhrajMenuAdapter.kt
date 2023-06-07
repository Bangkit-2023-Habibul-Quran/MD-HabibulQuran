package com.everybodv.habibulquran.ui.makhraj

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.everybodv.habibulquran.data.model.Hijaiyah
import com.everybodv.habibulquran.data.remote.response.DataItem
import com.everybodv.habibulquran.databinding.ItemHijaiyahCardBinding
import com.everybodv.habibulquran.ui.makhraj.detail.DetailMakhrajActivity
import com.everybodv.habibulquran.utils.Const
import com.everybodv.habibulquran.utils.setSafeOnClickListener

class MakhrajMenuAdapter(private val listHijaiyah: List<DataItem>) :
    RecyclerView.Adapter<MakhrajMenuAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemHijaiyahCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataItem) {
            with(binding) {
                tvHijaiyah.text = item.arabic
            }
            itemView.setSafeOnClickListener {
                with(it.context) {
                    val toDetailMakhrajIntent = Intent(this, DetailMakhrajActivity::class.java)
                    toDetailMakhrajIntent.putExtra(Const.MAKHRAJ, item)
                    startActivity(toDetailMakhrajIntent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHijaiyahCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listHijaiyah.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hijaiyah = listHijaiyah[position]
        holder.bind(hijaiyah)
    }

}