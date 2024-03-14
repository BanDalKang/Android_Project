package com.example.mykiosk

class Customer {
    var balance:Double
    var orders:MutableList<Menu>

    constructor(_money:Double) {
        balance = _money
        orders = mutableListOf<Menu>()
    }
}

// 장바구니 조회
fun displayOrderMenu(orders: MutableList<Menu>) {
    println("[ Orders ]")
    for ((index, order) in orders.withIndex()) {
        order.displayInfo(index + 1)
    }
}

// 장바구니 총액 계산
fun calculateTotal(orders: MutableList<Menu>): Double {
    var total = 0.0
    for (order in orders) {
        total += order.price
    }
    return total
}