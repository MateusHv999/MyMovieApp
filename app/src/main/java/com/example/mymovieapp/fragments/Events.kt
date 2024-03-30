package com.example.mymovieapp.fragments

class Events<out T> (private val content: T) {
    var Handled = false
    private set
    fun getContentNotHandled(): T?{
        return if(Handled){
            null
        }else{
             Handled = true
            content
        }
    }
}