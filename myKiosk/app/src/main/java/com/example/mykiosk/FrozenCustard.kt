package com.example.mykiosk

class FrozenCustardMenu {
    val frozenCustardMenu = mutableListOf<myMenu>()

    fun init() {
        frozenCustardMenu.add(myMenu("Shack-made Vanilla", 3.9, "오리지널 바닐라 플레이버의 아이스크림"))
        frozenCustardMenu.add(myMenu("Chocolate Chip Cookie Dough", 5.9, "초코칩과 쿠키 도우가 섞인 아이스크림"))
        frozenCustardMenu.add(myMenu("Shack Attack", 6.4, "초콜릿, 초콜릿 칩, 단맛이 어우러진 특별한 아이스크림"))
        frozenCustardMenu.add(myMenu("Black & White", 6.2, "바닐라 아이스크림에 초콜릿 시럽이 들어간 클래식 아이스크림"))
        frozenCustardMenu.add(myMenu("Concretes", 5.8, "다양한 토핑이 섞인 믹스드 아이스크림"))
    }

    fun displayMenu() {
        println("[ FrozenCustard MENU ]")
        for ((index, frozenCustard) in frozenCustardMenu.withIndex()) {
            frozenCustard.displayInfo(index + 1)
        }
        println("0. 뒤로가기 | 뒤로가기\n")
    }
}