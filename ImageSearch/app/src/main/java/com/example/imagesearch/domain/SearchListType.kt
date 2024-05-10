package com.example.imagesearch.domain

enum class SearchListType {
    IMAGE,
    VIDEO;

    companion object {
        fun from(type: SearchListType): String = when (type) {
            IMAGE -> "이미지"
            VIDEO -> "비디오"
        }
    }
}