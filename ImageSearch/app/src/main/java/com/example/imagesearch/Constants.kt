package com.example.imagesearch

class Constants {
    companion object {
        // retrofit
        const val BASE_URL = "https://dapi.kakao.com"
        const val AUTH_KEY = "b11ad40fcf6f806b33183c7a0317932e"
        // preference
        const val SEARCH_WORD = "search_keyword"
        const val PREFERENCE_NAME = "searchPreference"
        const val STORAGE_ITEMS = "favorite_items"

        const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

        // page count
        const val MAX_PAGE_COUNT_IMAGE = 50
        const val MAX_PAGE_COUNT_VIDEO = 15
        const val MAX_SIZE_IMAGE = 80
        const val MAX_SIZE_VIDEO = 30
        const val SORT_TYPE = "accuracy"
    }
}