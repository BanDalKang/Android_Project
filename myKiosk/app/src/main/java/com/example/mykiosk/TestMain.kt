package com.example.mykiosk

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.concurrent.thread

fun main() {
    testMain()
}

var isBasketEmpty: Boolean = true // 장바구니가 비어있는 지 확인하는 플래그
@Volatile var orderQueue: Int = 0 // 현재 주문 대기수
val bankMaintenanceStartTime = LocalTime.of(22, 15) //은행점검 시작시간
val bankMaintenanceEndTime = LocalTime.of(23, 0) // 은행점검 종료시간

fun testMain() {
    // 손님의 보유 금액 입력 받기
    var balance = inputCustomerInfo("money").toString().toDouble()
    var curCustomer = Customer(balance)

    println("[현재 보유 금액]: W $balance")

    while (true) {
        println()
        println("아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.")
        println("[ SHAKESHACK MENU ]")
        println("1. Burgers         | 앵거스 비프 통살을 다져만든 버거")
        println("2. Frozen Custard  | 매장에서 신선하게 만드는 아이스크림")
        println("3. Drinks          | 매장에서 직접 만드는 음료")
        println("4. Beer            | 뉴욕 브루클린 브루어리에서 양조한 맥주")
        println("0. 종료             | 프로그램 종료\n")

        if(!isBasketEmpty){
            println("[ ORDER MENU ]")
            println("5. Order       | 장바구니를 확인 후 주문합니다.")
            println("6. Cancel      | 진행중인 주문을 취소합니다.")
        }

        var selectNumber= inputCustomerInfo("selectNumber").toString()
        when (selectNumber) {
            "1" -> {
                val menu = BurgerMenu()
                menu.init()
                menu.displayMenu()
                // 버거 숫자 선택
                val burgerIdx = selectMenu(menu.burgersMenu.size)
                if (burgerIdx == 0){
                    continue
                } else {
                    val selectedBurger = menu.burgersMenu[burgerIdx!!-1]
                    addBasket(curCustomer, selectedBurger)
                }

            }
            "2" -> {
                val menu = FrozenCustardMenu()
                menu.init()
                menu.displayMenu()
                // 버거 숫자 선택
                val frozenCustardIdx = selectMenu(menu.frozenCustardMenu.size)
                if (frozenCustardIdx == 0){
                    continue
                } else {
                    val selectedFrozenCustard = menu.frozenCustardMenu[frozenCustardIdx!!-1]
                    addBasket(curCustomer, selectedFrozenCustard)
                }

            }
            "5" -> {
                if (!isBasketEmpty){
                    checkOrder(curCustomer)
                    if (!isMaintenanceTime()){
                        isBasketEmpty=true
                    } else{

                    }
                } else {
                    println("잘못된 입력입니다. 다시 입력해주세요.")
                    continue
                }
            }
            "6" -> {
                if (!isBasketEmpty){
                    println("진행중인 주문을 취소합니다.")
                    curCustomer.orders.clear()
                    isBasketEmpty=true
                } else {
                    println("잘못된 입력입니다. 다시 입력해주세요.")
                    continue
                }
            }
            "0" -> {
                println("프로그램 종료")
                return
            }
            else -> {
                println("잘못된 입력입니다. 다시 입력해주세요.")
            }
        }
    }
}

// 현재 손님의 정보 등 입력 받는 함수
fun inputCustomerInfo(type:String): Any? {
    return when(type) {
        "money" -> {
            print("보유 금액을 입력하세요(W 1.0 = 1000원): ")
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

// 메뉴 선택하는 함수
fun selectMenu(listSize:Int): Int? {
    // 해당 리스트의 인덱스 에외처리해서 입력 받기
    while (true) {
        try {
            print("메뉴 선택: ")
            // 인덱스 입력 받아서 버거 리스트 요소에 접근
            val burgerIdx = inputCustomerInfo("selectNumber").toString().toInt()

            if (burgerIdx in 0..listSize) {
                return burgerIdx
            } else {
                println("유효한 번호를 선택해주세요.")
            }
        } catch (e: Exception) {
            println("숫자를 다시 선택해주세요.")
        }
    }
}

// 장바구니에 추가하는 함수
fun addBasket(customer: Customer,selectedMenu: Menu) {
    // 해당 리스트의 인덱스 에외처리해서 입력 받기
    while (true) {
        try {
            println("${selectedMenu.name} | W ${"%.1f".format(selectedMenu.price)} | ${selectedMenu.description}")
            println("위 메뉴를 장바구니에 추가하시겠습니까?")
            println("1. 확인        2. 취소")

            when (inputCustomerInfo("selectNumber").toString()) {
                "1" -> {
                    customer!!.orders.add(selectedMenu)
                    isBasketEmpty=false
                    println("${selectedMenu.name} <- 장바구니에 추가했습니다.")
                    break
                }
                "2" -> {
                    break
                }
                else -> {
                    println("유효한 번호를 선택해주세요.")
                }
            }
        } catch (e: Exception) {
            println("숫자를 다시 선택해주세요.")
        }
    }
}


// 현재 손님의 주문 메뉴 결제하는 함수
fun toPay(customer:Customer, amount: Double){
    var pay = Pay.getInstance()
    println("결제 진행 중입니다...")
    Thread.sleep(3000) // 3초 동안 메인 스레드를 멈춤
    val paymentSuccess = pay.payOrder(customer, amount)
    if (paymentSuccess){
        val paymentCompletionTime = getCurrentTime()
        println("결제를 완료했습니다. ($paymentCompletionTime)")
        customer.orders.clear() // 장바구니 비우기
    }
    orderQueue--
}

// 현재 주문 대기 수 보여주는 함수
fun displayOrderQueue() {
    thread(start = true) {
        while (isMaintenanceTime()) { // 은행 점검시간이 끝날 때까지
            println("(현재 주문 대기수: $orderQueue)\n")
            runBlocking {
                launch {
                    delay(5000)
                }
            }
        }
    }
}

// 주문 전 장바구니 확인하는 함수
fun checkOrder(customer:Customer) {
    println("\n아래와 같이 주문 하시겠습니까?")
    displayOrderQueue()
    displayOrderMenu(customer.orders)
    val totalAmount = calculateTotal(customer.orders)
    println("\n[ Total ]")
    println("W ${"%.1f".format(totalAmount)}\n")
    println("1. 주문        2. 메뉴판")
    when (inputCustomerInfo("selectNumber").toString().toInt()) {
        1 -> {
            orderQueue++
            if(!isMaintenanceTime()){
                toPay(customer, totalAmount)
            } else{
                val formatter = DateTimeFormatter.ofPattern("H시 m분")
                val currentTime = LocalTime.now()
                val formattedCurrentTime = currentTime.format(formatter)
                val formattedStartTime = bankMaintenanceStartTime.format(formatter)
                val formattedEndTime = bankMaintenanceEndTime.format(formatter)
                println("현재 시각은 $formattedCurrentTime 입니다.")
                println("은행 점검 시간은 $formattedStartTime ~ $formattedEndTime 이므로 결제할 수 없습니다.")
                displayOrderQueue()
            }
        }
        2 -> {

        }
        else -> println("유효한 번호를 선택해주세요.")
    }
}

// 은행 점검 기산인지 확인하는 함수
fun isMaintenanceTime():Boolean  {
    val currentTime = LocalTime.now()

    return currentTime in bankMaintenanceStartTime..bankMaintenanceEndTime
}

fun getCurrentTime(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val currentTime = Date()
    return formatter.format(currentTime)
}