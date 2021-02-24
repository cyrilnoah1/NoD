package com.example.nod

import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.example.nod.data.local.cache.NewsDao
import com.example.nod.data.local.cache.NewsDatabase
import com.example.nod.data.local.models.News
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class LocalCacheTest {

    private lateinit var newsDao: NewsDao
    private lateinit var db: NewsDatabase

    @Before
    fun createDb() {
        val appContext = InstrumentationRegistry.getTargetContext()
        db = Room.inMemoryDatabaseBuilder(appContext, NewsDatabase::class.java).build()
        newsDao = db.newsDao()
    }

    /**
     * [Test] to check whether a single [News] object inserted into the database is being
     * retrieved correctly.
     */
    @Test
    @Throws(Exception::class)
    fun writeToDbAndFetch() {
        newsDao.insertNews(getNews())
        val newsList = newsDao.getNewsList()
        assertTrue(newsList.size == 1)
    }

    /**
     * [Test] to check whether multiple [News] objects inserted into the database are being
     * retrieved correctly.
     */
    @Test
    @Throws(Exception::class)
    fun writeMultipleToDbAndFetch() {
        newsDao.insertNewsList(getNewsList())
        val newsList = newsDao.getNewsList()
        assertTrue(newsList.size == 3)
    }

    /**
     * [Test] to check whether multiple identical [News] objects (items with same primary key)
     * are being replaced to store only the latest item inserted.
     */
    @Test
    @Throws(Exception::class)
    fun writeIdenticalToDbAndFetch() {

        // Creating a list of three identical news objects (they have the same primary key).
        val news = mutableListOf<News>().apply {
            add(getNews())
            add(getNews())
            add(getNews())
        }

        // As the primary keys are same the existing row should be overridden,
        // and we should be able to yield only one row from the database.
        newsDao.insertNewsList(news)

        val newsList = newsDao.getNewsList()
        assertTrue(newsList.size == 1)
    }


    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    /**
     * Function to create and return a dummy [News] item.
     *
     * The [contentUrl] is an optional parameter (Required for checking insertion conflicts
     * as it is the primary key).
     */
    private fun getNews(contentUrl: String = "https://www.google.com") = News(
        "Cyril",
        contentUrl,
        "Google is a search engine.",
        "http://www.google.com/google.png",
        "11/03/2019",
        "Google",
        "Google Search"
    )

    /**
     * Function to create an return a dummy list of discrete [News] items list.
     */
    private fun getNewsList() = mutableListOf<News>().apply {
        val google = getNews()
        val giggle = getNews("https://www.giggle.com")
        val goggles = getNews("https://www.goggles.com")

        add(google)
        add(giggle)
        add(goggles)
    }
}