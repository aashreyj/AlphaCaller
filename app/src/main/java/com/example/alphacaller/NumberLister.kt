package com.example.alphacaller

import android.app.Application

class NumberLister : Application() {
    var numbers: ArrayList<Double> = arrayListOf()

    fun getList(): ArrayList<Double> {
        return numbers
    }

    fun setList(list: ArrayList<Double>) {
        numbers = list
    }
}