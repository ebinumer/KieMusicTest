package com.ebinumer.kiemusictest.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ebinumer.kiemusictest.data.model.Genres
import com.ebinumer.kiemusictest.databinding.GenreItemBinding

class GenreAdapter(
    private var genreData: ArrayList<Genres>,
    var onItemDltClicked: (Genres, Int) -> Unit
) : RecyclerView.Adapter<GenreAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            GenreItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = genreData.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        genreData[position].let {
            holder.bind(it,position,onItemDltClicked)
        }
    }

    class MyViewHolder(private val binding: GenreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            data: Genres,
            position: Int,
            onItemDltClicked: (data: Genres, position: Int) -> Unit
        ) {
            binding.apply {
                itemData = data
                itemCard.setOnClickListener {
                    onItemDltClicked(data, position)
                }

            }
        }
    }
}