package com.example.imagesearch.data.repository

import com.example.imagesearch.Constants.Companion.MAX_SIZE_IMAGE
import com.example.imagesearch.Constants.Companion.MAX_SIZE_VIDEO
import com.example.imagesearch.Constants.Companion.SORT_TYPE
import com.example.imagesearch.data.model.ImageDocumentResponse
import com.example.imagesearch.data.model.SearchResponse
import com.example.imagesearch.data.model.VideoDocumentResponse
import com.example.imagesearch.domain.SearchModel
import com.example.imagesearch.domain.SearchUiState
import retrofit2.http.Query

interface SearchRepository {

    suspend fun searchImage(
        query: String,
        sort: String = SORT_TYPE,
        page: Int,
        size: Int = MAX_SIZE_IMAGE
    ): SearchResponse<ImageDocumentResponse>

    suspend fun searchVideo(
        @Query("query") query: String,
        @Query("sort") sort: String = SORT_TYPE,
        @Query("page") page: Int,
        @Query("size") size: Int = MAX_SIZE_VIDEO
    ): SearchResponse<VideoDocumentResponse>


    suspend fun saveStorageItem(searchModel: SearchModel)

    suspend fun removeStorageItem(searchModel: SearchModel)

    suspend fun getStorageItems(): List<SearchModel>

    suspend fun searchCombinedResults(
        query: String,
        imagePage: Int,
        videoPage: Int
    ): Pair<SearchUiState, SearchUiState>

    suspend fun saveSearchData(searchWord: String)

    suspend fun loadSearchData(): String?
}