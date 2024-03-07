package com.example.mycalculator

class DivideOperation : AbstractOperation() {
    override fun operate(num1: Double, num2: Double): Double {
        if (num2 == 0.0) {
            throw IllegalArgumentException("0으로 나눌 수 없습니다.")
        }
        return num1 / num2
    }
}