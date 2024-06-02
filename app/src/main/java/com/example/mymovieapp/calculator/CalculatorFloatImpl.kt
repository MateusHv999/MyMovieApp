package com.example.mymovieapp.calculator

class CalculatorFloatImpl: CalculatorInterface<Float> {
    override fun sum(num1: Float, num2: Float): Float {
        return num1 + num2
    }

    override fun multiplication(num1: Float, num2: Float): Float {
        return num1 * num2
    }
}