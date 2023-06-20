package com.ebinumer.kiemusictest.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ebinumer.kiemusictest.data.model.Recordings
import com.ebinumer.kiemusictest.databinding.PopularSongItemBinding

class PopularAdapter(
    private var popularData: ArrayList<Recordings>,
    var onItemDltClicked: (Recordings, Int) -> Unit
) : RecyclerView.Adapter<PopularAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            PopularSongItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = popularData.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        popularData[position].let {
            holder.bind(it, position, onItemDltClicked)
        }
    }

    class MyViewHolder(private val binding: PopularSongItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            data: Recordings,
            position: Int,
            onItemDltClicked: (data: Recordings, position: Int) -> Unit
        ) {
            binding.apply {
                itemData = data
                itemMain.setOnClickListener {
                    onItemDltClicked(data, position)
                }

            }
        }
    }
}