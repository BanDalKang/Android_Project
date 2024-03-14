package com.example.mykiosk

class BurgerMenu {
    val burgersMenu = mutableListOf<Menu>()

    fun init() {
        burgersMenu.add(Menu("ShackBurger", 6.9, "토마토, 양상추, 쉑소스가 토핑된 치즈버거"))
        burgersMenu.add(Menu("SmokeShack", 8.9, "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거"))
        burgersMenu.add(Menu("Showroom Burger", 9.4, "몬스터 치즈와 체다 치즈로 속을 채운 베지테리안 버거"))
        burgersMenu.add(Menu("Cheeseburger", 6.9, "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거"))
        burgersMenu.add(Menu("Hamburger", 5.4, "비프패티를 기반으로 야채가 들어간 기본버거"))
    }

    fun displayMenu() {
        println("[ Burgers MENU ]")
        for ((index, menuItem) in burgersMenu.withIndex()) {
            menuItem.displayInfo(index + 1)
        }
        println("0. 뒤로가기 | 뒤로가기\n")
    }
}