package com.example.mykiosk

class Burger(private val burgerName: String, private val burgerPrice: Double, private val burgerDescription: String) : SHAKESHACK() {
    override val name: String
        get() = burgerName

    override val price: Double
        get() = burgerPrice

    override val description: String
        get() = burgerDescription

    override fun displayInfo(index: Int) {
        println("$index. $burgerName | W ${"%.1f".format(burgerPrice)} | $burgerDescription")
    }
}

class BurgerMenu {
    val burgersMenu = mutableListOf<Burger>()

    fun init() {
        burgersMenu.add(Burger("ShackBurger", 6.9, "토마토, 양상추, 쉑소스가 토핑된 치즈버거"))
        burgersMenu.add(Burger("SmokeShack", 8.9, "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거"))
        burgersMenu.add(Burger("Showroom Burger", 9.4, "몬스터 치즈와 체다 치즈로 속을 채운 베지테리안 버거"))
        burgersMenu.add(Burger("Cheeseburger", 6.9, "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거"))
        burgersMenu.add(Burger("Hamburger", 5.4, "비프패티를 기반으로 야채가 들어간 기본버거"))
    }

    fun displayMenu() {
        println("[ Burgers MENU ]")
        for ((index, burger) in burgersMenu.withIndex()) {
            burger.displayInfo(index + 1)
        }
        println("0. 뒤로가기 | 뒤로가기")
    }
}