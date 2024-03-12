package com.example.mykiosk

class Customer {
    var balance:Double
    var orders:MutableList<String>

    constructor(_money:Double) {
        balance = _money
        orders = mutableListOf<String>()
    }
}