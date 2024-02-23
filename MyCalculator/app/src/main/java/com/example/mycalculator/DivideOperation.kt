package com.example.mycalculator

class DivideOperation {
    fun operate(num1: Double, num2: Double): Double {
        if (num2 != 0.0) {
            return num1 / num2
        } else {
            throw ArithmeticException("Cannot divide by zero")
        }
    }
}