package com.ebinumer.kiemusictest.data.roomDb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SearchItem::class], version = 1, exportSchema = true)
abstract class KieDataBase : RoomDatabase() {

    abstract val searchDao: SearchDao

    companion object {

        @Volatile
        private var INSTANCE: KieDataBase? = null


    }
}
