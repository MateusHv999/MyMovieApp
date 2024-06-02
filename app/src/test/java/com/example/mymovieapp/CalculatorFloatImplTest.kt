package com.example.mymovieapp

import com.example.mymovieapp.calculator.CalculatorFloatImpl
import com.example.mymovieapp.calculator.CalculatorIntImpl
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test

class CalculatorFloatImplTest {
    lateinit var calculator : CalculatorFloatImpl
    @Before
    fun setup(){
        calculator = CalculatorFloatImpl()
    }
    @Test
    fun sum_WhenReceiving2Numbers_thenResultTheirSum(){
        //Given
        val num1 = 2.0f
        val num2 = 3.0f

        //When
        val result = calculator.sum(num1, num2)

        //Then
        Truth.assertThat(result).isEqualTo(5)
    }
    @Test
    fun multiplication_WhenReceiving2Numbers_thenResultTheirSum(){
        //Given
        val num1 = 2.0f
        val num2 = 3.0f

        //When
        val result = calculator.multiplication( num1,num2)

        //Then
        Truth.assertThat(result).isEqualTo(6)
    }
}