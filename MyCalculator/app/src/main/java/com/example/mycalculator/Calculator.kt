package com.example.mycalculator

object Calculator {
    fun calculate(num1: Double, num2: Double, operation: AbstractOperation): Double {
        return operation.operate(num1, num2)
    }
}