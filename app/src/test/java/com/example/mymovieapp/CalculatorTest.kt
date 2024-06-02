package com.example.mymovieapp

import com.example.mymovieapp.calculator.Calculator
import com.example.mymovieapp.calculator.CalculatorFloatImpl
import com.example.mymovieapp.calculator.CalculatorIntImpl
import com.example.mymovieapp.calculator.Operations
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CalculatorTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    lateinit var calculator: Calculator
    @MockK
    lateinit var calcInt: CalculatorIntImpl
    @MockK
    lateinit var calcFloat: CalculatorFloatImpl
    @Before
    fun setUp() {
        every {
            calcInt.sum(any(), any())
        }returns 0

        every {
            calcInt.multiplication(any(), any())
        }returns 0

        every {
            calcFloat.sum(any(), any())
        }returns 0f

        every {
            calcFloat.multiplication(any(), any())
        }returns 0f

        calculator = Calculator(calcInt, calcFloat)
    }

    @Test
    fun operation_whenOperationSum_AndTwoNumbersInt_thenShouldCallCalIntSum() {
        val num1 = 3
        val num2 = 6

        //Operation
        val operation = Operations.SUM

        //When
        calculator.calculate(operation, num1, num2)

        //Then
        verify(exactly = 1) {
            calcInt.sum(num1, num2)
        }
    }
    @Test
    fun operation_whenOperationMultiplication_AndTwoNumbersInt_thenShouldCallCalIntMultiplication() {
        val num1 = 3
        val num2 = 6

        //Operation
        val operation = Operations.MULTIPLICATION

        //When
        calculator.calculate(operation, num1, num2)

        //Then
        verify(exactly = 1) {
            calcInt.multiplication(num1, num2)
        }
    }
    @Test
    fun operation_whenOperationSum_AndTwoNumbersFloat_thenShouldCallCalFloatSum() {
        val num1 = 3.0f
        val num2 = 6.0f

        //Operation
        val operation = Operations.SUM

        //When
        calculator.calculate(operation, num1, num2)

        //Then
        verify(exactly = 1) {
            calcFloat.sum(num1, num2)
        }
    }
    @Test
    fun operation_whenOperationMultiplication_AndTwoNumbersFloat_thenShouldCallCalFloatMultiplication() {
        val num1 = 3.0f
        val num2 = 6.0f

        //Operation
        val operation = Operations.MULTIPLICATION

        //When
        calculator.calculate(operation, num1, num2)

        //Then
        verify(exactly = 1) {
            calcFloat.multiplication(num1, num2)
        }
    }
    @Test
    fun operation_WhenOperationSum_AndTwoNumbersDouble_thenShouldThrowException(){
        var num1 = 2.3
        var num2 = 3.0

        //Operation
        val operation = Operations.SUM

        //When
        calculator.calculate(operation, num1, num2)
    }

    @Test
    fun operation_WhenOperationMultiplication_AndTwoNumbersDouble_thenShouldThrowException(){
        var num1 = 2.3
        var num2 = 3.0

        //Operation
        val operation = Operations.MULTIPLICATION

        //When
        calculator.calculate(operation, num1, num2)
    }
}