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

    fun payOrder(customer: Customer, amount: Double): Boolean {
        val paymentSuccess: Boolean
        if (customer.balance >= amount) {
            val remainingMoney = (customer.balance - amount).toBigDecimal().setScale(2, RoundingMode.HALF_UP)
            println("[구매 후 잔액]: [${customer.balance} - ${amount}] = W $remainingMoney")
            customer.balance = remainingMoney.toDouble()
            paymentSuccess = true
        } else {
            val lackingMoney = (amount - customer.balance).toBigDecimal().setScale(2, RoundingMode.HALF_UP)
            println("[현재 보유 금액]: W ${customer.balance}\nW $lackingMoney 만큼 부족해서 주문할 수 없습니다.\n")
            paymentSuccess = false
        }
        return paymentSuccess
    }
}