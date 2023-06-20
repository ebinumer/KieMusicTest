package com.ebinumer.kiemusictest.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ebinumer.kiemusictest.data.roomDb.SearchItem
import com.ebinumer.kiemusictest.databinding.RecentItemBinding


class SearchHistoryAdapter (
    private var searchItems: ArrayList<SearchItem>,
    var onItemDltClicked: (SearchItem, Int) -> Unit
) : RecyclerView.Adapter<SearchHistoryAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RecentItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = searchItems.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        searchItems[position].let {
            holder.bind(it,position,onItemDltClicked)
        }
    }

    class MyViewHolder(private val binding: RecentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            data: SearchItem,
            position: Int,
            onItemDltClicked: (data: SearchItem, position: Int) -> Unit
        ) {
            binding.apply {
                item = data
                itemMain.setOnClickListener {
                    onItemDltClicked(data, position)
                }

            }
        }
    }
}