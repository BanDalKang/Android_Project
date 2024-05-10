package com.example.imagesearch.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesearch.Constants.Companion.MAX_PAGE_COUNT_IMAGE
import com.example.imagesearch.Constants.Companion.MAX_PAGE_COUNT_VIDEO
import com.example.imagesearch.R
import com.example.imagesearch.data.repository.SearchRepository
import com.example.imagesearch.domain.SearchModel
import com.example.imagesearch.domain.SearchPageCountUiState
import com.example.imagesearch.domain.SearchUiState
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _searchResult = MutableLiveData(SearchUiState.init())
    val searchResult: LiveData<SearchUiState> get() = _searchResult

    private var _pageCounts = MutableLiveData(SearchPageCountUiState.init())
    val pageCounts: LiveData<SearchPageCountUiState> get() = _pageCounts

    private val _searchWord = MutableLiveData<String>()
    val searchWord: LiveData<String> get() = _searchWord

    init {
        getStorageSearchWord()
    }

    fun saveStorageSearchWord(query: String) = viewModelScope.launch {
        searchRepository.saveSearchData(query)
        _searchWord.value = query
    }

    private fun getStorageSearchWord() = viewModelScope.launch {
        _searchWord.value = searchRepository.loadSearchData().orEmpty()
    }

    fun searchCombinedResults(query: String) = viewModelScope.launch {
        try {
            val pageCounts = _pageCounts.value ?: SearchPageCountUiState.init()

            val (imageResponse, videoResponse) =
                searchRepository.searchCombinedResults(
                    query = query,
                    imagePage = pageCounts.imagePageCount,
                    videoPage = pageCounts.videoPageCount
                )

            _searchResult.value = SearchUiState(
                list = (imageResponse.list + videoResponse.list).sortedByDescending { it.datetime }
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun reloadStorageItems() = viewModelScope.launch {
        val storageItems = searchRepository.getStorageItems()

        _searchResult.value = _searchResult.value?.copy(
            showSnackMessage = false,
            list = _searchResult.value?.list?.map { currentItem ->
                currentItem.copy(bookmarked = storageItems.any { it.id == currentItem.id })
            } ?: emptyList()
        )
    }

    private fun saveStorageImage(searchModel: SearchModel) = viewModelScope.launch {
        searchRepository.saveStorageItem(searchModel)

        updateSnackMessage(R.string.snack_image_save)
    }

    private fun removeStorageItem(searchModel: SearchModel) = viewModelScope.launch {
        searchRepository.removeStorageItem(searchModel)

        updateSnackMessage(R.string.snack_image_delete)
    }

    private fun updateSnackMessage(snackMessage: Int) {
        _searchResult.value = _searchResult.value?.copy(
            showSnackMessage = true,
            snackMessage = snackMessage
        )
    }

    fun updateStorageItem(searchModel: SearchModel) {
        val updatedItem = searchModel.copy(bookmarked = searchModel.bookmarked.not())

        viewModelScope.launch {
            if (updatedItem.bookmarked) {
                saveStorageImage(updatedItem)
            } else {
                removeStorageItem(updatedItem)
            }

            _searchResult.value = _searchResult.value?.copy(
                list = _searchResult.value?.list?.map {
                    if (it.id == updatedItem.id) updatedItem else it
                } ?: emptyList()
            )
        }
    }

    fun resetPageCount() {
        _pageCounts.value = SearchPageCountUiState.init()
    }

    fun plusPageCount() {
        val currentCounts = _pageCounts.value ?: SearchPageCountUiState.init()

        val imageCount = if (currentCounts.imagePageCount < MAX_PAGE_COUNT_IMAGE)
            currentCounts.imagePageCount + 1
        else 1

        val videoCount = if (currentCounts.videoPageCount < MAX_PAGE_COUNT_VIDEO)
            currentCounts.videoPageCount + 1
        else 1

        _pageCounts.value = SearchPageCountUiState(
            imagePageCount = imageCount,
            videoPageCount = videoCount
        )
    }
}