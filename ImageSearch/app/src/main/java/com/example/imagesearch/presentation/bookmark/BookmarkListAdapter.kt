package com.example.imagesearch.presentation.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.imagesearch.Constants
import com.example.imagesearch.databinding.ImageSearchItemBinding
import com.example.imagesearch.domain.SearchListType
import com.example.imagesearch.domain.SearchModel
import java.text.SimpleDateFormat
import java.util.Locale

class BookmarkListAdapter(
    private val itemClickListener: (SearchModel) -> Unit
) : ListAdapter<SearchModel, BookmarkListAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<SearchModel>() {

        override fun areItemsTheSame(
            oldItem: SearchModel,
            newItem: SearchModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SearchModel,
            newItem: SearchModel
        ): Boolean {
            return oldItem == newItem
        }
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ImageSearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ImageSearchItemBinding,
        private val itemClickListener: ((SearchModel) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchModel) = with(binding) {
            tvSiteName.text = item.siteName
            tvItemType.text = SearchListType.from(item.itemType)
            val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault())
            tvDatetime.text = item.datetime?.let { dateFormat.format(it) }
            ivThumbnail.load(item.thumbnailUrl)
            ivThumbnail.setOnClickListener {
                itemClickListener?.invoke(item)
            }
        }
    }
}