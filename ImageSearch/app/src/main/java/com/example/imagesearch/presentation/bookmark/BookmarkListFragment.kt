package com.example.imagesearch.presentation.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.imagesearch.data.repository.SearchRepository
import com.example.imagesearch.data.repository.SearchRepositoryImpl
import com.example.imagesearch.databinding.FragmentBookmarkListBinding
import com.example.imagesearch.domain.SearchModel
import com.example.imagesearch.network.RetrofitClient

class BookmarkListFragment : Fragment() {
    companion object {
        fun newInstance() = BookmarkListFragment()
    }

    private var _binding: FragmentBookmarkListBinding? = null

    private val binding get() = _binding!!

    private val viewModel: BookmarkViewModel by viewModels {
        StorageViewModelFactory(
            SearchRepositoryImpl(requireActivity(), RetrofitClient.searchNetwork)
        )
    }

    private val storageListAdapter by lazy {
        BookmarkListAdapter(
            itemClickListener = {
                showBottomSheetDialog(it)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarkListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getStorageItems()
    }

    private fun initView() {
        binding.recyclerFavorite.adapter = storageListAdapter
    }

    private fun initViewModel() = with(viewModel) {
        storageItems.observe(viewLifecycleOwner) {
            storageListAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun smoothScrollToTop() {
        binding.recyclerFavorite.smoothScrollToPosition(0)
    }

    private fun showBottomSheetDialog(searchModel: SearchModel) {
        BottomSheetDialog(
            onRemoveClickListener = { removedItem ->
                viewModel.removeStorageItem(removedItem)
            },
            searchModel = searchModel
        ).show(requireActivity().supportFragmentManager, null)
    }
}

class StorageViewModelFactory(
    private val searchRepository: SearchRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookmarkViewModel::class.java)) {
            return BookmarkViewModel(
                searchRepository
            ) as T
        }
        throw IllegalArgumentException("ViewModel class not found")
    }
}