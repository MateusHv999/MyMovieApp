package com.example.mymovieapp.calculator

class CalculatorIntImpl: CalculatorInterface<Int> {
    override fun sum(num1: Int, num2: Int): Int {
        return num1 + num2
    }

    override fun multiplication(num1: Int, num2: Int): Int {
        return num1 * num2
    }
}