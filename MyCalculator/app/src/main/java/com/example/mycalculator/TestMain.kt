package com.example.mycalculator

import java.util.InputMismatchException
import java.util.Scanner

fun main() {
    testMain()
}

fun testMain() {
    val scanner = Scanner(System.`in`)

    while (true) {
        println("연산자(+, -, *, /)를 입력하세요 Or 종료하려면 'exit'을 입력하세요")
        val operator = scanner.nextLine()

        if (operator == "exit") {
            println("종료합니다...")
            break
        }

        // operator가 리스트 listOf("+", "-", "*", "/") 안에 포함되어 있지 않을 때
        if (operator !in listOf("+", "-", "*", "/")) {
            println("잘못된 연산자입니다. 올바른 연산자를 입력하세요.")
            continue
        }

        println("첫 번째 숫자를 입력하세요:")
        var num1: Double
        // 숫자를 입력할 때까지 반복과 예외 처리
        while (true) {
            try {
                num1 = scanner.nextDouble()
                break
            } catch (e: InputMismatchException) {
                println("숫자를 입력하세요.")
                scanner.next() // 입력 버퍼를 비움
            }
        }

        println("두 번째 숫자를 입력하세요:")
        var num2: Double
        while (true) {
            try {
                num2 = scanner.nextDouble()
                break
            } catch (e: InputMismatchException) {
                println("숫자를 입력하세요.")
                scanner.next() // 입력 버퍼를 비움
            }
        }

        // 버퍼에 남아있는 해당 줄의 데이터를 소비(없앰)
        scanner.nextLine()

        val result = when (operator) {
            "+" -> Calculator.calculate(num1, num2, AddOperation())
            "-" -> Calculator.calculate(num1, num2, SubtractOperation())
            "*" -> Calculator.calculate(num1, num2, MultiplyOperation())
            "/" -> {
                if (num2 == 0.0) {
                    println("오류! 0으로 나눌 수 없습니다.")
                    continue
                }
                Calculator.calculate(num1, num2, DivideOperation())
            }
            else -> {
                println("잘못된 연산자입니다.")
                continue
            }
        }
        println("결과: $result")
        println()
    }
    scanner.close()
}