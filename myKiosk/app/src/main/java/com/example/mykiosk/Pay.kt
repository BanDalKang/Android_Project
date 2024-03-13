package com.example.mykiosk

import java.math.RoundingMode

class Pay private constructor() {
    companion object {
        @Volatile private var instance: Pay? = null

        fun getInstance(): Pay {
            // 외부에서 요청왔을때 instance가 null인지 검증
            if(instance == null) {
                // synchronized로 외부 쓰레드의 접근을 막음
                synchronized(this) {
                    instance = Pay()
                }
            }
            return instance!!
        }
    }

    fun payOrder(customer:Customer, orderedMenu:SHAKESHACK){
        if(customer.balance >= orderedMenu.price) {
            // customer.balance - orderedMenu.price 부동 소수점 문제 발생해서 수정
            println("[구매 후 잔액]: [${customer.balance} - ${orderedMenu.price}] = W ${(customer.balance - orderedMenu.price).toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()}")
            customer.balance = (customer.balance - orderedMenu.price).toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
            customer.orders.add(orderedMenu.name)
        } else {
            println("돈이 부족합니다...\n[현재 보유 금액]: W ${customer.balance}")
        }
    }
}