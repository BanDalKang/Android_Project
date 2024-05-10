package com.example.imagesearch.presentation.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearch.data.repository.SearchRepository
import com.example.imagesearch.data.repository.SearchRepositoryImpl
import com.example.imagesearch.databinding.FragmentSearchListBinding
import com.example.imagesearch.network.RetrofitClient
import com.google.android.material.snackbar.Snackbar

class SearchListFragment : Fragment() {
    companion object {
        fun newInstance() = SearchListFragment()
    }

    private var _binding: FragmentSearchListBinding? = null

    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels {
        SearchViewModelFactory(
            SearchRepositoryImpl(requireActivity(), RetrofitClient.searchNetwork)
        )
    }

    private val searchListAdapter by lazy {
        SearchListAdapter(
            itemClickListener = {
                viewModel.updateStorageItem(it)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.reloadStorageItems()
    }

    private fun initView() {
        initSearchView()

        initRecyclerView()
    }

    private fun initRecyclerView() = with(binding) {
        recyclerSearch.adapter = searchListAdapter

        recyclerSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (binding.recyclerSearch.canScrollVertically(1).not()
                    && newState == RecyclerView.SCROLL_STATE_IDLE
                ) {
                    viewModel.plusPageCount()
                }
            }
        })
    }

    private fun initViewModel() = with(viewModel) {
        searchResult.observe(viewLifecycleOwner) {
            searchListAdapter.submitList(it.list)

            if (it.showSnackMessage) {
                it.snackMessage?.let { resId ->
                    showSnackBar(resId)
                }
            }
        }

        searchWord.observe(viewLifecycleOwner) {
            binding.searchView.setQuery(it, false)
        }

        pageCounts.observe(viewLifecycleOwner) {
            val query = binding.searchView.query.toString()
            viewModel.searchCombinedResults(query)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onStop() {
        val query = binding.searchView.query.toString()
        viewModel.saveStorageSearchWord(query)
        super.onStop()
    }

    private fun initSearchView() = with(binding) {
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.resetPageCount()
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }

    private fun showSnackBar(resId: Int) {
        Snackbar.make(
            binding.searchFragment,
            getString(resId),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    fun smoothScrollToTop() =
        binding.recyclerSearch.smoothScrollToPosition(0)

}

class SearchViewModelFactory(
    private val searchRepository: SearchRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(
                searchRepository
            ) as T
        }
        throw IllegalArgumentException("ViewModel class not found")
    }
}