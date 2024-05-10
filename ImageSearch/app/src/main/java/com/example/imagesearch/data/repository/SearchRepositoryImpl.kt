package com.example.imagesearch.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.imagesearch.Constants
import com.example.imagesearch.data.model.ImageDocumentResponse
import com.example.imagesearch.data.model.SearchResponse
import com.example.imagesearch.data.model.VideoDocumentResponse
import com.example.imagesearch.data.remote.SearchRemoteDatasource
import com.example.imagesearch.domain.SearchListType
import com.example.imagesearch.domain.SearchModel
import com.example.imagesearch.domain.SearchUiState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class SearchRepositoryImpl(
    context: Context,
    private val remoteDatasource: SearchRemoteDatasource,
    ) : SearchRepository {

    override suspend fun searchImage(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): SearchResponse<ImageDocumentResponse> {
        return remoteDatasource.getSearchImage(query, sort, page, size)
    }

    override suspend fun searchVideo(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): SearchResponse<VideoDocumentResponse> {
        return remoteDatasource.getSearchVideo(query, sort, page, size)
    }

    private val pref: SharedPreferences = context.getSharedPreferences(Constants.PREFERENCE_NAME, 0)

    override suspend fun saveStorageItem(searchModel: SearchModel) {
        val favoriteItems = getPrefsStorageItems().toMutableList()
        val findItem = favoriteItems.find { it.id == searchModel.id }

        if (findItem == null) {
            favoriteItems.add(searchModel)
            savePrefsStorageItems(favoriteItems)
        }
    }

    override suspend fun removeStorageItem(searchModel: SearchModel) {
        val favoriteItems = getPrefsStorageItems().toMutableList()
        favoriteItems.removeAll { it.id == searchModel.id }
        savePrefsStorageItems(favoriteItems)
    }

    override suspend fun getStorageItems(): List<SearchModel> {
        return getPrefsStorageItems()
    }

    override suspend fun searchCombinedResults(
        query: String,
        imagePage: Int,
        videoPage: Int
    ): Pair<SearchUiState, SearchUiState> = coroutineScope {
        val imageDeferred = async {
            try {
                val response = searchImage(query = query, page = imagePage)
                SearchUiState(list = response.documents?.map {
                    SearchModel(
                        thumbnailUrl = it.thumbnailUrl,
                        siteName = it.displaySitename,
                        datetime = it.datetime,
                        itemType = SearchListType.IMAGE
                    )
                } ?: emptyList())
            } catch (e: Exception) {
                throw e
            }
        }

        val videoDeferred = async {
            try {
                val response = searchVideo(query = query, page = videoPage)
                SearchUiState(list = response.documents?.map {
                    SearchModel(
                        thumbnailUrl = it.thumbnail,
                        siteName = it.title,
                        datetime = it.datetime,
                        itemType = SearchListType.VIDEO
                    )
                } ?: emptyList())
            } catch (e: Exception) {
                throw e
            }
        }

        Pair(imageDeferred.await(), videoDeferred.await())
    }

    private fun getPrefsStorageItems(): List<SearchModel> {
        val jsonString = pref.getString(Constants.STORAGE_ITEMS, "")
        return if (jsonString.isNullOrEmpty()) {
            emptyList()
        } else {
            Gson().fromJson(jsonString, object : TypeToken<List<SearchModel>>() {}.type)
        }
    }

    private fun savePrefsStorageItems(items: List<SearchModel>) {
        val jsonString = Gson().toJson(items)
        pref.edit().putString(Constants.STORAGE_ITEMS, jsonString).apply()
    }

    override suspend fun saveSearchData(searchWord: String) {
        pref.edit {
            putString(Constants.SEARCH_WORD, searchWord)
        }
    }

    override suspend fun loadSearchData(): String? =
        pref.getString(Constants.SEARCH_WORD, "")

}