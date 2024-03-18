package com.example.mykiosk

class Customer {
    var balance:Double
    var orders:MutableList<myMenu>

    constructor(_money:Double) {
        balance = _money
        orders = mutableListOf<myMenu>()
    }
}

// 장바구니 조회
fun displayOrderMenu(orders: MutableList<myMenu>) {
    println("[ Orders ]")
    for ((index, order) in orders.withIndex()) {
        order.displayInfo(index + 1)
    }
}

// 장바구니 총액 계산
fun calculateTotal(orders: MutableList<myMenu>): Double {
    var total = 0.0
    for (order in orders) {
        total += order.price
    }
    return total
}