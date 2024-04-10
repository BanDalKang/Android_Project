package com.example.aboutfragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.aboutfragment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), FragmentDataListener {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.run {
            fragment1Btn.setOnClickListener{
                // [1] Activity -> FirstFragment
                val dataToSend = "Hello First Fragment! \n From Activity"
                val fragment = FirstFragment.newInstance(dataToSend)
                setFragment(fragment)
            }

            fragment2Btn.setOnClickListener {
                // [1] Activity -> SecondFragment
                val dataToSend = "Hello Second Fragment!\n From Activity"
                val fragment = SecondFragment.newInstance(dataToSend)
                setFragment(fragment)
            }
        }

        setFragment(FirstFragment())
    }

    private fun setFragment(frag : Fragment) {
        supportFragmentManager.commit {
            replace(R.id.frameLayout, frag)
            setReorderingAllowed(true)
            addToBackStack("")
        }
    }

    // [3] SecondFragment -> Activity
    override fun onDataReceived(data: String) {
        // Fragment에서 받은 데이터를 처리
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show()
    }
}