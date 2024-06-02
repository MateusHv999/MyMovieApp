package com.example.mymovieapp.calculator

enum class Operations {
    SUM,
    MULTIPLICATION
}

class Calculator(
    private val calcInt : CalculatorIntImpl,
    private val calcFloat : CalculatorFloatImpl)
{
    fun calculate(operation: Operations, num1:Number, num2: Number): Number {
        return if(num1 is Int && num2 is Int){
            when(operation) {
                Operations.SUM -> calcInt.sum(num1, num2)
                Operations.MULTIPLICATION -> calcInt.multiplication(num1, num2)
            }
        }else if(num1 is Float && num2 is Float){
            when(operation) {
                Operations.SUM -> calcFloat.sum(num1, num2)
                Operations.MULTIPLICATION -> calcFloat.multiplication(num1, num2)
            }
        }else{
            throw Exception("Invalid Operation")
        }
    }
}