package com.example.nod.data.local.cache

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nod.data.local.models.News


/**
 * Data Access Object to perform C.R.U.D and retrieval operations on the local news
 * information data.
 */
@Dao
interface NewsDao {

    /**
     * Function to insert the news information into the local cache.
     *
     * Provided there is a conflict during the insertion process, the [OnConflictStrategy] is
     * configured to replace the existing row.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(news: News)

    /**
     * Function to insert the news information list into the local cache.
     *
     * Provided there is a conflict during the insertion process, the [OnConflictStrategy] is
     * configured to replace the existing row.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewsList(news: List<News>)

    /**
     * Function to query paginated data order by the latest publishing date.
     */
    @Query("SELECT * FROM NEWS ORDER BY published_at DESC")
    fun getNewsInformation(): LiveData<List<News>>

    /**
     * Function to return the list of all [News] rows in the database.
     */
    @Query("SELECT * FROM NEWS")
    fun getNewsList(): List<News>
}