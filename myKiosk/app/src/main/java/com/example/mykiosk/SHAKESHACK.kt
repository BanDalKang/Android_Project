package com.example.mykiosk

abstract class SHAKESHACK {
    abstract val name: String
    abstract val price: Double
    abstract val description: String
    abstract fun displayInfo(index: Int): Unit
}