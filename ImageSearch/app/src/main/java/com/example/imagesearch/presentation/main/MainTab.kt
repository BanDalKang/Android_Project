package com.example.imagesearch.presentation.main

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

data class MainTab (
    val fragment: Fragment,
    @StringRes val title: Int,
    val icon: Int
)