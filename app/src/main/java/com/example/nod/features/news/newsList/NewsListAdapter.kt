package com.example.nod.features.news.newsList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nod.R
import com.example.nod.common.UI_DATE_FORMAT
import com.example.nod.common.UTC_DATE_FORMAT
import com.example.nod.common.getDesiredDateInDesiredFormat
import com.example.nod.data.local.models.News
import com.example.nod.databinding.ItemNewsHeadlinesBinding

/**
 * [RecyclerView.Adapter] to display generate a paginated list of [News] information list.
 */
class NewsListAdapter(private val onClick: (news: News) -> Unit) :
    ListAdapter<News, NewsListAdapter.NewsListViewHolder>(ITEM_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {

        // Instantiating ViewHolder.
        return NewsListViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_news_headlines,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        // Binding the required data to the news item.
        getItem(position)?.let { holder.bind(it) }
    }

    /**
     * [RecyclerView.ViewHolder] to recycle the item layouts during the population of data.
     */
    inner class NewsListViewHolder(private val item: ItemNewsHeadlinesBinding) :
        RecyclerView.ViewHolder(item.root) {

        /**
         * Function to bind the [News] details to the item layout.
         */
        fun bind(news: News) {
            item.news = news

            if (news.sourceName.isNotBlank() && news.publishedAt.isNotBlank()) {
                news.run {
                    item.publishing =
                        "$sourceName   |   ${getDesiredDateInDesiredFormat(
                            publishedAt,
                            UTC_DATE_FORMAT,
                            UI_DATE_FORMAT
                        )}"
                }
            }

            item.clNewsContainer.setOnClickListener {
                onClick(news)
            }
        }
    }

    companion object {

        /**
         * List item comparator to handle the conflict between identical items.
         */
        val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<News>() {

            // Checking if the items are same based on the content URL of the news object.
            override fun areItemsTheSame(old: News, new: News) = old.contentUrl == new.contentUrl

            // Checking if the contents are same by seeing if both the objects point to the same address in the memory.
            override fun areContentsTheSame(old: News, new: News) = old == new
        }
    }
}