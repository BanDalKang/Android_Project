package com.example.applemarket

import android.content.Intent
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.applemarket.databinding.ActivityDetailBinding
import com.example.applemarket.model.MarketItem
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var isLiked = false

    private val item: MarketItem? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constants.ITEM_OBJECT, MarketItem::class.java)
        } else {
            intent.getParcelableExtra<MarketItem>(Constants.ITEM_OBJECT)
        }
    }
    private val itemPosition: Int by lazy {
        intent.getIntExtra(Constants.ITEM_INDEX,0)
    }

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
            exit()
        }
        binding.ivLikeBtn.setOnClickListener {
            addWishList()
        }
    }

    private fun setData() {
        binding.ivDetailImage.setImageDrawable(item?.let {
            ResourcesCompat.getDrawable(
                resources,
                it.itemImage,
                null
            )
        })
        binding.tvDetailTitle.text = item?.itemTitle
        binding.tvDetailContent.text = item?.itemContent
        binding.tvDetailPrice.text = DecimalFormat("#,###").format(item?.itemPrice) + "원"
        binding.tvUserName.text = item?.userName
        binding.tvUserAddress.text = item?.userAddress
        isLiked = item?.isLiked == true
        binding.ivLikeBtn.setImageResource(
            if (isLiked) {R.drawable.ic_like_full}
            else {R.drawable.ic_like_empty}
        )

    }

    // 텍스트 밑줄 그리기
    private fun setUnderLineText() {
        binding.tvMannerTemperature.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    private fun addWishList() {
        if(!isLiked){
            binding.ivLikeBtn.setImageResource(R.drawable.ic_like_full)
            showSnackbar()
            isLiked = true
        }else {
            binding.ivLikeBtn.setImageResource(R.drawable.ic_like_empty)
            isLiked = false
        }
    }

    private fun showSnackbar() {
        /*
        LENGTH_SHORT: 약 2초간
        LENGTH_LONG: 약 4.5초간
        LENGTH_INDEFINITE: 사용자가 스낵바를 닫을 때까지
        */
        val snackbar = Snackbar.make(binding.root, getString(R.string.add_wishlist), Snackbar.LENGTH_SHORT)
        val actionText = getString(R.string.show_wishlist)
        val actionBoldText = SpannableString(actionText)
        actionBoldText.setSpan(StyleSpan(Typeface.BOLD), 0, actionText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.main))

        snackbar.setAction(actionBoldText) {
            // 버튼을 클릭했을 때
        }

        // 표시될 위치 조정
        snackbar.setAnchorView(binding.lineHorizontal2)
        snackbar.show()
    }

    private fun exit() {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("itemIndex", itemPosition)
            putExtra("isLiked", isLiked)
        }
        setResult(RESULT_OK, intent)
        if (!isFinishing) finish()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        exit()
    }
}