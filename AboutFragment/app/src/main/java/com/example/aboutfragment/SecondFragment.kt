package com.example.aboutfragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aboutfragment.databinding.FragmentSecondBinding

private const val ARG_PARAM1 = "param1"

interface FragmentDataListener {
    fun onDataReceived(data: String)
}

class SecondFragment : Fragment() {

    // [3] SecondFragment -> Activity
    private var param1: String? = null
    private var listener: FragmentDataListener? = null
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // [3] SecondFragment -> Activity
        if (context is FragmentDataListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement FragmentDataListener")
        }
    }

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
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // [2] Fragment -> Fragment
        binding.tvFrag2Text.text = param1

        // [3] SecondFragment -> Activity
        binding.btnSendActivity.setOnClickListener{
            val dataToSend = "Hello from SecondFragment!"
            listener?.onDataReceived(dataToSend)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            // [1] Activity -> FirstFragment
            SecondFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Binding 객체 해제
        _binding = null
        listener = null
    }
}