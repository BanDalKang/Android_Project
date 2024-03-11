package com.example.mycalculator

class DivideOperation(override val num1: Double, override val num2: Double) : AbstractOperation() {
    override fun operate(num1: Double, num2: Double): Double {
        // 0.0보다 크지 않을 경우 메세지를 이용하여 익셉션을 던진다
        require(num2>0.0) { "0으로 나눌 수 없습니다." }
        /*if (num2 == 0.0) {
            throw IllegalArgumentException("0으로 나눌 수 없습니다.")
        }*/
        return num1 / num2
    }
}