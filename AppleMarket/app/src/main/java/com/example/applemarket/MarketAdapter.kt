package com.example.applemarket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.applemarket.databinding.ItemRecyclerviewBinding
import com.example.applemarket.model.MarketItem
import java.text.DecimalFormat

class MarketAdapter(private val mItems: MutableList<MarketItem>) : RecyclerView.Adapter<MarketAdapter.Holder>() {

    interface ItemClick {
        fun onClick(view : View, position : Int)
    }

    interface ItemLongClick {
        fun onLongClick(view : View, position : Int)
    }

    var itemClick : ItemClick? = null
    var itemLongClick : ItemLongClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }
        holder.itemView.setOnLongClickListener() OnLongClickListener@{
            itemLongClick?.onLongClick(it, position)
            return@OnLongClickListener true
        }

        holder.itemImage.setImageResource(mItems[position].itemImage)
        holder.itemTitle.text = mItems[position].itemTitle
        holder.userAddress.text = mItems[position].userAddress
        val price = mItems[position].itemPrice
        holder.itemPrice.text = DecimalFormat("#,###").format(price)+"원"
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
                    itemLongClick?.onLongClick(it, position)
                    true
                } else {
                    false
                }
            }
        }
    }
}