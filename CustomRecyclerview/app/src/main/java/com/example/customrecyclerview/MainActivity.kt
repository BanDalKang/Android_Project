package com.example.customrecyclerview;

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customrecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 데이터 원본 준비
        val dataList = mutableListOf<MyItem>()
        dataList.add(MyItem(R.drawable.cat_1, "Bella", "1"))
        dataList.add(MyItem(R.drawable.cat_2, "Charlie", "2"))
        dataList.add(MyItem(R.drawable.cat_3, "Daisy", "1.5"))
        dataList.add(MyItem(R.drawable.cat_4, "Duke", "1"))
        dataList.add(MyItem(R.drawable.cat_5, "Max", "2"))
        dataList.add(MyItem(R.drawable.cat_6, "Happy", "4"))
        dataList.add(MyItem(R.drawable.cat_7, "Luna", "3"))
        dataList.add(MyItem(R.drawable.cat_8, "Bob", "2"))
        dataList.add(MyItem(R.drawable.cat_9, "Bob", "2"))
        dataList.add(MyItem(R.drawable.cat_10, "Bob", "2"))

        binding.recyclerView.adapter = MyAdapter(dataList)

        val adapter = MyAdapter(dataList)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        adapter.itemClick = object : MyAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val name: String = dataList[position].aName
                Toast.makeText(this@MainActivity," $name 선택!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
