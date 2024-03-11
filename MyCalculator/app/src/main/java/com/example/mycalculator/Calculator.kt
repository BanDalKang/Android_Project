package com.example.mycalculator

class Calculator {
    fun calculate(operation: AbstractOperation): Double {
        return operation.operate(operation.num1, operation.num2)
    }
}