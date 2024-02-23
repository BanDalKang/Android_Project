package com.example.mycalculator

class Calculator {
    // 더하기 연산을 수행하는 함수
    fun add(num1: Double, num2: Double): Double {
        val addOperation = AddOperation()
        return addOperation.operate(num1, num2)
    }

    // 빼기 연산을 수행하는 함수
    fun subtract(num1: Double, num2: Double): Double {
        val subtractOperation = SubtractOperation()
        return subtractOperation.operate(num1, num2)
    }

    // 곱하기 연산을 수행하는 함수
    fun multiply(num1: Double, num2: Double): Double {
        val multiplyOperation = MultiplyOperation()
        return multiplyOperation.operate(num1, num2)
    }

    // 나누기 연산을 수행하는 함수
    fun divide(num1: Double, num2: Double): Double {
        val divideOperation = DivideOperation()
        return divideOperation.operate(num1, num2)
    }
}