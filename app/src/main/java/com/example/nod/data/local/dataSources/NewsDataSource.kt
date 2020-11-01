package com.example.nod.data.local.dataSources

import androidx.lifecycle.LiveData
import com.example.nod.data.local.models.News

/**
 * DataSource to handle the C.R.U.D. operations related to the local news information cache.
 */
interface NewsDataSource {

    /**
     * Function to store the [News] information in the database.
     */
    fun saveNewsInformation(news: News)

    /**
     * Function to store the [News] information list in the database.
     */
    fun saveNewsInformationList(news: List<News>)

    /**
     * Function to retrieve the list of cached [News] information list.
     */
    fun getNewsInformation(): LiveData<List<News>>
}