package com.example.applemarket

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.applemarket.databinding.ActivityDetailBinding
import com.google.android.material.snackbar.Snackbar

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var isLiked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        setData()
        setUnderLineText()
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.ivLikeBtn.setOnClickListener {
            addWishList()
        }
    }

    private fun setData() {
        val clickedItem = intent.getParcelableExtra<MarketItem>("clickedItem")
        clickedItem?.let {
            binding.ivDetailItem.setImageResource(it.itemImage)
            binding.tvDetailTitle.text = it.itemTitle
            binding.tvDetailContent.text = it.itemContent
            binding.tvDetailPrice.text = it.itemPrice
            binding.tvUserName.text = it.userName
            binding.tvUserAddress.text = it.userAddress
            isLiked = it.isLiked == true
            binding.ivLikeBtn.setImageResource(
                if (isLiked) {R.drawable.ic_like_full}
                else {R.drawable.ic_like_empty}
            )
        }
    }

    // 텍스트 밑줄 그리기
    private fun setUnderLineText() {
        binding.tvMannerTemperature.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    private fun addWishList() {
        if(!isLiked){
            binding.ivLikeBtn.setImageResource(R.drawable.ic_like_full)
            Snackbar.make(binding.root, getString(R.string.add_wishlist), Snackbar.LENGTH_SHORT).show()
            isLiked = true
        }else {
            binding.ivLikeBtn.setImageResource(R.drawable.ic_like_empty)
            isLiked = false
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}