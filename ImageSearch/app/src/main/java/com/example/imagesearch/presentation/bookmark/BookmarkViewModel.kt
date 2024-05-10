package com.example.imagesearch.presentation.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesearch.data.repository.SearchRepository
import com.example.imagesearch.domain.SearchModel
import kotlinx.coroutines.launch

class BookmarkViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _storageItems = MutableLiveData<List<SearchModel>>()
    val storageItems: LiveData<List<SearchModel>> get() = _storageItems

    fun removeStorageItem(searchModel: SearchModel) = viewModelScope.launch {
        searchRepository.removeStorageItem(searchModel)
        getStorageItems()
    }

    fun getStorageItems() = viewModelScope.launch {
        val images = searchRepository.getStorageItems()
        _storageItems.value = images
    }
}