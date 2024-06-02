package com.example.mymovieapp.calculator

interface CalculatorInterface<T> {
    fun sum (num1: T, num2: T): T
    fun multiplication(num1: T, num2: T): T
}