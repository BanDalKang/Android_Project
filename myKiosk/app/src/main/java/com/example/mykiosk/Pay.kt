package com.example.mykiosk

class Pay private constructor() {
    companion object {
        @Volatile private var instance: Pay? = null

        fun getInstance(): Pay {
            // 외부에서 요청왔을때 instance가 null인지 검증
            if(instance == null) {
                // synchronized로 외부 쓰레드의 접근을 막음
                // 쓰레드는 다음챕터에서 소개합니다!
                // 쓰레드간의 객체상태 혼돈을 막기위해 사용한다고 이해해주세요
                synchronized(this) {
                    instance = Pay()
                }
            }
            return instance!!
        }
    }

    fun payOrder(customer:Customer, orderedMenu:SHAKESHACK){
        if(customer.balance >= orderedMenu.price) {
            println("[구매 후 금액]: [${customer.balance} - ${orderedMenu.price}] = ${customer.balance-orderedMenu.price}")
            customer.balance -= orderedMenu.price
            customer.orders.add(orderedMenu.name)
        } else {
            println("돈이 부족합니다.")
        }
    }
}