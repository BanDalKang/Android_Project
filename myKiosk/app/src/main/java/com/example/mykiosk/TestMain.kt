package com.example.mykiosk

import java.util.Scanner

fun main() {
    testMain()
}

fun testMain() {
    val scanner = Scanner(System.`in`)

    // 손님의 보유 금액 입력 받기
    var balance = inputCustomerInfo("money").toString().toDouble()
    var curCustomer = Customer(balance)

    println("현재 보유 금액: W $balance")

    while (true) {
        println()
        println("아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.")
        println("[ SHAKESHACK MENU ]")
        println("1. Burgers         | 앵거스 비프 통살을 다져만든 버거")
        println("2. Frozen Custard  | 매장에서 신선하게 만드는 아이스크림")
        println("3. Drinks          | 매장에서 직접 만드는 음료")
        println("4. Beer            | 뉴욕 브루클린 브루어리에서 양조한 맥주")
        println("0. 종료             | 프로그램 종료\n")
        print("메뉴 선택: ")

        var selectNumber= inputCustomerInfo("selectNumber").toString()
        when (selectNumber) {
            "1" -> {
                val menu = BurgerMenu()
                menu.init()
                menu.displayMenu()
                // 버거 숫자 선택 /////////
                val burgerIdx = selectMenu(menu.burgersMenu.size)
                if (burgerIdx == 0){
                    continue
                } else {
                    val selectedBurger = menu.burgersMenu[burgerIdx!!-1]
                    toPay(curCustomer, selectedBurger)
                }

            }
            "2" -> {
                val menu = FrozenCustardMenu()
                menu.init()
                menu.displayMenu()
                print("FrozenCustard 메뉴 선택: ")

            }
            "0" -> {
                println("프로그램 종료")
                return
            }
            else -> {
                println("잘못된 입력입니다. 0부터 4 사이의 숫자를 입력해주세요.")
            }
        }
    }
    scanner.close()
}

// 현재 손님의 정보 등 입력 받는 함수
fun inputCustomerInfo(type:String): Any? {
    return when(type) {
        "money" -> {
            println("보유 금액을 입력하세요(W 1.0 = 1000원): ")
            // 바르게 입력할 때까지 반복 & 예외 처리
            while (true) {
                try {
                    var moneyInput = readLine()?.toDouble()
                    if (moneyInput != null && moneyInput >= 0) {
                        return moneyInput
                    } else {
                        println("보유 금액을 다시 입력해주세요")
                    }
                } catch (e: Exception) {
                    println("보유 금액을 다시 입력해주세요")
                }
            }
        }
        "selectNumber" -> {
            while(true) {
                try {
                    var selectNumber:String? = readLine()
                    return selectNumber?.toInt() ?: -1
                } catch(e:Exception) {
                    println("번호를 다시 선택해주세요")
                }
            }
        }
        else -> {}
    }
}

//
fun selectMenu(listSize:Int): Int? {
    // 해당 리스트의 인덱스 에외처리해서 입력 받기
    while (true) {
        try {
            print("메뉴 선택: ")
            // 인덱스 입력 받아서 버거 리스트 요소에 접근
            val burgerIdx = inputCustomerInfo("selectNumber").toString().toInt()

            if(burgerIdx==0){
                return 0  // 선택이 취소된 경우 null 반환
            }
            if (burgerIdx in 1..listSize) {
                return burgerIdx
            } else {
                println("유효한 번호를 선택해주세요.")
            }
        } catch (e: Exception) {
            println("숫자를 다시 선택해주세요.")
        }
    }
}

// 현재 손님의 주문 메뉴 결제하는 함수
fun toPay(customer:Customer, orderedMenu:SHAKESHACK){
    var pay = Pay.getInstance()
    pay.payOrder(customer, orderedMenu)
    println("주문한 메뉴: ${customer.orders}")
}