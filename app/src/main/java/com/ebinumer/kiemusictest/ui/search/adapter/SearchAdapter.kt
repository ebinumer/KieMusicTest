package com.ebinumer.kiemusictest.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ebinumer.kiemusictest.data.model.Recordings
import com.ebinumer.kiemusictest.databinding.SearchItemBinding


class SearchAdapter(
    private val onItemClicked: (data: Recordings, position: Int) -> Unit,
    private val onPlayClicked: (data: Recordings, position: Int) -> Unit,
) : PagingDataAdapter<Recordings, SearchAdapter.MyViewHolder>(SearchComparator) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            SearchItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      getItem(position)?.let {
            holder.bind(it, position, onItemClicked,onPlayClicked)
        }
    }

    class MyViewHolder(private val binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            data: Recordings,
            position: Int,
            onItemClicked: (data: Recordings, position: Int) -> Unit,
            onPlayClicked: (data: Recordings, position: Int) -> Unit,
        ) {
            binding.apply {
                itemData = data
                itemCard.setOnClickListener {
                    onItemClicked(data, position)
                }
                btnPlay.setOnClickListener {
                    onPlayClicked(data,position)
                }

            }
        }
    }

    object SearchComparator : DiffUtil.ItemCallback<Recordings>() {
        override fun areItemsTheSame(oldItem: Recordings, newItem: Recordings): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Recordings, newItem: Recordings): Boolean {
            return oldItem == newItem
        }
    }
}