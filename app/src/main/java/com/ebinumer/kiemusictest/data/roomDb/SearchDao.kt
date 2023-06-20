package com.ebinumer.kiemusictest.data.roomDb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSearchItem(searchItem: SearchItem)

    @Query("SELECT * FROM search_items ORDER BY id DESC")
     fun getRecentSearches(): Flow<List<SearchItem>>

    @Query("DELETE FROM search_items")
    suspend fun deleteAll()
}
