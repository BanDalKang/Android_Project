package com.example.mykiosk.correct;

class Food(name: String, price: Double, category: String, description: String): Menu(name, description) {
    var price: Double
    var category: String

    init {
        this.price = price
        this.category = category
    }

    override fun displayInfo() {
        println("카테고리: $category, 가격: $price, 이름: $name, 설명: [ $description ]")
    }
}