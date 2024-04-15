package com.example.mykiosk.correct

import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.util.Timer
import java.util.TimerTask

val menus: MutableList<Menu> = ArrayList()
val foods: MutableList<Food> = ArrayList()
val orders: MutableList<Order> = ArrayList()
var money: Double = 0.0
var now = LocalDateTime.now()
// 현재 시간의 연도와 달을 가져오는 법....
var start = LocalDateTime.of(now.year, now.month, now.dayOfMonth, 1, 1, 0)
var end = LocalDateTime.of(now.year, now.month, now.dayOfMonth, 1, 2, 0)

suspend fun main() {
    init()

    while(true) {
        displayMenu()

        var categorySelect = getPureNumber()
        if(categorySelect == 0) {
            println("프로그램을 3초뒤에 종료합니다")
            globalDelay(3000)
            return
        }

        var selectedObject = selectMenus(categorySelect)
        selectedObject?.let { obj ->
            addOrder(obj)
        } ?: run {
            println("\n현재 잔액: $money \n")
        }

    }
}

fun init() {
    money = 100.0

    menus.add(Menu("Burgers", "앵거스 비프 통살을 다져만든 버거"))
    menus.add(Menu("Forzen Custard", "매장에서 신선하게 만드는 아이스크림"))
    menus.add(Menu("Drinks", "매장에서 직접 만드는 음료"))
    menus.add(Menu("Beer", "뉴욕 브루클린 브루어리에서 양조한 맥주"))
    menus.add(Menu("Order", "주문"))
    menus.add(Menu("Cancel", "취소"))

    foods.add(Food("ShackBurger", 6.9, "Burgers", "토마토,양상추,쉑소스가 토핑된 치즈버거"))
    foods.add(Food("SmokeBurger", 8.9, "Burgers", "체리 페퍼에 쉑소스가 토핑된 치즈버거"))
    foods.add(Food("Shroom Burger", 9.4, "Burgers", "몬스터 치즈와 체다 치즈로 속을 채운 베지테리안 버거"))
    foods.add(Food("Cheese Burger", 6.9, "Burgers", "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거"))
    foods.add(Food("Hamburger", 5.4, "Burgers", "비프패티를 기반으로 야채가 들어간 기본버거"))

    foods.add(Food("Plain Ice Cream", 12.1, "Forzen Custard", "바닐라 아이스크림"))
    foods.add(Food("Chocolate Ice Cream", 10.2, "Forzen Custard", "초콜릿 아이스크림"))
    foods.add(Food("Fruits Ice Cream", 15.14, "Forzen Custard", "과일 아이스크림"))
    foods.add(Food("Nuts Ice Cream", 15.14, "Forzen Custard", "아몬드 아이스크림"))
    foods.add(Food("Ice Milk", 9.9, "Forzen Custard", "저지방 아이스크림"))

    foods.add(Food("Ade", 7.5, "Drinks", "에이드"))
    foods.add(Food("Americano", 6.4, "Drinks", "아메리카노"))
    foods.add(Food("Beverage", 6.8, "Drinks", "음료수"))
    foods.add(Food("Black Tea", 7.7, "Drinks", "홍차"))
    foods.add(Food("Barley Tea", 8.9, "Drinks", "보리차"))

    foods.add(Food("Bokbunja", 16.2, "Beer", "복분자"))
    foods.add(Food("Bourbon", 19.2, "Beer", "버번위스키"))
    foods.add(Food("Cocktail", 15.4, "Beer", "칵테일"))
    foods.add(Food("Gin", 25.2, "Beer", "진"))
    foods.add(Food("Armand de Brignac", 999.99, "Beer", "아르망디 샴페인"))

    checkOrder()
}

fun displayMenu() {
    println("아래 메뉴판을 보시고 메뉴를 골라 입력해주세요")
    println("[MYSHOP MENU]")

    // 메뉴 이름의 여백을 맞추기 위한 작업
    // 가장 긴 이름의 길이 얻옴
    val maxNameLength = menus.maxOfOrNull { it.name.length } ?: 0
    var menuSize = menus.size
    var count = 1
    for(idx in 1..menuSize) {
        val menu = menus[idx-1]
        val name = menu.name
        if(name == "Order") println("[ ORDER MENU ]")
        val desc = menu.description
        val padding = " ".repeat(maxNameLength - name.length)
        println("$idx. $name$padding | $desc")
        count++
    }
    println("0. 종료 | 프로그램 종료")
}

fun getPureNumber(): Int {
    var userInput: String?
    var number: Int?

    while(true) {
        print("번호를 입력해주세요")
        userInput = readLine()
        number = userInput?.toIntOrNull()

        if(number != null) {
            return number
        } else {
            println("올바른 숫자를 입력해주세요")
        }
    }
}

fun selectMenus(cateNumber: Int): Food? {
    var menu = menus[cateNumber-1]
    var categoryName = menu.name

    if(categoryName != "Order" && categoryName != "Cancel") {
        var filteredFoods = foods.filter { it.category == categoryName }
        displayMenuDetail(categoryName)

        while(true) {
            val selectFoodNumber = getPureNumber()
            if(selectFoodNumber > filteredFoods.size || selectFoodNumber < 0) {
                println("올바른 숫자를 입력해주세요")
            } else if(selectFoodNumber == 0) {
                return null
            } else {
                return filteredFoods[selectFoodNumber-1]
            }
        }
    } else { // ORDER관련 메뉴
        when(categoryName) {
            "Order" -> {
                var totalOrderPrice = displayOrderDetail()
                if(totalOrderPrice < 0.0) {
                    println("주문 내역이 존재하지 않습니다.")
                    return null
                }
                println("1. 주문\t\t 2. 메뉴판") // \t는 실제로 탭 간격만큼의 여백을 만들어내는 제어문자
                while(true) {
                    var selectOrderNumber = getPureNumber()
                    when(selectOrderNumber) {
                        1 -> {
                            var isMaintainance = isMaintainance()
                            if(isMaintainance.first) {
                                println("현재 시각은 ${isMaintainance.second.hour}시 ${isMaintainance.second.minute}분입니다.")
                                println("은행 점검 시간은 ${start.hour}시 ${start.minute}분 ~ ${end.hour}시 ${end.minute}분이므로 결제할 수 없습니다.")
                            } else if(money >= totalOrderPrice) {
                                orders.clear()
                                money -= totalOrderPrice
                                println("결제를 완료했습니다. ${isMaintainance.second.toString()}")
                            }
                            return null
                        }
                        2 -> {
                            println("메뉴판으로 이동합니다.")
                            return null
                        }
                        else -> {
                            println("올바른 숫자를 입력해주세요")
                        }

                    }
                }
            }
            "Cancel" -> {
                orders.clear()
                println("메뉴판으로 이동합니다.")
                return null
            }
            else -> {
                return null
            }
        }
    }

}

fun displayMenuDetail(categoryName: String) {
    println("\n[ $categoryName MENU ]")

    var filteredFoods = foods.filter { it.category == categoryName } // filter???

    // 메뉴 이름의 여백을 맞추기 위한 작업
    // 가장 긴 이름의 길이 얻옴
    val maxNameLength = filteredFoods.maxOfOrNull { it.name.toString().length } ?: 0
    val maxPriceLength = filteredFoods.maxOfOrNull { it.price.toString().length } ?: 0
    var foodSize = filteredFoods.size

    for(i in 1..foodSize) {
        val food = filteredFoods[i-1]
        val name = food.name
        val price = food.price
        val desc = food.description

        val namePadding = " ".repeat(maxNameLength - name.length)
        val pricePadding = " ".repeat(maxPriceLength - price.toString().length)
        println("$i. $name$namePadding | W $price$pricePadding | $desc")
    }
    val backPadding = " ".repeat(maxNameLength - "0. back".length)
    println("0. back$backPadding | 뒤로가기")
}

fun addOrder(food: Food) {
    food.displayInfo()
    println("위 메뉴를 장바구니에 추가하시겠습니까?")
    println("1. 확인\t\t 2. 취소")

    while(true) {
        var selectOrderNumber = getPureNumber()
        when(selectOrderNumber) {
            1 -> {
                orders.add(Order(food))
                println("${food.name}를 장바구니에 추가했습니다.")
                return
            }
            2 -> {
                println("구매를 취소했습니다.")
                return
            }
            else -> {
                println("올바른 숫자를 입력해주세요")
            }
        }
    }
}

fun displayOrderDetail(): Double {
    var orderSize = orders.size
    if(orderSize > 0) {
        println("\n아래와 같이 주문 하시겠습니까?\n")

        println("[ Orders ]")
        for(i in 0 until orderSize) {
            orders[i].food.displayInfo()
        }

        println("[ Total ]")
        // fold: 컬렉션의 각 요소를 순회하면서 어떤 연산을 수행하고 최종 결과값을 반환
        val totalOrderPrice = orders.fold(0.0) { accmulator, order ->
            accmulator + order.food.price
        }
        println("W $totalOrderPrice")
        return totalOrderPrice
    } else {
        return -1.0
    }
}

suspend fun globalDelay(time: Long) {
    delay(time)
}

fun isMaintainance(): Pair<Boolean, LocalDateTime> {
    var now = LocalDateTime.now()

    return Pair(now.toLocalTime() >= start.toLocalTime() && now.toLocalTime() <= end.toLocalTime(), now)
}

fun checkOrder() {
    var timer = Timer() // 새로운 개념...
    timer.schedule(object: TimerTask() {
        override fun run() {
            println("\n 현재 주문 대기수: ${orders.size}")
        }
    }, 0, 5000)
}