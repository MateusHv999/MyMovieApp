package com.example.mymovieapp

import com.example.mymovieapp.calculator.CalculatorIntImpl
import com.example.mymovieapp.calculator.Operations
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class CalculatorIntImplTest {
    lateinit var calculator : CalculatorIntImpl
    @Before
    fun setup(){
        calculator = CalculatorIntImpl()
    }
    @Test
    fun sum_WhenReceiving2Numbers_thenResultTheirSum(){
        //Given
        val num1 = 2
        val num2 = 3

        //When
        val result = calculator.sum(num1, num2)

        //Then
        assertThat(result).isEqualTo(5)
    }
    @Test
    fun multiplication_WhenReceiving2Numbers_thenResultTheirSum(){
        //Given
        val num1 = 2
        val num2 = 3

        //When
        val result = calculator.multiplication( num1,num2)

        //Then
        assertThat(result).isEqualTo(6)
    }
}