package com.example.particulatematter

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.particulatematter.data.DustItem
import com.example.particulatematter.databinding.ActivityMainBinding
import com.example.particulatematter.retrofit.NetWorkClient
import com.skydoves.powerspinner.IconSpinnerAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    var items = mutableListOf<DustItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.spinnerViewSido.setOnSpinnerItemSelectedListener<String> { _, _, _, text ->
            communicateNetWork(setUpDustParameter(text))
        }

        binding.spinnerViewGoo.setOnSpinnerItemSelectedListener<String> { _, _, _, text ->
            var selectedItem = items.filter { f -> f.stationName == text }

            binding.tvCityname.text = selectedItem[0].sidoName + "  " + selectedItem[0].stationName
            binding.tvDate.text = selectedItem[0].dataTime
            binding.tvP10value.text = selectedItem[0].pm10Value + " ㎍/㎥"

            when (getGrade(selectedItem[0].pm10Value)) {
                1 -> {
                    binding.mainBg.setBackgroundColor(Color.parseColor("#9ED2EC"))
                    binding.ivFace.setImageResource(R.drawable.image_mise1)
                    binding.tvP10grade.text = "좋음"
                }

                2 -> {
                    binding.mainBg.setBackgroundColor(Color.parseColor("#D6A478"))
                    binding.ivFace.setImageResource(R.drawable.image_mise2)
                    binding.tvP10grade.text = "보통"
                }

                3 -> {
                    binding.mainBg.setBackgroundColor(Color.parseColor("#DF7766"))
                    binding.ivFace.setImageResource(R.drawable.image_mise3)
                    binding.tvP10grade.text = "나쁨"
                }

                4 -> {
                    binding.mainBg.setBackgroundColor(Color.parseColor("#BB3320"))
                    binding.ivFace.setImageResource(R.drawable.image_mise4)
                    binding.tvP10grade.text = "매우나쁨"
                }
            }
        }
    }

    private fun communicateNetWork(param: HashMap<String, String>) = lifecycleScope.launch() {
        val responseData = NetWorkClient.dustNetWork.getDust(param)
        items = responseData.response.dustBody.dustItem!!

        val goo = ArrayList<String>()
        items.forEach {
            Log.d("add Item :", it.stationName)
            goo.add(it.stationName)
        }

        runOnUiThread {
            binding.spinnerViewGoo.setItems(goo)
        }
    }

    private fun setUpDustParameter(sido: String): HashMap<String, String> {
        val authKey = getString(R.string.key)

        return hashMapOf(
            "serviceKey" to authKey,
            "returnType" to "json",
            "numOfRows" to "100",
            "pageNo" to "1",
            "sidoName" to sido,
            "ver" to "1.0"
        )
    }

    fun getGrade(value: String): Int {
        val mValue = value.toInt()
        var grade = 1

        grade = if (mValue >= 0 && mValue <= 30) {
            1
        } else if (mValue >= 31 && mValue <= 80) {
            2
        } else if (mValue >= 81 && mValue <= 100) {
            3
        } else 4

        return grade
    }
}