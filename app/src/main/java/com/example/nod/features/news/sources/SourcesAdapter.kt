package com.example.nod.features.news.sources

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nod.R
import com.example.nod.data.local.models.Source
import com.example.nod.databinding.ItemSourcesBinding

class SourcesAdapter(val onClick: (source: Source) -> Unit) :
    ListAdapter<Source, SourcesAdapter.SourceViewHolder>(ITEM_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        return SourceViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_sources,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class SourceViewHolder(val item: ItemSourcesBinding) :
        RecyclerView.ViewHolder(item.root) {

        fun bind(source: Source) {
            item.source = source
            item.clSourceContainer.setOnClickListener {
                onClick(source)
            }
        }
    }

    companion object {
        val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Source>() {
            override fun areItemsTheSame(oldItem: Source, newItem: Source): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Source, newItem: Source): Boolean {
                return oldItem == newItem
            }

        }
    }
}