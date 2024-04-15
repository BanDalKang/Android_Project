package com.example.aboutfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.aboutfragment.databinding.FragmentFirstBinding

private const val ARG_PARAM1 = "param1"

class FirstFragment : Fragment() {
    private var param1: String? = null
    private val binding by lazy { FragmentFirstBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // [1] Activity -> FirstFragment
        binding.tvFrag1Text.text = param1

        // [2] Fragment -> Fragment
        binding.btnSendFrag2.setOnClickListener{
            val dataToSend = "Hello Fragment2! \n From Fragment1"
            val fragment2 = SecondFragment.newInstance(dataToSend)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment2)
                .addToBackStack(null)
                .commit()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            // [1] Activity -> FirstFragment
            FirstFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}