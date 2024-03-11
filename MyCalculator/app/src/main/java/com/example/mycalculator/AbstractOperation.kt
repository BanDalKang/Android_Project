package com.example.mycalculator

abstract class AbstractOperation {
    abstract val num1: Double
    abstract val num2: Double
    abstract fun operate(num1: Double, num2: Double): Double
}
