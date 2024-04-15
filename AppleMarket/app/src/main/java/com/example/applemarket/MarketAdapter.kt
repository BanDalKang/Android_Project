package com.example.applemarket

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.applemarket.databinding.ItemRecyclerviewBinding

class MarketAdapter(private val mItems: MutableList<MarketItem>) : RecyclerView.Adapter<MarketAdapter.Holder>() {

    interface ItemClick {
        fun onClick(view : View, position : Int)
    }

    interface LongItemClick {
        fun onLongClick(view : View, position : Int)
    }

    var itemClick : ItemClick? = null
    var longItemClick : LongItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }
        holder.itemImage.setImageResource(mItems[position].itemImage)
        holder.itemTitle.text = mItems[position].itemTitle
        holder.userAddress.text = mItems[position].userAddress
        holder.itemPrice.text = mItems[position].itemPrice
        holder.itemChatCount.text = mItems[position].itemChatCount.toString()
        holder.itemLikeCount.text = mItems[position].itemLikeCount.toString()
        if(mItems[position].isLiked)
            holder.likeIcon.setImageResource(R.drawable.ic_like_full)
        else
            holder.likeIcon.setImageResource(R.drawable.ic_like_empty)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class Holder(private val binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemImage = binding.ivItem
        val itemTitle = binding.tvTitle
        val userAddress = binding.tvAddress
        val itemPrice = binding.tvPrice
        val itemChatCount = binding.tvChat
        val itemLikeCount = binding.tvLike
        val likeIcon = binding.ivLike
        init {
            itemImage.clipToOutline = true

            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClick?.onClick(it, position)
                }
            }
            binding.root.setOnLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    longItemClick?.onLongClick(it, position)
                    true
                } else {
                    false
                }
            }
        }
    }
}