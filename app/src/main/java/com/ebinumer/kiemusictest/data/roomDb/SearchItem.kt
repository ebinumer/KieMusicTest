package com.ebinumer.kiemusictest.data.roomDb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_items")
data class SearchItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "item_id") val itemId: String? = null,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "type") val type: String = "Unknown",
)