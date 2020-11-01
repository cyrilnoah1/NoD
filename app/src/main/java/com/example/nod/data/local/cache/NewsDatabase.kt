package com.example.nod.data.local.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nod.data.local.models.News

/**
 * [RoomDatabase] to handle the C.R.U.D. operations through the respective Data Access Objects.
 */
@Database(entities = [News::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
}