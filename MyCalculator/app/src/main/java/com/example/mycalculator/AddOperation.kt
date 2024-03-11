package com.example.mycalculator

class AddOperation(override val num1: Double, override val num2: Double) : AbstractOperation() {
    override fun operate(num1: Double, num2: Double): Double {
        return num1 + num2
    }
}